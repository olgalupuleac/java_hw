package ru.spbau202.lupuleac.ticTacToe.bot;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.ticTacToe.logic.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Tic Tac Toe bot.
 */
public abstract class Bot {
    Bot(Board.Player player) {
        this.player = player;
    }

    protected Board.Player player;

    /**
     * Class which represents a move on board.
     * Keeps the coordinates of the move.
     */
    public static class Move {
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Move && ((Move) o).y == y && ((Move) o).x == x;
        }
    }

    /**
     * Analyses the board and chooses the move to be made.
     *
     * @param board is a board where the returned move is to be made
     * @return move to be made on the specified board
     */
    public abstract Move makeMove(Board board);

    /**
     * Returns a List of all possible moves for a specified board.
     *
     * @param board is a board from which the possible moves are to be got.
     * @return a List of possible moves
     */
    @NotNull
    public static List<Move> getPossibleMoves(@NotNull Board board) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.verify(i, j)) {
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
        return possibleMoves;
    }

    public Board.Player getPlayer() {
        return player;
    }
}
