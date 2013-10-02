package org.hexlet.gamexo.ai.brutforceway;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * * <br>Sets weight into certain cells in accordance its own check methods</br>
 * <br>By method 'getConstructiveWinMove' returns some 'constructiveWinMove'</br>
 */
public class Constructor {

    private static final int X = 0;
    private static final int Y = 1;
    private final Integer NEAR_MOVE = 1; // i.e. cell close to bot move
    private final Integer NEAR_WIN_BOT_1;  // i.e. string XXXX_ without 1 sign
    private final Integer SOME = 10;  // to 'weightMap' for nonWIN cell

    private String stringResultOfCheck;
    /**
     * Temporary list of cell coordinate for checked line
     */
    private ArrayList<int[]> listCheckedCell = new ArrayList<int[]>();
    /**
     * All about handling my own LastMove is just copy-past from 'Destructor' =)
     */
    private ArrayList<int[]> listCellsNearLastEnemyMove = new ArrayList<int[]>();
    private int[] lastMyOwnMove = new int[2];
    private int[] moveWin;


    public Constructor() {
        NEAR_WIN_BOT_1 = GameOptions.numCheckedSigns * 20;
    }

    //    --------------Public Methods-------------------
    public int[] getConstructiveWinMove() {


        if (setWeightInRow(lastMyOwnMove[X], lastMyOwnMove[Y])) {
            return moveWin;
        }
        if (setWeightInColumn(lastMyOwnMove[X], lastMyOwnMove[Y])) {
            return moveWin;
        }
        if (setWeightInDiagonalCW(lastMyOwnMove[X], lastMyOwnMove[Y])) {
            return moveWin;
        }
        if (setWeightInDiagonalCCW(lastMyOwnMove[X], lastMyOwnMove[Y])) {
            return moveWin;
        }


        return null;
    }

    /**
     * @param move last my own move
     *             <br>It's called into Brutforce::findMove(...)</br>
     */
    public void setLastMyOwnMove(int[] move) {
        lastMyOwnMove = move;
    }

    //------------Private Methods-----------------

    /**
     * @param weightMap
     * @return KeyCell 'keyMaxWeight' that corresponds to max value from HashMap
     */
    private KeyCell getMaxWeight(Map<KeyCell, Integer> weightMap) {

        Integer maxWeight = null;
        KeyCell keyMaxWeight = null;

        Iterator iterator = weightMap.keySet().iterator();
        while (iterator.hasNext()) {
            KeyCell key = (KeyCell) iterator.next();
            Integer value = weightMap.get(key);
            if (maxWeight == null || maxWeight < value) {
                maxWeight = value;
                keyMaxWeight = key;
            }
        }
        return keyMaxWeight;
    }

    //    ---------------Check lines and set weight of the cells-------------------------

    /**
     * @param cellX lastMyOwnMove X
     * @param cellY lastMyOwnMove 'Y'
     *              <br>Sets value (signs from fieldMatrix cells) into 'stringResultOfCheck'</br>
     *              <br>Sets value (coordinate fieldMatrix cells) into 'listCheckedCell'</br>
     */
    private boolean setWeightInRow(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY);
            writeCheckedValueInverse(cellX - i, cellY);
        }
//        We have  'stringResultOfCheck' and   'listCheckedCell' here
//        And we have 'winCell' (if it exist) with WIN coordinate into 'listCheckedCell'
//        And value 'GameOptions.DEFAULT_CELL_VALUE' into 'listCheckedCell'
//        And we go to the 'setWeightToNearWin_1()' in order to check out - exist or no

        return setWeightToNearWin_1();
    }

    /**
     * @param cellX lastMyOwnMove X
     * @param cellY lastMyOwnMove 'Y'
     *              <br>Sets value (signs from fieldMatrix cells) into 'stringResultOfCheck'</br>
     *              <br>Sets value (coordinate fieldMatrix cells) into 'listCheckedCell'</br>
     */
    private boolean setWeightInColumn(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX, cellY + i);
            writeCheckedValueInverse(cellX, cellY - i);
        }
