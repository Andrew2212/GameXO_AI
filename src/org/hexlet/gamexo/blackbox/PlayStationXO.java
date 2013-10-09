package org.hexlet.gamexo.blackbox;

import org.hexlet.gamexo.blackbox.game.GameField;
import org.hexlet.gamexo.blackbox.game.GameHistoric;

import java.util.Scanner;

public class PlayStationXO {

    private static final int BOT_ENEMY_VS_BOT = 1;
    private static final int MAN_VS_BOT = 2;
    private static final int BOT_VS_BOT_ENEMY = 3;
    private static final int BOT_VS_BOT_ENEMY_REPEAT = 4;
    private static final String NEW_GAME = "N";
    private static final String REVERSE_HISTORY = "R";
    private static final String QUIT = "Q";
    private static boolean isGameFirst = true;
    private static boolean isQuitPressed = false;


    public static void setGameOptions() {
        String strEnter = null;
        System.out.println("\n***SET GAME OPTIONS:");

        System.out.println("*Set 'size' for the GameField:");
        strEnter = new Scanner(System.in).next();
        GameField.FIELD_SIZE = setGameOptionValueFieldSize(strEnter);


        System.out.println("*Set Number of the Checked Signs:");
        strEnter = new Scanner(System.in).next();
        GameField.NUM_CHECKED = setGameOptionNumChecked(strEnter);

    }

    public static void startGame() {
        if (isGameFirst) {
            startNewGame(selectWhoVsWho());
        }
        if (!isQuitPressed) {
            chooseNewGameReverseHistoryOrQuit();
        }
    }

    //    --------------Private Methods----------------------

    private static void chooseNewGameReverseHistoryOrQuit() {
        //Enter the choice  New or History
        String strEnter = initiateChooseString();

        if (strEnter.equalsIgnoreCase(NEW_GAME)) {
            GameField.resetGameFieldMatrix();
            GameHistoric.clearHistory();
            startNewGame(selectWhoVsWho());
            return;
        }

        else if (strEnter.equalsIgnoreCase(REVERSE_HISTORY)) {

            if (!GameHistoric.isListHistoryEmty()) {

                GameHistoric.removePreviousStep();
                chooseNewGameReverseHistoryOrQuit();

            } else {
                System.out.println("Step History is terminated!");
            }
            startNewGame(selectWhoVsWho());
        }

        else if (strEnter.equalsIgnoreCase(QUIT)) {
            isQuitPressed = true;
            return;
        }
    }

    private static String initiateChooseString() {
        String strEnter = null;
        while (true) {
            printGameChoice();
            strEnter = new Scanner(System.in).next();
            if (isChoiceNewGame(strEnter) || isChoiceReverseHistory(strEnter) || isChoiceQuit(strEnter)) break;
            System.out.println("That'll never fly! Try again!\n");
        }
        return strEnter;
    }


    private static void printGameChoice() {
        System.out.println("\n***Let's choose:\n" +
                "New Game (press '" + NEW_GAME + "')\n" +
                "Reverse History (press '" + REVERSE_HISTORY + "')\n" +
                "Quit (press '" + QUIT + "')");
    }

    private static boolean isChoiceNewGame(String strEnter) {
        return (strEnter.equalsIgnoreCase(NEW_GAME));
    }

    private static boolean isChoiceReverseHistory(String strEnter) {
        return (strEnter.equalsIgnoreCase(REVERSE_HISTORY));
    }

    private static boolean isChoiceQuit(String strEnter) {
        return (strEnter.equalsIgnoreCase(QUIT));
    }

    private static int selectWhoVsWho() {
        isGameFirst = false;

        System.out.println("\n***Game OPTIONS:");
        System.out.println("***GameField Size = " + GameField.FIELD_SIZE + " x " + GameField.FIELD_SIZE);
        System.out.println("***Number of the checked signs = " + GameField.NUM_CHECKED);

        System.out.println("\n***SELECT GAME:");
        System.out.println("*BotEnemy vs Bot: press 1");
        System.out.println("*Human vs Bot: press 2");
        System.out.println("*Bot vs BotEnemy: press 3");
        System.out.println("*Bot vs BotEnemy REPEAT: press 4");

        String strEnter = new Scanner(System.in).next();
        return setGameOptionValueFieldSize(strEnter);
    }

    private static int setGameOptionValueFieldSize(String enter) {
        if (isEnterGameOptionValid(enter)) {
            return Integer.valueOf(enter);
        }
        System.out.println("***That'll never fly! Let's set Default Value");
        return GameField.FIELD_SIZE;
    }


    private static int setGameOptionNumChecked(String enter) {

        int numChecked;
        if (3 < GameField.FIELD_SIZE) {
            numChecked = GameField.NUM_CHECKED_4;
        } else {
            numChecked = GameField.NUM_CHECKED;
        }

//      If enter is NOT valid
        if (!isEnterGameOptionValid(enter)) {
            System.out.println("***That'll never fly! Let's set Default Value");
            return numChecked;
        }

//      If enter is valid
        if (3 < GameField.FIELD_SIZE && Integer.valueOf(enter) < 4) {
            numChecked = GameField.NUM_CHECKED_4;
            return numChecked;
        } else {
            numChecked = Integer.valueOf(enter);
            return numChecked;
        }
    }


    private static boolean isEnterGameOptionValid(String strEnter) {
        return (strEnter.matches("[0-9]+"));
    }

    private static void startNewGame(int kindOfGame) {
        switch (kindOfGame) {

            case BOT_ENEMY_VS_BOT:
                insertGame().gameBotEnemyVsBot();
                break;

            case MAN_VS_BOT:
                insertGame().gameManVsBot();
                break;

            case BOT_VS_BOT_ENEMY:
                insertGame().gameBotVsBotEnemy();
                break;

            case BOT_VS_BOT_ENEMY_REPEAT:
                insertGame().gameBotVsBotEnemyRepeat();
                break;

            default:
                System.out.println("***You set something WRONG! Try again!***\n");
                startGame();
                break;
        }
    }

//    private static Game insertGame() {
public static Game insertGame() {
        return Game.getInstance();

    }
}
