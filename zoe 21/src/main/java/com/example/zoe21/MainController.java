package com.example.zoe21;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.zoe21.RegularGameController.setHumanMode;

public class MainController implements Initializable {
    @FXML
    private Button highscoreButton;
    @FXML
    private Button regularGameButton;
    @FXML
    private Button machineGameButton;


    @FXML
    private Label welcomeText; // tbd

    @FXML
    public void onHighScoreButtonClick() { // Thorsten
        System.out.println("Switching to Highscore");
        SwitchingScenes.setScene(1);
    }

    @FXML
    public void onRegularGameButtonClick() { // Thorsten
        System.out.println("Switching to Regular Game");
        SwitchingScenes.setScene(2);
        setHumanMode();
    }

    @FXML
    public void onMachineGameButtonClick() { // Thorsten
        System.out.println("Switching to Machine Set Difficulty");
        SwitchingScenes.setScene(3);
    }
    @FXML
    private AnchorPane gameLayout;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font font = Font.getDefault();
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if(is != null){
                font = Font.loadFont(is, 20);
            }
            else{
                System.err.println("Font file not found, using default font.");
            }
            welcomeText.setFont(font);
            highscoreButton.setFont(font);
            regularGameButton.setFont(font);
            machineGameButton.setFont(font);
        } catch (Exception e) {
            System.err.println("Error loading font, using default font:" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static class SpaceBackground extends Canvas {


        private static final int STAR_COUNT = 200;

        private final List<Star> stars = new ArrayList<>();
        private final Random random = new Random();

        @FXML
        private VBox root;
        public void initialize() {
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            Label label = new Label("Hello, JavaFX!");
            label.setFont(Font.font("Arial", 24));
            label.setTextFill(Color.web("#0076a3"));
            label.setTextAlignment(TextAlignment.CENTER);

            root.getChildren().add(label);

            SpaceBackground spaceBackground = new SpaceBackground(800, 600);
            root.getChildren().add(spaceBackground);
        }

        public SpaceBackground(int width, int height) {
            super(width, height);

            for (int i = 0; i < STAR_COUNT; i++) {
                double x = random.nextDouble() * width;
                double y = random.nextDouble() * height;
                double speed = 1.0 + random.nextDouble() * 3.0;
                stars.add(new Star(x, y, speed));
            }

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    draw();
                }
            };
            timer.start();
        }

        private void draw() {
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, getWidth(), getHeight());

            stars.forEach(star -> {
                gc.setFill(Color.WHITE);
                gc.fillOval(star.getX(), star.getY(), star.getSpeed(), star.getSpeed());
                star.move();
            });
        }

        private class Star {
            private double x;
            private double y;
            private final double speed;

            public Star(double x, double y, double speed) {
                this.x = x;
                this.y = y;
                this.speed = speed;
            }

            public double getX() {
                return x;
            }

            public double getY() {
                return y;
            }

            public double getSpeed() {
                return speed;
            }

            public void move() {
                y += speed;
                if (y > getHeight()) {
                    y = 0;
                    x = random.nextDouble() * getWidth();
                }
            }
        }
    }
}