//        We have  'stringResultOfCheck' and   'listCheckedCell' here
//        And we have 'winCell' (if it exist) with WIN coordinate into 'listCheckedCell'
//        And value 'GameOptions.DEFAULT_CELL_VALUE' into 'listCheckedCell'
//        And we go to the 'setWeightToNearWin_1()' in order to check out - exist or no

        return setWeightToNearWin_1();
    }

    /**
     * @param cellX lastMyOwnMove X
     * @param cellY lastMyOwnMove 'Y'
     *              <br>Sets value (signs from fieldMatrix cells) into 'stringResultOfCheck'</br>
     *              <br>Sets value (coordinate fieldMatrix cells) into 'listCheckedCell'</br>
     */
    private boolean setWeightInDiagonalCW(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY - i);
            writeCheckedValueInverse(cellX - i, cellY + i);
        }
//        We have  'stringResultOfCheck' and   'listCheckedCell' here
//        And we have 'winCell' (if it exist) with WIN coordinate into 'listCheckedCell'
//        And value 'GameOptions.DEFAULT_CELL_VALUE' into 'listCheckedCell'
//        And we go to the 'setWeightToNearWin_1()' in order to check out - exist or no

        return setWeightToNearWin_1();
    }

    /**
     * @param cellX lastMyOwnMove X
     * @param cellY lastMyOwnMove 'Y'
     *              <br>Sets value (signs from fieldMatrix cells) into 'stringResultOfCheck'</br>
     *              <br>Sets value (coordinate fieldMatrix cells) into 'listCheckedCell'</br>
     */
    private boolean setWeightInDiagonalCCW(int cellX, int cellY) {

        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY + i);
            writeCheckedValueInverse(cellX - i, cellY - i);
        }
