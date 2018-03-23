package ru.spbau202.lupuleac.TicTacToe.Logic;

public class Statistics {
    private int crossWins;
    private int draws;
    private int noughtWins;

    public void increment(Board.GameStatus gameStatus) {
        if(gameStatus == Board.GameStatus.CROSS_WON){
            crossWins++;
        }
        if(gameStatus == Board.GameStatus.NOUGHT_WON){
            noughtWins++;
        }
        if(gameStatus == Board.GameStatus.DRAW){
            draws++;
        }
    }

    public String showStatistics(){
        return "Cross won: " +
                crossWins +
                "\nNought won: " +
                noughtWins +
                "\nDraws: " +
                draws;
    }
}
