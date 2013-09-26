package org.hexlet.gamexo.ai.spareway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

/**
 * Date: 24.09.13
 * Time: 12:17
 */
public class Spare implements IBrainAI {

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;
    private char signBot;

    private GetterLastEnemyMove getterLastEnemyMove;

    public Spare(int fieldSize, int numChecked) {
        getterLastEnemyMove = new GetterLastEnemyMove(fieldSize);
    }

    /**
     * @param fieldMatrix char[][]
     * @return MOVE i.e. int[2] - coordinates of cell
     */
    public int[] findMove(char[][] fieldMatrix) {

         signBot = GameField.getSignForNextMove();
        //        This is what you calculate
        MOVE[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
        MOVE[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);

        //        checkout for random - it isn't needed for real AI
        if (GameField.isCellValid(MOVE[X], MOVE[Y])) {
            int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrix);
            if (null != lastEnemyMove) {
                //do something
            }

            System.out.println("Spare::findMove MOVE[X] = " + MOVE[X] + " findMove MOVE[Y] = " + MOVE[Y] + " signBot = " + signBot);
            getterLastEnemyMove.setMyOwnMove(MOVE[X], MOVE[Y], signBot);
        }
        return MOVE;
    }

}
