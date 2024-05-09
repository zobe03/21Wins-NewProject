package model;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Stack;

public class Game {
    private int roundNr = 1;
    private int currentPlayer = 1;
    private final Stack<Integer> gameStack = new Stack<>();
    private boolean stop = false;


    public void askForInput(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        String input;
        System.out.println(Arrays.toString(playersList));
        if (playersList[0] instanceof HumanPlayer) {
            input = inputField.getText();
            System.out.println("Human");
            playTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);

            if (playersList[1] instanceof MachinePlayer) {
                System.out.println("Machine");
                //Verzögerug um 2-3 Sekunden
                    int machineMove = MachinePlayer.makeMove(roundNr, gameStack);
                    inputField.setText(String.valueOf(machineMove));
                    input = String.valueOf(machineMove);
                    playTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            }
            else {
                System.out.println("Human");
                input = inputField.getText();
                playTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            }
        }
    }

    public void playTheGame(String input, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        int inputNr;

        if (input.isEmpty()) {
            inputNr = 0;
            System.out.println("Input is empty");
        } else {
            inputNr = Math.abs(Integer.parseInt(input));
        }
        System.out.println(inputNr);

        //welche methode wurde benutzt
        berechnen(inputNr, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
        // Is there a WINNER?
        if (gameStack.peek() >= 21) {
            messageLabel.setText("\n!!!! We have a winner !!!! " + currentPlayer);
            stop = true;
        }

        if (!stop) {
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

    private void berechnen(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        if (inputNr < 10 && inputNr > 0) {
            if (gameStack.size() < 6) {
                gameStack.add(inputNr);
                updateGrid(gridPane);
            } else {
                // Creating sum of input & top number
                int number = gameStack.pop() + inputNr;
                gameStack.push(number);
                updateGrid(gridPane);
            }
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
                    updateGrid(gridPane);
                } else {
                    messageLabel.setText("Not enough numbers in Stack!");
                }
            }
        } else {
            messageLabel.setText("Please provide a number between 1-9.");
        }
    }

    private void playSum(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        if (roundNr < 4) {
            messageLabel.setText("Please provide a number between 1 & 9. The function 'Sum' will be available in the 4th round.");
        } else {
            if (gameStack.size() < 6 && inputNr == 0) {
                int firstTopNr = gameStack.pop();
                int secTopNr = gameStack.pop();
                gameStack.push(firstTopNr + secTopNr);
                inputValid = true;
                updateGrid(gridPane);
            }
        }
    }
}


