package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import com.example.model.MachinePlayer;
import javafx.scene.text.Font;

import java.io.InputStream;
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
    protected RadioButton expert;
    @FXML
    protected RadioButton impossible;

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
    protected void onExpertSelected() {
        MachinePlayer.setDifficulty(4);
        System.out.println("on expert selected");
    }
    @FXML
    protected void onImpossibleSelected(){
        MachinePlayer.setDifficulty(5);
        System.out.println("on impossible selected");
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
        try {
            InputStream is = MainController.class.getResourceAsStream("/fonts/PressStart2P-vaV7.ttf");
            Font fontmachine = Font.loadFont(is, 12);

            next.setFont(fontmachine);
            backtomenu.setFont(fontmachine);
            easy.setFont(fontmachine);
            medium.setFont(fontmachine);
            hard.setFont(fontmachine);
            expert.setFont(fontmachine);
            impossible.setFont(fontmachine);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
