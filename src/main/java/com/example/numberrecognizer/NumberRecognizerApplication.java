package com.example.numberrecognizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NumberRecognizerApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NumberRecognizerApplication.class.getResource("recognizer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 450);
        stage.setTitle("Handwritten Number Recognizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}