package ru.spbau202.lupuleac.TicTacToe.Bot;

import org.junit.Test;
import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MiniMaxBotTest {
    private Board mockedBoard = mock(Board.class);

    @Test
    public void loseInOneMove() throws Exception {
        when(mockedBoard.deepCopy()).thenReturn(mockedBoard);
        when(mockedBoard.verify(0, 0)).thenReturn(true);
        when(mockedBoard.verify(0, 1)).thenReturn(false);
        when(mockedBoard.verify(0, 2)).thenReturn(false);
        when(mockedBoard.verify(1, 0)).thenReturn(true);
        when(mockedBoard.verify(1, 1)).thenReturn(false);
        when(mockedBoard.verify(1, 1)).thenReturn(false);
    }

}