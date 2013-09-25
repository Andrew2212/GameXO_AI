package org.hexlet.gamexo.ai.gardnerway;

import org.hexlet.gamexo.ai.IBrainAI;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.lang.Exception;

/**
 * Date: 03.09.13
 * Time: 15:33
 * Вычисления ходов данного ИИ основываются на принципе
 * коробочных самообучающихся машин.
 * Обучение ИИ состоит в том, что проигрышные позиции
 * запоминаются и при следующей партии ходы, приводящие
 * к тем же позициям, блокируются.
 * Таким образом накапливается опыт ИИ и сила его игры
 * постепенно возрастает.
 * Проигрышные позиции хранятся в файлах, и извлекются
 * на момент проверки. Для уменьшения количества записанных
 * позиций, доска ,при проверке, поворачивается, зеркалится
 * и снова поворачивается. Этим достигается проверка всех
 * перевернутых и зеркальных вариантов.
 */
public class Gardner implements IBrainAI {

    private char[][] gameBoard;
    private char[][] oldGameBoard;
    private char[][] rotateBoard;
    private boolean isFirst;
    private char chip, myChip;
    private int numInTheRow;
    private int column;
    private int row;
    private int coordX, coordY;
    private int columnX, rowY;

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;

    public Gardner(int fieldSize, int numChecked) {
        gameBoard = new char[fieldSize][fieldSize];
        this.numInTheRow = numChecked;
    }

    /*
    Этот конструктор комментить не надо.
    Пусть будет просто перегрузка конструкторов и
    методов
     */
    public Gardner(int columnAmount, int rowAmount,
                   int numInTheRow, boolean isFirst) {

        gameBoard = new char[columnAmount][rowAmount];
        this.numInTheRow = numInTheRow;
        /*
        Выбор фишки будет валидным при условии, что
        крестики всегда играют первыми.
         */
    }

//    public int[]  letsPlay(int x, int y)

