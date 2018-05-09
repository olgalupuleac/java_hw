package ru.spbau202.lupuleac.tictactoe.logic;

import org.junit.Test;
import ru.spbau202.lupuleac.tictactoe.Settings;

import static org.junit.Assert.*;

public class StatisticsTest {
    @Test
    public void testStatistics() throws Exception {
        Statistics statistics = new Statistics();
        statistics.increment(Board.GameStatus.CROSS_WON, Settings.PlayMode.BOT);
        statistics.increment(Board.GameStatus.CROSS_WON, Settings.PlayMode.HOT_SEAT);
        statistics.increment(Board.GameStatus.DRAW, Settings.PlayMode.BOT);
        statistics.increment(Board.GameStatus.NOUGHT_WON, Settings.PlayMode.HOT_SEAT);
        statistics.increment(Board.GameStatus.DRAW, Settings.PlayMode.HOT_SEAT);
        statistics.increment(Board.GameStatus.GAME_CONTINUES, Settings.PlayMode.HOT_SEAT);
        statistics.increment(null, Settings.PlayMode.BOT);
        assertEquals(1, statistics.getCrossWins());
        assertEquals(1, statistics.getDraws());
        assertEquals(1, statistics.getNoughtWins());
        assertEquals(1, statistics.getBotLoses());
        assertEquals(1, statistics.getBotDraws());
        assertEquals(0, statistics.getBotWins());
        assertEquals("\nHot seat games:\nCross won: 1\nNought won: 1\nDraws: 1\n" +
                        "\nGames with bot:\nYou won: 1\nBot won: 0\nDraws: 1",
                statistics.showStatistics());
    }


}