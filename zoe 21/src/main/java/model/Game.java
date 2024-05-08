package model;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.Stack;

public class Game {
    private int roundNr = 1;
    private int currentPlayer = 1;
    private Stack<Integer> gameStack = new Stack<>();
    private final int nrPlayers = 2;
    private boolean stop = false;

    public void playWithPlayer(Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList){
        String input = inputField.getText();
        PlayTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
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
        PlayTheGame(input, roundLabel, playerLabel, messageLabel, inputField, gridPane, playersList);
    }
    public void PlayTheGame(String input, Label roundLabel, Label playerLabel, Label messageLabel, TextField inputField, GridPane gridPane, Player[] playersList){
        boolean inputValid = false;
        if(!stop) {
            try {
                int inputNr = Math.abs(Integer.parseInt(input));
                if (inputNr < 10 && inputNr > 0) {
                    if (gameStack.size() < 6) {
                        gameStack.add(inputNr);
                        updateGrid(gridPane);
                    } else{
                        // Creating sum of input & top number
                        gameStack.push(gameStack.pop() + inputNr);
                        updateGrid(gridPane);
                    }
                }
                else {
                    messageLabel.setText("Please provide a number between 1-9.");
                }
                inputValid = true;
            } catch (NumberFormatException e) {
                int inputNr = Math.abs(Integer.parseInt(input));
                if ((input.isEmpty() || inputNr==0) && roundNr >= 4 && gameStack.size()>1) {
                    // Creating sum of the 2 top numbers
                    int firstTopNr = gameStack.pop();
                    int secTopNr = gameStack.pop();
                    gameStack.push(firstTopNr + secTopNr);
                    inputValid = true;
                } else{
                    messageLabel.setText("Please enter a valid input!");
                    inputValid = false;
                }
            }
            while(!inputValid){
                if (gameStack.peek() >= 21) {
                    messageLabel.setText("\n!!!! We have a winner !!!! PLAYER " + currentPlayer);
                    stop = true;
                }
            }
            if (!stop) {
                currentPlayer = (currentPlayer % nrPlayers) + 1;
                Player currentPlayerObject = playersList[currentPlayer -1];
                if (currentPlayer == 1) {
                    roundNr++;
                    roundLabel.setText("Round " + roundNr);
                }
                playerLabel.setText(currentPlayerObject.getName());
                inputField.clear();
            }
        }else{
            messageLabel.setText("Spiel ist vorbei!");//kann weg sobald andere lösung für rückgang auf die startseite gibt
        }
    }
    private void updateGrid(GridPane gridPane){
        for(int i = 0; i < gameStack.size(); i++){
            Label label = (Label) gridPane.getChildren().get(i);
            label.setText(String.valueOf(gameStack.get(i)));
        }
    }
}
