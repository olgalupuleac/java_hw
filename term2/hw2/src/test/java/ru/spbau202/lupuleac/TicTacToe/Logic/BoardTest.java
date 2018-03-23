package ru.spbau202.lupuleac.TicTacToe.Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board;
    @Before
    public void init(){
        board = new Board();
    }

    @Test
    public void verify() throws Exception {
        assertTrue(board.verify(0, 0));
        board.makeMove(0, 0);
        assertFalse(board.verify(0, 0));
    }
    

    @Test
    public void makeMove() throws Exception {
    }

    @Test
    public void makeMove1() throws Exception {
    }

    @Test
    public void getCurrentPlayer() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void getWinningCombo() throws Exception {
    }

    @Test
    public void deepCopy() throws Exception {
    }

}