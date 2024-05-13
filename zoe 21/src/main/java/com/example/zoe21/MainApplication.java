package com.example.zoe21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// main program
public class MainApplication extends Application {
    Stage window;
    Scene mainMenuScene;
    Scene highscoreScene, regularGameScene, machineSetDifficulty;


    @Override
    public void start(Stage stage) throws Exception {
        // create the main menu



        window = stage;

        // create main menu
        MainController.SpaceBackground spaceBackgroundMenu = new MainController.SpaceBackground(800, 600);
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("mainmenu.fxml"));
        mainMenuScene = new Scene(fxmlLoaderMenu.load(), 600, 600);
        StackPane root = new StackPane();
        root.getChildren().addAll((Node) spaceBackgroundMenu, mainMenuScene.getRoot());
        mainMenuScene.setRoot(root);
        stage.setScene(mainMenuScene);
        stage.show();


        // create various scenes + respective loader (size may be changed)

        // highscore scene
       // MainController.SpaceBackground spaceBackgroundHighscore = new MainController.SpaceBackground(800, 600);
        FXMLLoader highscoreFxmlLoader = new FXMLLoader(MainApplication.class.getResource("highscore.fxml"));
        highscoreScene = new Scene(highscoreFxmlLoader.load(), 600, 600);
        //root.getChildren().addAll((Node) spaceBackgroundHighscore, highscoreScene.getRoot());
        //highscoreScene.setRoot(root);



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