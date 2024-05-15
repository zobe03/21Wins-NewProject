package com.example.model;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import com.example.model.leaderboard.ScoreTracker;
import com.example.model.leaderboard.StaticLeaderBoard;
import com.example.zoe21.MainController;
import com.example.zoe21.RegularGameController;
import com.example.zoe21.SwitchingScenes;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

/* Game is the class that contains the logic of the game; it was created in order to
differentiate between the controller class 'RegularGameController' and the main game logic (as described in the task)
(alternative: implementing all methods in the controller class)
 */
public class Game {
    public int roundNr = 1;
    private int currentPlayer = 1; // can either be 1 or 2 for user friendliness (0 and 1 in the playersList)
    private final Stack<Integer> gameStack = new Stack<>(); // stack that holds the numbers that are being entered
    private boolean stop = false; // to stop the game when there is a winner
    private static boolean SUM; // to know whether the sum button has been pressed
    private static boolean ENTER;
    public String input; // to store the input of the user (usually the number if entered correctly)

    private final LeaderBoard leaderBoard; // final variable of leaderboard to save highscores

    // game constructor associates leaderBoard variable with current version of leaderboard (containing information from past games)
    public Game() {
        leaderBoard = StaticLeaderBoard.getLeaderBoard();
    }

    public static void setSum() { // set sum true if it has been selected
        SUM = true;
        System.out.println("SUM: " + SUM);
    }

    public static void setEnter() { // if the user has pressed enter, set the class variable to true
        ENTER = true;
        System.out.println("Enter: " + ENTER);
    }

