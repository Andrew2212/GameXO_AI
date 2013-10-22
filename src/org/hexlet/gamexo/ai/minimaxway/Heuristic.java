package org.hexlet.gamexo.ai.minimaxway;

/**
 Package-private class for Heuristics

 Пока сделал так, чтобы нужно было создавать объект, потому что оно само по себе должно работать,
 а у статик класса будут проблемы с параметрами, которые необходимо инициализировать сразу, а их по-хорошему
 надо передавать.

 Поэтому пока остановился на синглтоне.
 */
class Heuristic {

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
    private static Heuristic uniqueInstance = null;

    private final int LENGTH; //длинна линии, необходимая для победы
    private final int X_SIZE; // количество строк поля
    private final int Y_SIZE; // количество столбцов поля
    private int[][] field; //используется для хранения поля

    static Heuristic createInstance(int len, int x, int y) {
        if (uniqueInstance == null) {
            uniqueInstance = new  Heuristic(len, x, y);
        }
        return uniqueInstance;
    }

    private Heuristic(int len, int x, int y){
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
     int heuristicRating(int x, int y, int sign){
        this.field[x][y] = sign;
        for (int j = 0; j < OFFSET.length; j++){   //последовательно перебираем все направления по часой стрелке,
            try {                                   // начиная с верха, на достижение требуемой длинны
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
    //TODO люто, бешенно рефакторить!
    boolean isEnoughLength(int x, int y, int sign, int direction) throws ArrayIndexOutOfBoundsException{
        int cntLine = 1;
        int a = x;
        int b = y;
        try {
            while (cntLine < LENGTH) {                                 //здесь может вылетить   ArrayIndexOutOfBoundsException
                a +=  OFFSET[direction][0];                            // что помешает нам проверить противополжное направление
                b +=  OFFSET[direction][1];
                if (this.field[a][b] != sign){
                    break;
                }
                cntLine++;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            // мы его перехватываем, и тупо ничего не делаем
        }

        a = x;
        b = y;
        while (cntLine < LENGTH){
            a += OFFSET[(direction + 4) % 8][0];
            b += OFFSET[(direction + 4) % 8][1];
            if (this.field[a][b] != sign){
                break;
            }
            cntLine++;
        }
        if (cntLine == LENGTH){
            return true;
        }  else {
            return false;
        }
    }

    //TODO проверяем наличие пустых ячеек тупо перебором, что не есть хорошо
    boolean hasEmptyCell(){
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                if (field[i][j] == 0){
                    return true;
                }
            }
        }
        return false;
    }

    // interface copies to Heuristic class field from parameter
    // may be IndexOutOfBorder exception and may doesn't work well if size of this field not equal to that field
    public void copyField(char[][] thatField) {
        for (int i = 0; i < thatField.length; i++) {
            for (int j = 0; j < thatField[i].length; j++) {
                if (thatField[i][j] == Minimax.VALUE_X) {
                    this.field[i][j] = 1;  // x
                }
                else if (thatField[i][j] == Minimax.VALUE_O) {
                    this.field[i][j] = -1;
                }
                else {
                    this.field[i][j] = 0;
                }
            }
        }
    }

    //метод используется в клиенте тестирования, в дальнейшем не нужен
    private void showField(){
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
