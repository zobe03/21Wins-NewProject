package model;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Stack;


public class Game {
    public int roundNr = 1;
    private int currentPlayer = 1;
    private final Stack<Integer> gameStack = new Stack<>();
    private boolean stop = false;
    public String input;

    public void playTheGame(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {

        boolean inputValid = false;
        System.out.println(Arrays.toString(playersList));
        if (playersList[0] instanceof HumanPlayer) {
            System.out.println("Human");
            input = inputField.getText();
            checkTheRules(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);

            if (playersList[1] instanceof MachinePlayer) {
                System.out.println("Machine");
                //Verzögerug um 2-3 Sekunden
                int machineMove = MachinePlayer.makeMove(roundNr, gameStack);
                inputField.setText(String.valueOf(machineMove));
                input = String.valueOf(machineMove);
                inputValid = checkTheRules(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            } else {
                System.out.println("Human");
                input = inputField.getText();
                inputValid = checkTheRules(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            }
        }
    }
    public boolean checkTheRules(String input, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        int inputNr;

        if (input.isEmpty()) {
            inputNr = 0;
            System.out.println("Input is empty");
        } else {
            inputNr = Math.abs(Integer.parseInt(input));
        }
        play(inputNr, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);

        return inputValid;
    }
    private void play(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        if (inputNr < 10 && inputNr > 0) {
            if (gameStack.size() < 6) {
                gameStack.push(inputNr);
                updateGrid(gridPane);
                inputValid = true;
            } else {
                // Creating sum of input & top number
                int number = gameStack.pop() + inputNr;
                gameStack.push(number);
                updateGrid(gridPane);
                inputValid = true;
            }
        }
        else if (inputNr == 0) {
            if (roundNr < 4) {
                messageLabel.setText("Addition not allowed in first three rounds!");
            } else {
                if (gameStack.size() > 1) {
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    int number = firstTopNr + secTopNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                } else {
                    messageLabel.setText("Not enough numbers in Stack!");
                }
            }
        } else {
            messageLabel.setText("Invalid input, Please enter a number between 1-9.");
        }
        if (inputValid) {
            checkWinner(messageLabel);
            switchPlayer(roundLabel, playerLabel, messageLabel, inputField, playersList);
            inputField.clear();
        }
    }
    private void updateGrid(GridPane gridPane) {
        int stackSize = gameStack.size();

        for (int i = 0; i < gameStack.size(); i++) {
            Label label = (Label) gridPane.getChildren().get(i);
            label.setText(String.valueOf(gameStack.get(i)));
        }
    }
    private void checkWinner(Label messageLabel){
        if (gameStack.peek() >= 21) {
            messageLabel.setText("\n!!!! We have a winner !!!! " + currentPlayer);
            stop=true;
        }
    }
    private void switchPlayer(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, Player[] playersList){
        if(!stop){
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
            messageLabel.setText("Spiel ist vorbei!");
        }
    }
//Sum Button gedrückt
    public void playTheGame2(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {

        boolean inputValid = false;
        System.out.println(Arrays.toString(playersList));
        if (playersList[0] instanceof HumanPlayer) {
            System.out.println("Human");
            input = inputField.getText();
            checkTheRules2(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);

            if (playersList[1] instanceof MachinePlayer) {
                System.out.println("Machine");
                //Verzögerug um 2-3 Sekunden
                int machineMove = MachinePlayer.makeMove(roundNr, gameStack);
                inputField.setText(String.valueOf(machineMove));
                input = String.valueOf(machineMove);
                inputValid = checkTheRules2(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            } else {
                System.out.println("Human");
                input = inputField.getText();
                inputValid = checkTheRules2(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
            }
        }
    }
    public boolean checkTheRules2(String input, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        int inputNr;

        if (input.isEmpty()) {
            inputNr = 0;
            System.out.println("Input is empty");
        } else {
            inputNr = Math.abs(Integer.parseInt(input));
        }
        play2(inputNr, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);

        return inputValid;
    }
    private void play2(Integer inputNr, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList) {
        boolean inputValid = false;
        if (inputNr < 10 && inputNr > 0) {
            if (gameStack.size() < 6) {
                gameStack.push(inputNr);
                updateGrid(gridPane);
                inputValid = true;
            } else {
                // Creating sum of input & top number
                int number = gameStack.pop() + inputNr;
                gameStack.push(number);
                updateGrid(gridPane);
                inputValid = true;
            }
        }
        else if (inputNr == 0) {
            if (roundNr < 4) {
                messageLabel.setText("Addition not allowed in first three rounds!");
            } else {
                if (gameStack.size() > 1) {
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    int number = firstTopNr + secTopNr;
                    gameStack.push(number);
                    updateGrid(gridPane);
                    inputValid = true;
                } else {
                    messageLabel.setText("Not enough numbers in Stack!");
                }
            }
        } else {
            messageLabel.setText("Invalid input, Please enter a number between 1-9.");
        }
        if (inputValid) {
            checkWinner(messageLabel);
            switchPlayer(roundLabel, playerLabel, messageLabel, inputField, playersList);
            inputField.clear();
        }
    }
}


