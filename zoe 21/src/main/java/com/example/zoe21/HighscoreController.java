package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.MachinePlayer;
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class HighscoreController implements Initializable {
    @FXML
    protected Button backtomenu;

    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @FXML
    private AnchorPane gameLayout;
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        String imagePath = "file:resources/grün.jpeg";// Bild oder Gif im ressource hochladen
        Image image = new Image(imagePath);
        // Erstellen Sie ein BackgroundImage-Objekt
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // Setzen Sie das Hintergrundbild
        gameLayout.setBackground(new Background(backgroundImage));
    }
}
