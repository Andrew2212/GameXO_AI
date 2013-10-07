package org.hexlet.gamexo.ai.brutforceway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 28.09.13
 * Time: 18:49
 * <br>This class is container for "Game Options' that are obtained from rest part of application or calculated here</br>
 */
public class GameOptions {

    private static Character signBot;
    private static Character signEnemy;
    public static final Character DEFAULT_CELL_VALUE = '_';
    private static final Character VALUE_X = 'X';
    private static final Character VALUE_O = 'O';

//    private static char signBot;
//    private static char signEnemy;
//public static final char DEFAULT_CELL_VALUE = '_';
//    private static char  VALUE_X = 'X';
//    private static char VALUE_O = 'O';

    public static int fieldSize;
    public static int numCheckedSigns;

    private static String stringWinnerX = ""; // i.e. string XXXX
    private static String stringWinnerO = ""; // i.e. string OOOO
    private static String stringNearWinner_XXX_ = "";// i.e. string _XXX_ for numChecked = 4
    private static String stringNearWinner_OOO_ = "";// i.e. string _OOO_ for numChecked = 4

    private static List<String> listStringNearWinnerX_1;// i.e. string XXX_ without 1 sign
    private static List<String> listStringNearWinnerO_1; // i.e. string OOO_ without 1 sign
    private static List<String> listStringNearWinnerX_2; // i.e. string XX__ without 2 sign
    private static List<String> listStringNearWinnerO_2;  //i.e. string OO__ without 2 sign

    public static List<String> listStringNearWinBot_1;
    public static List<String> listStringNearWinBot_2;
    public static List<String> listStringNearWinEnemy_1;
    public static List<String> listStringNearWinEnemy_2;

    public static String stringWinnerBot;
    public static String stringWinnerEnemy;
    public static String stringNearWinner_SSS_Bot; // S - either 'X' or 'Y'
    public static String stringNearWinner_SSS_Enemy; // S - either 'X' or 'Y'

    // ---------------------Public Methods-----------------------------

    public static Character getSignEnemy() {
        return signEnemy;
    }

    public static Character getSignBot() {
        return signBot;
    }

    public static void initGameOptions(int fieldSize, int numCheckedSigns) {
//        System.out.println("GameOptions::initGameOptions");
        GameOptions.fieldSize = fieldSize;
        GameOptions.numCheckedSigns = numCheckedSigns;

        stringWinnerX = createStringWinner(VALUE_X);
        stringWinnerO = createStringWinner(VALUE_O);
        stringNearWinner_XXX_ = createStringNearWinner_SSS_(VALUE_X);
        stringNearWinner_OOO_ = createStringNearWinner_SSS_(VALUE_O);
        listStringNearWinnerX_1 = createListStringWin_1(VALUE_X);
        listStringNearWinnerO_1 = createListStringWin_1(VALUE_O);
        listStringNearWinnerX_2 = createListStringWin_2(VALUE_X);
        listStringNearWinnerO_2 = createListStringWin_2(VALUE_O);
    }

    public static void setSignBotAndSignEnemy(char signBot) {
//        System.out.println("GameOptions::setSignBotAndSignEnemy::signBot = " + signBot);
//        if (String.valueOf(signBot).equalsIgnoreCase(VALUE_X)) {
        if (signBot == VALUE_X) {
            //Bot strings
            GameOptions.signBot = VALUE_X;
            GameOptions.stringWinnerBot = stringWinnerX;
            GameOptions.stringNearWinner_SSS_Bot = stringNearWinner_XXX_;
            GameOptions.listStringNearWinBot_1 = listStringNearWinnerX_1;
            GameOptions.listStringNearWinBot_2 = listStringNearWinnerX_2;

            //Enemy strings
            GameOptions.signEnemy = VALUE_O;
            GameOptions.stringWinnerEnemy = stringWinnerO;
            GameOptions.stringNearWinner_SSS_Enemy = stringNearWinner_OOO_;
            GameOptions.listStringNearWinEnemy_1 = listStringNearWinnerO_1;
            GameOptions.listStringNearWinEnemy_2 = listStringNearWinnerO_2;

        } else {
            //Bot strings
            GameOptions.signBot = VALUE_O;
            GameOptions.stringWinnerBot = stringWinnerO;
            GameOptions.stringNearWinner_SSS_Bot = stringNearWinner_OOO_;
            GameOptions.listStringNearWinBot_1 = listStringNearWinnerO_1;
            GameOptions.listStringNearWinBot_2 = listStringNearWinnerO_2;

            //Enemy strings
            GameOptions.signEnemy = VALUE_X;
            GameOptions.stringWinnerEnemy = stringWinnerX;
            GameOptions.stringNearWinner_SSS_Enemy = stringNearWinner_XXX_;
            GameOptions.listStringNearWinEnemy_1 = listStringNearWinnerX_1;
            GameOptions.listStringNearWinEnemy_2 = listStringNearWinnerX_2;
        }
    }

//    ----------------------Private Methods------------------------------------

