package hqup.game;

import hqup.PlayStationXO;
import hqup.gamers.GamerBot;
import hqup.gamers.GamerMan;
import hqup.gamers.IGamer;

/**
 * Realises pattern Singleton
 */
public class Game {

    private static Game uniqueInstance;

    private IGamer gamerMan;
    private IGamer gamerBot;
    private GameFieldMatrixChecker gameFieldMatrixChecker;

    private Game() {
        GameField.getNewGameField();
        gameFieldMatrixChecker = new GameFieldMatrixChecker();
        gamerMan = new GamerMan();
        gamerBot = new GamerBot();
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
