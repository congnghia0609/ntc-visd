/*
 * Copyright 2021 nghiatc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ntc.jvertx;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nghiatc
 * @since Jan 14, 2021
 */
public class SdService {
    private static final Logger log = LoggerFactory.getLogger(SdService.class);
    private String pathModelSD = "models/sd/visd-latest.bin";
    private SentenceDetectorME viSdME;

    public SdService() throws IOException {
        InputStream streamModel = getResourceAsStream(pathModelSD);
        // Load model
        SentenceModel model = new SentenceModel(streamModel);
        viSdME = new SentenceDetectorME(model);
    }
    
    public InputStream getResourceAsStream(String name) {
        InputStream in = null;
        ClassLoader loader = getClass().getClassLoader();
        if (loader != null) {
            in = loader.getResourceAsStream(name);
        } else {
            in = ClassLoader.getSystemResourceAsStream(name);
        }
        return in;
    }
    
    public void visd(Message<JsonObject> message) {
        try {
            JsonObject params = message.body();
            String s = params.getString("s", "");
            String[] sents = viSdME.sentDetect(s);
            JsonArray ast = new JsonArray();
            if (sents != null && sents.length > 0) {
                for (String st : sents) {
                    ast.add(st);
                }
            }
            
            // 1. Render data
            JsonObject ls = new JsonObject();
            ls.put("ls", ast);
            JsonObject resp = new JsonObject();
            resp.put("data", ls);
            message.reply(resp);
        } catch (Exception e) {
            log.error("visd: " + e.getMessage(), e);
        }
    }
}
