package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

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
    private AnchorPane gameLayout;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String imagePath = "file:resources/bücher3.jpeg";
        Image image = new Image(imagePath);
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        gameLayout.setBackground(background);
    }

}