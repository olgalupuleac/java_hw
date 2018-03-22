package ru.spbau202.lupuleac.TicTacToe;


import org.jetbrains.annotations.Contract;
import ru.spbau202.lupuleac.TicTacToe.Bot.Bot;

import static ru.spbau202.lupuleac.TicTacToe.Board.SquareState.CROSS;
import static ru.spbau202.lupuleac.TicTacToe.Board.SquareState.NOUGHT;

public class Board {
    private SquareState[][] board = new SquareState[3][3];
    private Player currentPlayer = Player.CROSS;
    private GameStatus gameStatus = GameStatus.GAME_CONTINUES;
    private int numberOfBusySquares;
    private ComboWrapper winningCombo;

    public Board(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = SquareState.EMPTY;
            }
        }
    }

    public Board(Board other){
        this.numberOfBusySquares = other.numberOfBusySquares;
        this.currentPlayer = other.currentPlayer;
        this.gameStatus = other.gameStatus;
        this.winningCombo = other.winningCombo;
        board = new SquareState[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = other.board[i][j];
            }
        }
    }

    public boolean verify(int i, int j){
        if(board[i][j] == SquareState.EMPTY && gameStatus == GameStatus.GAME_CONTINUES){
            System.err.println(gameStatus.name());
            System.err.println(board[i][j].name());
        }
        return board[i][j] == SquareState.EMPTY && gameStatus == GameStatus.GAME_CONTINUES;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void makeMove(int i, int j){
        assert board[i][j] == SquareState.EMPTY;
        numberOfBusySquares++;
        board[i][j] = currentPlayer.toSquareState();
        if(isOver()){
            gameStatus = currentPlayer.toGameStatus();
        }
        else if(numberOfBusySquares == 9){
            gameStatus = GameStatus.DRAW;
        }
        currentPlayer = currentPlayer.opponent();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void clear(){
        currentPlayer = Player.CROSS;
        numberOfBusySquares = 0;
        gameStatus = GameStatus.GAME_CONTINUES;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = SquareState.EMPTY;
            }
        }
    }

    private boolean isOver() {
        for(int i = 0; i < 3; i++){
            if(board[i][0] != SquareState.EMPTY
                    && board[i][0] == board[i][1]
                    && board[i][0] == board[i][2]){
                winningCombo = new ComboWrapper(i, 0, i, 1, i, 2);
                return true;
            }
            if(board[0][i] != SquareState.EMPTY
                    && board[0][i] == board[1][i]
                    && board[0][i] == board[2][i]){
                winningCombo = new ComboWrapper( 0, i, 1, i, 2, i);
                return true;
            }
        }
        if(board[1][1] != SquareState.EMPTY
                && (board[0][0] == board[1][1]
                && board[1][1] == board[2][2])){
            winningCombo = new ComboWrapper(0, 0, 1, 1, 2, 2);
            return true;
        }
        if(board[1][1] != SquareState.EMPTY
                && (board[0][2] == board[1][1]
                && board[1][1] == board[2][0])){
            winningCombo = new ComboWrapper(0, 2, 1, 1, 2, 0);
            return true;
        }
        return false;
    }

    public ComboWrapper getWinningCombo(){
        return winningCombo;
    }

    public Board deepCopy() {
        return new Board(this);
    }

    public void makeMove(Bot.Move move) {
        makeMove(move.getX(), move.getY());
    }

    public void discardChanges(Bot.Move move) {
        assert board[move.getX()][move.getY()] != SquareState.EMPTY;
        numberOfBusySquares--;
        gameStatus = GameStatus.GAME_CONTINUES;
        board[move.getX()][move.getY()] = SquareState.EMPTY;
        currentPlayer = currentPlayer.opponent();
    }

    public enum SquareState {
        EMPTY,
        CROSS,
        NOUGHT
    }

    public enum GameStatus {
        GAME_CONTINUES,
        CROSS_WON,
        NOUGHT_WON,
        DRAW
    }

    public enum Player {
        CROSS,
        NOUGHT;

        @Contract(pure = true)
        public SquareState toSquareState(){
            return this == CROSS ? SquareState.CROSS : SquareState.NOUGHT;
        }

        @Contract(pure = true)
        public GameStatus toGameStatus(){
            return this == CROSS ? GameStatus.CROSS_WON : GameStatus.NOUGHT_WON;
        }

        @Contract(pure = true)
        public Player opponent() {
            return this == CROSS ? NOUGHT : CROSS;
        }
    }

    public static class ComboWrapper {
        private int[] coords;
        public ComboWrapper(int... coords){
            this.coords = coords;
        }

        public int[] getCoords(){
            return coords;
        }
    }

    /**
     * Prints board in console. Used for debug.
     *
     */
    @SuppressWarnings("unchecked")
    public  void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.err.print("=");
        }
        System.err.println();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.err.print(toChar(board[i][j]));
            }
            System.err.println();
        }
    }

    /**
     * Transforms square status to char. Used for console output in debug.
     *
     * @param t is a Status to be transformed
     * @return char relevant to the given status
     */
    private static char toChar(Board.SquareState t) {
        if (t == CROSS) {
            return 'x';
        }
        if (t == NOUGHT) {
            return '0';
        }
        return '-';
    }
}
