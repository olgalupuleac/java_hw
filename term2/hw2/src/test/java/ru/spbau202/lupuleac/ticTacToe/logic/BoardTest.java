package ru.spbau202.lupuleac.ticTacToe.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.GameStatus.CROSS_WON;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.GameStatus.DRAW;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.GameStatus.NOUGHT_WON;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.SquareState.CROSS;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.SquareState.EMPTY;
import static ru.spbau202.lupuleac.ticTacToe.logic.Board.SquareState.NOUGHT;

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
        board.makeMove(0, 0);
        assertEquals(CROSS, board.getSquare(0, 0));
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(i != 0 || j != 0){
                    assertEquals(EMPTY, board.getSquare(i, j));
                }
            }
        }
    }

    @Test
    public void getCurrentPlayer() throws Exception {
        assertEquals(Board.Player.CROSS, board.getCurrentPlayer());
        board.makeMove(0, 0);
        assertEquals(Board.Player.NOUGHT, board.getCurrentPlayer());
        board.makeMove(1, 0);
        assertEquals(Board.Player.CROSS, board.getCurrentPlayer());
    }

    @Test
    public void clear() throws Exception {
        board.makeMove(2, 0);
        board.makeMove(1, 1);
        board.makeMove(1, 0);
        board.makeMove(2, 2);
        board.makeMove(0, 1);
        board.makeMove(0, 0);
        board.clear();
        assertNull(board.getWinningCombo());
        assertEquals(Board.Player.CROSS, board.getCurrentPlayer());
        assertEquals(Board.GameStatus.GAME_CONTINUES, board.getGameStatus());
        assertEquals(0, board.getNumberOfBusySquares());
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                assertEquals(EMPTY, board.getSquare(i, j));
            }
        }
    }

    @Test
    public void getWinningComboVertical() throws Exception {
        board.makeMove(0, 0);
        board.makeMove(1, 0);
        board.makeMove(0, 1);
        board.makeMove(2, 0);
        board.makeMove(0, 2);
        assertEquals(CROSS_WON, board.getGameStatus());
        int[] expected = new int[6];
        expected[3] = 1;
        expected[5] = 2;
        assertArrayEquals(expected, board.getWinningCombo().getCoords());
    }

    @Test
    public void getWinningComboHorizontal() throws Exception {
        board.makeMove(0, 2);
        board.makeMove(1, 0);
        board.makeMove(1, 1);
        board.makeMove(2, 0);
        board.makeMove(0, 1);
        board.makeMove(0, 0);
        assertEquals(NOUGHT_WON, board.getGameStatus());
        int[] expected = new int[6];
        expected[2] = 1;
        expected[4] = 2;
        assertArrayEquals(expected, board.getWinningCombo().getCoords());
    }

    @Test
    public void getWinningComboFirstDiagonal() throws Exception {
        board.makeMove(0, 0);
        board.makeMove(1, 0);
        board.makeMove(1, 1);
        board.makeMove(2, 0);
        board.makeMove(2, 2);
        assertEquals(CROSS_WON, board.getGameStatus());
        int[] expected = new int[6];
        expected[2] = 1;
        expected[3] = 1;
        expected[4] = 2;
        expected[5] = 2;
        assertArrayEquals(expected, board.getWinningCombo().getCoords());
    }


    @Test
    public void getWinningComboSecondDiagonal() throws Exception {
        board.makeMove(0, 0);
        board.makeMove(2, 0);
        board.makeMove(1, 0);
        board.makeMove(0, 2);
        board.makeMove(2, 2);
        board.makeMove(1, 1);
        assertEquals(NOUGHT_WON, board.getGameStatus());
        int[] expected = new int[6];
        expected[2] = 1;
        expected[3] = 1;
        expected[1] = 2;
        expected[4] = 2;
        assertArrayEquals(expected, board.getWinningCombo().getCoords());
    }


    /*x x 0
      0 0 x
      x x 0*/
    @Test
    public void draw() throws Exception {
        board.makeMove(0, 0);
        board.makeMove(1, 1);
        board.makeMove(1, 0);
        board.makeMove(2, 0);
        board.makeMove(0, 2);
        board.makeMove(0, 1);
        board.makeMove(2, 1);
        board.makeMove(2, 2);
        board.makeMove(1, 2);
        assertEquals(DRAW, board.getGameStatus());
    }

    @Test
    public void deepCopy() throws Exception {
        board.makeMove(0, 0);
        board.makeMove(2, 0);
        board.makeMove(1, 0);
        board.makeMove(0, 2);
        board.makeMove(2, 2);
        Board other = board.deepCopy();
        assertEquals(Board.Player.NOUGHT, other.getCurrentPlayer());
        assertEquals(5, other.getNumberOfBusySquares());
        assertEquals(CROSS, other.getSquare(0, 0));
        assertEquals(NOUGHT, other.getSquare(2, 0));
        assertEquals(CROSS, other.getSquare(1, 0));
        assertEquals(NOUGHT, other.getSquare(0, 2));
        assertEquals(CROSS, other.getSquare(2, 2));
        assertEquals(EMPTY, other.getSquare(0, 1));
        assertEquals(EMPTY, other.getSquare(1, 1));
        assertEquals(EMPTY, other.getSquare(1, 2));
        assertEquals(EMPTY, other.getSquare(2, 1));
    }

    @Test(expected = Board.InvalidMoveException.class)
    public void invalidMove() throws Exception{
        board.makeMove(1, 0);
        board.makeMove(1, 0);
    }

    @Test
    public void verifyOnBoardWhereGameIsOver() throws Exception {
        board.makeMove(0, 2);
        board.makeMove(1, 0);
        board.makeMove(1, 1);
        board.makeMove(2, 0);
        board.makeMove(0, 1);
        board.makeMove(0, 0);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                assertFalse(board.verify(i, j));
            }
        }
    }

}