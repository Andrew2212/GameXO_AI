package org.hexlet.gamexo.blackbox;

import org.hexlet.gamexo.blackbox.game.GameField;
import org.hexlet.gamexo.blackbox.game.GameHistoric;

import java.util.Scanner;

public class PlayStationXO {

    private static final int MAN_TO_MAN = 1;
    private static final int MAN_TO_COMP = 2;
    private static final int COMP_TO_COMP = 3;
    private static final String NEW_GAME = "N";
    private static final String REVERSE_HISTORY = "R";
    private static boolean isGameFirst = true;


    public static void setGameOptions() {
        String strEnter = null;
        System.out.println("\n***SET GAME OPTIONS:");

        System.out.println("*Set 'size' for the GameField:");
        strEnter = new Scanner(System.in).next();
        GameField.FIELD_SIZE = setGameOptionValue(strEnter);


        System.out.println("*Set Number of the Checked Signs:");
        strEnter = new Scanner(System.in).next();
        GameField.NUM_CHECKED = setGameOptionValue(strEnter);

    }

    public static void startGame() {

        if (isGameFirst) {
            startNewGame(selectWhoVsWho());
        }
        chooseNewGameOrReverseHistory();
    }

    //    --------------Private Methods----------------------

    private static void chooseNewGameOrReverseHistory() {
        //Enter the choice  New or History
        String strEnter = initiateChooseString();

        if (strEnter.equalsIgnoreCase(NEW_GAME)) {
            GameField.resetGameFieldMatrix();
            GameHistoric.clearHistory();
            startNewGame(selectWhoVsWho());
            return;
        }

        if (strEnter.equalsIgnoreCase(REVERSE_HISTORY)) {

            if (!GameHistoric.isListHistoryEmty()) {

                GameHistoric.removePreviousStep();
                chooseNewGameOrReverseHistory();

            } else {
                System.out.println("Step History is terminated!");
            }
            startNewGame(selectWhoVsWho());
        }

    }

    private static String initiateChooseString() {
        String strEnter = null;
        while (true) {
            printGameChoice();
            strEnter = new Scanner(System.in).next();
            if (isChoiceNewGame(strEnter) || isChoiceReverseHistory(strEnter)) break;
            System.out.println("That'll never fly! Try again!\n");
        }

        return strEnter;
    }


    private static void printGameChoice() {
        System.out.println("\n***Let's choose:\n" +
                "New Game (press '" + NEW_GAME + "')\n" +
                "Reverse History (press '" + REVERSE_HISTORY + "')");
    }

    private static boolean isChoiceNewGame(String strEnter) {
        return (strEnter.equalsIgnoreCase(NEW_GAME));
    }

    private static boolean isChoiceReverseHistory(String strEnter) {
        return (strEnter.equalsIgnoreCase(REVERSE_HISTORY));
    }

    private static int selectWhoVsWho() {

        isGameFirst = false;

        System.out.println("\n***Game OPTIONS:");
        System.out.println("***GameField Size = " + GameField.FIELD_SIZE + " x " + GameField.FIELD_SIZE);
        System.out.println("***Number of the checked signs = " + GameField.NUM_CHECKED);

        System.out.println("\n***SELECT GAME:");
        System.out.println("*Human vs Human: press 1");
        System.out.println("*Human vs Bot: press 2");
        System.out.println("*Bot vs BotEnemy: press 3");

        String strEnter = new Scanner(System.in).next();
        return setGameOptionValue(strEnter);
    }

    private static int setGameOptionValue(String enter) {
        if (isEnterGameOptionValid(enter)) {
            return Integer.valueOf(enter);
        }
        System.out.println("***That'll never fly! Let's set Default Value");
        return GameField.FIELD_SIZE;
    }

    private static boolean isEnterGameOptionValid(String strEnter) {

        return (strEnter.matches("[0-9]+"));
    }

    private static void startNewGame(int kindOfGame) {

        switch (kindOfGame) {

            case MAN_TO_MAN:
                insertGame().gameManToMan();
                break;

            case MAN_TO_COMP:
                insertGame().gameManToBot();
                break;

            case COMP_TO_COMP:
                insertGame().gameBotToBot();
                break;

            default:
                System.out.println("***You set something WRONG! Try again!***\n");
                startGame();
                break;
        }

    }

    private static Game insertGame() {

        return Game.getInstance();
    }
}
