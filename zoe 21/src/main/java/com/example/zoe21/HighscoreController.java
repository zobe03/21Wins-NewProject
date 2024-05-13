package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;



public class HighscoreController implements Initializable {
    @FXML
    protected Button backtomenu;

    @FXML
    protected void setBacktomenu(){
        SwitchingScenes.setScene(0);
    }
    @FXML
    private AnchorPane gameLayout;
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        /*String imagePath = "file:resources/bücher3.jpeg";
        Image image = new Image(imagePath);
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        gameLayout.setBackground(new Background(backgroundImage));

         */
          try {
              InputStream is20 = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
              Font font = Font.loadFont(is20, 20);
              backtomenu.setFont(font);
          } catch (Exception e) {
              e.printStackTrace();
          }

    }
}
