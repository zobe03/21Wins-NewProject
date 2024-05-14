package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import com.example.model.Game;
import com.example.model.Player;
import com.example.model.HumanPlayer;
import com.example.model.MachinePlayer;
import javafx.scene.text.Font;


import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;

public class RegularGameController implements Initializable {
    @FXML
    private Label roundLabel;
    @FXML
    private Label playerLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField inputField;
    @FXML
    private GridPane gridPane;
    @FXML
    protected Button backtomenu;
    @FXML
    protected Button sumButton;
    public static boolean MACHINEMODE;
    private final Player[] playersList = new Player[2];
    private final Game game = new Game();


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Font fontround = Font.getDefault(); // Fallback-Schriftart verwenden
        Font fontplayer = Font.getDefault();
        Font fontmessage = Font.getDefault();
        try {
            InputStream is20 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is20 != null) {
                fontround = Font.loadFont(is20, 20);
            }

            InputStream is14 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is14 != null) {
                fontplayer = Font.loadFont(is14, 14);
            }

            InputStream is8 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is8 != null) {
                fontmessage = Font.loadFont(is8, 8);
            }
            else{
                System.err.println("Font file not found, using default font.");
            }
            roundLabel.setFont(fontround);
            playerLabel.setFont(fontplayer);
            messageLabel.setFont(fontmessage);
            inputField.setFont(fontround);
            backtomenu.setFont(fontplayer);
            sumButton.setFont(fontplayer);
        } catch (Exception e) {
            System.err.println("Error loading font, using default font: " + e.getMessage());
            e.printStackTrace();
        }

        roundLabel.setText("Round 1");
        messageLabel.setText("Enter a Number between 1 & 9 \nand press ENTER to add the Number to the Stack");

        // String imagePath = "file:resources/grün.jpeg";
        // Image image = new Image(imagePath);
        // BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, false, true);
        // BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // gameLayout.setBackground(new Background(backgroundImage));
    }

    public static void setMachineMode() {
        MACHINEMODE = true;
        System.out.println("setMachinemode: " + MACHINEMODE);
    }

    public void addNames() {
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
        playersList[0].getScoreTracker().startTimer();

    }

    @FXML
    private AnchorPane gameLayout;

    private String askForPlayerName(String defaultName) {
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Enter your Name");
        dialog.setHeaderText("Please enter your Name: ");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(defaultName);
    }

    @FXML
    protected void setBacktomenu() {
        SwitchingScenes.setScene(0);
    }

    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Game.setEnter();
            play();
        }
    }

    @FXML
    protected void onSumSelect() {
        Game.setSum();
        play();

    }


    public void play() {
        game.askForInput(roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }
}



