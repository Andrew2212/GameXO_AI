package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;

/**
 * Date: 24.09.13
 * Time: 12:17
 */
public class Minimax implements IBrainAI {

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;

    public Minimax(int fieldSize, int numChecked){

    }

    /**
     * If you need to have last step you can get it by comparing arrays (old and new)
     * @param fieldMatrix
     * @return
     */
    public int[] findMove(char[][] fieldMatrix) {

        MOVE[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
        MOVE[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);

        return MOVE;
    }
}
