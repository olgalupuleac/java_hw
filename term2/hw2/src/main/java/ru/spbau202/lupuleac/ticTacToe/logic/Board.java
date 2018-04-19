package ru.spbau202.lupuleac.ticTacToe.logic;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.ticTacToe.bot.Bot;

/**
 * Class which represents Tic Tac Toe board.
 */
public class Board {
    private SquareState[][] board = new SquareState[3][3];
    private Player currentPlayer = Player.CROSS;
    private GameStatus gameStatus = GameStatus.GAME_CONTINUES;
    private int numberOfBusySquares;
    private ComboWrapper winningCombo;

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = SquareState.EMPTY;
            }
        }
    }

    //package private access for tests
    int getNumberOfBusySquares(){
        return numberOfBusySquares;
    }

    @NotNull
    public SquareState getSquare(int x, int y){
        return board[x][y];
    }

    private Board(@NotNull Board other) {
        this.numberOfBusySquares = other.numberOfBusySquares;
        this.currentPlayer = other.currentPlayer;
        this.gameStatus = other.gameStatus;
        this.winningCombo = other.winningCombo;
        board = new SquareState[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(other.board[i], 0, board[i], 0, 3);
        }
    }

    /**
     * Verifies if the move can be made on a square with the specified coordinates.
     *
     * @param x is  x coordinate
     * @param y is y coordinate
     * @return true if the game on board continues and the specified square is empty
     */
    public boolean verify(int x, int y) {
        return board[x][y] == SquareState.EMPTY && gameStatus == GameStatus.GAME_CONTINUES;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Makes move on a given square.
     *
     * @param x is x coordinate of a square where a move is to be made
     * @param y is y coordinate of a square where a move is to be made
     * @throws InvalidMoveException when the specified move cannot be made
     */
    public void makeMove(int x, int y) {
        if(!verify(x, y)){
            throw new InvalidMoveException();
        }
        numberOfBusySquares++;
        board[x][y] = currentPlayer.toSquareState();
        if (checkForWin()) {
            gameStatus = currentPlayer.toGameStatus();
        } else if (numberOfBusySquares == 9) {
            gameStatus = GameStatus.DRAW;
        }
        currentPlayer = currentPlayer.opponent();
    }

    /**
     * Makes the move on board.
     *
     * @param move is a move to be made.
     */
    public void makeMove(@NotNull Bot.Move move) {
        makeMove(move.getX(), move.getY());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Clears the board for a new game.
     **/
    public void clear() {
        winningCombo = null;
        currentPlayer = Player.CROSS;
        numberOfBusySquares = 0;
        gameStatus = GameStatus.GAME_CONTINUES;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = SquareState.EMPTY;
            }
        }
    }

    /**
     * Checks if the current player won on board.
     * If so, changes the game status and sets the combination of squares
     * which provided him a victory (a wining combo).
     *
     * @return true if someone won after last move (it could be only a current player).
     */
    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != SquareState.EMPTY
                    && board[i][0] == board[i][1]
                    && board[i][0] == board[i][2]) {
                winningCombo = new ComboWrapper(i, 0, i, 1, i, 2);
                return true;
            }
            if (board[0][i] != SquareState.EMPTY
                    && board[0][i] == board[1][i]
                    && board[0][i] == board[2][i]) {
                winningCombo = new ComboWrapper(0, i, 1, i, 2, i);
                return true;
            }
        }
        if (board[1][1] != SquareState.EMPTY
                && (board[0][0] == board[1][1]
                && board[1][1] == board[2][2])) {
            winningCombo = new ComboWrapper(0, 0, 1, 1, 2, 2);
            return true;
        }
        if (board[1][1] != SquareState.EMPTY
                && (board[0][2] == board[1][1]
                && board[1][1] == board[2][0])) {
            winningCombo = new ComboWrapper(0, 2, 1, 1, 2, 0);
            return true;
        }
        return false;
    }

    public ComboWrapper getWinningCombo() {
        return winningCombo;
    }

    /**
     * Returns a deep copy of this board.
     *
     * @return a deep copy of this board
     */
    @NotNull
    public Board deepCopy() {
        return new Board(this);
    }

    /**
     * Enum which represents a state of a square on board.
     */
    public enum SquareState {
        EMPTY,
        CROSS,
        NOUGHT
    }

    /**
     * Enum which represents a game status.
     */
    public enum GameStatus {
        GAME_CONTINUES,
        CROSS_WON,
        NOUGHT_WON,
        DRAW
    }

    /**
     * Enum which represents a player.
     */
    public enum Player {
        CROSS,
        NOUGHT;

        /**
         * Transforms player to a square state.
         *
         * @return SquareState.CROSS for a CROSS, SquareState.NOUGHT for a NOUGHT
         */
        @Contract(pure = true)
        public SquareState toSquareState() {
            return this == CROSS ? SquareState.CROSS : SquareState.NOUGHT;
        }

        /**
         * Transforms player to a game status
         *
         * @return GameStatus.CROSS_WON for a CROSS, GameStatus.NOUGHT_WON for a NOUGHT
         */
        @Contract(pure = true)
        public GameStatus toGameStatus() {
            return this == CROSS ? GameStatus.CROSS_WON : GameStatus.NOUGHT_WON;
        }

        /**
         * Returns the opponent for this player.
         *
         * @return NOUGHT for CROSS, CROSS for NOUGHT.
         */
        @Contract(pure = true)
        public Player opponent() {
            return this == CROSS ? NOUGHT : CROSS;
        }
    }

    /**
     * Class which keeps a coordinates of three squares, which provides a win.
     */
    public static class ComboWrapper {
        private int[] coords;

        ComboWrapper(int... coords) {
            this.coords = coords;
        }

        public int[] getCoords() {
            return coords;
        }
    }

    /**
     * Exception thrown when the move is invalid.
     */
    public class InvalidMoveException extends RuntimeException {
        public InvalidMoveException(){
            super();
        }
    }
}
