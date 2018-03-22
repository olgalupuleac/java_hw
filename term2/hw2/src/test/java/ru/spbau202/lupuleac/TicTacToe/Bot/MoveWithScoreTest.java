package ru.spbau202.lupuleac.TicTacToe.Bot;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoveWithScoreTest {
    @Test
    public void compareToScore() throws Exception {
        assertTrue((new MiniMaxBot.MoveWithScore(
                null, 10, 8)).compareTo(
                        new MiniMaxBot.MoveWithScore(null, -10, 7)) > 0 );
    }

    @Test
    public void compareToDepthWithPositiveScore() throws Exception {
        assertTrue((new MiniMaxBot.MoveWithScore(
                null, 10, 8)).compareTo(
                new MiniMaxBot.MoveWithScore(null, 10, 7)) < 0 );
    }

    @Test
    public void compareToDepthWithNegativeScore() throws Exception {
        assertTrue((new MiniMaxBot.MoveWithScore(
                null, -10, 8)).compareTo(
                new MiniMaxBot.MoveWithScore(null, -10, 7)) > 0 );
    }
}