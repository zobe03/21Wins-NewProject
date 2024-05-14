package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is != null) {
                fontmachine = Font.loadFont(is, 12);
            } else {
                System.err.println("Font file not found, using default font.");
            }
            backToMainMenuButton.setFont(fontmachine);// Anpassung des Labels
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

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (!label.getText().isEmpty()) {
                    label.setText(" ");
                }
            }
        }

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
                Font font = Font.getDefault();
                try {
                    InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
                    if (is != null) {
                        font = Font.loadFont(is, 12);
                    } else {
                        System.err.println("Font file not found, using default font.");
                    }
                    label.setFont(font);
                } catch (Exception e) {
                    System.err.println("Error loading font, using default font: " + e.getMessage());
                    e.printStackTrace();
                }
                label.setStyle("-fx-text-fill: #eaff00;");
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
