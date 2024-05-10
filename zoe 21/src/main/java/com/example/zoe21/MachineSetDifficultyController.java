package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import com.example.model.MachinePlayer;

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
        System.out.println("on easy selected");
    }
    @FXML
    protected void onMediumSelect(){
        MachinePlayer.setDifficulty(2);
        System.out.println("on medium selected");
    }
    @FXML
    protected void onHardSelected(){
        MachinePlayer.setDifficulty(3);
        System.out.println("on hard selected");
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
