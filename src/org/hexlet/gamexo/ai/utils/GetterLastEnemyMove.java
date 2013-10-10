package org.hexlet.gamexo.ai.utils;

import org.hexlet.gamexo.ai.CoreGame;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 25.09.13
 * Time: 10:37 <br>
 * * Gets last enemy move from fieldMatrix[][] by comparing with previousFieldMatrix[][]
 * and set our own move into  previousFieldMatrix[][]
 */
public class GetterLastEnemyMove {

    private Character[][] previousFieldMatrix;
    private int fieldSize;

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;

    public GetterLastEnemyMove() {

        fieldSize = CoreGame.getFieldSize();
        previousFieldMatrix = new Character[fieldSize][fieldSize];
        fillDefaultGameMatrix(previousFieldMatrix);

    }

    public int[] getLastEnemyMove(Character[][] fieldMatrix) {
//        System.out.println("Getter::getLastEnemyField()");
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {

                if (!fieldMatrix[i][j].equals(CoreGame.DEFAULT_CELL_VALUE)
                        && (!previousFieldMatrix[i][j].equals(fieldMatrix[i][j]))) {

                    MOVE[X] = i;
                    MOVE[Y] = j;

//                    System.out.println("previousFieldMatrix[" + i + "][" + j + "] = " + previousFieldMatrix[i][j]);
//                    System.out.println("fieldMatrix[" + i + "][" + j + "] = " + fieldMatrix[i][j]);
                    System.out.println("getLastEnemyMove():: MOVE[X] = " + MOVE[X] + ", MOVE[Y] = " + MOVE[Y]);

                    previousFieldMatrix[i][j] = fieldMatrix[i][j];
                    return MOVE;

                }
            }
        }

        return null;
    }

    public void setMyOwnMove(int moveX, int moveY, char signBot) {

//        checkout for random - it isn't needed for real AI
        if (isCellValid(moveX, moveY)) {
            previousFieldMatrix[moveX][moveY] = signBot;
        }

    }

//    -----------------Private Methods---------------------------

    private void fillDefaultGameMatrix(Character[][] fieldMatrix) {
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix.length; j++) {
                fillDefaultCurrentCell(i, j);
            }
        }
    }

    private void fillDefaultCurrentCell(int i, int j) {
        previousFieldMatrix[i][j] = CoreGame.DEFAULT_CELL_VALUE;
    }

    private boolean isCellValid(int x, int y) {

        if (!CoreGame.isValueValid(x, y)) {
            return false;
        }

        if (previousFieldMatrix[x][y].equals(CoreGame.DEFAULT_CELL_VALUE)) {
            return true;
        }
        return false;
    }

}
