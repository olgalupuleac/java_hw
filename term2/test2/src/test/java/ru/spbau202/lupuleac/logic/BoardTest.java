package ru.spbau202.lupuleac.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void allNumbersInPairs(){
        Board board = new Board(8);
        int[] pairs = new int[32];
        for(int i = 0; i < 64; i++){
            pairs[board.getSquare(i)]++;
        }
        for(int i = 0; i < 32; i++){
            assertEquals(2, pairs[i]);
        }
    }

}