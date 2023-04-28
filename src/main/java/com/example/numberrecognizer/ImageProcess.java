package com.example.numberrecognizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;

public class ImageProcess {

    private static final String greyImagePath = "src/main/convert.png";
    private static float[][] rgbArray;

/*  Read an image file as stream from the specific path, and transform it to a buffered image,
    which is composed of a ColorModel and a raster of image data.*/

    private static BufferedImage getImage() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File( greyImagePath )));
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Transform a BufferedImage to 2-dimensional float array
    public static float[][] convertImageToArray() {
        BufferedImage image = getImage();
        //Computes and returns a region of the BufferedImage.
        Raster graphic = image.getData();
        //Get the image's width and height
        int width = image.getWidth();
        int height = image.getHeight();
        //Get the image's pixels for each point
        int[] pixels = new int[width * height];
        pixels = graphic.getPixels(0, 0, width, height, pixels);
        //Check if image is blank
        boolean valid = false;
        for (int pixel : pixels) {
            if (pixel != 255) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return null;
        }
        /* Convert the int array to a float array and make each pixel's value is between 0 and 1,
        while 0 is pure white and 1 is pure black */
        float[] temp = new float[width * height];
        for(int i = 0; i < pixels.length; i++) {
            temp[i] = (255-pixels[i])*1.0f/255.0f;
        }
        rgbArray = new float[1][width*height]; //Convert float array to 2-dimensional float array
        for(int i = 0; i < width*height; i++) {
            rgbArray[0][i] = temp[i];
        }
        return rgbArray;
    }

}
