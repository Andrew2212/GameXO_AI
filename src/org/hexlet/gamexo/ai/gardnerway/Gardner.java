package org.hexlet.gamexo.ai.gardnerway;

import org.hexlet.gamexo.ai.IBrainAI;

import java.util.*;
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
public class Gardner {

    private char[][] gameBoard;
    private char[][] oldFieldMatrix;
    private ArrayList<Integer> history;
    private char enemyChip, myChip;
    private int outX, outY;
    private int inX, inY;
	private boolean firstMove = true;

    private int[] move = new int[2];
	private final int BOARD_SIZE;
    private final int NUM_IN_THE_ROW;
	private final char EMPTY = '_';
	private final String FILE_NAME;
	private final String BASE_DIR;
    private static final int X = 0;
    private static final int Y = 1;


    public Gardner(int fieldSize, int numInTheRow) {
        gameBoard = new char[fieldSize][fieldSize];
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                gameBoard[x][y] = EMPTY;
            }
        }
        NUM_IN_THE_ROW = numInTheRow;
        history = new ArrayList<Integer>();
	    BOARD_SIZE = fieldSize;
	    FILE_NAME = "(" + BOARD_SIZE + "x" + BOARD_SIZE + ")[" + NUM_IN_THE_ROW + "].xog";
	    BASE_DIR = BOARD_SIZE + " x " + BOARD_SIZE + " [" + NUM_IN_THE_ROW + "]/";
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
        NUM_IN_THE_ROW = numInTheRow;
        /*
        ****Maybe it needs to get sign from rest part of 'Game' ? ****
        *
        * It's will be inner representation of game signs.
        * Needs for memorization identical positions whatever sign
        * moves first.
        */
        myChip = isFirst ? 'X' : 'O';
        enemyChip = isFirst ? 'O' : 'X';
	    BOARD_SIZE = columnAmount;
        FILE_NAME = "(" + BOARD_SIZE + "x" + BOARD_SIZE + ")[" + NUM_IN_THE_ROW + "].xog";
	    BASE_DIR = BOARD_SIZE + " x " + BOARD_SIZE + " [" + NUM_IN_THE_ROW + "]/";
    }

    /**
     * Пока что метод выискивает последний ход, и передает дальше, для
     * вычисления следующего хода. Перегрузка методов получилась из
     * желания как можно меньше переделывать код, и иметь более гибкий
     * интерфейс для тестирования.
     * @param fieldMatrix матрица поля, переданная ядром.
     * @return массив координат клетки, куда выполняется ход.
     */
