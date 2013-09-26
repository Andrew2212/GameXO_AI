package org.hexlet.gamexo.ai.utils;

import org.hexlet.gamexo.blackbox.game.GameField;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 25.09.13
 * Time: 10:37 <br>
 * * Gets last enemy move from fieldMatrix[][] by comparing with previousFieldMatrix[][]
 * and set our own move into  previousFieldMatrix[][]
 */
public class GetterLastEnemyMove {

    private char[][] previousFieldMatrix;
    private int fieldSize;

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;

    public GetterLastEnemyMove(int fieldSize) {

        this.fieldSize = fieldSize;
        previousFieldMatrix = new char[fieldSize][fieldSize];
        fillDefaultGameMatrix(previousFieldMatrix);

    }

    public int[] getLastEnemyMove(char[][] fieldMatrix) {
        System.out.println("getLastEnemyField()");
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {

                if (fieldMatrix[i][j] != GameField.getDefaultCellValue()
                        && (previousFieldMatrix[i][j] != fieldMatrix[i][j])) {

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
        if (GameField.isCellValid(moveX, moveY)) {
            previousFieldMatrix[moveX][moveY] = signBot;
        }

    }

    private void fillDefaultGameMatrix(char[][] fieldMatrix) {
        for (int i = 0; i < GameField.FIELD_SIZE; i++) {
            for (int j = 0; j < GameField.FIELD_SIZE; j++) {
                fillDefaultCurrentCell(i, j);
            }
        }
    }

    private void fillDefaultCurrentCell(int i, int j) {
        previousFieldMatrix[i][j] = GameField.getDefaultCellValue();
    }

}
