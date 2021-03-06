package org.hexlet.gamexo.blackbox.players;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.IPlayerBot;
import org.hexlet.gamexo.ai.WayEnum;
import org.hexlet.gamexo.ai.brutforceway.BrutforceAI;
import org.hexlet.gamexo.ai.gardnerway.Gardner;
import org.hexlet.gamexo.ai.spareway.Spare;
import org.hexlet.gamexo.blackbox.game.GameField;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * Date: 24.09.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class PlayerBotEnemy implements IPlayer, IPlayerBot {

    private static final int X = 0;
    private static final int Y = 1;
    private static int[] position = new int[2];
    private IBrainAI iBrainAI;

    /**
     *
     * @param fieldSize
     * @param numChecked
     */
    public PlayerBotEnemy(int fieldSize, int numChecked) {

        WayEnum wayEnum;// Switch on that you need
//        wayEnum = WayEnum.GARDNER;
//        wayEnum = WayEnum.MINIMAX;
//        wayEnum = WayEnum.SPARE;
        wayEnum = WayEnum.BRUTFORCE;

        switch (wayEnum) {

            case GARDNER:
                iBrainAI = new Gardner(fieldSize,numChecked);
                break;

            case MINIMAX:

//                iBrainAI = new Minimax(fieldSize,numChecked);

                break;

            case SPARE:
                iBrainAI = new Spare(fieldSize,numChecked);
                break;

            case BRUTFORCE:
                iBrainAI = new BrutforceAI(fieldSize, numChecked);
                break;

            default:
                break;
        }
    }

    public int[] doMove() {
        System.out.println("PlayerBotEnemy::doMove()");
        do {
            position = getCoordinate(iBrainAI, GameField.getFieldMatrix(), GameField.getSignForNextMove());
        } while (GameField.getFieldMatrix()[position[X]][position[Y]] != GameField.getDefaultCellValue());

        return position;
    }

//    ---------Private Methods-----------------

        public int[] getCoordinate(IBrainAI iBrainAI, Object[][] matrix, Object figure) {
            position = iBrainAI.findMove(matrix, figure);
            System.out.println("PlayerBot::getCoordinate::position[X] = " + position[X] + ", position[Y] = " + position[Y]);
            return position;
        }

}

