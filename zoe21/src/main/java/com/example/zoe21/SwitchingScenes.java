package com.example.zoe21;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// with the help of this class, switching between different scenes can be handled effectively
public class SwitchingScenes {

    // set scenes and stages on static to be able to access from other classes
    private static Scene menu, highscore, regularGame, machineDifficulty;
    private static Stage window;

    // connection to MainApplication: passing existing window stage and other scenes to save them in this class
    public static void creatingSceneSwitcher(Stage pStage, Scene pMenu, Scene pHighscore, Scene pRegularGame, Scene pMachineDifficulty) {
        // assign window as the main stage for all following scenes
        window = pStage;
        // assign scenes
        menu = pMenu;
        highscore = pHighscore;
        regularGame = pRegularGame;
        machineDifficulty = pMachineDifficulty;
    }

    // method to set current scene (when wanting to switch)
    public static void setScene(int sceneNr) {
        // change scene

        switch (sceneNr) {
            case 0:
                window.setScene(menu); // change to mainmenu
                break;
            case 1:
                window.setScene(highscore); // change to highscore
                break;
            case 3:
                window.setScene(machineDifficulty); // change to difficulty selection
                break;
            case 2: // special case: changing to regularGame may cause an exception and therefore is handled differently
                try {
                    // create association to fxml
                    FXMLLoader loader = new FXMLLoader(SwitchingScenes.class.getResource("regularGame.fxml"));
                    // create regularGameScene
                    Scene regularGameScene = new Scene(loader.load());
                    window.setScene(regularGameScene);
                    RegularGameController controller = loader.getController();
                    controller.addNames();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default: // if there should be an invalid value (mostly for error handling reasons during programming; should not occur in final game version)
                System.out.println("No such scene available");
        }
    }
}

