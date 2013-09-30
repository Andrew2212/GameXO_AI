package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

public class Minimax implements IBrainAI{

    public static final char VALUE_X = 'X';
    public static final char VALUE_O = 'O';
    private static final char DEFAULT_CELL_VALUE = '_';
    public static final int MINUSINFINITY = -1000;

    private final int LENGTH; //длинна линии, необходимая для победы
    private final int X_SIZE; // количество строк поля
    private final int Y_SIZE; // количество столбцов поля
    private char[][] field; //используется для хранения оригинального поля

    private int weightOfBestMove;
    private Heuristic heuristic;
//*************REMAKE Random dummy*********************************
    private final int[] MOVE = new int[2];    // координаты следующих ходов
    private static final int X = 0;
    private static final int Y = 1;
    private GetterLastEnemyMove getterLastEnemyMove;
    private char signBot; // за кого играет бот. Х либо О

    public Minimax(int fieldSize,int numChecked){
        this.LENGTH = numChecked;
        this.X_SIZE = fieldSize;
        this.Y_SIZE = fieldSize;

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


//    **********End of random dummy******************************************

    /**
     * конструктор, инициализирует все клетки нулями
     * @param len длинна линии, необходимая для победы
     * @param x   количество строк поля
     * @param y   количество столбцов на поле
     */
    public Minimax(int len, int x, int y) {
        this.LENGTH = len;
        this.X_SIZE = x;
        this.Y_SIZE = y;
        this.field = new char[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                this.field[i][j] = DEFAULT_CELL_VALUE;
            }
        }
        weightOfBestMove = MINUSINFINITY;
        heuristic = Heuristic.createInstance(len,x,y);
    }

    // Begin
    public int[] findMoveMiniMax(char[][] fieldMatrix) {
        int[] curBestMove = {0, 0};
        weightOfBestMove = MINUSINFINITY;
        maxMinStrategy(3, 3, fieldMatrix, 0, curBestMove); // maxMin on depth 2. Only for X!!!
        //  best move didn't find. Will use random
        // TODO add check if the cell is not empty.
        if  (weightOfBestMove == 0)  {
            MOVE[X] = 2; //(int) Math.floor(Math.random() * fieldMatrix.length);
            MOVE[Y] = 2; //(int) Math.floor(Math.random() * fieldMatrix.length);
        }
        System.out.println("Weight = " + weightOfBestMove);
        return MOVE;  // returns garbage. Need to return to this, when will finish findMove procedure
    }

    private char[][] copyField(char[][] fieldMatrix) {
        char[][] temp = new char[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                temp[i][j] = fieldMatrix[i][j];
            }
        }
        return temp;
    }

    /* ***** ОПИСАЛОВО *******
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
         TODO протестировать, отладить и сделать возможным работу для O

         есть косяк, что постоянно на пустом поле эвристика выдает 12, если сделать ход x в {0,0},
         а О куда-то еще поставить. хотя вообще по идее на пустом поле при глубине 2, на которой я тестировал
         веса всех ходов должны быть 0.
         [x][][]
         [][o][]
         [][][]

         Причем дальше, если продолжать играть все равно он упорно считает, что лучший ход в левый верхний угол
         с весом 12.

         Короче надо все вместе с эвристикой смотреть.
     */

    // search in the depth
    private void maxMinStrategy(int depth, final int depthMax, char[][] curField, int curStepWeight, int[] curBestMove) {
        if (depth != 0) {
            for (int j = 0; j < X_SIZE; j++) {  // looking for empty cell. Maybe it will be the best step.
                for (int k = 0; k < Y_SIZE; k++) {
                    if (curField[j][k] == DEFAULT_CELL_VALUE) {
                        int deltaWeight = 0;
                        if ( (depthMax - depth) % 2 == 0) {
                            curField[j][k] = VALUE_X;
                            if (depth == 2) {
                                curBestMove[X] = j; // save temp best step
                                curBestMove[Y] = k;
                            }
                            deltaWeight = heuristic.heuristicRating(j, k, 1);
                        }
                        else {
                            curField[j][k] = VALUE_O;
                            deltaWeight = heuristic.heuristicRating(j, k, -1);
                        }// end of operators before recursive call
                        maxMinStrategy(depth - 1, depthMax, curField, curStepWeight + deltaWeight, curBestMove);
                        // operators after recursive call
                        // reverse all changes
                        curField[j][k] = DEFAULT_CELL_VALUE;  // reverse curField back, cancel our current step
                        // other operators (depth, curStepWeight) reverse themselves, because we haven't changed them
                    }
                }
            }
        }
        else {
            if (weightOfBestMove < curStepWeight) {
                weightOfBestMove = curStepWeight;
                MOVE[X] = curBestMove[X];
                MOVE[Y] = curBestMove[Y];
            }
        }
    }

    //метод используется в клиенте тестирования, в дальнейшем не нужен
    public void showField() {
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                if (j == Y_SIZE - 1){
                    System.out.println(field[i][j]);
                } else {
                    System.out.print(field[i][j] + " ");
                }
            }
        }
    }

}