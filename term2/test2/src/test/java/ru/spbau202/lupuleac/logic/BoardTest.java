package ru.spbau202.lupuleac.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void allNumbersInPairs() {
        Board board = new Board(8);
        int[] pairs = new int[32];
        for(int i = 0; i < 64; i++){
            pairs[board.getSquare(i)]++;
        }
        for(int i = 0; i < 32; i++){
            assertEquals(2, pairs[i]);
        }
    }

    @Test
    public void match() {
        Board board = new Board(10);
        int matches = 0;
        for(int i = 1; i < 100; i++){
            if(board.match(0, i)){
                matches++;
            }
        }
        assertEquals(1, matches);
    }

    @Test
    public void getOpenedNum(){
        Board board = new Board(10);
        for(int i = 1; i < 100; i++){
            board.match(0, i);
        }
        assertEquals(2, board.getOpenedNum());
    }
}