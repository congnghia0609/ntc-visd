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

package com.ntc.app;

import com.ntc.visd.ViSentenceDetector;
import io.vertx.core.Launcher;

/**
 *
 * @author nghiatc
 * @since Jan 13, 2021
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // 1. Training
            //ViSentenceDetector visd = new ViSentenceDetector();
            //visd.train();
            
            // 2. Test
            //ViSentenceDetector visd = new ViSentenceDetector();
            //visd.test();
            
            // 3. Start VertX Http Server
            Launcher.executeCommand("run", "com.ntc.jvertx.MainVerticle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
