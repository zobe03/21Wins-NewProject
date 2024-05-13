package com.example.model;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import com.example.model.leaderboard.ScoreTracker;
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

import static com.example.zoe21.RegularGameController.setHumanMode;


public class Game {
    public int roundNr = 1;
    private int currentPlayer = 1;
    private final Stack<Integer> gameStack = new Stack<>();
    private boolean stop = false;
    private static boolean SUM;
    private static boolean ENTER;
    public String input;

    public static LeaderBoard leaderBoard = new LeaderBoard();

    public Game(LeaderBoard leaderBoard) {
        Game.leaderBoard = leaderBoard;
    }

    public static void setSum() {
        SUM = true;
        System.out.println("SUM: " + SUM);
    }

    public static void setEnter() {
        ENTER = true;
        System.out.println("Enter: " + ENTER);
    }

    public void askForInput(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        System.out.println(Arrays.toString(playersList));

        if (playersList[0] instanceof HumanPlayer) {
            input = inputField.getText();
            final int[] inputNr = {stringToInteger(input, messageLabel)};
            inputValid = playTheGame(inputNr[0], roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            if (inputValid) {
                if (playersList[1] instanceof MachinePlayer) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
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

    private int stringToInteger(String input, Label messageLabel) {
        int inputNr = 0;
        try {
            if (!input.isEmpty()) {
                inputNr = Math.abs(Integer.parseInt(input));
                messageLabel.setText("Entered Number: " + inputNr);
            }
        } catch(NumberFormatException e){
            messageLabel.setText("Input invalid! Please enter a number between 1 & 9!");
        }
        return inputNr;
    }




    private boolean playTheGame(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {

        System.out.println("Play 1");
        boolean inputValid = false;
        if(SUM) {
            System.out.println("Sum");
            if (roundNr < 4) {
                messageLabel.setText("Addition not allowed in first three rounds!");
            } else {
                if (inputNr == 0) {
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    int number = firstTopNr + secTopNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                } else {
                    int number = gameStack.pop() + inputNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                }
            }
        }
        else {
            System.out.println("Enter");
            if (inputNr < 10 && inputNr > 0) {
                System.out.println("0<x<10");
                if (gameStack.size() >= 6) {
                    int number = gameStack.pop() + inputNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;

                }
                else {
                    gameStack.push(inputNr);
                    updateGrid(gridPane);
                    inputValid = true;
                }
            }
            else if (inputNr == 0) {
                if (roundNr < 4) {
                    messageLabel.setText("Addition not allowed in first three rounds!");
                }
                else {
                    if (gameStack.size() > 1) {
                        int firstTopNr = gameStack.pop();
                        int secTopNr = gameStack.pop();
                        int number = firstTopNr + secTopNr;
                        gameStack.push(number);
                        updateGrid(gridPane);
                        inputValid = true;
                    }
                    else {
                        messageLabel.setText("Not enough numbers in Stack!");
                    }
                }
            }
            else {
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

        // Starte von der untersten Reihe des Grids
        int gridRowIndex = gridPane.getRowCount() - 1;

        // Index für das Iterieren durch den Stapel
        int stackIndex = stackSize -1;

        // Durchlaufe das GridPane von unten nach oben und aktualisiere die Labels entsprechend
        while (gridRowIndex >= 0) {
            Label label = (Label) gridPane.getChildren().get(gridRowIndex);
            Font fontstack = Font.getDefault();
            try {
                InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
                if (is != null) {
                    fontstack = Font.loadFont(is, 12);
                } else {
                    System.err.println("Font file not found, using default font.");
                }
                label.setFont(fontstack);
            }catch (Exception e){
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

    private void checkWinner(Player[] playersList){
        if (gameStack.peek() >= 21) {
            stop=true;
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
                SwitchingScenes.setScene(0);
                RegularGameController.setHumanMode(); // Setze MACHINEMODE auf false
            }
        });
    }


    private void switchPlayer(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, Player[] playersList){
        if(!stop){
            int nrPlayers = 2;
            currentPlayer = (currentPlayer % nrPlayers) + 1;
            Player currentPlayerObject = playersList[currentPlayer - 1];
            if (currentPlayer == 1) {
                roundNr++;
                roundLabel.setText("Round " + roundNr);
                if (roundNr == 4){
                    messageLabel.setText("Addition is now allowed!");
                }
            }
            playerLabel.setText(currentPlayerObject.getName());
            inputField.clear();
            timeTracking(currentPlayerObject, playersList, currentPlayer);
        } else {
            Player winningPlayer = playersList[currentPlayer - 1];
            if (winningPlayer != null) {
                ScoreTracker scoreTracker = winningPlayer.getScoreTracker();
                if (scoreTracker != null) {
                    scoreTracker.stopTimer();
                    if (winningPlayer instanceof HumanPlayer) {
                        LeaderBoardItem leaderBoardItem = scoreTracker.toLeaderBoardItem(winningPlayer.getName());
                        System.out.println(winningPlayer.getName() + " won with a score of: " + leaderBoardItem.getFormatedScore());
                        leaderBoard.addItem(leaderBoardItem);
                    }
                }
            }
        }
    }

    private static void timeTracking(Player currentPlayerObject, Player[] playersList, int currentPlayer) { // Parameter definieren
        if(currentPlayer == 1) {
            playersList[1].getScoreTracker().stopTimer();
            System.out.println("Player1 :" + playersList[1].getScoreTracker().time);
        }else{
            playersList[0].getScoreTracker().stopTimer();
            System.out.println("Player1 :" + playersList[0].getScoreTracker().time);
        }
        currentPlayerObject.getScoreTracker().startTimer();
    }
}


