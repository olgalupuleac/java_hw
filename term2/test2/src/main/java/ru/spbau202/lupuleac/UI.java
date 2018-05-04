package ru.spbau202.lupuleac;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.logic.Board;

import java.util.ArrayList;



/**
 * Class which launches the application and represents the user interface.
 */
public class UI extends Application {
    private Stage window;
    private Board board;
    private Pane root;
    private boolean hide;
    private static int n;
    private ArrayList<Box> pairToOpen = new ArrayList<>();
    public static void main(String[] args) {
        n = Integer.parseInt(args[0]);
        if(args.length != 1 || n > 16 || n % 2 != 0){
            throw new RuntimeException("Invalid arguments");
        }
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        board = new Board(n);
        window = primaryStage;
        window.setMinHeight(600);
        window.setMinWidth(600);
        primaryStage.setScene(mainScene());
        primaryStage.show();
    }

    /**
     * Creates the main scene where game happens.
     *
     * @return the scene which is set as a scene to a primary stage
     */
    @NotNull
    private Scene mainScene() {
        window.setTitle("Game");
        root = new Pane();
        root.setPrefSize(600, 600);
        drawGrid();
        Button restart = new Button("Restart");
        restart.setOnAction(e -> restart());
        root.getChildren().addAll(restart);
        return new Scene(root);
    }

    /**
     * Restarts the game.
     */
    private void restart() {
        board = new Board(n);
        window.setScene(mainScene());
    }


    /**
     * Draws the grid on the main scene.
     */
    private void drawGrid() {
        Box[][] grid = new Box[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Box(j, i);
                root.getChildren().addAll(grid[i][j]);
            }
        }
    }


    /**
     * Displays result of this game.
     */
    private void displayResult() {
        root = new Pane();
        root.setPrefSize(200 , 200);
        Label result = new Label();
        result.setFont(Font.font(30));
        result.setText("YOU WON!");
        result.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(result);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Class which represents a square of the game board.
     */
    private class Box extends StackPane {
        private int x;
        private int y;
        private Text sign = new Text();

        private Box(int x, int y) {
            this.x = x;
            this.y = y;
            Rectangle border = new Rectangle(600 / n, 600 / n);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            sign.setFont(Font.font(120 / n));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, sign);
            setTranslateX(x * 600 / n);
            setTranslateY(y * 600 / n);
            setOnMouseClicked(event -> {
                move();
            });
        }
        private int codePair(int x, int y){
            return x + n * y;
        }

        /**
         * Handles the player's move.
         */
        private void move() {
            if(pairToOpen.size() == 2){
                hidePair();
            }
            int v = codePair(x, y);
            if(pairToOpen.contains(this) || board.isOpened(v)){
                System.err.println("cannot open");
                return;
            }
            pairToOpen.add(this);
            sign.setText(Integer.toString(board.getSquare(v)));
            sign.setVisible(true);
            if(pairToOpen.size() == 2 && board.getOpenedNum() == n * n - 2){
                hidePair();
            }
        }

        /**
         * Hides the opened pair.
         */
        private void hidePair(){
            int u = codePair(pairToOpen.get(0).x, pairToOpen.get(0).y);
            int v = codePair(pairToOpen.get(1).x, pairToOpen.get(1).y);
            if(!board.match(u, v)){
                System.err.println("set invisible");
                pairToOpen.get(0).sign.setVisible(false);
                pairToOpen.get(1).sign.setVisible(false);
            }
            pairToOpen.clear();
            if (board.gameOver()){
                displayResult();
            }
        }

    }

}