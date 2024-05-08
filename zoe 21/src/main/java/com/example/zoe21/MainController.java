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

    protected void switchToHighscoreScene() {
        SwitchingScenes.setScene(1);
    }

    protected void switchToRegularGameScene() {
        SwitchingScenes.setScene(2);
    }

    protected void switchToMachineDifficultyScene() {
        SwitchingScenes.setScene(3);
    }

    @FXML
    private AnchorPane gameLayout;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String imagePath = "file:resources/grün.jpeg";// Bild oder Gif im ressource hochladen
        Image image = new Image(imagePath);
        // Erstellen Sie ein BackgroundImage-Objekt
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // Setzen Sie das Hintergrundbild
        gameLayout.setBackground(new Background(backgroundImage));
    }


}