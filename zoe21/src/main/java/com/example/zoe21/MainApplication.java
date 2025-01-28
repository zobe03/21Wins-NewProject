package com.example.zoe21;

import com.example.model.leaderboard.StaticLeaderBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

// MainApplication is a subclass of Application and is the main class that starts the game

public class MainApplication extends Application {

    // main stage where the selected scene will be placed on
    private Stage window;

    /* create different scenes for the game
    - mainMenuScene: shows the main menu at the start of the game
    - highscoreScene: shows the ranking of all players in the form of a table
    - regularGameScene: is where the game happens; either between two human players or
    between the user and a machine, if he previously selected the machine difficulty mode in
    the scene 'machineSetDifficultyScene'
    - machineSetDifficultyScene: where the user is able to select the difficulty level of the
    machine he/she wants to play against
     */
    private Scene mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene;

    @Override
    public void start(Stage stage) throws Exception {

        window = stage; // set window as main stage where the respective scenes will be applied to

        // create all scenes
        createScenes();
        window.setScene(mainMenuScene);
        window.setTitle("21 Wins!!!");
        window.show();
        SwitchingScenes.creatingSceneSwitcher(window, mainMenuScene, highscoreScene, regularGameScene, machineSetDifficultyScene);
    }

    private void createScenes() {
        /* for every scene:
        1. creates a loader in order to load the respective fxml
        2. creates the scene by adressing the fxml (loader) &
            uses MainController to add the same background and size for each scene
         */
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
