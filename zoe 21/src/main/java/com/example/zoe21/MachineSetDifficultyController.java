package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.MachinePlayer;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.zoe21.RegularGameController.MACHINEMODE;

public class MachineSetDifficultyController implements Initializable {
    private MachinePlayer machinePlayer = new MachinePlayer();
    @FXML
    protected Button next;
    @FXML
    protected Button backtomenu;
    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @FXML
    protected void onEasySelect(){
        machinePlayer.setDifficulty(1);
    }
    @FXML
    protected void onMediumSelect(){
        machinePlayer.setDifficulty(2);
    }
    @FXML
    protected void onHardSelected(){
        machinePlayer.setDifficulty(3);
    }
    @FXML
    protected void nextToRegularGame(){
        SwitchingScenes.setScene(2);
        MACHINEMODE = true;;
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
