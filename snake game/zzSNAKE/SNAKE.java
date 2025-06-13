package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SNAKE extends Application {
    // variable
    static int speed = 5;
    static int foodcolor = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();

    public enum Dir {
        left, right, up, down
    }

    public static class Corner {
        int x;
        int y;

        public Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void start(Stage primaryStage) {
        try {
            newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(width * cornersize, height * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();

            Scene scene = new Scene(root, width * cornersize, height * cornersize);

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W && direction != Dir.down) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A && direction != Dir.right) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S && direction != Dir.up) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D && direction != Dir.left) {
                    direction = Dir.right;
                }
            });

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));

            // If you do not want to use CSS style, you can just delete the next line.
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tick
    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.LAVENDERBLUSH);
            gc.setFont(Font.font("Comic Sans MS", 50)); // Change the font of - GAME OVER to Comic Sans MS
            gc.fillText("GAME OVER", 100, 250);
            return;
        }

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= height) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= width) {
                    gameOver = true;
                }
                break;
        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornersize, height * cornersize);

        // score
        gc.setFill(Color.HOTPINK);
        gc.setFont(Font.font("Pacifico", 30)); // Change the font of the score to Pacifico
        gc.fillText("Score: " + (speed - 6), 10, 30);

        // random foodcolor
        Color cc = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.LIGHTCORAL;
                break;
            case 1:
                cc = Color.CORNSILK;
                break;
            case 2:
                cc = Color.MOCCASIN;
                break;
            case 3:
                cc = Color.PALEVIOLETRED;
                break;
            case 4:
                cc = Color.SALMON;
                break;
        }
        gc.setFill(cc);

        // Change the shape of the Food
        switch (foodcolor) {
            case 0:
                // Two Cherries
                gc.setFill(Color.RED);
                gc.fillOval(foodX * cornersize - 5, foodY * cornersize, cornersize + 10, cornersize);
                gc.setFill(Color.GREEN);
                gc.fillOval(foodX * cornersize - 5, foodY * cornersize, 5, cornersize);
                break;
            case 1:
                // Apple
                gc.fillRoundRect(foodX * cornersize, foodY * cornersize, cornersize, cornersize, 10, 10);
                break;
            case 2:
                // Banana
                gc.setFill(Color.YELLOW);
                gc.fillRoundRect(foodX * cornersize, foodY * cornersize, cornersize, cornersize / 2, 5, 5);
                break;
            case 3:
                // Strawberry
                gc.setFill(Color.LIGHTCORAL);
                gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                gc.setFill(Color.RED);
                gc.fillOval(foodX * cornersize + 5, foodY * cornersize + 5, cornersize - 10, cornersize - 10);
                gc.setFill(Color.GREEN);
                gc.fillOval(foodX * cornersize + cornersize / 2 - 5, foodY * cornersize + cornersize / 2 - 5, 10, 10);
                break;
            case 4:
                // Default Circle
                gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                break;
        }

        // snake
        for (int i = 0; i < snake.size(); i++) {
            Corner c = snake.get(i);
            // Change the snake color
            gc.setFill(i == 0 ? Color.LAVENDERBLUSH : Color.PALEVIOLETRED);
            // Change the snake shape to a more realistic snake
            double bodyWidth = cornersize * 0.8;
            double bodyHeight = cornersize * 0.8;
            gc.fillRoundRect(c.x * cornersize + (cornersize - bodyWidth) / 2, c.y * cornersize + (cornersize - bodyHeight) / 2, bodyWidth, bodyHeight, 5, 5);
        }
    }

    // food
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(5);
            speed++;
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}