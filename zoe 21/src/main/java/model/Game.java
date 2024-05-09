package model;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.Stack;

public class Game {
    private int roundNr = 1;
    private int currentPlayer = 1;
    private final Stack<Integer> gameStack = new Stack<>();
    private boolean stop = false;
    public static boolean ENTER;
    public static boolean SUM;

    public void playWithPlayer(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        String input = inputField.getText();
        playTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }

    public void playWithMachine(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        String input;
        Player currentPlayerObject = playersList[currentPlayer - 1];
        if (currentPlayerObject instanceof MachinePlayer) {
            int machineMove = currentPlayerObject.makeMove(roundNr, gameStack);
            inputField.setText(String.valueOf(machineMove));
            input = String.valueOf(machineMove);
        } else {
            input = inputField.getText();
        }
        playTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }

    public void playTheGame(String input, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        int inputNr;
        //nichts → 0
        if (input.isEmpty()) {
            inputNr = 0;
        } else {
            inputNr = Math.abs(Integer.parseInt(input));
        }
        //welche methode wurde benutzt
        if (SUM) {
            inputValid = playSum(inputNr, roundLabel);

        } else {
            inputValid = playEnter(inputNr, roundLabel, gridPane);
        }

        // Is there a WINNER?
        if (gameStack.peek() >= 21) {
            messageLabel.setText("\n!!!! We have a winner !!!! " + currentPlayer);
        }

        if (inputValid) {
            int nrPlayers = 2;
            currentPlayer = (currentPlayer % nrPlayers) + 1;
            Player currentPlayerObject = playersList[currentPlayer - 1];
            if (currentPlayer == 1) {
                roundNr++;
                roundLabel.setText("Round " + roundNr);
            }
            playerLabel.setText(currentPlayerObject.getName());
            inputField.clear();
        } else {
            messageLabel.setText("Spiel ist vorbei!");//kann weg, sobald andere lösung für rückgang auf die startseite gibt
        }
    }

    private void updateGrid(GridPane gridPane) {
        for (int i = 0; i < gameStack.size(); i++) {
            Label label = (Label) gridPane.getChildren().get(i);
            label.setText(String.valueOf(gameStack.get(i)));
        }
    }

    private boolean playEnter(Integer inputNr, Label messageLabel, GridPane gridPane) {
        boolean inputValid = false;
        if (inputNr < 10 && inputNr > 0) {
            if (gameStack.size() < 6) {
                gameStack.add(inputNr);
            } else {
                // Creating sum of input & top number
                gameStack.push(gameStack.pop() + inputNr);
            }
            updateGrid(gridPane);
            inputValid = true;
        } else if (inputNr == 0) {
            if (roundNr < 4) {
                messageLabel.setText("Please provide a number between 1 & 9. The function 'Sum' will be available in the 4th round.");
            } else {
                if (gameStack.size() > 1) {
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    gameStack.push(firstTopNr + secTopNr);
                    inputValid = true;
                } else {
                    messageLabel.setText("Not enough numbers in Stack!");
                }
            }
        } else {
            messageLabel.setText("Please provide a number between 1-9.");
        }
        return inputValid;
    }

    private boolean playSum(Integer inputNr, Label messageLabel) {
        boolean inputValid = false;
        if (roundNr < 4) {
            messageLabel.setText("Please provide a number between 1 & 9. The function 'Sum' will be available in the 4th round.");
        } else {
            if (gameStack.size() < 6 && inputNr == 0) {
                int firstTopNr = gameStack.pop();
                int secTopNr = gameStack.pop();
                gameStack.push(firstTopNr + secTopNr);
                inputValid = true;
            }
        }
        return inputValid;
    }
}


