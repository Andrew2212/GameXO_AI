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
    private final Integer NEAR_WIN_BOT_1;  // i.e. string XXXX_ without 1 sign
    private Map weightMap;
    private String stringResultOfCheck;
    /**
     * Temporary list of cell coordinate for checked line
     */
    private ArrayList<int[]> listCheckedCell = new ArrayList<int[]>();

    //    private int lastBotMoveX;
//    private int lastBotMoveY;
    private int[] lastMyOwnMove = new int[2];


    public Constructor() {
        NEAR_WIN_BOT_1 = GameOptions.numCheckedSigns * 20;
        weightMap = new HashMap<int[], Integer>();
    }

    //    --------------Public Methods-------------------
    public int[] getConstructiveWinMove() {

        setWeightInRow(lastMyOwnMove[X], lastMyOwnMove[Y]);
        setWeightInColumn(lastMyOwnMove[X], lastMyOwnMove[Y]);
        setWeightInDiagonalCW(lastMyOwnMove[X], lastMyOwnMove[Y]);
        setWeightInDiagonalCCW(lastMyOwnMove[X], lastMyOwnMove[Y]);

        if (weightMap.isEmpty()) {
            return null;
        }

        int[] constructiveWinMove;// It's 'move' to return
        KeyCell keyMaxWeight = getMaxWeight(weightMap); //Coordinate of the cell with max 'weight'
        constructiveWinMove = new int[]{keyMaxWeight.getX(), keyMaxWeight.getY()};
        return constructiveWinMove;
//        return null;
    }

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
     * @param cellX lastEnemyMove X
     * @param cellY lastEnemyMove 'Y'
     *              <br>Checks particular line and set 'weight' of cells into 'weightMap' by methods 'setWeightTo...' </br>
     */
    private void setWeightInRow(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY);
            writeCheckedValueInverse(cellX - i, cellY);
        }
        setWeightToNearWin_1();

        listCheckedCell.clear();
        listCheckedCell.trimToSize();
    }

    /**
     * @param cellX lastEnemyMove X
     * @param cellY lastEnemyMove 'Y'
     *              <br>Checks particular line and set 'weight' of cells into 'weightMap' by methods 'setWeightTo...' </br>
     */
    private void setWeightInColumn(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX, cellY + i);
            writeCheckedValueInverse(cellX, cellY - i);
        }
        setWeightToNearWin_1();
        listCheckedCell.clear();
        listCheckedCell.trimToSize();
    }

    /**
     * @param cellX lastEnemyMove X
     * @param cellY lastEnemyMove 'Y'
     *              <br>Checks particular line and set 'weight' of cells into 'weightMap' by methods 'setWeightTo...' </br>
     */
    private void setWeightInDiagonalCW(int cellX, int cellY) {
        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY - i);
            writeCheckedValueInverse(cellX - i, cellY + i);
        }
        setWeightToNearWin_1();
        listCheckedCell.clear();
        listCheckedCell.trimToSize();
    }

    /**
     * @param cellX lastEnemyMove X
     * @param cellY lastEnemyMove 'Y'
     *              <br>Checks particular line and set 'weight' of cells into 'weightMap' by methods 'setWeightTo...' </br>
     */
    private void setWeightInDiagonalCCW(int cellX, int cellY) {

        stringResultOfCheck = GameOptions.signBot;
        listCheckedCell.add(new int[]{cellX, cellY});

        for (int i = 1; i < GameOptions.numCheckedSigns; i++) {
            writeCheckedValue(cellX + i, cellY + i);
            writeCheckedValueInverse(cellX - i, cellY - i);
        }
        setWeightToNearWin_1();
        listCheckedCell.clear();
        listCheckedCell.trimToSize();
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

    //-------------------------Setup weight into checked cell---------------------------------------------------------

    /**
     * <br>Puts into weightMap 'value' (weight = NEAR_WIN_BOT_1) with 'key' (KeyCell keyWeight = new KeyCell(listCheckedCell.get(i)))</br>
     * <br> if checked line of cells is 'line WIN without 1 sign' from 'listStringNearWinBot_1' and cellValue into fieldMatrix == DEFAULT (cell is empty)
     * <br>Return current 'listCheckedCell' </br>
     */
    private List<int[]> setWeightToNearWin_1() {

        for (int j = 0; j < GameOptions.listStringNearWinBot_1.size(); j++) {
//            System.out.println("W1*** " + j + " strWin_1 = " + GameOptions.listStringNearWinEnemy_1.get(j));
//            System.out.println("W2*** " + j + " stringResultOfCheck = " + stringResultOfCheck);

//            Check condition 'contains' for each string from 'listStringNearWinEnemy_1'
            if (stringResultOfCheck.contains(GameOptions.listStringNearWinBot_1.get(j))) {
                for (int i = 0; i < stringResultOfCheck.length(); i++) {

                    KeyCell keyCell = new KeyCell(listCheckedCell.get(i));
                    char cellValue = stringResultOfCheck.charAt(i);

//                System.out.println("Constructor::setWeightToNearWin_1::cell[0] = " + keyCell.getX() + " cell[1] = " + keyCell.getX() + " cellValue = " + cellValue);
                    if (cellValue == (GameOptions.DEFAULT_CELL_VALUE)) {
                        weightMap.put(keyCell, NEAR_WIN_BOT_1);
//                        System.out.println("1************Destructor::setWeightToNearWin_1::cell = " + keyCell.toString() + "cellNewWeight = " + NEAR_WIN_BOT_1);
                    } else {
//                      Remove from 'weightMap' if cell is not empty
                        weightMap.remove(keyCell);
                    }
                }
                return listCheckedCell;
            }
        }
        return null;
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

}