    /**
     * Пока что метод выискивает последний ход, проверяет в соответствии
     * с ним возможность выигра противником, и делает простой рандомный
     * ход. Если противник может выиграть следующим ходом, то он занимает
     * победное поле своей фишкой.
     * @param fieldMatrix матрица поля, переданная ядром.
     * @return массив координат клетки, куда выполняется ход.
     */
    public int[]  findMove(char[][] fieldMatrix) /*throws Exception*/{

//            throws CellIsNotEmptyException{

//        if (!isCellEmpty(x, y)){
//            String ex = x + " " + y;
//            throw new  CellIsNotEmptyException(ex);
//        }
        setMoveCell(fieldMatrix);   // возвращаем координаты хода противника
        coordX = 25; // обнуляем потенциально выигрышную клетку
        coordY = 25;
        if (isWin(columnX, rowY, chip)) {
		    /*
		    Изврат с возвратом победы,
		    надо будет прописать исключение победы
		    и прописать его же в интерфейсе
		     */
            MOVE[X] = 25;
            MOVE[Y] = 25;
            return MOVE;
//		    throw new Exception("Win");
        } else {

            do {
			    /*
			    Мозг червяка - если ИИ видит, что следующим
			    ходом противник выиграет, то ставит в это место
			    свою фишку.
			     */
                if (coordX != 25 && coordY != 25){
                    MOVE[X] = coordX;
                    MOVE[Y] = coordY;
                    return MOVE;
                }
                MOVE[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
                MOVE[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);
            } while (isCellEmpty(MOVE[X], MOVE[Y]));
        }

        return MOVE;
    }

    /**
     * Сверяет новуюя матрицу доски с матрицей доски
     * предыдущего хода. Определяет клетку последнего
     * хода и фишку, поставленную в нее. За свою фишку
     * ИИ принимает фишку противоположную походившей
     * @param fieldMatrix поле, которое пришло из ядра
     */
    public void setMoveCell(char[][] fieldMatrix){

        for (int y = 0; y < fieldMatrix.length; y++) {
            for (int x = 0; x < fieldMatrix[1].length; x++) {
			    /*
			    На самом деле лучше проверить всю доску,
			    что бы удостовериться, что нет еще лишних
			    полей. Так же не очень удобно, если позволительно
			    правило пасса (хотя в крестиках пасс выглядит абсурдно).
			     */

                if (fieldMatrix[x][y] != fieldMatrix[x][y]){
                    columnX = x;
                    rowY = y;
                    chip = fieldMatrix[x][y];
                    myChip = (chip == 'X') ? 'O' : 'X';

                    gameBoard = BoardModifier.copyBoard(fieldMatrix);
                }
            }
        }
    }

    // Проверяет - пуста ли ячейка в которую хочет походить противник
    public boolean isCellEmpty(int x,int y){
        return gameBoard[x][y] == '\u0000';
    }

    // Проверка на победу
    public boolean isWin(int x, int y, char chip) {
        HashSet<Integer[]> win = new HashSet<Integer[]>();
		/*
		Проход победных рядов по четырем направлениям
		 */
        for (int i = 0; i < 4; i++) {
            win.addAll(checkRow(x, y, chip, i));

			/*
			Если в Set элементов координат больше, чем один,
			то партия считается выигранной/проигранной, т.к.
			есть минимум одна из двух клеток, которую не успевает
			обезвредить противник.
			 */
            if ((win.size()) > 1) {
                return true;
            }
        }
		/*
		Получаем координату потенциалной победной клетки
		 */
        for (Integer[] integers : win){
            coordX = integers[0];
            coordY = integers[1];
        }
        return false;
    }

    /**
     * Выполняется проверка победы при заполненнии указанной фишкой
     * победного ряда, состоящего из numInTheRow количества фишек подряд.
     * При проверке осуществляется перебор полей на длину победного ряда
     * слева-направо, сверху-вниз, по диагонали слева-вниз и
     * по диагонали слева-вверх. При этом начальная клетка для проверки
     * определяется максимальным выносом на длину победного ряда влево или вверх.
     * После проверки граничной клетки, выполняется проверка на длину ряда для
     * сосденей с ней клетки в направлении проверки.
     *
     * @param xx координата клетки по Х
     * @param yy координата клетки по Y
     * @param chip фишка (камень) для которого проверяется состояние выигрыша
     * @param direction напраление проверки ряда
     * @return HeshSet координат пустых потенциально победных клеток
     * @exception IndexOutOfBoundsException выкидывается при попадании
     * проверочных координат за границы массива.
     */
    public HashSet<Integer[]> checkRow(int xx, int yy, char chip, int direction) {
        int x = 0, y = 0;
        HashSet<Integer[]> emptySell = new HashSet<Integer[]>();   //сборщик пустых полей от прохода ряда
        HashSet<Integer[]> emptySum = new HashSet<Integer[]>();    //сборщик пустых полей от всех проходов
        Integer[] xy = new Integer[2];

        start:
        for (int i = 0; i < numInTheRow; i++) {
            for (int k = 0; k < numInTheRow; k++) {
                x = xx - (numInTheRow - 1) + i + k;
                switch (direction) {      // переключатель направлений
                    case 0:               // проверка по горизонтали слева
                        y = yy;
                        break;
                    case 1:               // проверка по вертикали сверху
                        x = xx;
                        y = yy - (numInTheRow - 1) + i + k;
                        break;
                    case 2:               // проверка по диагонали слева-сверху
                        y = x + (yy - xx);
                        break;
                    case 3:               // проверка по диагонали слева-снизу
                        y = (yy + xx) - x;
                        break;
                }
                try {

			        /*
			         Если при проходе попадается знак отличный от
			         проверяемого, то Set пустых клеток обнуляется и
			         прекращается дальнейший перебор ряда.
			         */
                    if (gameBoard[x][y] != chip & gameBoard[x][y] != '\u0000') {
                        emptySell.clear();
                        continue start;
                    }
			        /*
			         Здесь мы закидываем пустую клетку в Set
			         */
                    if (gameBoard[x][y] == '\u0000') {
                        xy[0] = x;
                        xy[1] = y;
                        emptySell.add(xy);
                    }
		        /*
		         Если проверка попала за границы массива, то обнуляем
		         Set пустых клеток.
		         */
                } catch (IndexOutOfBoundsException ex) {
                    emptySell.clear();
                    continue start;
                }
            }
	        /*
	         при отсутствии пустых полей - победа
	         записываем в кучу больше одного элемента
	        */
            if (emptySell.size() == 0) {
                System.out.println(emptySell.size());
                for (int g = 0; g < 3; g++) {
                    emptySum.add(xy);
                }
                return emptySum;
            }
	        /*
	         Если пустых полей больше одного,
		     то очистить счетчик пустых полей
		    */
            if (emptySell.size() > 1) {
                emptySell.clear();
            }
            emptySum.addAll(emptySell);
            emptySell.clear();
        }
//	    emptySum.addAll(emptySell);
        return emptySum;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public class CellIsNotEmptyException extends Exception{

        public CellIsNotEmptyException(String ex) {
            super(ex);
        }

    }


}
