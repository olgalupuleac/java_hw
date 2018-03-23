package ru.spbau202.lupuleac.TicTacToe;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.TicTacToe.Bot.Bot;
import ru.spbau202.lupuleac.TicTacToe.Bot.MiniMaxBot;
import ru.spbau202.lupuleac.TicTacToe.Bot.RandomBot;
import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

public class Settings {
    public enum PlayMode {
        HOT_SEAT,
        BOT
    }
    private int botLevel;
    private PlayMode playMode;
    private Board.Player player = Board.Player.NOUGHT;
    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public void setBotLevel(int botLevel){
        this.botLevel = botLevel;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public int getBotLevel() {
        return botLevel;
    }

    @NotNull
    public Bot createBot(){
        if(botLevel == 1){
            return new RandomBot(player);
        }
        return new MiniMaxBot(player);
    }
}
