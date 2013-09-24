package org.hexlet.gamexo.blackbox;

import org.hexlet.gamexo.blackbox.game.GameField;
import org.hexlet.gamexo.blackbox.game.GameFieldMatrixChecker;
import org.hexlet.gamexo.blackbox.game.GameFieldPainter;
import org.hexlet.gamexo.blackbox.players.PlayerBot;
import org.hexlet.gamexo.blackbox.players.IPlayer;
import org.hexlet.gamexo.blackbox.players.PlayerMan;

/**
 * Realises pattern Singleton
 */
public class Game {

    private static Game uniqueInstance;

    private IPlayer gamerMan;
    private IPlayer gamerBot;
    private GameFieldMatrixChecker gameFieldMatrixChecker;

    private Game() {
        GameField.getNewGameField();
        gameFieldMatrixChecker = new GameFieldMatrixChecker();
        gamerMan = new PlayerMan();
        gamerBot = new PlayerBot(GameField.FIELD_SIZE, GameField.NUM_CHECKED);
    }

//----------Public Methods------------------

    public static Game getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    public void gameManToMan() {

        GameFieldPainter.showFields();

        while (true) {

            GameField.setSignToCell(gamerMan);
            GameFieldPainter.showFields();
            System.out.println("--------------------------------\n");

            if (gameFieldMatrixChecker.isGameOver()) {
                break;
            }
        }
        PlayStationXO.startGame();
    }

    public void gameManToBot() {

        GameFieldPainter.showFields();

        while (true) {

            GameField.setSignToCell(gamerMan);
            GameFieldPainter.showFields();
            System.out.println("--------------------------------\n");

            if (gameFieldMatrixChecker.isGameOver()) {
                break;
            }

            GameField.setSignToCell(gamerBot);
            GameFieldPainter.showFields();
            System.out.println("--------------------------------\n");

            if (gameFieldMatrixChecker.isGameOver()) {
                break;
            }
        }
        PlayStationXO.startGame();

    }

    public void gameBotToBot() {

        GameFieldPainter.showFields();

        while (true) {
            GameField.setSignToCell(gamerBot);
            GameFieldPainter.showFields();
            System.out.println("--------------------------------\n");

            if (gameFieldMatrixChecker.isGameOver()) {
                break;
            }
        }
        PlayStationXO.startGame();
    }

}
