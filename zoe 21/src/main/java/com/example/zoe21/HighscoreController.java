package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    @FXML
    protected Button backtomenu;
    @FXML
    private AnchorPane gameLayout;
    @FXML
    private Label highscoreLabel;

    @FXML
    protected void setBacktomenu() {
        SwitchingScenes.setScene(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Laden der Schriftart für den Button
        try {
            InputStream fontStream = getClass().getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            Font font = Font.loadFont(fontStream, 20);
            backtomenu.setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Laden des Highscores
        displayHighscore();
    }

    // Methode zur Anzeige des Highscores
    private void displayHighscore() {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager(); // Laden des Leaderboards
        StringBuilder highscoreText = new StringBuilder("Highscore:\n");
        int rank = 1;
        for (LeaderBoardItem item : leaderBoard.getItems()) {
            highscoreText.append(rank).append(". ").append(item.getName()).append(": ").append(item.getFormatedScore()).append("\n");
            rank++;
        }
        highscoreLabel.setText(highscoreText.toString());
    }
}
