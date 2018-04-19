package ru.spbau202.lupuleac.ticTacToe.bot;

import org.junit.Test;
import ru.spbau202.lupuleac.ticTacToe.logic.Board;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotTest {
    @Test
    public void getPossibleMoves() throws Exception {
        Board mockedBoard = mock(Board.class);
        when(mockedBoard.verify(0, 0)).thenReturn(true);
        when(mockedBoard.verify(0, 1)).thenReturn(false);
        when(mockedBoard.verify(0, 2)).thenReturn(false);
        when(mockedBoard.verify(1, 0)).thenReturn(true);
        when(mockedBoard.verify(1, 1)).thenReturn(false);
        when(mockedBoard.verify(1, 2)).thenReturn(false);
        when(mockedBoard.verify(2, 0)).thenReturn(false);
        when(mockedBoard.verify(2, 1)).thenReturn(false);
        when(mockedBoard.verify(2, 2)).thenReturn(true);
        List<Bot.Move> possibleMoves = Bot.getPossibleMoves(mockedBoard);
        List expected = Arrays.asList(new Bot.Move(0, 0),
                new Bot.Move(1, 0), new Bot.Move(2, 2));
        assertThat(possibleMoves, is(expected));
    }

}