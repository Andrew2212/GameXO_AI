package org.hexlet.gamexo.ai.brutforceway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 28.09.13
 * Time: 18:49
 * <br>This class is container for "Game Options' that obtained from rest part of application or calculated here</br>
 */
public class GameOptions {

    public static int fieldSize;
    public static int numCheckedSigns;

    public static String signBot;
    public static String signEnemy;
    public static final char DEFAULT_CELL_VALUE = '_';
    private static String VALUE_X = "X";
    private static String VALUE_O = "O";

    private static String stringWinnerX = ""; // i.e. string XXXX
    private static String stringWinnerO = ""; // i.e. string OOOO
    private static String stringNearWinnerX_1 = ""; // i.e. string XXX_ without 1 sign
    private static String stringNearWinnerO_1 = ""; //i.e. string OOO_ without 1 sign
    private static String stringNearWinnerX_2 = ""; // i.e. string XX__ without 2 sign
    private static String stringNearWinnerO_2 = ""; //i.e. string OO__ without 2 sign
    private static List<String> listStringNearWinnerX_1;
    private static List<String> listStringNearWinnerO_1;
    private static List<String> listStringNearWinnerX_2;
    private static List<String> listStringNearWinnerO_2;

    public static List<String> listStringNearWinBot_1;
    public static List<String> listStringNearWinBot_2;
    public static List<String> listStringNearWinEnemy_1;
    public static List<String> listStringNearWinEnemy_2;

    public static String stringWinnerBot;
    public static String stringNearWinnerBot_1;
    public static String stringNearWinnerBot_2;

    public static String stringWinnerEnemy;
    public static String stringNearWinnerEnemy_1;
    public static String stringNearWinnerEnemy_2;

    public static void initGameOptions(int fieldSize, int numCheckedSigns) {
//        System.out.println("GameOptions::initGameOptions");
        GameOptions.fieldSize = fieldSize;
        GameOptions.numCheckedSigns = numCheckedSigns;
        createStringWinnerX();
        createStringWinnerO();
        listStringNearWinnerX_1 = createListStringWin_1(VALUE_X);
        listStringNearWinnerO_1 = createListStringWin_1(VALUE_O);
        listStringNearWinnerX_2 = createListStringWin_2(VALUE_X);
        listStringNearWinnerO_2 = createListStringWin_2(VALUE_O);

        //Without this condition we'll get 'Winner_2' string = ONE sign 'X' or 'O'
        if (3 < numCheckedSigns) {
            createStringNearWinnerX_2();
            createStringNearWinnerO_2();
        }

    }
//    *********************************************
//    private static void initListStringWinX_1(){
//        for(int i = 0; i < numCheckedSigns; i++){
//            String strWin = "";
//            for(int j = 0; j < numCheckedSigns; j++){
//             if(i == j){
//                 strWin += DEFAULT_CELL_VALUE;
//             } else {
//                 strWin += VALUE_X;
//             }
//            }
//            listStringNearWinnerX_1.add(strWin);
//        }
//    }
//
//    private static void initListStringWinO_1(){
//        for(int i = 0; i < numCheckedSigns; i++){
//            String strWin = "";
//            for(int j = 0; j < numCheckedSigns; j++){
//                if(i == j){
//                    strWin += DEFAULT_CELL_VALUE;
//                } else {
//                    strWin += VALUE_O;
//                }
//            }
//            listStringNearWinnerX_1.add(strWin);
//        }
//    }

    private static List<String> createListStringWin_1(String value) {
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

    private static List<String> createListStringWin_2(String value) {
        List<String> listStringNearWinner_1 = new ArrayList<String>();

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
            listStringNearWinner_1.add(strWin);
        }

        return listStringNearWinner_1;
    }

    //     ***************************************************************
    public static void setSignBotAndSignEnemy(char signBot) {
//        System.out.println("GameOptions::setSignBotAndSignEnemy::signBot = " + signBot);
        if (String.valueOf(signBot).equalsIgnoreCase(VALUE_X)) {
            //Bot strings
            GameOptions.signBot = VALUE_X;
            GameOptions.stringWinnerBot = stringWinnerX;
            GameOptions.stringNearWinnerBot_1 = stringNearWinnerX_1;
            GameOptions.stringNearWinnerBot_2 = stringNearWinnerX_2;

            GameOptions.listStringNearWinBot_1 = listStringNearWinnerX_1;
            GameOptions.listStringNearWinBot_2 = listStringNearWinnerX_2;

            //Enemy strings
            GameOptions.signEnemy = VALUE_O;
            GameOptions.stringWinnerEnemy = stringWinnerO;
            GameOptions.stringNearWinnerEnemy_1 = stringNearWinnerO_1;
            GameOptions.stringNearWinnerEnemy_2 = stringNearWinnerO_2;

            GameOptions.listStringNearWinEnemy_1 = listStringNearWinnerO_1;
            GameOptions.listStringNearWinEnemy_2 = listStringNearWinnerO_2;

        } else {
            //Bot strings
            GameOptions.signBot = VALUE_O;
            GameOptions.stringWinnerBot = stringWinnerO;
            GameOptions.stringNearWinnerBot_1 = stringNearWinnerO_1;
            GameOptions.stringNearWinnerBot_2 = stringNearWinnerO_2;

            GameOptions.listStringNearWinBot_1 = listStringNearWinnerO_1;
            GameOptions.listStringNearWinBot_2 = listStringNearWinnerO_2;

            //Enemy strings
            GameOptions.signEnemy = VALUE_X;
            GameOptions.stringWinnerEnemy = stringWinnerX;
            GameOptions.stringNearWinnerEnemy_1 = stringNearWinnerX_1;
            GameOptions.stringNearWinnerEnemy_2 = stringNearWinnerX_2;

            GameOptions.listStringNearWinEnemy_1 = listStringNearWinnerX_1;
            GameOptions.listStringNearWinEnemy_2 = listStringNearWinnerX_2;
        }
    }

    private static String createStringWinnerX() {
        stringWinnerX = createStringNearWinnerX_1() + VALUE_X;
        return stringWinnerX;
    }

    private static String createStringWinnerO() {
        stringWinnerO = createStringNearWinnerO_1() + VALUE_O;
        return stringWinnerO;
    }

    private static String createStringNearWinnerX_1() {
        String result = "";
        for (int i = 0; i < numCheckedSigns - 1; i++) {
            result += VALUE_X;
        }
        stringNearWinnerX_1 = result;
        return stringNearWinnerX_1;
    }

    private static String createStringNearWinnerO_1() {
        String result = "";
        for (int i = 0; i < numCheckedSigns - 1; i++) {
            result += VALUE_O;
        }
        stringNearWinnerO_1 = result;
        return stringNearWinnerO_1;
    }

    private static String createStringNearWinnerX_2() {
        String result = "";
        for (int i = 0; i < numCheckedSigns - 2; i++) {
            result += VALUE_X;
        }
        stringNearWinnerX_2 = result;
        return stringNearWinnerX_2;
    }

    private static String createStringNearWinnerO_2() {
        String result = "";
        for (int i = 0; i < numCheckedSigns - 2; i++) {
            result += VALUE_O;
        }
        stringNearWinnerO_2 = result;
        return stringNearWinnerO_2;
    }


}
