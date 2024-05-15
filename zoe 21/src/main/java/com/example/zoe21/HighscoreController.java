package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import com.example.model.leaderboard.StaticLeaderBoard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import javax.print.DocFlavor;
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
    protected Button updateButton;
    @FXML
    private Label rankLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label scoreLabel;


    @FXML
    protected void setBackToMenu() {
        SwitchingScenes.setScene(0);
    }

    @FXML
    protected void setUpdate() {
        updateGrid();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Laden der Schriftart für den Button
        Font fontmachine = Font.getDefault();
        Font fontheading = Font.getDefault();
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            InputStream is40 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is != null) {
                fontmachine = Font.loadFont(is, 12);
                fontheading = Font.loadFont(is40, 40);
            } else {
                System.err.println("Font file not found, using default font.");
            }
            backToMainMenuButton.setFont(fontmachine);// Anpassung des Labels
            highscoreLabel.setFont(fontheading);
            updateButton.setFont(fontmachine);
            rankLabel.setFont(fontmachine);
            nameLabel.setFont(fontmachine);
            scoreLabel.setFont(fontmachine);

        } catch (Exception e) {
            System.err.println("Error loading font, using default font: " + e.getMessage());
            e.printStackTrace();
        }

        // Laden des Leaderboards
        // Annahme, dass das GridPane das zweite Kind des AnchorPane ist
        updateGrid();
    }

    protected void updateGrid() {
        AnchorPane parentPane = (AnchorPane) highscoreLabel.getParent();
        GridPane gridPane = (GridPane) parentPane.getChildren().get(6);

        // Clear existing labels in the grid
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (!label.getText().isEmpty()) {
                    label.setText(" ");
                }
            }
        }

        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager(); // Load the leaderboard
        List<LeaderBoardItem> items = leaderBoard.getItems();

        // Populate the grid with leaderboard data
        for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
            LeaderBoardItem item = items.get(rowIndex);
            // Adjusting rowIndex since ranks start from 1
            int rank = rowIndex + 1;
            // Get the name and score of the item
            String name = item.getName();
            String score = item.getFormattedScore();

            // Create labels for rank, name, and score
            Label rankLabel = new Label(Integer.toString(rank));
            Label nameLabel = new Label(name);
            Label scoreLabel = new Label(String.format(score));

            // Apply font and style
            Font font = Font.getDefault();
            try {
                // loads the font
                InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
                if (is != null) {
                    font = Font.loadFont(is, 12);
                } else {
                    System.err.println("Font file not found, using default font.");
                }
            } catch (Exception e) {
                System.err.println("Error loading font, using default font: " + e.getMessage());
                e.printStackTrace();
            }
            rankLabel.setFont(font);
            nameLabel.setFont(font);
            scoreLabel.setFont(font);
            rankLabel.setStyle("-fx-text-fill: #eaff00;");
            nameLabel.setStyle("-fx-text-fill: #eaff00;");
            scoreLabel.setStyle("-fx-text-fill: #eaff00;");

            // Add labels to grid
            gridPane.add(rankLabel, 0, rowIndex);
            gridPane.add(nameLabel, 1, rowIndex);
            gridPane.add(scoreLabel, 2, rowIndex);
        }

        // If the number of entries is less than the maximum number of rows,
        // fill the remaining rows with empty labels
        for (int rowIndex = items.size(); rowIndex < LeaderBoard.MAX_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
                Label emptyLabel = new Label("");
                gridPane.add(emptyLabel, columnIndex, rowIndex);
            }
        }
    }
}