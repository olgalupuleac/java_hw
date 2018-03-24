package ru.spbau202.lupuleac.TicTacToe.Logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsTest {
    @Test
    public void testStatistics() throws Exception {
        Statistics statistics = new Statistics();
        statistics.increment(Board.GameStatus.CROSS_WON);
        statistics.increment(Board.GameStatus.CROSS_WON);
        statistics.increment(Board.GameStatus.DRAW);
        statistics.increment(Board.GameStatus.NOUGHT_WON);
        statistics.increment(Board.GameStatus.DRAW);
        statistics.increment(Board.GameStatus.GAME_CONTINUES);
        statistics.increment(null);
        assertEquals(2, statistics.getCrossWins());
        assertEquals(2, statistics.getDraws());
        assertEquals(1, statistics.getNoughtWins());
        assertEquals("Cross won: 2\nNought won: 1\nDraws: 2",
                statistics.showStatistics());
    }


}