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

// regularGameController organizes the elements in the scene that displays a game (either between two humans or one human and a machine)
// belongs to fxml: regularGame.fxml

public class RegularGameController implements Initializable {
    @FXML
    private Label roundLabel; // shows current round nr
    @FXML
    private Label playerLabel; // shows name of player whose turn it is
    @FXML
    private Label messageLabel; // in order to improve user friendliness; indicate game rules & guide
    @FXML
    private TextField inputField; // where the user can input a number
    @FXML
    private GridPane gridPane; // in order to create the stack
    @FXML
    protected Button backtomenu; // button to exit game and return to main menu
    @FXML
    protected Button sumButton; // button to create sum of upper two numbers
    @FXML
    protected Pane backgroundPane; // used in order to be able to load the previously set MainApplication.Background

    public static boolean MACHINEMODE; // used to set the machinemode (static to use it in MachinePlayer)
    // list contains both Players; either two humans or 0: HumanPlayer and 1: MachinePlayer
    private final Player[] playersList = new Player[2];
    // Game is the class that contains all the logic (therefore strictly separating controller and game logic)
    private final Game game = new Game();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Set fonts
        Font fontround = Font.getDefault();
        Font fontplayer = Font.getDefault();
        Font fontmessage = Font.getDefault();

        try {
            // create individual font size for each element in the regular game
            InputStream is20 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is20 != null) {
                fontround = Font.loadFont(is20, 28); // set font of round-Label
            }

            InputStream is14 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is14 != null) {
                fontplayer = Font.loadFont(is14, 20); // set font of player-label
            }

            InputStream is8 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is8 != null) {
                fontmessage = Font.loadFont(is8, 16); // set font of message label
            } else {
                System.err.println("Font file not found, using default font.");
            }
            // set fonts with different sizes according to the different elements
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

        // Set background (possible since MainController.SpaceBackground is static)
        MainController.SpaceBackground spaceBackground = new MainController.SpaceBackground(800, 800);
        // in order to be able to center the content
        AnchorPane.setTopAnchor(spaceBackground, 0.0);
        AnchorPane.setLeftAnchor(spaceBackground, 0.0);
        AnchorPane.setBottomAnchor(spaceBackground, 0.0);
        AnchorPane.setRightAnchor(spaceBackground, 0.0);
        backgroundPane.getChildren().add(spaceBackground);
        backgroundPane.toBack(); // Bring the background to the back so that elements are visible

        // set initial values of roundlabel and messagelabel
        roundLabel.setText("Round 1");
        messageLabel.setText("Enter a number between 1 & 9 \nand press ENTER to add the number to the stack");
    }


    public static void setMachineMode() {
        MACHINEMODE = true;
        System.out.println("setMachinemode: " + MACHINEMODE);
    }

    public static void setHumanMode() {
        MACHINEMODE = false;
        System.out.println("setMachinemode: " + MACHINEMODE);
    }

    public void addNames() {
        if (!MACHINEMODE) { // if it is a regular game between two humans (no machine)
            System.out.println("Machinemode false");
            // ask for both player names and create for each a new HumanPlayer object to store it in playersList
            String playerName1 = askForPlayerName("Player 1");
            playersList[0] = new HumanPlayer(playerName1);
            String playerName2 = askForPlayerName("Player 2");
            playersList[1] = new HumanPlayer(playerName2);
        } else { // if the game is between a human and the machine
            System.out.println("Machinemode true");
            // only ask for the name of one player
            String playerName = askForPlayerName("Player 1");
            // store a HumanPlayer and a MachinePlayer in playersList<Player> -> polymorphism
            playersList[0] = new HumanPlayer(playerName); // create and save the human player with the given name
            playersList[1] = new MachinePlayer(); // create and save MachinePlayer (always gets the second turn in a game)
        }
        playerLabel.setText(playersList[0].getName()); // first player starts -> indicate at current player label
        playersList[0].getScoreTracker().startTimer(); // start tracking the time for the first player (to calculate the highscore at the end)
    }

    private String askForPlayerName(String defaultName) {
        // asking the user to enter his name (in order to create a new player object)
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Enter your Name");
        dialog.setHeaderText("Please enter your Name: ");
        dialog.setContentText("Name:");
        // Optional: variable of object that holds either a value (here: a String), or null
        Optional<String> result = dialog.showAndWait(); // waiting for the user to enter his name
        return result.orElse(defaultName); // if result is null, return the default name
    }

    // if the user decides to click back to the main menu and abort the game
    @FXML
    protected void setBacktomenu() {
        setHumanMode(); // disable the machine player so he stops entering values
        SwitchingScenes.setScene(0); // switch back to main menu
    }

    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) { // as soon as the user hits enter,
            Game.setEnter(); // game gets notified
            play(); // the game is being started
        }
    }

    @FXML
    protected void onSumSelect() {
        Game.setSum();
        play();
    }

    // the game starts (connection between controller class and game logic class) by passing all important gui elements + playerslist
    public void play() {
        game.askForInput(roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }
}
