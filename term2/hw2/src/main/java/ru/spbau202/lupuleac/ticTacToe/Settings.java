package ru.spbau202.lupuleac.ticTacToe;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.ticTacToe.bot.Bot;
import ru.spbau202.lupuleac.ticTacToe.bot.MiniMaxBot;
import ru.spbau202.lupuleac.ticTacToe.bot.RandomBot;
import ru.spbau202.lupuleac.ticTacToe.logic.Board;

/**
 * Class which keeps the settings of the game.
 * Access is package private because the class is used only inside the package.
 */
public class Settings {

    private PlayMode playMode;

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public PlayMode getPlayMode() {
        return playMode;
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
