package org.hexlet.gamexo.ai.brutforceway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 28.09.13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class BrutforceAI implements IBrainAI {

    private int[] MOVE;
    private static final int X = 0;
    private static final int Y = 1;
    private static char[][] fieldMatrix;
    private char signBot;

    private GetterLastEnemyMove getterLastEnemyMove;
    private Destructor destructor;
    private boolean isFirstMoveDone = false;//  Crutch for giving signBot

    public BrutforceAI(int fieldSize, int numChecked) {
        getterLastEnemyMove = new GetterLastEnemyMove(fieldSize);
        GameOptions.initGameOptions(fieldSize, numChecked);
        destructor = new Destructor();
    }

    /**
     * @param fieldMatrix char[][]
     * @return MOVE i.e. int[2] - coordinates of cell
     */
    public int[] findMove(char[][] fieldMatrix) {

        this.fieldMatrix = fieldMatrix;
        //Executes only one time

        if (!isFirstMoveDone) {
            signBot = GameField.getSignForNextMove();
            System.out.println("isFirstMoveDone*****************signBot = " + signBot);
            GameOptions.setSignBotAndSignEnemy(signBot);
            isFirstMoveDone = true;
        }

        //Get lastEnemyMove (if it exists)
        int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrix);

        if (null != lastEnemyMove) {
            // Get DESTRUCTIVE MOVE
            int[] moveDestructive = destructor.getDestructiveMove(lastEnemyMove[X], lastEnemyMove[Y]);
            if (moveDestructive != null) {
                System.out.println("BrutforceAI::findMove moveDestructive[X] = " + moveDestructive[X] + " moveDestructive[Y] = " + moveDestructive[Y] + " signBot = " + signBot);
                getterLastEnemyMove.setMyOwnMove(moveDestructive[X], moveDestructive[Y], signBot);
                return moveDestructive;
            }
        }

        // Get CONSTRUCTIVE move
        MOVE = getConstructiveMove();

        System.out.println("BrutforceAI::getConstructiveMove constructiveMove[X] = " + MOVE[X] + " constructiveMove[Y] = " + MOVE[Y]);
        getterLastEnemyMove.setMyOwnMove(MOVE[X], MOVE[Y], signBot);
        return MOVE;
    }

    public static char[][] getFieldMatrix(){
        return fieldMatrix;
    }

    //---------Private Methods---------------------

    private boolean isCellValid(int x, int y) {

        if (!isValueValid(x, y)) {
            return false;
        }

        if (fieldMatrix[x][y] == GameOptions.DEFAULT_CELL_VALUE) {
            return true;
        }

        return false;
    }

    private static boolean isValueValid(int x, int y) {

        if ((0 <= x && x < GameOptions.fieldSize) && (0 <= y && y < GameOptions.fieldSize)) {
            return true;
        }
        return false;
    }

    private int[] getConstructiveMove() {
        // Get CONSTRUCTIVE move
        int[] constructiveMove = new int[2];
        //        This is what you calculate
        do {
            constructiveMove[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
            constructiveMove[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);
        }
        while (!isCellValid(constructiveMove[X], constructiveMove[Y]));

        return constructiveMove;
    }

}