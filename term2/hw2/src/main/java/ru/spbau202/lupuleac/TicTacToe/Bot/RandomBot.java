package ru.spbau202.lupuleac.TicTacToe.Bot;

import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

import java.util.List;
import java.util.Random;

/**
 * Bot which makes random moves on board.
 */
public class RandomBot extends Bot{

    private Random rand = new Random(System.currentTimeMillis());

    public RandomBot(Board.Player player) {
        super(player);
    }

    @Override
    public Move makeMove(Board board) {
        List<Move> possibleMoves = getPossibleMoves(board);
        return possibleMoves.get(rand.nextInt(possibleMoves.size()));
    }
}
