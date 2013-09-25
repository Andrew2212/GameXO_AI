package org.hexlet.gamexo.blackbox.players;


import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.gardnerway.Gardner;
import org.hexlet.gamexo.ai.minimaxway.Minimax;
import org.hexlet.gamexo.ai.spareway.Spare;
import org.hexlet.gamexo.blackbox.game.GameField;

public class PlayerBot implements IPlayer {

    private static final int X = 0;
    private static final int Y = 1;
    private static int[] position = new int[2];
    private IBrainAI iBrainAI;

    /**
     * @param fieldSize
     * @param numChecked
     */
    public PlayerBot(int fieldSize, int numChecked) {

        WayEnum wayEnum;// Switch on that you need
//        wayEnum = WayEnum.GARDNER;
//        wayEnum = WayEnum.MINIMAX;
        wayEnum = WayEnum.SPARE;

        switch (wayEnum) {

            case GARDNER:
                iBrainAI = new Gardner(fieldSize, numChecked);
                break;

            case MINIMAX:
                iBrainAI = new Minimax(fieldSize, numChecked);
                break;

            case SPARE:
                iBrainAI = new Spare(fieldSize, numChecked);
                break;

            default:
                break;
        }
    }

    public int[] doMove() {
        System.out.println("PlayerBot::doMove()");
        do {
            position = getCoordinate();
        } while (GameField.getFieldMatrix()[position[X]][position[Y]] != GameField.getDefaultCellValue());


        return position;
    }

//    ---------Private Methods-----------------

    private int[] getCoordinate() {

//        Just random AI - it works perfectly
//        position[X] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);
//        position[Y] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);

        position = iBrainAI.findMove(GameField.getFieldMatrix());

        return position;
    }

}

