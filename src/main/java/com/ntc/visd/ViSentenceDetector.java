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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author nghiatc
 * @since Jan 13, 2021
 */
public class ViSentenceDetector {

    public void train() throws FileNotFoundException, IOException {
        // File to save the model ViSD
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String date = sdf.format(new Date());
        String modelPath = "model/visd-" + date + ".bin";
        File modelFile = new File(modelPath);

        // Training data: training-vn.txt
        File dataTrain = new File("data/train/training-vn.txt");
        InputStreamFactory in = new MarkableFileInputStreamFactory(dataTrain);

        // Parameters used by machine learning algorithm, Maxent, to train its weights
        TrainingParameters mlParams = TrainingParameters.defaultParams();

        // Train model
        ObjectStream<String> lineStream = new PlainTextByLineStream(in, StandardCharsets.UTF_8);
        SentenceModel model;
        try (ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream)) {
            model = SentenceDetectorME.train("vi", sampleStream, true, null, mlParams);
        }
        
        // Save model to file
        try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(modelFile))) {
            model.serialize(modelOut);
        }
        System.out.println("Done!");
    }
    
    public void test() throws IOException {
        System.out.println("Load model visd...");
        // File to save the model ViSD
        File modelFile = new File("model/visd-latest.bin");
        // Load model
        SentenceModel model = new SentenceModel(modelFile);
        SentenceDetectorME visd = new SentenceDetectorME(model);
        // Run test
        // Test data: test.txt
        System.out.println("Run test...");
        String s = readAllFileToString("data/test/test.txt");
        System.out.println("========== Input ==========");
        System.out.println(s);
        String[] sents = visd.sentDetect(s);
        System.out.println("========== Output ==========");
        for(int i=0;i<sents.length;i++){
            System.out.println("Sentence["+(i+1)+"]: "+sents[i]);
        }
    }
    
    public String readAllFileToString(String path) {
        String content = "";
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            // UNIX \n, WIndows \r\n
            content = lines.collect(Collectors.joining(System.lineSeparator()));
            // File to List
            //List<String> list = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    public List<String> readAllFileToListString(String path) {
        List<String> content = null;
        try {
            content = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    public void writeListStringToFile(String filePath, List<String> lines) {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
