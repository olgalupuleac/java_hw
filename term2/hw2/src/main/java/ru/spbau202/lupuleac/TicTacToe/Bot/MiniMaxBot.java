package ru.spbau202.lupuleac.TicTacToe.Bot;


import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.TicTacToe.Board;

import java.util.Random;




/**
 * Bot which implements the MiniMax algorithm.
 */
public class MiniMaxBot extends Bot {
    public MiniMaxBot(Board.Player player){
        super(player);
    }
    @Override
    public Move makeMove(Board board) {
        MoveWithScore res = miniMax(board, 0, null);
        System.err.printf("res = %d\n", res.score);
        return res.move;
    }

    /**
     * The body of the algorithm.
     *
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private MoveWithScore miniMax(Board givenBoard, int currentDepth, Move turn) {
        int maxDepth = 9;
        if (currentDepth++ == maxDepth || givenBoard.getGameStatus() != Board.GameStatus.GAME_CONTINUES) {
            givenBoard.printBoard();
            System.err.printf("score = %d\n", score(givenBoard));
            return new MoveWithScore(turn, score(givenBoard), currentDepth - 1);
        }
        if (givenBoard.getCurrentPlayer() == player) {
            return getMax(givenBoard, currentDepth);
        } else {
            return getMin(givenBoard, currentDepth);
        }
    }

    /**
     * Play the move with the highest score.
     *
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private MoveWithScore getMax(Board givenBoard, int currentDepth) {
        MoveWithScore best = null;
        for (Move move : getPossibleMoves(givenBoard)) {
            givenBoard.makeMove(move);
            MoveWithScore moveWithScore = miniMax(givenBoard, currentDepth, move);
            if(best == null || best.compareTo(moveWithScore) < 0){
                best = moveWithScore;
            }
            if(best.compareTo(moveWithScore) == 0){
                Random rand = new Random(System.currentTimeMillis());
                if(rand.nextInt(2) == 0){
                    best = moveWithScore;
                }
            }
            givenBoard.discardChanges(move);
        }
        return best;
    }

    /**
     * Play the move with the highest score.
     *
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private MoveWithScore getMin(Board givenBoard, int currentDepth) {
        MoveWithScore best = null;
        for (Move move : getPossibleMoves(givenBoard)) {
            givenBoard.makeMove(move);
            MoveWithScore moveWithScore = miniMax(givenBoard, currentDepth, move);
            if(best == null || best.compareTo(moveWithScore) > 0){
                best = moveWithScore;
            }
            if(best.compareTo(moveWithScore) == 0){
                Random rand = new Random(System.currentTimeMillis());
                if(rand.nextInt(2) == 0){
                    best = moveWithScore;
                }
            }
            givenBoard.discardChanges(move);
        }
        return best;
    }



    /**
     * Get the score of the board.
     *
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

    /**
     * Class which represents a move, a score relevant to this move and number of turns
     * before the game is over (or max recursion depth if the end of game wasn't reached).
     */
    static class MoveWithScore implements Comparable {
        private Move move;
        private int score;
        private int depth;

        MoveWithScore(Move move, int score, int depth) {
            this.move = move;
            this.score = score;
            this.depth = depth;
        }

       /**
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         */
        @Override
        public int compareTo(@NotNull Object o) {
            if(score != ((MoveWithScore)o).score){
                return score - ((MoveWithScore)o).score;
            }
            return (((MoveWithScore)o).depth - depth) * score;
        }
    }



}