package com.example.zoe21;

import com.example.model.leaderboard.LeaderBoard;
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

// MainController belongs to MainApplication and organizes the elements in mainMenu
// belongs to fxml: mainmenu.fxml

public class MainController implements Initializable {

    // selection buttons to switch to respective scene
    @FXML
    private Button highscoreButton;
    @FXML
    private Button regularGameButton;
    @FXML
    private Button machineGameButton;


    @FXML
    private Label welcomeText;

    // if highScoreButton is selected, the scene on window will be set to highscoreScene
    // the same logic applies to the following methods with the according scenes
    @FXML
    public void onHighScoreButtonClick() {
        System.out.println("Switching to Highscore");
        SwitchingScenes.setScene(1);
    }

    @FXML
    public void onRegularGameButtonClick() {
        System.out.println("Switching to Regular Game");
        SwitchingScenes.setScene(2);
        setHumanMode();
    }

    @FXML
    public void onMachineGameButtonClick() {
        System.out.println("Switching to Machine Set Difficulty");
        SwitchingScenes.setScene(3);
    }

    // initializes the mainMenu scene
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the font for all text in the game
        Font font = Font.getDefault();
        try {
            InputStream is = MainController.class.getResourceAsStream("/font/PressStart2P-vaV7.ttf");
            if (is != null) {
                font = Font.loadFont(is, 20); // set font and size=20
            } else {
                System.err.println("Font file not found, using default font.");
            }
            // set font for all buttons and labels in mainMenu
            welcomeText.setFont(font);
            highscoreButton.setFont(font);
            regularGameButton.setFont(font);
            machineGameButton.setFont(font);
        } catch (Exception e) {
            System.err.println("Error loading font, using default font:" + e.getMessage());
            e.printStackTrace();
        }
    }

    // create space background as subclass of Canvas (in order to place the other elements on top of it)
    static class SpaceBackground extends Canvas {


        private static final int STAR_COUNT = 200; // set amount of stars to 200

        private final List<Star> stars = new ArrayList<>(); // holds all stars in the background
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

        // constructor that creates a background according to the given size
        public SpaceBackground(int width, int height) {
            super(width, height);

            for (int i = 0; i < STAR_COUNT; i++) {
                // for each star, generate random x and y values for placement on the background canvas
                double x = random.nextDouble() * width; // creates random double between 0.0 and 1.0 times the width
                double y = random.nextDouble() * height;
                double speed = 1.0 + random.nextDouble() * 3.0; // generate random star speed
                stars.add(new Star(x, y, speed)); // add generated star to the list
            }

            // anonymous inner class that creates a new AnimationTimer in order to draw stars
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    draw();
                }
            };
            timer.start();
        }

        // draws the background
        private void draw() {
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(Color.BLACK); // black background as basis
            gc.fillRect(0, 0, getWidth(), getHeight());

            // use stream in order to repeat for each star:
            stars.forEach(star -> {
                gc.setFill(Color.WHITE); // white fill
                // communicate position and speed with graphics context
                gc.fillOval(star.getX(), star.getY(), star.getSpeed(), star.getSpeed());
                // star starts moving
                star.move();
            });
        }

        private class Star {
            private double x;
            private double y;
            private final double speed; // cannot be changed

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
                y += speed; // star moves along the y axis according to the speed
                if (y > getHeight()) { // if star has reached the edge of the scene
                    y = 0; // place the star back to the beginning of the scene
                    x = random.nextDouble() * getWidth(); // and at a random width
                }
            }
        }
    }
}

