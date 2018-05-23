package ru.spbau202.lupuleac.logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which represents game on board.
 */
public class Board {
    private int n;
    private int openedNum;
    private int[] board;
    private boolean[] opened;

    /**
     * Creates the board with n * n squares.
     * @param n is size of every dimension
     */
    public Board(int n){
        this.n = n;
        board = new int[n * n];
        opened = new boolean[n * n];
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < n * n; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        for(int i = 0; i < list.size() / 2; i++){
            board[list.get(2 * i)] = i;
            board[list.get(2 * i + 1)] = i;
        }
    }

    /**
     * Return true if the squares matches.
     * @param x is a number of the first square
     * @param y is a number of the second square
     * @return true if the numbers on squares are equal
     */
    public boolean match(int x, int y){
        if(x == y || opened[x] || opened[y]){
           return false;
        }
        if(board[x] == board[y]){
            opened[x] = true;
            opened[y] = true;
            openedNum += 2;
            return true;
        }
        return false;
    }

    public int getSquare(int i){
        return board[i];
    }

    public boolean isOpened(int i){
        return opened[i];
    }

    public boolean gameOver(){
        return openedNum == n * n;
    }

    public int getOpenedNum() {
        return openedNum;
    }
}
