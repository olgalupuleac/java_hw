package ru.spbau202.lupuleac.TicTacToe;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.TicTacToe.Bot.Bot;
import ru.spbau202.lupuleac.TicTacToe.Bot.MiniMaxBot;
import ru.spbau202.lupuleac.TicTacToe.Bot.RandomBot;
import ru.spbau202.lupuleac.TicTacToe.Logic.Board;

/**
 * Class which keeps the settings of the game.
 * Access is package private because the class is used only inside the package.
 */
class Settings {

    private PlayMode playMode;

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public enum PlayMode {
        HOT_SEAT,
        BOT
    }

    private int botLevel;
    private Board.Player player = Board.Player.NOUGHT;

    void setBotLevel(int botLevel) {
        this.botLevel = botLevel;
    }

    /**
     * Creates the instance of the bot depending on the bot level.
     *
     * @return an instance of the bot.
     */
    @NotNull
    Bot createBot() {
        if (botLevel == 1) {
            return new RandomBot(player);
        }
        return new MiniMaxBot(player);
    }
}
