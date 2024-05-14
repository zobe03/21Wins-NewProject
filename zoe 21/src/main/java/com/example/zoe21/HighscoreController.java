package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    protected Label ranklabel;
    @FXML
    protected Label nameLabel;
    @FXML
    protected Label scoreLabel;

    @FXML
    protected void setBackToMenu() {
        SwitchingScenes.setScene(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Laden der Schriftart für den Button
        Font fontmachine = Font.getDefault();
        Font fontHighscore = Font.getDefault();
        Font fontLabels = Font.getDefault();
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is != null) {
                fontmachine = Font.loadFont(is, 12);
                fontHighscore = Font.loadFont(is, 30);
                fontLabels = Font.loadFont(is, 20);
            } else {
                System.err.println("Font file not found, using default font.");
            }
            backToMainMenuButton.setFont(fontmachine);// Anpassung des Labels
            highscoreLabel.setFont(fontHighscore);
            ranklabel.setFont(fontLabels);
            nameLabel.setFont(fontLabels);
            scoreLabel.setFont(fontLabels);

        } catch (Exception e) {
            System.err.println("Error loading font, using default font: " + e.getMessage());
            e.printStackTrace();
        }

        // Laden des Leaderboards
        AnchorPane parentPane = (AnchorPane) highscoreLabel.getParent();
        GridPane gridPane = (GridPane) parentPane.getChildren().get(2); // Annahme, dass das GridPane das zweite Kind des AnchorPane ist
        updateGrid(gridPane);
    }

    private void updateGrid(GridPane gridPane) {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager(); // Laden des Leaderboards
        List<LeaderBoardItem> items = leaderBoard.getItems();

        // Durchlaufe das GridPane von oben nach unten und aktualisiere die Labels entsprechend
        for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
            LeaderBoardItem item = items.get(rowIndex);
            String[] parts = item.toString().split(",");

            // Überprüfen, ob die Zeile die maximale Anzahl von Spalten überschreitet
            if (parts.length != 3) {
                System.err.println("Invalid data format for leaderboard item: " + item.toString());
                continue;
            }

            // Füge die Daten in die Labels ein
            for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
                // Anpassen der Reihenfolge der Teile entsprechend der gewünschten Spaltenanordnung
                int columnToInsert = (columnIndex == 0) ? 1 : (columnIndex == 1) ? 0 : 2;
                Label label = new Label(parts[columnToInsert]);
                gridPane.add(label, columnIndex, rowIndex);
            }
        }

        // Wenn die Anzahl der Einträge kleiner als die maximale Anzahl von Zeilen ist,
        // leere die restlichen Zeilen im GridPane
        for (int rowIndex = items.size(); rowIndex < LeaderBoard.MAX_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
                Label emptyLabel = new Label("");
                gridPane.add(emptyLabel, columnIndex, rowIndex);
            }
        }
    }
}
