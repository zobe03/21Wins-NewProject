package com.example.zoe21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// main program
public class MainApplication extends Application {

    private Stage window;
    private Scene mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene;

    @Override
    public void start(Stage stage) {
        window = stage;

        // Erstelle die Szenen
        createScenes();

        // Setze die Hauptmenüszene als Startszene
        window.setScene(mainMenuScene);
        window.setTitle("21 Wins!!!");
        window.show();
        SwitchingScenes.creatingSceneSwitcher(window, mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene);
    }

    private void createScenes() {
        // Erstelle die Hintergrundobjekte
        MainController.SpaceBackground spaceBackgroundMenu = new MainController.SpaceBackground(800, 600);
        MainController.SpaceBackground spaceBackgroundHighscore = new MainController.SpaceBackground(800, 600);
        MainController.SpaceBackground spaceBackgroundRegulargame = new MainController.SpaceBackground(800, 600);
        MainController.SpaceBackground spaceBackgroundDifficulty = new MainController.SpaceBackground(800, 600);

        // Lade die FXML-Dateien und erstelle die Szenen
        try {
            FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
            mainMenuScene = createSceneWithBackground(fxmlLoaderMenu.load(), spaceBackgroundMenu);

            FXMLLoader highscoreFxmlLoader = new FXMLLoader(getClass().getResource("highscore.fxml"));
            highscoreScene = createSceneWithBackground(highscoreFxmlLoader.load(), spaceBackgroundHighscore);

            FXMLLoader regularGameFxmlLoader = new FXMLLoader(getClass().getResource("regularGame.fxml"));
            regularGameScene = createSceneWithBackground(regularGameFxmlLoader.load(), spaceBackgroundRegulargame);

            FXMLLoader machineSetDifficultyFxmlLoader = new FXMLLoader(getClass().getResource("machineSetDifficulty.fxml"));
            machineSetDifficultyScene = createSceneWithBackground(machineSetDifficultyFxmlLoader.load(), spaceBackgroundDifficulty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createSceneWithBackground(Parent root, MainController.SpaceBackground background) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, root);
        Scene scene = new Scene(stackPane, 600, 600);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}