    // 'RegularGameController' uses this method to pass all necessary arguments (parameters) to the Game class
    // (connection between controller/GUI and logic class 'Game')
    public void askForInput(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        System.out.println(Arrays.toString(playersList));

        if (playersList[0] instanceof HumanPlayer) {
            input = inputField.getText();
            final int[] inputNr = {stringToInteger(input, messageLabel)};
            inputValid = playTheGame(inputNr[0], roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            if (!stop) { // if player has won
                if (inputValid) {
                    if (playersList[1] instanceof MachinePlayer) {
                        PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); // simulate 1.5 secs waiting time of machine player
                        delay.setOnFinished(event -> {
                            int machineMove = MachinePlayer.makeMove(roundNr, gameStack);
                            inputField.setText(String.valueOf(machineMove));
                            input = String.valueOf(machineMove);
                            System.out.println("Machine number: " + input);
                            inputNr[0] = stringToInteger(input, messageLabel);
                            playTheGame(inputNr[0], roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
                        });
                        delay.play();
                    }
                }
            }
        }
    }

    private int stringToInteger(String input, Label messageLabel) {
        int inputNr = 0;
        try {
            if (!input.isEmpty()) {
                inputNr = Math.abs(Integer.parseInt(input));
                messageLabel.setText("Entered Number: " + inputNr);
            }
        } catch (NumberFormatException e) { // when a user enters a symbol that is not a number
            messageLabel.setText("Input invalid! Please enter a number between 1 & 9!");
            inputNr = -1; // default wrong (handled in playTheGame)
        }
        return inputNr;
    }


    private boolean playTheGame(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {

        boolean inputValid = false;
        if (SUM) { // if sum is selected
            System.out.println("Sum");
            if (roundNr < 4) { // addition is allowed at 4th round
                messageLabel.setText("Addition not allowed in first three rounds!");
            } else {
                if (inputNr == 0) { // 0 means create sum of two upper nrs
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    int number = firstTopNr + secTopNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                } else if (inputNr == -1) { // handling exception when input is not a number (NumberFormatException)
                    messageLabel.setText("Invalid input! Please enter a number between 1-9!");
                } else { // if the entered nr should be merged with upper nr
                    int number = gameStack.pop() + inputNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                }
            }
        } else { // if sum has not been pressed
            System.out.println("Enter");
            if (inputNr < 10 && inputNr > 0) {
                System.out.println("0<x<10");
                if (gameStack.size() >= 6) { // to limit the gamestack size to 6 elements
                    int number = gameStack.pop() + inputNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;

                } else { // if the gamestack has less than 6 nrs
                    gameStack.push(inputNr);
                    updateGrid(gridPane);
                    inputValid = true;
                }
            } else if (inputNr == 0) {
                if (roundNr < 4) {
                    messageLabel.setText("Addition not allowed in first three rounds!");
                } else { // addition is allowed after round 4
                    if (gameStack.size() > 1) {
                        int firstTopNr = gameStack.pop();
                        int secTopNr = gameStack.pop();
                        int number = firstTopNr + secTopNr;
                        gameStack.push(number);
                        updateGrid(gridPane);
                        inputValid = true;
                    } else { // sum can only be created if there are two nrs in stack min.
                        messageLabel.setText("Not enough numbers in Stack!");
                    }
                }
            } else { // error message
                messageLabel.setText("Invalid input, Please enter a number between 1-9.");
            }
        }
        if (inputValid) {
            checkWinner(playersList);
            switchPlayer(roundLabel, playerLabel, messageLabel, inputField, playersList);
            inputField.clear();
            if (playersList[currentPlayer - 1].getScoreTracker().isTimerRunning()) {
                playersList[currentPlayer - 1].getScoreTracker().incrementMoves();
            }
        }
        ENTER = false;
        SUM = false;
        return inputValid;
    }

    private void updateGrid(GridPane gridPane) {
        int stackSize = gameStack.size();

        int gridRowIndex = gridPane.getRowCount() - 1;

        int stackIndex = stackSize - 1;

        while (gridRowIndex >= 0) {
            Label label = (Label) gridPane.getChildren().get(gridRowIndex);
            Font fontstack = Font.getDefault();
            try {
                InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
                if (is != null) {
                    fontstack = Font.loadFont(is, 36);
                } else {
                    System.err.println("Font file not found, using default font.");
                }
                label.setFont(fontstack);
            } catch (Exception e) {
                System.err.println("Error loading font, using default font: " + e.getMessage());
                e.printStackTrace();
            }

            if (stackIndex >= 0) {
                // Es gibt noch Werte im Stapel, aktualisiere das Label entsprechend
                label.setText(String.valueOf(gameStack.get(stackIndex)));

            } else {
                // Der Stapel hat keine weiteren Werte, setze das Label auf 0
                label.setText(" ");
            }

            // Gehe eine Zeile nach oben im GridPane und dekrementiere den Index für den Stapel
            gridRowIndex--;
            stackIndex--;
        }

    }

    private void checkWinner(Player[] playersList) {
        if (gameStack.peek() >= 21) {
            stop = true;
            Player winningPlayer = playersList[currentPlayer - 1];
            String winnerName = winningPlayer.getName();
            showWinnerPopup(winnerName);
        }
    }

    private void showWinnerPopup(String winnerName) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("The winner is " + winnerName + "!");
            alert.setContentText("Click 'Next' to return to the main menu.");

            ButtonType nextButtonType = new ButtonType("Next", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(nextButtonType);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == nextButtonType) {
                SwitchingScenes.setScene(0); // return to main menu scene
                RegularGameController.setHumanMode(); // set machinemode on false so that next game does not start in machine mode (unless previously selected)
            }
        });
    }


    private void switchPlayer(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, Player[] playersList) {
        if (!stop) {
            int nrPlayers = 2;
            currentPlayer = (currentPlayer % nrPlayers) + 1;
            Player currentPlayerObject = playersList[currentPlayer - 1]; // subtract 1 to get to either 0 or 1 (not 1 or 2)
            if (currentPlayer == 1) {
                roundNr++; // go to next round
                roundLabel.setText("Round " + roundNr);
                if (roundNr == 4) { // starting from round 4, users are allowed to use addition and therefore the sum button
                    messageLabel.setText("Addition is now allowed!");
                }
            }
            playerLabel.setText(currentPlayerObject.getName()); // indicate whose turn it is
            inputField.clear();
            timeTracking(currentPlayerObject, playersList, currentPlayer);
        } else {
            Player winningPlayer = playersList[currentPlayer - 1]; // identify the winner
            if (winningPlayer != null) {
                ScoreTracker scoreTracker = winningPlayer.getScoreTracker();
                if (scoreTracker != null) {
                    scoreTracker.stopTimer(); // stop tracking the time of the winner
                    if (winningPlayer instanceof HumanPlayer) { // if the user (a human) has won
                        // add his name and scoring info into the leaderboard
                        LeaderBoardItem leaderBoardItem = scoreTracker.toLeaderBoardItem(winningPlayer.getName());
                        System.out.println(winningPlayer.getName() + " won with a score of: " + leaderBoardItem.getFormattedScore());
                        leaderBoard.addItem(leaderBoardItem);
                    }
                }
            }
        }
    }

    // stops the time tracking of the current player (and print the respective time)
    private static void timeTracking(Player currentPlayerObject, Player[] playersList, int currentPlayer) {
        if (currentPlayer == 1) {
            playersList[1].getScoreTracker().stopTimer();
            System.out.println("Player2 :" + playersList[1].getScoreTracker().time);
        } else {
            playersList[0].getScoreTracker().stopTimer();
            System.out.println("Player1 :" + playersList[0].getScoreTracker().time);
        }
        currentPlayerObject.getScoreTracker().startTimer();
    }
}


