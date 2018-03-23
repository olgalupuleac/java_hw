package ru.spbau202.lupuleac.TicTacToe.Bot;


import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.TicTacToe.Logic.Board;


/**
 * An implementation of MiniMax algorithm.
 */
public class MiniMaxBot extends Bot {
    private Move bestMove;

    public MiniMaxBot(Board.Player player) {
        super(player);
    }

    @Override
    @NotNull
    public Move makeMove(Board board) {
        miniMax(board, 0);
        return bestMove;
    }

    /**
     * The body of the algorithm.
     *
     * @param board        the Tic Tac Toe board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private int miniMax(Board board, int currentDepth) {
        int maxDepth = 9;
        if (currentDepth++ == maxDepth || board.getGameStatus() != Board.GameStatus.GAME_CONTINUES) {
            return score(board);
        }
        if (board.getCurrentPlayer() == player) {
            return getMax(board, currentDepth);
        } else {
            return getMin(board, currentDepth);
        }
    }

    /**
     * Play the move with the highest score.
     *
     * @param board        the Tic Tac Toe board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private int getMax(Board board, int currentDepth) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move theMove : getPossibleMoves(board)) {
            Board modifiedBoard = board.deepCopy();
            modifiedBoard.makeMove(theMove);
            int score = miniMax(modifiedBoard, currentDepth);
            if (score >= bestScore) {
                bestScore = score;
                bestMove = theMove;
            }

        }
        this.bestMove = bestMove;
        return bestScore;
    }

    /**
     * Play the move with the lowest score.
     *
     * @param board        the Tic Tac Toe board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private int getMin(Board board, int currentDepth) {
        int bestScore = Integer.MAX_VALUE;
        for (Move theMove : getPossibleMoves(board)) {
            Board modifiedBoard = board.deepCopy();
            modifiedBoard.makeMove(theMove);
            int score = miniMax(modifiedBoard, currentDepth);
            if (score <= bestScore) {
                bestScore = score;
            }
        }
        return bestScore;
    }

    /**
     * Get the score of the board.
     *
     * @param board the Tic Tac Toe board to play on
     * @return the score of the board
     */
    private int score(Board board) {

        if (board.getGameStatus() == player.toGameStatus()) {
            return 10;
        }
        if (board.getGameStatus() == player.opponent().toGameStatus()) {
            return -10;
        }
        return 0;
    }


}