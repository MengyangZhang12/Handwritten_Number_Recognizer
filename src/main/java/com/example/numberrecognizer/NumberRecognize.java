package com.example.numberrecognizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.tensorflow.*;


public class NumberRecognize {

        private static float[][] target;
        private static int result;

        // Read a file as a byte array
        private static byte[] readAllBytes(Path path) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static int getResult() {

            //Load the model which has been trained before
            byte[] graphDef = readAllBytes(Paths.get("src/grf.pb"));
            Graph graph = new Graph();
            graph.importGraphDef(graphDef);

            //Create a session to handle input and output of the model
            Session session = new Session(graph);

            //Get the 2-dimensional float array of the target image
            target = ImageProcess.convertImageToArray();
            //Check if image is blank
            if (target == null) {
                return -1;
            }
            //Create input and get the result out of the model
            Tensor inputTarget = Tensor.create(target);
            List<Tensor<?>> out = session.runner().feed("input_x", inputTarget).fetch("final_result").run();

            /*The result out is a list of possibilities for each number from 0 to 9,
            and the number which has the highest possibility is the recognized result.*/
            for (Tensor tensor : out) {
                float[][] matrix = new float[1][10];
                tensor.copyTo(matrix);
                float max = 0f;
                for (int i = 0; i < matrix[0].length; i++) {
                    if(max < matrix[0][i]) {
                        max = matrix[0][i];
                        result = i;
                    }
                }
            }
            return result;
        }

    }

