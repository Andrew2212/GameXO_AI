package org.hexlet.gamexo.ai.gardnerway;

import org.hexlet.gamexo.ai.IBrainAI;
import java.util.ArrayList;
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
public class Gardner implements IBrainAI{

    private char[][] gameBoard;
    private char[][] oldFieldMatrix;
    private char[][] rotateBoard;
    private ArrayList<Integer> history;
    private boolean isFirst;
    private char enemyChip, myChip;
    private final char EMPTY = '_';
    private int numInTheRow;
    private int outX, outY;
    private int inX, inY;
	private boolean firstMove = true;

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;


    public Gardner(int fieldSize, int numChecked) {
        gameBoard = new char[fieldSize][fieldSize];
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                gameBoard[x][y] = EMPTY;
            }
        }
        this.numInTheRow = numChecked;
        history = new ArrayList<Integer>();
    }

    /*
    Этот конструктор комментить не надо.
    Пусть будет просто перегрузка конструкторов и
    методов.
    Don't used in this game implementation.
     */
    public Gardner(int columnAmount, int rowAmount,
                   int numInTheRow, boolean isFirst) {

        gameBoard = new char[columnAmount][rowAmount];
        this.numInTheRow = numInTheRow;
        /*
        ****Maybe it needs to get sign from rest part of 'Game' ? ****
        *
        * It's will be inner representation of game signs.
        * Needs for memorization identical positions whatever sign
        * moves first.
        */
        myChip = isFirst ? 'X' : 'O';
        enemyChip = isFirst ? 'O' : 'X';
    }

    /**
     * Пока что метод выискивает последний ход, и передает дальше, для
     * вычисления следующего хода. Перегрузка методов получилась из
     * желания как можно меньше переделывать код, и иметь более гибкий
     * интерфейс для тестирования.
     * @param fieldMatrix матрица поля, переданная ядром.
     * @return массив координат клетки, куда выполняется ход.
     */
    public int[]  findMove(char[][] fieldMatrix) {

        setMoveCell(fieldMatrix);   // возвращаем координаты хода противника

        try {
            return findMove(inX, inY);
        } catch (CellIsNotEmptyException ex){
            return  MOVE;    // в случае исключения  пока что получится падение программы
        }
    }

    /**
     * проверяет в соответствии с входящими координатами
     * возможность выигрыша противником, и делает простой рандомный
     * ход. Если противник может выиграть следующим ходом, то он занимает
     * победное поле своей фишкой.
     * @param x координата по X
     * @param y координата по Y
     * @return массив кординаты со значениями x и y
     * @throws CellIsNotEmptyException  кидется, если пришли координаты
     * уже занятой клетки
     */
    public int[] findMove(int x, int y) throws CellIsNotEmptyException{


//        if (!isCellEmpty(x, y)){
//            String ex = x + " " + y;
//            throw new  CellIsNotEmptyException(ex);
//        }
        outX = 25;              // обнуляем потенциально выигрышную клетку
        outY = 25;

        if (!firstMove){
            gameBoard[x][y] = enemyChip;
            history.add(getIndexOfCell(x, y));
	        if (isWin(x, y, enemyChip)) {
		    /*
		    Изврат с возвратом победы,
		    надо будет прописать исключение победы
		     */
		        MOVE[X] = 25;
		        MOVE[Y] = 25;
		        return MOVE;
//		    throw new Exception("Win");
	        }
            if (history.size() > numInTheRow) {
                /*
                Checks last AI move. Maybe it lead to win.
                */
                int[] checkMyMoves = getCoordinateFromIndex(history.get(history.size() - 2));
                if (isWin(checkMyMoves[X], checkMyMoves[Y], myChip)) {
                    MOVE[X] = 25;
                    MOVE[Y] = 25;
                    return MOVE;
//		    throw new Exception("Win");
                }
            }
        }

        firstMove = false;

	    do {
			    /*
			    Мозг червяка - если ИИ видит, что следующим
			    ходом противник выиграет, то ставит в это место
			    свою фишку.
			     */
		    if (outX != 25 && outY != 25){
			    MOVE[X] = outX;
			    MOVE[Y] = outY;
		    } else {
                MOVE[X] = (int) Math.floor(Math.random() * gameBoard.length);
                MOVE[Y] = (int) Math.floor(Math.random() * gameBoard.length);
            }
	    } while (!isCellEmpty(MOVE[X], MOVE[Y]));     //мы должны походить в пустую клетку


	    gameBoard[MOVE[X]][MOVE[Y]] = myChip;
        history.add(getIndexOfCell(MOVE[X],MOVE[Y]));
        return MOVE;
    }

    /**
     * Сверяет новую матрицу доски с предыдущей матрицей и
     * определяет координаты последнего хода. Так же
     * ИИ вычисляет очередь своих ходов и оперирует своим
     * внутренним внутренним порядком фишек в зависимости от
     * полученного результата.
     * @param fieldMatrix поле, которое пришло из ядра
     */
    public void setMoveCell(char[][] fieldMatrix) {

        /*
        Very first move done by AI
        It recognise hwo is doing first move.
         */
        if (firstMove) {
            oldFieldMatrix = BoardModifier.copyBoard(fieldMatrix);
            for (int y = 0; y < fieldMatrix.length; y++) {
                for (int x = 0; x < fieldMatrix[1].length; x++) {
                    if ((x == 0) && (y == 0)) continue;
                    if (fieldMatrix[0][0] != fieldMatrix[x][y]) {
                        /*
                        Move might be done in 0-0 sell.
                         */
                        if (fieldMatrix[0][0] !=
                                fieldMatrix[fieldMatrix.length - 1]
                                        [fieldMatrix.length - 1]) {
                            inX = 0;
                            inY = 0;
                        } else {
                            inX = x;
                            inY = y;
                        }
                        myChip = 'O';
                        enemyChip = 'X';
                        isFirst = false;
                        firstMove = false;
                        return;
                    }
                }
            }

            myChip = 'X';
            enemyChip = 'O';
            inX = 100;
            inY = 100;
            isFirst = true;
            return;
        }
        /*
        Gets last move coordinates from incoming game field.
         */
        for (int y = 0; y < fieldMatrix.length; y++) {
            for (int x = 0; x < fieldMatrix[1].length; x++) {
                if (fieldMatrix[x][y] != oldFieldMatrix[x][y]) {
                    int num = getIndexOfCell(x, y);
                    int historyNum = history.get(history.size() - 1);
                    if (num != historyNum) {
                        inX = x;
                        inY = y;
                        oldFieldMatrix = BoardModifier.copyBoard(fieldMatrix);
                        return;
                    }
                }
            }
        }
        // Has no new moves. Or move was done in occupied cell.
    }

    // Проверяет - пуста ли ячейка в которую хочет походить противник
    public boolean isCellEmpty(int x,int y){
        return gameBoard[x][y] == EMPTY;
    }

    // Проверка на победу
    public boolean isWin(int x, int y, char chip) {
        HashSet<Integer> win = new HashSet<Integer>();

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
        if (win.size() > 0){
            for (Integer integers : win){
				/*
				Преобразование порядкового номера клетки
				в ее координаты.
				 */
                int[] coordinate = getCoordinateFromIndex(integers);
                outX = coordinate[X];
                outY = coordinate[Y];
            }
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
    public HashSet<Integer> checkRow(int xx, int yy, char chip, int direction) {
        int x = 0, y = 0;
        HashSet<Integer> emptySell = new HashSet<Integer>();   //сборщик пустых полей от прохода ряда
        HashSet<Integer> emptySum = new HashSet<Integer>();    //сборщик пустых полей от всех проходов

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
                    if (gameBoard[x][y] != chip & gameBoard[x][y] != EMPTY) {
                        emptySell.clear();
                        continue start;
                    }
			        /*
			         Здесь мы закидываем порядковый номер пустой
			         клетки в Set, который получаем из координат.
			         */
                    if (gameBoard[x][y] == EMPTY) {
                        Integer number = getIndexOfCell(x, y);
                        emptySell.add(number); 	         //делаем преобразование
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
                    emptySum.add(50);
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

    /**
     * Переводим координаты в порядковый номер ячейки
     * массива считая слева сверху.
     * @param x  координата по Х
     * @param y  координата по У
     * @return порядковый номер ячейки массива.
     */
    public Integer getIndexOfCell(Integer x, Integer y) {
        Integer number;
        number = y * gameBoard.length + x;
        return number;
    }

    /**
     * Переводим порядковый номер ячейки массива в
     * координаты этой ячейки по Х и У.
     * @param number порядковый номер ячейки
     * @return массив координат
     */
    public int[] getCoordinateFromIndex(int number) {
        int[] num = new int[2];
        num[X] = number % gameBoard.length;
        num[Y] = number / gameBoard.length;
        return num;
    }

    public class CellIsNotEmptyException extends Exception{

        public CellIsNotEmptyException(String ex) {
            super(ex);
        }

    }


}
