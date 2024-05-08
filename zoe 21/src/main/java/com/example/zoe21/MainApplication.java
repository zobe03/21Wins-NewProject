package com.example.zoe21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// main program
public class MainApplication extends Application {
    Stage window;
    Scene mainMenuScene;
    Scene highscoreScene, regularGameScene, machineSetDifficulty;


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        FXMLLoader fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("mainmenu.fxml"));
        mainMenuScene = new Scene(fxmlLoaderMenu.load(), 600, 600);

        // create various scenes + respective loader (size may be changed)

        // highscore scene
        FXMLLoader highscoreFxmlLoader = new FXMLLoader(MainApplication.class.getResource("highscore.fxml"));
        highscoreScene = new Scene(highscoreFxmlLoader.load(), 600, 600);

        // regularGame (human players)
        FXMLLoader regularGameFxmlLoader = new FXMLLoader(MainApplication.class.getResource("regularGame.fxml"));
        regularGameScene = new Scene(regularGameFxmlLoader.load(), 600, 600);

        // machineGame (human against player)
        FXMLLoader machineSetDifficultyFxmlLoader = new FXMLLoader(MainApplication.class.getResource("machineSetDifficulty.fxml"));
        machineSetDifficulty = new Scene(machineSetDifficultyFxmlLoader.load(), 600, 600);

        SwitchingScenes.creatingSceneSwitcher(window, mainMenuScene, highscoreScene, regularGameScene,machineSetDifficulty);
        window.setScene(mainMenuScene);
        window.setTitle("21 Wins!!!");
        window.show();

    }

    public static void main(String[] args) {
        launch();
    }
}