    /**
     * @param value 'X' or 'O'
     * @return all strings such as '_XX, X_X, XX_, XXX_' (so if numCheckedSigns = 4) and all that
     */
    private static List<String> createListStringWin_1(char value) {
        List<String> listStringNearWinner_1 = new ArrayList<String>();

        for (int i = 0; i < numCheckedSigns; i++) { // number of the strings
            String strWin = "";
            for (int j = 0; j < numCheckedSigns; j++) { // number of the signs
                if (i == j) {
                    strWin += String.valueOf(DEFAULT_CELL_VALUE);
                } else {
                    strWin += value;
                }
            }
            listStringNearWinner_1.add(strWin);
        }

        return listStringNearWinner_1;
    }

    /**
     * @param value 'X' or 'O'
     * @return all strings such as 'XX__, X__X, __XX' (so if numCheckedSigns = 4) and all that
     */
    private static List<String> createListStringWin_2(char value) {
        List<String> listStringNearWinner_2 = new ArrayList<String>();

        for (int i = 0; i < numCheckedSigns - 1; i++) { // number of the strings
            String strWin = "";
            for (int j = 0; j < numCheckedSigns; j++) { // number of the signs
                if (i == j) {
                    strWin += String.valueOf(DEFAULT_CELL_VALUE + "" + DEFAULT_CELL_VALUE);
                    j += 1;
                } else {
                    strWin += value;
                }
            }
            listStringNearWinner_2.add(strWin);
        }
        if (3 < numCheckedSigns) {
//        Add  all strings such as '_X_X'
            listStringNearWinner_2.addAll(createListStringWin_2add(value));
            //        Add  all strings such as '_XX_'
            listStringNearWinner_2.addAll(createListStringWin_2add_(value));
        }
        return listStringNearWinner_2;
    }

    /**
     * @param value 'X' or 'O'
     * @return all strings such as '_X_X, X_X_' (so if numCheckedSigns = 4) and all that
     *         <br>It is called ONLY for fieldSize > 3 </br>
     */
    private static List<String> createListStringWin_2add(char value) {
        List<String> listStringNearWinner_2w = new ArrayList<String>();

        for (int i = 0; i < numCheckedSigns - 2; i++) { // number of the strings
            String strWin = "";
            for (int j = 0; j < numCheckedSigns - 1; j++) { // number of the signs
                if (i == j) {
                    strWin += String.valueOf("" + DEFAULT_CELL_VALUE + value + DEFAULT_CELL_VALUE);
                    j += 1;
                } else {
                    strWin += value;
                }
            }
            listStringNearWinner_2w.add(strWin);
        }
        return listStringNearWinner_2w;
    }

    /**
     * @param value 'X' or 'O'
     * @return all strings such as '_XX_X, X_XX_' (so if numCheckedSigns = 4) and all that
     *         <br>It is called ONLY for fieldSize > 3 </br>
     */
    private static List<String> createListStringWin_2add_(char value) {
        List<String> listStringNearWinner_2w = new ArrayList<String>();

        for (int i = 0; i < numCheckedSigns - 3; i++) { // number of the strings
            String strWin = "";
            for (int j = 0; j < numCheckedSigns - 2; j++) { // number of the signs
                if (i == j) {
                    strWin += String.valueOf("" + DEFAULT_CELL_VALUE + value + value + DEFAULT_CELL_VALUE);
                    j += 1;
                } else {
                    strWin += value;
                }
            }
            listStringNearWinner_2w.add(strWin);
        }
        return listStringNearWinner_2w;
    }

    private static String createStringWinner(char value) {
        String result = "";
        for (int i = 0; i <= numCheckedSigns - 1; i++) {
            result += value;
        }
        return result;
    }

    /**
     *
     * @param value  'X' or 'Y'
     * @return  string that could be WIN line by either of the two next enemy moves
     */
    private static String createStringNearWinner_SSS_(char value) {
        String result = "";
        for (int i = 0; i <= numCheckedSigns; i++) {
            if (i == 0) {
                result += String.valueOf("" + DEFAULT_CELL_VALUE);
            } else if (i == numCheckedSigns) {
                result += String.valueOf("" + DEFAULT_CELL_VALUE);
            } else {
                result += value;
            }
        }
        return result;
    }

}
