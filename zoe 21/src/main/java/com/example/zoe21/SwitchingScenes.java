package com.example.zoe21;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SwitchingScenes {

    private static Scene menu, highscore, regularGame, machineDifficulty;
    private static Stage window;

    public static void creatingSceneSwitcher(Stage pStage, Scene pMenu, Scene pHighscore, Scene pRegularGame, Scene pMachineDifficulty) {
        window = pStage;
        menu = pMenu;
        highscore = pHighscore;
        regularGame = pRegularGame;
        machineDifficulty = pMachineDifficulty;
    }

    public static void setScene(int sceneNr){
        // change scene

            switch(sceneNr){
                case 0:
                    window.setScene(menu); // change to mainmenu
                    break;
                case 1:
                    window.setScene(highscore); // change to highscore
                    break;
                case 3:
                    window.setScene(machineDifficulty); //
                    break;
                case 2:
                    try {
                        FXMLLoader loader = new FXMLLoader(SwitchingScenes.class.getResource("regularGame.fxml"));
                        Scene regularGameScene = new Scene(loader.load());
                        window.setScene(regularGameScene);
                        RegularGameController controller = loader.getController();
                        controller.addNames();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    //throw Exception("Wrong scene");
                    System.out.println("No such scene available");
            }
    }
}

