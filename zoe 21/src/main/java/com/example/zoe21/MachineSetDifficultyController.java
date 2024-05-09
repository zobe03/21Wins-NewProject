package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.MachinePlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MachineSetDifficultyController implements Initializable {
    @FXML
    protected Button next;
    @FXML
    protected Button backtomenu;
    @FXML
    protected RadioButton easy;
    @FXML
    protected RadioButton medium;
    @FXML
    protected RadioButton hard;

    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @FXML
    protected void onEasySelect(){
        MachinePlayer.setDifficulty(1);
    }
    @FXML
    protected void onMediumSelect(){
        MachinePlayer.setDifficulty(2);
    }
    @FXML
    protected void onHardSelected(){
        MachinePlayer.setDifficulty(3);
    }
    @FXML
    protected void nextToRegularGame(){
        RegularGameController.setMachineMode();
        System.out.println("next to regular game2");
        SwitchingScenes.setScene(2);
    }


    @FXML
    private AnchorPane gameLayout;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }
}
