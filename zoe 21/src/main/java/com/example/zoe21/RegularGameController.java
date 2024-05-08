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
    private Label roundLabel;
    @FXML
    private Label playerLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField inputField;
    @FXML
    private Button sumButton;
    @FXML
    private GridPane gridPane;
    @FXML
    protected Button backtomenu;
    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }

    public static boolean MACHINEMODE;

    private Player[] playersList = new Player[2]; //0 und 1
    private Game game = new Game();

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        roundLabel.setText("Round 1");
        messageLabel.setText("Enter a Number between 1 & 9 and press ENTER to add the Number to the Stack");

        // Laden Sie das Bild
        String imagePath = "file:resources/grün.jpeg";// Bild oder Gif im ressource hochladen
        Image image = new Image(imagePath);
        // Erstellen Sie ein BackgroundImage-Objekt
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // Setzen Sie das Hintergrundbild
        gameLayout.setBackground(new Background(backgroundImage));
    }
    public void addNames(){
        if (MACHINEMODE) {
            String playerName = askForPlayerName("Player 1");
            playersList[0] = new HumanPlayer(playerName);
            playersList[1] = new MachinePlayer();
        } else {
            String playerName1 = askForPlayerName("Player 1");
            playersList[0] = new HumanPlayer(playerName1);
            String playerName2 = askForPlayerName("Player 2");
            playersList[1] = new HumanPlayer(playerName2);
        }
        playerLabel.setText(playersList[0].getName());
    }

    @FXML
    private AnchorPane gameLayout;
    private void play() {
        if (MACHINEMODE) {
            game.playWithMachine( roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
        } else {
            game.playWithPlayer( roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
        }
    }
    private String askForPlayerName(String defaultName){
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Spielername eingeben");
        dialog.setHeaderText("Bitte geben Sie Ihren Namen ein:");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(defaultName);
    }

    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            play();
        }
    }

    @FXML
    protected void onSumSelect(){
        play();
    }

}

