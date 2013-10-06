package org.hexlet.gamexo.ai.spareway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.FieldMatrixConverter;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

/**
 *
 */
public class Spare implements IBrainAI {

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;
    private static Character[][] fieldMatrix;
    private Character signBot;
    private boolean isFirstMoveDone = false;//  Crutch for giving signBot

    private GetterLastEnemyMove getterLastEnemyMove;

    public Spare(int fieldSize, int numChecked) {
        getterLastEnemyMove = new GetterLastEnemyMove(fieldSize);
    }

    /**
     * @param fieldMatrixObject Object[][] matrix from 'Game core'
     * @param figure player's sign
     * @return MOVE i.e. int[2] - coordinates of cell
     */
    public int[] findMove(Object[][] fieldMatrixObject, Object figure) {

//      Initializes 'converter' and gets current 'fieldMatrix'
        FieldMatrixConverter converter = new FieldMatrixConverter();
        Character[][] fieldMatrixCharacter = converter.convertFieldMatrixToCharacter(fieldMatrixObject);
        this.fieldMatrix = fieldMatrixCharacter;

//      Executes only one time
        if (!isFirstMoveDone) {
            signBot = converter.convertSignToCharacter(figure);
            System.out.println("isFirstMoveDone*****************signBot = " + signBot);
//          GameOptions.setSignBotAndSignEnemy(signBot);
            isFirstMoveDone = true;
        }

//      Get last EnemyMove
        int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrix);
        if (null != lastEnemyMove) {
            //do something
        }

//      This is what you calculate
        MOVE[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
        MOVE[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);

//        checkout for random - it isn't needed for real AI
        if (GameField.isCellValid(MOVE[X], MOVE[Y])) {
            System.out.println("Spare::findMove MOVE[X] = " + MOVE[X] + " findMove MOVE[Y] = " + MOVE[Y] + " signBot = " + signBot);
            getterLastEnemyMove.setMyOwnMove(MOVE[X], MOVE[Y], signBot);
        }
        return MOVE;
    }

}
