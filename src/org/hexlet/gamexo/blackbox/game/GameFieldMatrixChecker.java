package org.hexlet.gamexo.blackbox.game;


public class GameFieldMatrixChecker {

    private static String stringWinnerX = "";
    private static String stringWinnerO = "";
    private static int countSteps = 0;
    private static String stringWinner = "***No Winner***";

    public GameFieldMatrixChecker() {
//      Get String for the comparison
        createStringWinnerX();
        createStringWinnerO();
    }


    public boolean isGameOver() {

        if (isRowOrColumnCompleted() || isDiagonalCompleted()) {
            System.out.println("*******Game over!*******");
            System.out.println("Gamer ***" + GameField.getSignForNextMove() + "*** is Winner!");
            System.out.println(stringWinner);
            countSteps = 0;
            return true;
        }

        if (isGameFieldFilled()) {
            System.out.println("*******Game over!*******");
            System.out.println("***Game Field is filled!***");
            countSteps = 0;
            return true;
        }

        return false;
    }

    //    ----------------Private Methods------------------

    private static boolean isGameFieldFilled() {

        if (countSteps == (GameField.FIELD_SIZE * GameField.FIELD_SIZE - 1)) {
            return true;
        }
        countSteps++;
        return false;
    }

    private static boolean isRowOrColumnCompleted() {
//   It should use checkup by one point (x,y) but so far it do it by cycle
        for (int i = 0; i < GameField.FIELD_SIZE; i++) {
            // Use String here isn't best practice but code is shorter
            String stringRow = "";
            String stringCol = "";
            for (int j = 0; j < GameField.FIELD_SIZE; j++) {
                stringRow += String.valueOf(GameField.getFieldMatrix()[j][i]);
                stringCol += String.valueOf(GameField.getFieldMatrix()[i][j]);
            }

            if (stringRow.contains(stringWinnerX) || stringRow.contains(stringWinnerO)) {
                stringWinner = "stringRow = " + stringRow;
                return true;
            }
            if (stringCol.contains(stringWinnerX) || stringCol.contains(stringWinnerO)) {
                stringWinner = "stringCol = " + stringCol;
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalCompleted() {
//   It should use checkup by one point (x,y) but so far it do it by cycle
        for (int i = 0; i < GameField.FIELD_SIZE; i++) {

            String stringCW = "";
            String stringCCW = "";

            for (int j = 0; j < GameField.FIELD_SIZE; j++) {
                stringCW = countDiagonalCW(i, j);
                stringCCW = countDiagonalCCW(i, j);

                if (stringCW.contains(stringWinnerX) || stringCW.contains(stringWinnerO)) {
                    stringWinner = "stringCW = " + stringCW;
                    return true;
                }
                if (stringCCW.contains(stringWinnerX) || stringCCW.contains(stringWinnerO)) {
                    stringWinner = "stringCCW = " + stringCCW;
                    return true;
                }
            }
        }
        return false;
    }

    private static String countDiagonalCCW(int x, int y) {

        String result = String.valueOf(GameField.getFieldMatrix()[x][y]);
        int position_X = 0;
        int position_Y = 0;

        if (result.equalsIgnoreCase(String.valueOf(GameField.getDefaultCellValue()))) {
            return result;
        }

        for (int i = 1; i < GameField.NUM_CHECKED; i++) {
            position_X = x + i;
            position_Y = y + i;
            result += calculateCellValue(position_X, position_Y);
        }

        for (int i = 1; i < GameField.NUM_CHECKED; i++) {
            position_X = x - i;
            position_Y = y - i;
            result = calculateCellValue(position_X, position_Y) + result;

        }

        return result;
    }

    private static String countDiagonalCW(int x, int y) {

        String result = String.valueOf(GameField.getFieldMatrix()[x][y]);
        int position_X = 0;
        int position_Y = 0;

        if (result.equalsIgnoreCase(String.valueOf(GameField.getDefaultCellValue()))) {
            return result;
        }

        for (int i = 1; i < GameField.NUM_CHECKED; i++) {

            position_X = x + i;
            position_Y = y - i;
            result += calculateCellValue(position_X, position_Y);
        }

        for (int i = 1; i < GameField.NUM_CHECKED; i++) {
            position_X = x - i;
            position_Y = y + i;
            result = calculateCellValue(position_X, position_Y) + result;

        }

        return result;
    }


    private static String calculateCellValue(int position_X, int position_Y) {
        String string = "";
//       Checkup for area around each cell (position is NOT generated by gamer!)
        if (0 <= position_X && position_X < GameField.FIELD_SIZE) {
            if (0 <= position_Y && position_Y < GameField.FIELD_SIZE) {

                string += GameField.getFieldMatrix()[position_X][position_Y];
            }
        }
        return string;
    }

    private static String createStringWinnerX() {
        for (int i = 0; i < GameField.NUM_CHECKED; i++) {
            stringWinnerX += GameField.VALUE_X;
        }
        return stringWinnerX;
    }

    private static String createStringWinnerO() {
        for (int i = 0; i < GameField.NUM_CHECKED; i++) {
            stringWinnerO += GameField.VALUE_O;
        }
        return stringWinnerO;
    }

}
