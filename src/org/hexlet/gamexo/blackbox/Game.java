package org.hexlet.gamexo.blackbox;

import org.hexlet.gamexo.blackbox.game.GameField;
import org.hexlet.gamexo.blackbox.game.GameFieldMatrixChecker;
import org.hexlet.gamexo.blackbox.game.GameFieldPainter;
import org.hexlet.gamexo.blackbox.players.IPlayer;
import org.hexlet.gamexo.blackbox.players.PlayerBot;
import org.hexlet.gamexo.blackbox.players.PlayerBotEnemy;
import org.hexlet.gamexo.blackbox.players.PlayerMan;

/**
 * Realises pattern Singleton
 */
public class Game {

    private static Game uniqueInstance;

    private static IPlayer gamerMan;
    private static IPlayer gamerBot;
    private static IPlayer gamerBotEnemy;
    private static GameFieldMatrixChecker gameFieldMatrixChecker;

    public Game() {
        gameFieldMatrixChecker = new GameFieldMatrixChecker();
        init();
    }

    private static void init() {
//        System.out.println("Game::init()");
        GameField.getNewGameField();
        gamerMan = new PlayerMan();
        gamerBot = new PlayerBot(GameField.FIELD_SIZE, GameField.NUM_CHECKED);
        gamerBotEnemy = new PlayerBotEnemy(GameField.FIELD_SIZE, GameField.NUM_CHECKED);
    }

//----------Public Methods------------------

    public static Game getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        init();
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

            GameField.setSignToCell(gamerBotEnemy);
            GameFieldPainter.showFields();
            System.out.println("--------------------------------\n");

            if (gameFieldMatrixChecker.isGameOver()) {
                break;
            }
        }
        PlayStationXO.startGame();
    }

}
