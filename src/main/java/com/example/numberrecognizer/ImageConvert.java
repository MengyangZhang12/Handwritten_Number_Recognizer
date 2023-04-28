package com.example.numberrecognizer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageConvert implements ImageConvertInterface{
    //Singleton design pattern
    private static ImageConvert instance = new ImageConvert();

    private ImageConvert (){}

    public static ImageConvert getInstance() {
        return instance;
    }

    @Override
    public void convert() {
        //Convert the original image to a 28*28 grayscale image
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat originalImage = Imgcodecs.imread("src/main/original.png");
            Mat dstImage = new Mat();

            Imgproc.resize(originalImage, originalImage, new Size(28, 28));
            Imgproc.cvtColor(originalImage, dstImage, Imgproc.COLOR_BGR2GRAY, 0);
            Imgcodecs.imwrite("src/main/convert.png", dstImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
