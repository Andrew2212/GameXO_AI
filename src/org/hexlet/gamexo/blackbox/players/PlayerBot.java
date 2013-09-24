package org.hexlet.gamexo.blackbox.players;


import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.gardnerway.Gardner;
import org.hexlet.gamexo.ai.minimaxway.Minimax;
import org.hexlet.gamexo.blackbox.game.GameField;

public class PlayerBot implements IPlayer {

    private static final int X = 0;
    private static final int Y = 1;
    private IBrainAI iBrainAI;

    /**
     *
     * @param fieldSize
     * @param numChecked
     */
    public PlayerBot(int fieldSize, int numChecked) {

        WayEnum wayEnum;// Switch on that you need
        wayEnum = WayEnum.GARDNER;
//        wayEnum = WayEnum.MINIMAX;

        switch (wayEnum) {

            case GARDNER:
                iBrainAI = new Gardner(fieldSize,numChecked);
                break;

            case MINIMAX:
                iBrainAI = new Minimax(fieldSize,numChecked);
                break;

            default:
                break;
        }
    }

    public int[] doMove() {

        int[] position = getCoordinate();

        while (GameField.getFieldMatrix()[position[X]][position[Y]] != GameField.getDefaultCellValue()) {
            position = getCoordinate();
        }

        return position;
    }

//    ---------Private Methods-----------------

    private int[] getCoordinate() {

        int[] position = new int[2];

//        Just random AI - it works perfectly
//        position[X] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);
//        position[Y] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);

        position[X] = iBrainAI.findMove(GameField.getFieldMatrix())[X];
        position[Y] = iBrainAI.findMove(GameField.getFieldMatrix())[Y];

        return position;
    }

}

