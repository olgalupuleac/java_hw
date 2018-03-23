package ru.spbau202.lupuleac.TicTacToe.Bot;

import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

import java.util.ArrayList;

public abstract class Bot {
    public Bot(Board.Player player){
        this.player = player;
    }
    protected Board.Player player;
    public class Move {
        Move(int x, int y){
            this.x = x;
            this.y = y;
        }
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public abstract Move makeMove(Board board);

    ArrayList<Move> getPossibleMoves(Board board){
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board.verify(i, j)){
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
        return possibleMoves;
    }

   public Board.Player getPlayer(){
        return player;
   }
}
