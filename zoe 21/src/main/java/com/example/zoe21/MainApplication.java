package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
import com.example.model.leaderboard.StaticLeaderBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private Stage window;
    private Scene mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene;

    @Override
    public void start(Stage stage) throws Exception {

        window = stage;
        createScenes();
        window.setScene(mainMenuScene);
        window.setTitle("21 Wins!!!");
        window.show();
        SwitchingScenes.creatingSceneSwitcher(window, mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene);
    }

    private void createScenes() {
        try {
            FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
            mainMenuScene = createSceneWithBackground(fxmlLoaderMenu.load(), new MainController.SpaceBackground(1000, 1000));

            FXMLLoader highscoreFxmlLoader = new FXMLLoader(getClass().getResource("highscore.fxml"));
            highscoreScene = createSceneWithBackground(highscoreFxmlLoader.load(), new MainController.SpaceBackground(1000, 1000));

            FXMLLoader regularGameFxmlLoader = new FXMLLoader(getClass().getResource("regularGame.fxml"));
            regularGameScene = createSceneWithBackground(regularGameFxmlLoader.load(), new MainController.SpaceBackground(1000, 1000));

            FXMLLoader machineSetDifficultyFxmlLoader = new FXMLLoader(getClass().getResource("machineSetDifficulty.fxml"));
            machineSetDifficultyScene = createSceneWithBackground(machineSetDifficultyFxmlLoader.load(), new MainController.SpaceBackground(1000, 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createSceneWithBackground(Parent root, MainController.SpaceBackground background) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, root);
        Scene scene = new Scene(stackPane, 800, 800);
        return scene;
    }


    public static void main(String[] args) {
        StaticLeaderBoard.initialize();
        launch(args);
    }
}
