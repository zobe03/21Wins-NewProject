package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.Game;
import model.Player;
import model.HumanPlayer;
import model.MachinePlayer;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;

public class RegularGameController implements Initializable{
    @FXML
    private  Label roundLabel;
    @FXML
    private  Label playerLabel;
    @FXML
    private  Label messageLabel;
    @FXML
    private  TextField inputField;
    @FXML
    private  GridPane gridPane;
    @FXML
    protected Button backtomenu;
    @FXML
    protected Button sumButton;
    public static boolean MACHINEMODE;
    private final Player[] playersList = new Player[2];
    private final Game game = new Game();

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        roundLabel.setText("Round 1");
        messageLabel.setText("Enter a Number between 1 & 9 and press ENTER to add the Number to the Stack");

        String imagePath = "file:resources/grün.jpeg";
        Image image = new Image(imagePath);
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        gameLayout.setBackground(new Background(backgroundImage));
    }
    public static void setMachineMode() {
        MACHINEMODE = true;
        System.out.println("setMachinemode: " + MACHINEMODE);
    }
    public void addNames(){
        if (!MACHINEMODE) {
            System.out.println("Maschinemode false");
            String playerName1 = askForPlayerName("Player 1");
            playersList[0] = new HumanPlayer(playerName1);
            String playerName2 = askForPlayerName("Player 2");
            playersList[1] = new HumanPlayer(playerName2);
        } else {
            System.out.println("Maschinemode true");
            String playerName = askForPlayerName("Player 1");
            playersList[0] = new HumanPlayer(playerName);
            playersList[1] = new MachinePlayer();
        }
        playerLabel.setText(playersList[0].getName());
    }

    @FXML
    private AnchorPane gameLayout;
    private String askForPlayerName(String defaultName){
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Enter your Name");
        dialog.setHeaderText("Please enter your Name: ");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(defaultName);
    }
    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            play();
        }
    }

    @FXML
    protected void onSumSelect(){
        if (game.roundNr < 4){
            messageLabel.setText("Addition not allowed in first three rounds!");
        } else if (!game.input.isEmpty()){
            play2();
        }
        else {
            play();
        }
    }


    public void play() {
        game.playTheGame(roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }
    public void play2() {
        game.playTheGame2(roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }

}

