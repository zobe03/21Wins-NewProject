package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.LeaderBoardItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    @FXML
    protected Button backtomenu;

    @FXML
    private AnchorPane gameLayout;

    @FXML
    private TableView<LeaderBoardItem> leaderboardTable; // Make sure this matches your FXML fx:id
    @FXML
    private TableColumn<LeaderBoardItem, String> nameColumn;
    @FXML
    private TableColumn<LeaderBoardItem, Integer> movesColumn;
    @FXML
    private TableColumn<LeaderBoardItem, Long> timeColumn;
    @FXML
    private TableColumn<LeaderBoardItem, Double> scoreColumn;

    private LeaderBoard leaderboard;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        String imagePath = "file:resources/bücher3.jpeg";
        Image image = new Image(imagePath);
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        gameLayout.setBackground(new Background(backgroundImage));

        // Leaderboard Setup and Data Loading
        leaderboard = new LeaderBoard();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        movesColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Populate TableView
        ObservableList<LeaderBoardItem> data = FXCollections.observableArrayList(leaderboard.getItems());
        leaderboardTable.setItems(data);
    }
    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
}
