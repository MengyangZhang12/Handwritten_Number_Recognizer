package com.example.numberrecognizer;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NumberRecognizerController {

    @FXML
    private Canvas drawPad;

    @FXML
    private Label result;

    @FXML
    void pathBegin(MouseEvent event) {
        //Set new start for every single stroke
        GraphicsContext drawPen = drawPad.getGraphicsContext2D();
        drawPen.beginPath();
        drawPen.moveTo(event.getX(), event.getY());
    }

    @FXML
    void pathLine(MouseEvent event) {
        //Draw the lines with mouse drag
        GraphicsContext drawPen = drawPad.getGraphicsContext2D();
        drawPen.setLineWidth(10.0);
        drawPen.setStroke(Color.STEELBLUE);
        drawPen.lineTo(event.getX(), event.getY());
        drawPen.stroke();
    }

    @FXML
    void drawPadClear(MouseEvent event) {
        //Clear the canvas and history result
        GraphicsContext drawClear = drawPad.getGraphicsContext2D();
        drawClear.clearRect(0,0,260,260);
        result.setText("");
    }

    void drawPadSave() {
        //Save the drawing on the canvas as a png image
        WritableImage image = drawPad.snapshot(new SnapshotParameters(), null);
        File file = new File("src/main/original.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
            result.setText("Save failed.");
        }
    }

    @FXML
    void numberRecognize(MouseEvent event) {
        int recognizeResult;
        //Save image
        drawPadSave();
        //Singleton design pattern
        //Convert to grey image
        ImageConvert.getInstance().convert();
        //Get recognize result and display
        try {
            recognizeResult = NumberRecognize.getResult();
            //Check if image is blank
            if (recognizeResult < 0) {
                result.setText("Invalid");
            } else {
                result.setText(recognizeResult+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setText("Recognize failed.");
        }
    }
}
