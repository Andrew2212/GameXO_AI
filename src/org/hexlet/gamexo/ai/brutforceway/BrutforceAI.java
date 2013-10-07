package org.hexlet.gamexo.ai.brutforceway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.FieldMatrixConverter;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * <br>Just simply AI </br>
 */
public class BrutforceAI implements IBrainAI {

    private int[] MOVE;
    private static final int X = 0;
    private static final int Y = 1;
    private static Character[][] fieldMatrix;
    private Character signBot;

    private GetterLastEnemyMove getterLastEnemyMove;
    private Constructor constructor;
    private Destructor destructor;
    private boolean isFirstMoveDone = false;//  Crutch for giving signBot

    public BrutforceAI(int fieldSize, int numChecked) {
        getterLastEnemyMove = new GetterLastEnemyMove(fieldSize);
        GameOptions.initGameOptions(fieldSize, numChecked);
        constructor = new Constructor();
        destructor = new Destructor();
    }

    /**
     * @param fieldMatrixObject Object[][] matrix from 'Game core'
     * @param figure player's sign
     * @return MOVE i.e. int[2] - coordinates of cell
     */
    public int[] findMove(Object[][] fieldMatrixObject, Object figure) {

        FieldMatrixConverter converter = new FieldMatrixConverter();
        Character[][] fieldMatrixCharacter = converter.convertFieldMatrixToCharacter(fieldMatrixObject);
        this.fieldMatrix = fieldMatrixCharacter;

        //Executes only one time
        if (!isFirstMoveDone) {
            signBot = converter.convertSignToCharacter(figure);
            System.out.println("isFirstMoveDone*****************signBot = " + signBot);
            GameOptions.setSignBotAndSignEnemy(signBot);
            isFirstMoveDone = true;
        }
        //Get lastEnemyMove (if it exists)
        int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrix);

        if (null != lastEnemyMove) {
            // Get ConstructiveWIN MOVE
            int[] moveConstructiveWin = constructor.getConstructiveWinMove();
            if (moveConstructiveWin != null) {
                System.out.println("BrutforceAI::findMove moveConstructiveWin[X] = " + moveConstructiveWin[X] + " moveConstructiveWin[Y] = " + moveConstructiveWin[Y] + " signBot = " + signBot);
                // Here should be GAME OVER =)
                return moveConstructiveWin;
            }
            // Get Destructive MOVE
            int[] moveDestructive = destructor.getDestructiveMove(lastEnemyMove[X], lastEnemyMove[Y]);
            if (moveDestructive != null) {
                System.out.println("BrutforceAI::findMove moveDestructive[X] = " + moveDestructive[X] + " moveDestructive[Y] = " + moveDestructive[Y] + " signBot = " + signBot);
                constructor.setLastMyOwnMove(moveDestructive);
                getterLastEnemyMove.setMyOwnMove(moveDestructive[X], moveDestructive[Y], signBot);
                return moveDestructive;
            }
        }

        // Get Constructive move
        MOVE = getConstructiveMove();
        System.out.println("BrutforceAI::getConstructiveMove constructiveMove[X] = " + MOVE[X] + " constructiveMove[Y] = " + MOVE[Y]);
        constructor.setLastMyOwnMove(MOVE);
        getterLastEnemyMove.setMyOwnMove(MOVE[X], MOVE[Y], signBot);
        return MOVE;
    }

    public static Character[][] getCopyFieldMatrix() {
        int size = fieldMatrix.length;
        Character[][] fieldMatrixCopy = new Character[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                fieldMatrixCopy[i][j] = fieldMatrix[i][j];
            }
        }
        return fieldMatrixCopy;

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