package ru.spbau202.lupuleac.ticTacToe.bot;

import org.junit.Before;
import org.junit.Test;
import ru.spbau202.lupuleac.ticTacToe.logic.Board;

import static org.junit.Assert.*;

public class MiniMaxBotTest {
    private int numberOfWins;
    private int numberOfLoses;
    private int numberOfDraws;

    @Before
    public void clear(){
        numberOfLoses = 0;
        numberOfWins = 0;
        numberOfDraws = 0;
    }

    @Test
    public void playWithRandomBotForCross() throws Exception {
        Board board = new Board();
        MiniMaxBot bot = new MiniMaxBot(Board.Player.CROSS);
        RandomBot opponent = new RandomBot(Board.Player.NOUGHT);
        for(int i = 0; i < 100; i++){
            while(board.getGameStatus() == Board.GameStatus.GAME_CONTINUES){
                if(board.getCurrentPlayer() == bot.getPlayer()){
                    board.makeMove(bot.makeMove(board));
                }
                else {
                    board.makeMove(opponent.makeMove(board));
                }
            }
            if(board.getGameStatus() == Board.GameStatus.DRAW){
                numberOfDraws++;
            }
            if(board.getGameStatus() == Board.GameStatus.NOUGHT_WON){
                numberOfLoses++;
            }
            if(board.getGameStatus() == Board.GameStatus.CROSS_WON){
                numberOfWins++;
            }
            board.clear();
        }
        assertEquals(0, numberOfLoses);
        System.out.printf("wins = %d, draws = %d\n", numberOfWins, numberOfDraws);
    }

    @Test
    public void playWithRandomBotForNought() throws Exception {
        Board board = new Board();
        MiniMaxBot bot = new MiniMaxBot(Board.Player.NOUGHT);
        RandomBot opponent = new RandomBot(Board.Player.CROSS);
        for(int i = 0; i < 100; i++){
            while(board.getGameStatus() == Board.GameStatus.GAME_CONTINUES){
                if(board.getCurrentPlayer() == bot.getPlayer()){
                    board.makeMove(bot.makeMove(board));
                }
                else {
                    board.makeMove(opponent.makeMove(board));
                }
            }
            if(board.getGameStatus() == Board.GameStatus.DRAW){
                numberOfDraws++;
            }
            if(board.getGameStatus() == Board.GameStatus.NOUGHT_WON){
                numberOfWins++;
            }
            if(board.getGameStatus() == Board.GameStatus.CROSS_WON){
                numberOfLoses++;
            }
            board.clear();
        }
        assertEquals(0, numberOfLoses);
        System.out.printf("wins = %d, draws = %d\n", numberOfWins, numberOfDraws);
    }
}