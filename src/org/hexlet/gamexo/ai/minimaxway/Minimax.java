package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;

public class Minimax implements IBrainAI{

    // массив смещений адресов ячеек по направлениям
    private static final int[][] OFFSET = {
            {-1,0},  // up
            {-1,1},  // up-right
            {0,1},   // right
            {1,1},   // right - down
            {1,0},   // down
            {1,-1},  // left - down
            {0,-1},  // left
            {-1,-1}  // left-up
    };


    private final int LENGTH; //длинна линии, необходимая для победы
    private final int X_SIZE; // количество строк поля
    private final int Y_SIZE; // количество столбцов поля

    private int[][] field; //используется для хранения поля

//*************REMAKE************************************
    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;
    private GetterLastEnemyMove getterLastEnemyMove;
    private char signBot;

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

            System.out.println("Minimax::findMove MOVE[X] = " + MOVE[X] + " findMove MOVE[Y] = " + MOVE[Y] + " signBot = " + signBot);
            getterLastEnemyMove.setMyOwnMove(MOVE[X], MOVE[Y], signBot);
        }
        return MOVE;
    }


//    ****************************************************

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



    /**
     * метод для оценки ситуации на поле
     * @param x      номер стоки
     * @param y      номер столбца
     * @param sign   знак хода: -1 - нолик, 1 - крестик
     * @return       возвращаем -5 при победе ноликов, 5 - крестиков, 2 - ничья, 0 - есть еще ходы
     */
    //TODO исправить косяк: при ходе, сделанном в середину линии, на краях которой уже есть такие же символы, победа не  фиксируется
    public int heuristic(int x, int y, int sign){
        this.field[x][y] = sign;
        for (int j = 0; j < OFFSET.length; j++){   //последовательно перебираем все направления по часой стрелке,
            try{                                   // начиная с верха, на достижение требуемой длинны
                if (isEnoughLength(x,y,sign,j)){
                    switch (sign){
                        case -1: return -5;
                        case  1: return 5;
                    }
                }
            }  catch (ArrayIndexOutOfBoundsException e){
                continue;                                  //хитрый план для контроля выхода за границы поля при проверке
            }
        }
        if (hasEmptyCell()) {
            return 0;
        }
        return 2;
    }

    /**
     *  проверяем , достигнута ли требуемая длинна линии в заданном направлении
     * @param x                                номер строки
     * @param y                                номер столбца
     * @param sign                             знак игрока
     * @param direction                        номер направления
     * @return                                 true, если длинна достигнута, иначе - false
     * @throws ArrayIndexOutOfBoundsException  кидаем исключение при попытке вылезти за пределы поля
     */
    //TODO можно добавить возврат false, в случае, когда количество оставшихся клеток в данном направлении заведомо меньше
    //TODO чем значение длинны выйгрышной линии  и убрать исключение
    private boolean isEnoughLength(int x, int y, int sign, int direction) throws ArrayIndexOutOfBoundsException{
        int cntLine = 1;
        int a = x;
        int b = y;
        while (cntLine < LENGTH) {
            a +=  OFFSET[direction][0];
            b +=  OFFSET[direction][1];
            if (this.field[a][b] != sign){
                return false;
            }
            cntLine++;
        }
        return true;


    }
    //TODO проверяем наличие пустых ячеек тупо перебором, что не есть хорошо
    private boolean hasEmptyCell(){
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                if (field[i][j] == 0){
                    return true;
                }
            }
        }
        return false;
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