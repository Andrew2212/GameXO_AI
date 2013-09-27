package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

public class Minimax implements IBrainAI{


    private final int LENGTH; //длинна линии, необходимая для победы
    private final int X_SIZE; // количество строк поля
    private final int Y_SIZE; // количество столбцов поля
    private int[][] field; //используется для хранения поля

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
    public Minimax(int len, int x, int y){
        this.LENGTH = len;
        this.X_SIZE = x;
        this.Y_SIZE = y;
        this.field = new int[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                this.field[i][j] = 0;
            }
        }
    }




    //метод используется в клиенте тестирования, в дальнейшем не нужен
    public void showField(){
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