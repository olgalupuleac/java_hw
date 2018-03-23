package ru.spbau202.lupuleac.TicTacToe.Bot;

import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

import java.util.ArrayList;
import java.util.Random;

public class RandomBot extends Bot{

    private Random rand = new Random(System.currentTimeMillis());

    public RandomBot(Board.Player player) {
        super(player);
    }

    @Override
    public Move makeMove(Board board) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        return possibleMoves.get(rand.nextInt(possibleMoves.size()));
    }
}
