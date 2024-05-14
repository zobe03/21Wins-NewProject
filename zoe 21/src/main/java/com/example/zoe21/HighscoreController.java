package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.model.leaderboard.LeaderBoard;

public class HighscoreController implements Initializable {
    @FXML
    private AnchorPane gameLayout;
    @FXML
    private TableView<LeaderBoardItem> highscoreTable;
    @FXML
    private TableColumn<LeaderBoardItem, Integer> placeColumn;
    @FXML
    private TableColumn<LeaderBoardItem, String> nameColumn;
    @FXML
    private TableColumn<LeaderBoardItem, String> scoreColumn;
    @FXML
    protected Button backToMainMenuButton;

    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setze die Zellenwerte für die Spalten
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("formattedScore"));

        // Lade den Leaderboard und setze die Daten in die Tabelle
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager();
        List<LeaderBoardItem> items = leaderBoard.getItems();
        highscoreTable.getItems().addAll(items);

}
}