//    public int[]  findMove(Character[][] fieldMatrix) {
//
//        setMoveCell(fieldMatrix);   // возвращаем координаты хода противника
//
//        try {
//            return findMove(inX, inY);
//        } catch (CellIsNotEmptyException ex){
//            return move;    // в случае исключения  пока что получится падение программы
//        }
//    }

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
        move[X] = 25;              // обнуляем потенциально выигрышную клетку
        move[Y] = 25;

        if (firstMove){
            move[X] = BOARD_SIZE / 2;
            move[Y] = BOARD_SIZE / 2;
        } else {
            gameBoard[x][y] = enemyChip;

            if (history.size() > NUM_IN_THE_ROW) {
                /*
                Checks last AI move. Maybe it lead to win.
                */
                int[] myMove = BoardModifier.getCoordinateFromIndex
                        (history.get(history.size() - 1), BOARD_SIZE);
                if (isWin(myMove[X], myMove[Y], myChip)) {
//		    throw new Exception("Win");
                    System.out.println("I win");
                }
            }

            if (move[X] == 25 || move[Y] == 25) {
                if (isWin(x, y, enemyChip)) {
                    String sortedHistory = sortHistory(history);
                    FileMaster file = new FileMaster(BASE_DIR, history.size() + "." + FILE_NAME);
                    file.writeFile(sortedHistory);
                    System.out.println(sortedHistory);
                }
                if (move[X] != 25 || move[Y] != 25) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (Integer i : history) {
                        temp.add(i);
                    }
                    temp.add(BoardModifier.getIndexOfCell(x, y, BOARD_SIZE));
                    temp.add(BoardModifier.getIndexOfCell(move[X], move[Y], BOARD_SIZE));
                    FileMaster fileTemp = new FileMaster(BASE_DIR, temp.size() + "." + FILE_NAME);
                    if (comparePos(fileTemp, temp)){
                        for (int i = 0; i < 4; i++) {
                            temp.remove(temp.size() - 1);
                        }
                        String loosingWay = sortHistory(temp);
                        FileMaster loosingFile = new FileMaster(BASE_DIR, temp.size() + "." + FILE_NAME);
                        loosingFile.writeFile(loosingWay);
                    }
                    return move;
                }

            }
        }



        firstMove = false;

        history.add(BoardModifier.getIndexOfCell(x, y, BOARD_SIZE));    // adds enemy move

        while (true){
            ArrayList<Integer> tempHistory = new ArrayList<Integer>(history.size());

            for (Integer i : history) {
                tempHistory.add(i);
            }

            ArrayList<Integer> deniedCells = new ArrayList<Integer>();

            start:
            while (true){
                /*
			    Мозг червяка - если ИИ видит, что следующим
			    ходом противник выиграет, то ставит в это место
			    свою фишку.
			     */
                if (move[X] == 25 || move[Y] == 25) {
                    move[X] = (int) Math.floor(Math.random() * BOARD_SIZE);
                    move[Y] = (int) Math.floor(Math.random() * BOARD_SIZE);
                }

                for (Integer i : deniedCells) {
                    if (move == BoardModifier.getCoordinateFromIndex(i, BOARD_SIZE)) {
                        continue start;
                    }
                }
                if (isCellEmpty(move[X], move[Y])) {//we have to play in empty cell
                    break;
                }
                move[X] = 25;
                move[Y] = 25;
            }


            tempHistory.add(BoardModifier.getIndexOfCell(move[X], move[Y], BOARD_SIZE));
//            String sortedTemp = sortHistory(tempHistory);
            FileMaster tempFile = new FileMaster(BASE_DIR, tempHistory.size() + "." + FILE_NAME);
            /*
            If there is all cells lead to defeat, AI marks his previous position as illegal.
             */
            if (!comparePos(tempFile, tempHistory)) break;

            deniedCells.add(BoardModifier.getIndexOfCell(move[X], move[Y], BOARD_SIZE));

            if (deniedCells.size() == BOARD_SIZE * BOARD_SIZE - history.size()) {
                for (int i = 0; i < 2; i++) {
                    tempHistory.remove(tempHistory.size() - 1);
                }
                String previousHistory = sortHistory(tempHistory);
                FileMaster previousFile = new FileMaster(BASE_DIR, tempHistory.size() + "." + FILE_NAME);
                previousFile.writeFile(previousHistory);
                break;
            }
            move[X] = 25;
            move[Y] = 25;
        }

	    gameBoard[move[X]][move[Y]] = myChip;
        /*
        adds AI move to history
         */
        history.add(BoardModifier.getIndexOfCell(move[X], move[Y], BOARD_SIZE));

        return move;
    }

    /**
     * Сверяет новую матрицу доски с предыдущей матрицей и
     * определяет координаты последнего хода. Так же
     * ИИ вычисляет очередь своих ходов и оперирует своим
     * внутренним порядком фишек в зависимости от
     * полученного результата.
     * @param fieldMatrix поле, которое пришло из ядра
     */

    public void setMoveCell(char[][] fieldMatrix) {

        /*
        Very first move done by AI
        It recognise hwo is doing first move.
        First player gets sign X and second gets O.
         */
        if (firstMove) {
            oldFieldMatrix = BoardModifier.copyBoard(fieldMatrix);
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if ((x == 0) && (y == 0)) continue;
                    /*
                    In this case AI moves second;
                     */
                    if (fieldMatrix[0][0] != fieldMatrix[x][y]) {
                        /*
                        Move might be done in 0-0 sell.
                         */
                        if (fieldMatrix[0][0] !=
                                fieldMatrix[BOARD_SIZE - 1]
                                           [BOARD_SIZE - 1]) {
                            inX = 0;
                            inY = 0;
                        } else {
                            inX = x;
                            inY = y;
                        }
                        myChip = 'O';
                        enemyChip = 'X';
                        firstMove = false;
                        return;
                    }
                }
            }

            /*
            If all cells was empty AI moves first
             */
            myChip = 'X';
            enemyChip = 'O';
            inX = 100;
            inY = 100;
            return;
        }
        /*
        Gets last move coordinates from incoming game field.
         */
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (fieldMatrix[x][y] != oldFieldMatrix[x][y]) {
                    int num = BoardModifier.getIndexOfCell(x, y, BOARD_SIZE);
                    /*
                    Checks if current cell is equal to previous move done by AI
                     */
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

    // Проверяет - пуста ли ячейка в которую хочет походить ИИ
    public boolean isCellEmpty(int x,int y){
        return gameBoard[x][y] == EMPTY;
    }

    // Проверка на победу
    public boolean isWin(int x, int y, char chip) {
        boolean result = false;
        TreeSet<Integer> win = new TreeSet<Integer>();

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
            if (win.size() > 1) {
                result = true;
                if (win.size() == 3) {
                    return true;
                }
                break;
            }
        }
		/*
		Получаем координату потенциалной победной клетки
		 */
        if (win.size() != 0) {
            System.out.println(win.last());
            move = BoardModifier.getCoordinateFromIndex(win.last(), BOARD_SIZE);
            System.out.println(move[X] + "&" + move[Y]);
        }

        return result;
    }

    /**
     * Выполняется проверка победы при заполненнии указанной фишкой
     * победного ряда, состоящего из NUM_IN_THE_ROW количества фишек подряд.
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
    public TreeSet<Integer> checkRow(int xx, int yy, char chip, int direction) {
        int x = 0, y = 0;
        TreeSet<Integer> emptySell = new TreeSet<Integer>();   //сборщик пустых полей от прохода ряда
        TreeSet<Integer> emptySum = new TreeSet<Integer>();    //сборщик пустых полей от всех проходов

        start:
        for (int i = 0; i < NUM_IN_THE_ROW; i++) {
            for (int k = 0; k < NUM_IN_THE_ROW; k++) {
                x = xx - (NUM_IN_THE_ROW - 1) + i + k;
                switch (direction) {      // переключатель направлений
                    case 0:               // проверка по горизонтали слева
                        y = yy;
                        break;
                    case 1:               // проверка по вертикали сверху
                        x = xx;
                        y = yy - (NUM_IN_THE_ROW - 1) + i + k;
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
                        Integer number = BoardModifier.getIndexOfCell(x, y, BOARD_SIZE);
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
                    emptySell.add(50 + g);
                }
                return emptySell;
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
        return emptySum;
    }

    /**
     * String position got from history and sorted with TreeSet.
     * It need for write history in the base,and make comparison with
     * positions already stored in the base.
     * @param history got from moves.
     * @return position in String
     */
    public String sortHistory(ArrayList<Integer> history){
        TreeSet<String> historySet = new TreeSet<String>();
        String sortedHistory = "";
        String cell;
        for (int i = 0; i < history.size(); i++) {
            if(i % 2 == 0){
               cell = "X_";
            } else {
               cell = "O_";
            }
            historySet.add(cell + history.get(i) + " ");
        }

        for (String s : historySet) {
            sortedHistory += s;
        }

        return sortedHistory;
    }

	public boolean comparePos(FileMaster file, ArrayList<Integer> tempHistory){
		String basePosition;
        String tempPosition = sortHistory(tempHistory);
        file.readFromScratch();
        while (true) {
            basePosition = file.readFile();
            if (basePosition == null){
                file.closeReading();
                break;
            }
            if (basePosition.equals(tempPosition)){
                file.closeReading();
                return true;
            }
        }
        file.closeReading();
        return false;
	}

    public class CellIsNotEmptyException extends Exception{

        public CellIsNotEmptyException(String ex) {
            super(ex);
        }

    }




}
