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

package com.ntc.visd;

import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nghiatc
 * @since Jan 15, 2021
 */
public class ViSD {
    private static final Logger log = LoggerFactory.getLogger(ViSD.class);
    private String pathModelSD = "models/sd/visd-latest.bin";
    private SentenceDetectorME viSdME;

    public ViSD() throws IOException {
        // Load model
        InputStream streamModel = getResourceAsStream(pathModelSD);
        SentenceModel model = new SentenceModel(streamModel);
        viSdME = new SentenceDetectorME(model);
    }

    public ViSD(String pathModel) throws IOException {
        if (pathModel == null || pathModel.isEmpty()) {
            throw new ExceptionInInitializerError("Path model is NULL or empty.");
        }
        this.pathModelSD = pathModel;
        // Load model
        InputStream streamModel = getResourceAsStream(pathModelSD);
        SentenceModel model = new SentenceModel(streamModel);
        viSdME = new SentenceDetectorME(model);
    }

    public String getPathModelSD() {
        return pathModelSD;
    }

    public SentenceDetectorME getViSdME() {
        return viSdME;
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

    public String[] visd(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return viSdME.sentDetect(s);
    }
    
}
