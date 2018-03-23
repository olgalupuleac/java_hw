package ru.spbau202.lupuleac.TicTacToe.Logic;

import org.jetbrains.annotations.NotNull;

/**
 * Class which represents game statistics.
 */
public class Statistics {
    private int crossWins;
    private int draws;
    private int noughtWins;

    /**
     * Takes the result of the game and increments a number of results of this type.
     *
     * @param gameStatus is a game status to be preserved
     */
    public void increment(Board.GameStatus gameStatus) {
        if (gameStatus == Board.GameStatus.CROSS_WON) {
            crossWins++;
        }
        if (gameStatus == Board.GameStatus.NOUGHT_WON) {
            noughtWins++;
        }
        if (gameStatus == Board.GameStatus.DRAW) {
            draws++;
        }
    }

    /**
     * Returns a message which shows a current statistics.
     *
     * @return a string representation of the statistics
     */
    @NotNull
    public String showStatistics() {
        return "Cross won: " +
                crossWins +
                "\nNought won: " +
                noughtWins +
                "\nDraws: " +
                draws;
    }
}
