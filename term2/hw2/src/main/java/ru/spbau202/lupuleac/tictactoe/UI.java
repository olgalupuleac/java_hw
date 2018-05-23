package ru.spbau202.lupuleac.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.tictactoe.bot.Bot;
import ru.spbau202.lupuleac.tictactoe.logic.Board;
import ru.spbau202.lupuleac.tictactoe.logic.Statistics;

/**
 * Class which launches the application and represents the user interface.
 */
public class UI extends Application {
    private Stage window;
    private Board board = new Board();
    private Bot bot;
    private Pane root;
    private Box[][] grid;
    private Settings settings = new Settings();
    private Statistics statistics = new Statistics();

    public static void main(String[] args) {
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
        window = primaryStage;
        window.setMinHeight(300);
        window.setMinWidth(300);
        primaryStage.setScene(choosePlayMode());
        primaryStage.show();
    }

    /**
     * Shows the first window where the player chooses a play mode.
     *
     * @return the scene which is set as a scene to a primary stage
     */
    @NotNull
    private Scene choosePlayMode() {
        window.setMinWidth(100);
        window.setMinHeight(100);
        window.setTitle("Choose play mode");
        GridPane grid = new GridPane();
        drawBoxesInGridPane(grid, 7, 5);
        Button hotSeat = new Button("Play with friend");
        hotSeat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Button gameWithBot = new Button("Play with bot");
        gameWithBot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gameWithBot.setOnAction(e -> {
            settings.setPlayMode(Settings.PlayMode.BOT);
            window.setTitle("Choose bot level");
            window.setScene(chooseBotLevel());
        });
        hotSeat.setOnAction(e -> {
            settings.setPlayMode(Settings.PlayMode.HOT_SEAT);
            window.setScene(mainScene());
        });
        Button showGameStatistics = new Button("Show game statistics");
        showGameStatistics.setOnAction(e -> window.setScene(showStatistics()));
        showGameStatistics.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.add(hotSeat, 2, 1);
        grid.add(gameWithBot, 2, 3);
        grid.add(showGameStatistics, 2, 5);
        return new Scene(grid);
    }

    /**
     * Displays the window with the statistics: number of cross wins,
     * number of nought wins, number of draws.
     *
     * @return the scene which is set as a scene to a primary stage
     */
    @NotNull
    private Scene showStatistics() {
        Pane root = new Pane();
        root.setPrefSize(600, 600);
        Label label = new Label(statistics.showStatistics());
        Button newGame = new Button("New Game");
        newGame.setAlignment(Pos.BOTTOM_CENTER);
        newGame.setLayoutX(270);
        newGame.setLayoutY(30);
        newGame.setOnAction(e -> restart());
        root.getChildren().addAll(label, newGame);
        return new Scene(root);
    }

    /**
     * Creates boxes on grid pane, sets its size and color.
     *
     * @param grid    is a grid to be changed
     * @param numRows is number of rows on grid pane to be created
     * @param numCols is number of columns on grid pane to be created
     */
    private void drawBoxesInGridPane(GridPane grid, int numRows, int numCols) {
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS); // allow row to grow
            rc.setFillHeight(true); // ask nodes to fill height for row
            grid.getRowConstraints().add(rc);
        }
        for (int colIndex = 0; colIndex < numCols; colIndex++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS); // allow column to grow
            cc.setFillWidth(true); // ask nodes to fill space for column
            grid.getColumnConstraints().add(cc);
        }
        grid.setPrefSize(600, 600);
        grid.setStyle("-fx-background-color: #0000ff");
    }

    /**
     * Shows the window where the player chooses a game level.
     *
     * @return the scene which is set as a scene to a primary stage
     */
    @NotNull
    private Scene chooseBotLevel() {
        GridPane grid = new GridPane();
        drawBoxesInGridPane(grid, 5, 3);
        Button bot1 = new Button("Easy");
        Button bot2 = new Button("Hard");
        bot1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bot2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bot2.setOnAction(e -> {
            settings.setBotLevel(2);
            window.setScene(mainScene());
        });
        bot1.setOnAction(e -> {
            settings.setBotLevel(1);
            window.setScene(mainScene());
        });
        bot = settings.createBot();
        grid.add(bot1, 1, 1);
        grid.add(bot2, 1, 3);
        return new Scene(grid);
    }

    /**
     * Creates the main scene where game happens.
     *
     * @return the scene which is set as a scene to a primary stage
     */
    @NotNull
    private Scene mainScene() {
        window.setTitle("Game");
        window.setMinHeight(600);
        window.setMinWidth(600);
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
        bot = null;
        statistics.increment(board.getGameStatus(), settings.getPlayMode());
        board.clear();
        window.setScene(choosePlayMode());
    }

    /**
     * Draws the grid on the main scene.
     */
    private void drawGrid() {
        grid = new Box[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = new Box(j, i);
                root.getChildren().addAll(grid[i][j]);
            }
        }
    }

    /**
     * Highlights the squares which provided a win on board.
     *
     * @param squares is squares to be highlighted
     */
    private void highlight(Board.ComboWrapper squares) {
        if (squares == null) {
            return;
        }
        for (int i = 0; i < squares.getCoords().length; i += 2) {
            grid[squares.getCoords()[i + 1]][squares.getCoords()[i]].setStyle("-fx-background-color: red;");
        }
    }

    /**
     * Displays result of this game.
     */
    private void displayResult() {
        highlight(board.getWinningCombo());
        root = new Pane();
        root.setPrefSize(200, 200);
        Label result = new Label();
        result.setFont(Font.font(30));
        if (board.getGameStatus() == Board.GameStatus.CROSS_WON) {
            result.setText("CROSS WON!");
        }
        if (board.getGameStatus() == Board.GameStatus.NOUGHT_WON) {
            result.setText("NOUGHT WON!");
        }
        if (board.getGameStatus() == Board.GameStatus.DRAW) {
            result.setText("DRAW!");
        }
        result.setTextAlignment(TextAlignment.JUSTIFY);
        Button ok = new Button("Ok");
        ok.setLayoutY(100);
        ok.setLayoutX(100);
        ok.setOnAction(e -> ((Stage) ok.getScene().getWindow()).close());
        root.getChildren().addAll(result, ok);
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
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            sign.setFont(Font.font(80));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, sign);
            setTranslateX(x * 200);
            setTranslateY(y * 200);
            setOnMouseClicked(event -> move());
        }

        /**
         * Handles the player's move.
         */
        private void move() {
            if (bot != null && bot.getPlayer() == board.getCurrentPlayer()
                    || !board.verify(x, y)) {
                return;
            }
            setSign(board.getCurrentPlayer());
            board.makeMove(x, y);
            if (board.getGameStatus() != Board.GameStatus.GAME_CONTINUES) {
                UI.this.displayResult();
                return;
            }
            if (bot != null) {
                botsMove(bot.makeMove(board));
            }
        }

        private void botsMove(Bot.Move move) {
            board.makeMove(move);
            UI.this.grid[move.getY()][move.getX()].setSign(bot.getPlayer());
            if (board.getGameStatus() != Board.GameStatus.GAME_CONTINUES) {
                displayResult();
            }
        }

        /**
         * Draws the sign on square
         *
         * @param player is a player which sign is to be set
         */
        private void setSign(Board.Player player) {
            if (player == Board.Player.CROSS) {
                sign.setText("X");
            } else {
                sign.setText("O");
            }
        }
    }
}
