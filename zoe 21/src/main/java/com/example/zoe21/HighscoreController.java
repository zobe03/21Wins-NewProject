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
import java.util.List;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    @FXML
    private Label highscoreLabel;
    @FXML
    protected Button backToMainMenuButton;

    @FXML
    protected void setBackToMenu() {
        SwitchingScenes.setScene(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Laden der Schriftart für den Button
        Font fontmachine = Font.getDefault();
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is != null) {
                fontmachine = Font.loadFont(is, 12);
            } else {
                System.err.println("Font file not found, using default font.");
            }
            backToMainMenuButton.setFont(fontmachine);
            highscoreLabel.setFont(fontmachine);
        }catch (Exception e){
            System.err.println("Error loading font, using default font: " + e.getMessage());
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
            highscoreText.append(rank).append(". ").append(item.getName()).append(": ").append(item.getFormattedScore()).append("\n");
            rank++;
        }
        highscoreLabel.setText(highscoreText.toString());
}
}


