package hqup.game;


import hqup.PlayStationXO;
import hqup.gamers.IGamer;

public class GameField {

    public static final char VALUE_X = 'X';
    public static final char VALUE_O = 'O';
    private static final char DEFAULT_CELL_VALUE = '_';

    private static final int X = 0;
    private static final int Y = 1;

    //Set GameField Size
    public static int FIELD_SIZE = 3;
    // Set number of the checked signs
    public static int NUM_CHECKED = 3;

    private static char[][] fieldMatrix;
    private static char signForNextMove = VALUE_X;
    private static int flagSign = 1;


    public static void getNewGameField() {
        fieldMatrix = new char[FIELD_SIZE][FIELD_SIZE];
        fillDefaultGameMatrix();
    }

    public static void fillDefaultGameMatrix() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                fillDefaultCurrentCell(i, j);
            }
        }
    }

    public static boolean setSignToCell(IGamer gamer) {

        int[] cellPosition = gamer.moveSign();
        int x = cellPosition[X];
        int y = cellPosition[Y];

        if (!isCellValid(x, y)) {

            while (true) {
                cellPosition = gamer.moveSign();
                x = cellPosition[X];
                y = cellPosition[Y];
                if (isCellValid(x, y))
                    break;
            }
        }

        char sign = chooseSignForNextMove();
        fieldMatrix[x][y] = sign;

        //Record Steps History
        GameHistoric.recordCurrentStep(x, y);

        System.out.println("Gamer *" + sign + "* move to x=" + x + " y=" + y);
        return true;
    }

    public static char getSignForNextMove() {
        return signForNextMove;
    }

    public static char getDefaultCellValue() {
        return DEFAULT_CELL_VALUE;
    }

    public static char[][] getFieldMatrix() {
        return fieldMatrix;
    }

    /**
     * Method for GameHistoric reverse move
     */
    public static void reverseFlagSign() {
        flagSign = -flagSign;
    }

    public static void fillDefaultCurrentCell(int i, int j) {
        fieldMatrix[i][j] = DEFAULT_CELL_VALUE;
    }

    public static void resetGameFieldMatrix() {

        System.out.println("\n--------------------------------\n");
        fillDefaultGameMatrix();
    }

//---------Private Methods---------------------

    private static boolean isCellValid(int x, int y) {

        if (!isValueValid(x, y)) {
            return false;
        }

        if (fieldMatrix[x][y] == DEFAULT_CELL_VALUE) {
            return true;
        }

        System.out.println("Caution! The cell x=" + x + " y=" + y + " is filled! Try again!");
        return false;
    }

    private static boolean isValueValid(int x, int y) {

        if ((0 <= x && x < FIELD_SIZE) && (0 <= y && y < FIELD_SIZE)) {
            return true;
        }
        System.out.println("Invalid entry!");
        return false;
    }

    private static char chooseSignForNextMove() {

        if (0 < flagSign) {
            signForNextMove = VALUE_X;
        } else {
            signForNextMove = VALUE_O;
        }
        reverseFlagSign();
        return signForNextMove;
    }
}
