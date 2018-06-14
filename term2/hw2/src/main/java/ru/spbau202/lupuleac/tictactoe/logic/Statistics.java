package ru.spbau202.lupuleac.tictactoe.logic;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.tictactoe.Settings;

/**
 * Class which represents game statistics.
 */
public class Statistics {
    private int crossWins;
    private int draws;
    private int noughtWins;
    private int botWins;
    private int botDraws;
    private int botLoses;


    private void incrementHotSeatStatistics(Board.GameStatus gameStatus) {
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

    private void incrementBotStatistics(Board.GameStatus gameStatus) {
        if (gameStatus == Board.GameStatus.CROSS_WON) {
            botLoses++;
        }
        if (gameStatus == Board.GameStatus.NOUGHT_WON) {
            botWins++;
        }
        if (gameStatus == Board.GameStatus.DRAW) {
            botDraws++;
        }
    }

    /**
     * Takes the result of the game and increments a number of results of this type.
     *
     * @param gameStatus is a game status to be preserved
     * @param playMode   is a play mode of the game which result is to be preserved
     */
    public void increment(Board.GameStatus gameStatus, Settings.PlayMode playMode) {
        if(playMode == null){
            return;
        }
        if (playMode == Settings.PlayMode.HOT_SEAT) {
            incrementHotSeatStatistics(gameStatus);
        }
        if (playMode == Settings.PlayMode.BOT) {
            incrementBotStatistics(gameStatus);
        }
    }

    /**
     * Returns a message which shows a current statistics.
     *
     * @return a string representation of the statistics
     */
    @NotNull
    public String showStatistics() {
        return "\nHot seat games:\nCross won: " +
                crossWins +
                "\nNought won: " +
                noughtWins +
                "\nDraws: " +
                draws +
                "\n\nGames with bot:\nYou won: " +
                botLoses + "\nBot won: " + botWins
                + "\nDraws: " + botDraws;
    }

    public int getCrossWins() {
        return crossWins;
    }

    public int getDraws() {
        return draws;
    }

    public int getNoughtWins() {
        return noughtWins;
    }

    public int getBotWins() {
        return botWins;
    }

    public int getBotDraws() {
        return botDraws;
    }

    public int getBotLoses() {
        return botLoses;
    }
}
