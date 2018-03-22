package ru.spbau202.lupuleac.TicTacToe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.TicTacToe.Bot.Bot;


public class UI extends Application {
    private Stage window;
    private Board board = new Board();
    private Bot bot;
    private Pane root;
    private Box[][] grid;
    private Settings settings = new Settings();
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
        window = primaryStage;
        window.setTitle("Choose play mode");
        primaryStage.setScene(choosePlayMode());
        primaryStage.show();
    }

    @NotNull
    private Scene choosePlayMode(){
        Pane root = new Pane();
        root.setPrefSize(200, 200);
        Button hotSeat = new Button("Play with friend");
        hotSeat.setLayoutX(40);
        hotSeat.setLayoutY(70);
        Button gameWithBot = new Button("Play with bot");
        gameWithBot.setLayoutX(40);
        gameWithBot.setLayoutY(30);
        gameWithBot.setOnAction(e -> {
            settings.setPlayMode(Settings.PlayMode.BOT);
            window.setTitle("Choose bot level");
            window.setScene(chooseBotLevel());
        });
        hotSeat.setOnAction(e -> {
            settings.setPlayMode(Settings.PlayMode.HOT_SEAT);
            window.setScene(createContent());
        });
        root.getChildren().addAll(gameWithBot, hotSeat);
        return new Scene(root);
    }

    @NotNull
    private Scene chooseBotLevel(){
        Pane root = new Pane();
        root.setPrefSize(400, 400);
        Button bot1 = new Button("Easy");
        bot1.setLayoutX(200);
        bot1.setLayoutY(300);
        Button bot2 = new Button("Hard");
        bot2.setLayoutX(200);
        bot2.setLayoutY(100);
        bot2.setOnAction(e -> {
            settings.setBotLevel(2);
            window.setScene(createContent());
        });
        bot1.setOnAction(e -> {
            settings.setBotLevel(1);
            window.setScene(createContent());
        });
        bot = settings.createBot();
        root.getChildren().addAll(bot2, bot1);
        return new Scene(root);
    }

    @NotNull
    private Scene createContent() {
        root = new Pane();
        root.setPrefSize(600, 600);
        drawGrid();
        return new Scene(root);
    }

    public static void main(String[] args){
        launch(args);
    }

    private void drawGrid(){
        grid = new Box[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                grid[i][j] = new Box(j, i);
                root.getChildren().addAll(grid[i][j]);
            }
        }
    }

    private class Box extends StackPane {
        private int x;
        private int y;
        private Text sign = new Text();
        private Box(int x, int y){
            this.x = x;
            this.y = y;
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            sign.setFont(Font.font(80));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, sign);
            setTranslateX(x * 200);
            setTranslateY(y * 200);
            setOnMouseClicked(event -> {
                move();
            });
        }

        private void move(){
            if(bot != null && bot.getPlayer() == board.getCurrentPlayer()
                    || !board.verify(x, y)){
                return;
            }
            setSign(board.getCurrentPlayer());
            board.makeMove(x, y);
            if(board.getGameStatus() != Board.GameStatus.GAME_CONTINUES){
                UI.this.displayResult();
            }
            botsMove(bot.makeMove(board));
        }

        private void botsMove(Bot.Move move){
            board.makeMove(move);
            UI.this.grid[move.getY()][move.getX()].setSign(bot.getPlayer());
        }

        private void setSign(Board.Player player){
            if(player == Board.Player.CROSS){
                sign.setText("X");
            }
            else {
                sign.setText("O");
            }
        }
    }

    private void highlight(Board.ComboWrapper squares){
        for(int i = 0; i < squares.getCoords().length; i += 2){
            grid[squares.getCoords()[i + 1]][squares.getCoords()[i]].setStyle("-fx-background-color: red;");
        }
    }

    private void displayResult() {
        highlight(board.getWinningCombo());
        root = new Pane();
        root.setPrefSize(200, 200);
        Label result = new Label();
        result.setFont(Font.font(30));
        if(board.getGameStatus() == Board.GameStatus.CROSS_WON){
            result.setText("CROSS WON!");
        }
        if(board.getGameStatus() == Board.GameStatus.NOUGHT_WON){
            result.setText("NOUGHT WON!");
        }
        if(board.getGameStatus() == Board.GameStatus.NOUGHT_WON){
            result.setText("DRAW!");
        }
        result.setTextAlignment(TextAlignment.JUSTIFY);
        root.getChildren().add(result);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
