package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.text.Font;
import java.io.InputStream;

public class MainController implements Initializable {
    @FXML
    private Button highscoreButton;
    @FXML
    private Button regularGameButton;
    @FXML
    private Button machineGameButton;


    @FXML
    private Label welcomeText; // tbd

    @FXML
    public void onHighScoreButtonClick() { // Thorsten
        System.out.println("Switching to Highscore");
        SwitchingScenes.setScene(1);
    }

    @FXML
    public void onRegularGameButtonClick() { // Thorsten
        System.out.println("Switching to Regular Game");
        SwitchingScenes.setScene(2);
    }

    @FXML
    public void onMachineGameButtonClick() { // Thorsten
        System.out.println("Switching to Machine Set Difficulty");
        SwitchingScenes.setScene(3);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.getDefault(); // Fallback-Schriftart verwenden
        try {
            InputStream is = MainController.class.getResourceAsStream("/fonts/PressStart2P-vaV7.ttf");
            if (is != null) {
                font = Font.loadFont(is, 20);
            } else {
                System.err.println("Font file not found, using default font.");
            }
            welcomeText.setFont(font);
        } catch (Exception e) {
            System.err.println("Error loading font, using default font: " + e.getMessage());
            e.printStackTrace();
        }


    }
}

