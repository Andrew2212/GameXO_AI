package org.hexlet.gamexo.ai;

import org.hexlet.gamexo.ai.utils.FieldMatrixConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 *<br>It's strict contract for each PlayerBot in order to setup some 'brain' </br>
 */
public interface IPlayerBot<T> {
    /**
     *
     * @param iBrainAI  distinct mode of the 'brain'
     * @param fieldMatrix  'core of Game' fieldMatrix
     * @param figure   player's sign (X or O)
     * @return   best move (i.e. coordinates of cell)
     * <br>Into this method we have to call 'iBrain.findMove(...)' and it should to return us the 'best move'</br>
     * <br>Ordinary realization is: int[] position = iBrainAI.findMove(matrix, figure);</br>
     * <br>there 'position' is coordinate of the 'best move' </br>
     */
     int[] getCoordinate(IBrainAI iBrainAI, T[][] fieldMatrix, T figure);
}
