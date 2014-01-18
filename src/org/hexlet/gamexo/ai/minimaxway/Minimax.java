package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.brutforceway.GameOptions;
//import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

    /* ***** ОПИСАНИЕ *******
            Программа строит дерево рекурсий на основе максиминной стратегии на глубину, задаваемую при вызове функции в
         параметре depth. Процедура осуществляет поиск в глубину. Она не отлажена, не протестирована, должна работать
         пока только для Х. Но зато должна работать для любого поля, на любую глубину. Еще она будет работать довольно
         медленно, сложность O(2^n), и использовать много памяти для стека вызовов.

         Идея: на каждой итерации проходимся по всему массиву в поисках пустой ячейки. При нахождении проверяем, какую
         фигуру надо поставить. Соответственно ставим ее. Если мы на самой вершине дерева, то мы запоминаем позицию,
         куда поставили Х, так как, возможно, это лучший ход. Оцениваем эвристику данного хода, записываем в deltaWeight.
         Далее идет рекурсивный вызов, при котором глубина уменьшается на 1, передается поле с установленной на нее
         фигурой (в первом случае Х), передается текущий вес ходов по пути от вершины до листа (на глубину depth),
         увеличенный на deltaWeight, и запомненный лучший ход.

         Когда добираемся до 0 глубины, оцениваем вес проделанного пути. Соответственно запоминаем максимальный вес и
         лучший ход. Здесь проиходит завершение рекурсивной функции, делается следующая итерация, поле и все веса
         возвращаются на шаг назад.
     */

public class Minimax implements IBrainAI {

    public static final char DEFAULT_CELL_VALUE = '_';

    public static final char VALUE_X = 'X';

    public static final char VALUE_O = 'O';

    public static final char VALUE_DRAW = 'D';

    public static final int ROW_COORD = 0;

    public static final int COL_COORD = 1;

    private int[] bestStep = {-1, -1};

    //    ==============It might be something like that=)===================

    private boolean isFirstMoveDone = false;//  Crutch for giving signBot
    private org.hexlet.gamexo.ai.utils.GetterLastEnemyMove getterLastEnemyMove;
    private org.hexlet.gamexo.ai.utils.FieldMatrixConverter converter;

    @Override
    public int[] findMove(Object[][] fieldMatrixObject, Object figure) {

        /*
        There are some crutches for Hexlet architecture - they use 'Enum[][] fieldMatrixEnum'
         */

//      Getting 'char[][] fieldMatrixChar' from 'T[][] fieldMatrixObject'
        converter = new org.hexlet.gamexo.ai.utils.FieldMatrixConverter();
        Character[][] fieldMatrixCharacter = converter.convertFieldMatrixToCharacter(fieldMatrixObject);
        char[][] fieldMatrixChar = org.hexlet.gamexo.ai.gardnerway.CoordinateConverter.characterToChar(fieldMatrixCharacter);

        //Executes only one time  - if it's necessary
        if (!isFirstMoveDone) {
//            Getting 'Character signBot' - if it's necessary
            Character signBot = converter.convertSignToCharacter(figure);
            System.out.println("isFirstMoveDone*****************signBot = " + GameOptions.getSignBot());
            isFirstMoveDone = true;
        }

        //Get lastEnemyMove (if it exists) - if it's necessary
        getterLastEnemyMove = new org.hexlet.gamexo.ai.utils.GetterLastEnemyMove();
        int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrixCharacter);

        return findMoveLocal(fieldMatrixChar);
    }

//===================================================================================

    private boolean isAIFigureX = true;
    /**
     * Constructs minimax AI instance with a given figure
     *
     * @param isAIFigureX boolean - true if AI figure = X
     *                    =========================================
     *                    !!!It's ALWAYS TRUE within constructor!!!
     *                    It might be cause of some problems
     *                    =========================================
     */
    public Minimax(boolean isAIFigureX) {
        this.isAIFigureX = isAIFigureX;
    }

    /**
     * Returns computer step using MaxMin strategy
     *
     * @param fieldMatrix char[][] - current field.
     * @return int[] with coords of computer step {row coordinate, column coordinate}.
     */

    public int[] findMoveLocal(char[][] fieldMatrix) {

        return bestStep;
    }

}