//        We have  'stringResultOfCheck' and   'listCheckedCell' here
//        And we have 'winCell' (if it exist) with WIN coordinate into 'listCheckedCell'
//        And value 'GameOptions.DEFAULT_CELL_VALUE' into 'listCheckedCell'
//        And we go to the 'setWeightToNearWin_1()' in order to check out - exist or no

        return setWeightToNearWin_1();
    }

    /**
     * <br>Puts into weightMap 'value' (weight = NEAR_WIN_BOT_1) with 'key' (KeyCell keyWeight = new KeyCell(listCheckedCell.get(i)))</br>
     * <br>ONLY if (stringResultOfCheck.contains(GameOptions.listStringNearWinBot_1.get(j) and cellValue into fieldMatrix == DEFAULT (cell is empty)</br>
     * <br>Return 'true' if something had been set into 'weightMap'</br>
     */
    private boolean setWeightToNearWin_1() {
        int[] cellWin = null;
        for (int j = 0; j < GameOptions.listStringNearWinBot_1.size(); j++) {
//            System.out.println("W1*** " + j + " strWin_1 = " + GameOptions.listStringNearWinEnemy_1.get(j));
//            System.out.println("W2*** " + j + " stringResultOfCheck = " + stringResultOfCheck);

// Check condition 'contains' for each string from 'listStringNearWinBot_1'
            if (stringResultOfCheck.contains(GameOptions.listStringNearWinBot_1.get(j))) {
                for (int i = 0; i < stringResultOfCheck.length(); i++) {

                    char cellValue = stringResultOfCheck.charAt(i);
// Cell coordinate = 'listCheckedCell.get(i)' and cellValue = 'cellValue' from 'stringResultOfCheck.charAt(i)'
                    if (cellValue == (GameOptions.DEFAULT_CELL_VALUE)) {
// Set  signBot with '[listCheckedCell.get(i)[X]][listCheckedCell.get(i)[Y]]' into 'testFieldMatrix'
                        char[][] testFieldMatrix = copyMatrix(BrutforceAI.getFieldMatrix());
                        int[] move = listCheckedCell.get(i);
                        testFieldMatrix[move[X]][move[Y]] = GameOptions.signBot.charAt(0);
// And do 'checkToWin(listCheckedCell)'
// If 'true' - moveWin is initialized into method checkToWin (moveWin = move;)
                        if (checkToWin(move, listCheckedCell, testFieldMatrix)) {
                            listCheckedCell.clear();
                            listCheckedCell.trimToSize();

                            return true;
                        }
                        testFieldMatrix[move[X]][move[Y]] = GameOptions.DEFAULT_CELL_VALUE;
                    }
                }
            }
        }
        listCheckedCell.clear();
        listCheckedCell.trimToSize();
        return false;
    }

    /**
     * @param listCheckedCell
     * @param testFieldMatrix
     * @return true if string value of fieldMatrix cells with coordinates from 'listCheckedCell' contains stringWinnerBot
     */
    private boolean checkToWin(int[] move, List<int[]> listCheckedCell, char[][] testFieldMatrix) {
        String resultOfCheck = "";
        for (int i = 0; i < listCheckedCell.size(); i++) {

            resultOfCheck += testFieldMatrix[listCheckedCell.get(i)[X]][listCheckedCell.get(i)[X]];
        }
//        System.out.println("Constructor::checkToWin::resultOfCheck = " + resultOfCheck);
        if (resultOfCheck.contains(GameOptions.stringWinnerBot)) {
            moveWin = move;
//            System.out.println("Constructor::checkToWin::moveWin[X] = " + moveWin[X] + " moveWin[Y] = " + moveWin[Y]);
            return true;
        }
        return false;
    }

    //    -------------------Write Checked Value--------------------------------------

    /**
     * @param x coordinate X of checked cell
     * @param y coordinate Y of checked cell
     *          <br>Writes value of the checked cell into 'stringResultOfCheck' to end (index = 'size')</br>
     *          <br>Writes coordinate of the checked cell into 'listCheckedCell' to end (index = 'size')</br>
     */
    private void writeCheckedValue(int x, int y) {
        stringResultOfCheck = stringResultOfCheck + fetchCellValue(x, y);
        if (isValueValid(x, y)) {
//                System.out.println("writeCheckedValue::listCheckedCell.add(cell_)::cell_[x] = " + x + " cell_[y] = " + y);
            listCheckedCell.add(new int[]{x, y});
        }
    }

    /**
     * @param x coordinate X of checked cell
     * @param y coordinate Y of checked cell
     *          <br>Writes value of the checked cell into 'stringResultOfCheck' to start(index = 0)</br>
     *          <br>Writes coordinate of the checked cell into 'listCheckedCell' to start(index = 0)</br>
     */
    private void writeCheckedValueInverse(int x, int y) {
        stringResultOfCheck = fetchCellValue(x, y) + stringResultOfCheck;
        if (isValueValid(x, y)) {
//                System.out.println("-  listCheckedCell.addInverse(cell_)::cell_[x] = " + x + " cell_[y] = " + y);
            listCheckedCell.add(0, new int[]{x, y});
        }
    }


    //    -------------------Util Methods-----------------------------

    /**
     * @param cellX
     * @param cellY
     * @return cellValue from GameField::gameFieldMatrix[][]
     *         <br> It just gets cell value</br>
     */
    private String fetchCellValue(int cellX, int cellY) {

        String cellValue = "";
        if (isValueValid(cellX, cellY)) {
            cellValue += BrutforceAI.getFieldMatrix()[cellX][cellY];
        }
        return cellValue;
    }


    private boolean isValueValid(int x, int y) {
        if ((0 <= x && x < GameOptions.fieldSize) && (0 <= y && y < GameOptions.fieldSize)) {
            return true;
        }
        return false;
    }

    public char[][] copyMatrix(char [][] fieldMatrix){
        int size = fieldMatrix.length;
        char[][] fieldMatrixCopy = new char[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                fieldMatrixCopy[i][j] = fieldMatrix[i][j];
            }
        }
        return fieldMatrixCopy;
    }

}
