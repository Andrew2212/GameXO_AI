package org.hexlet.gamexo.ai.gardnerway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.FieldMatrixConverter;

import java.util.*;


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

    private GameStatusChecker checker;
    private char[][] oldFieldMatrix;
    private ArrayList<Integer> history;
    private char enemyChip, aiChip;
	private boolean firstMove = true;
	private int[] move = new int[2];
	private final char[][] GAME_BOARD;
	private final int BOARD_SIZE;
    private final int NUM_IN_THE_ROW;
	private final char EMPTY = '_';
	private final String FILE_NAME;
	private final String BASE_DIR;
    private static final int X = 0;
    private static final int Y = 1;


    public Gardner(int fieldSize, int numInTheRow) {
        GAME_BOARD = new char[fieldSize][fieldSize];
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                GAME_BOARD[x][y] = EMPTY;
            }
        }
        NUM_IN_THE_ROW = numInTheRow;
        history = new ArrayList<Integer>();
	    BOARD_SIZE = fieldSize;
	    FILE_NAME = "(" + BOARD_SIZE + "x" + BOARD_SIZE + ")[" + NUM_IN_THE_ROW + "].xog";
	    BASE_DIR = BOARD_SIZE + " x " + BOARD_SIZE + " [" + NUM_IN_THE_ROW + "]/";
        checker = new GameStatusChecker(EMPTY, NUM_IN_THE_ROW, BOARD_SIZE);
    }

    /*
    Этот конструктор комментить не надо.
    Пусть будет просто перегрузка конструкторов и
    методов.
    Don't used in this game implementation.
     */
    public Gardner(int columnAmount, int rowAmount,
                   int numInTheRow, boolean isFirst) {

        GAME_BOARD = new char[columnAmount][rowAmount];
        NUM_IN_THE_ROW = numInTheRow;
        /*
        ****Maybe it needs to get sign from rest part of 'Game' ? ****
        *
        * It's will be inner representation of game signs.
        * Needs for memorization identical positions whatever sign
        * moves first.
        */
        aiChip = isFirst ? 'X' : 'O';
        enemyChip = isFirst ? 'O' : 'X';
	    BOARD_SIZE = columnAmount;
        FILE_NAME = "(" + BOARD_SIZE + "x" + BOARD_SIZE + ")[" + NUM_IN_THE_ROW + "].xog";
	    BASE_DIR = BOARD_SIZE + " x " + BOARD_SIZE + " [" + NUM_IN_THE_ROW + "]/";
        checker = new GameStatusChecker(EMPTY, NUM_IN_THE_ROW, BOARD_SIZE);
    }
    /*
    Constructor for tests
     */
    public Gardner (char[][] gB, char myChip, int numInTheRow, ArrayList<Integer> h) {
        GAME_BOARD = gB;
        BOARD_SIZE = gB.length;
        NUM_IN_THE_ROW = numInTheRow;
        FILE_NAME = "(" + BOARD_SIZE + "x" + BOARD_SIZE + ")[" + NUM_IN_THE_ROW + "].xog";
        BASE_DIR = BOARD_SIZE + " x " + BOARD_SIZE + " [" + NUM_IN_THE_ROW + "]/";
        firstMove = false;
        history = h;
        this.aiChip = myChip;
        enemyChip = (myChip == 'O') ? 'X' : 'O';
        checker = new GameStatusChecker(EMPTY, NUM_IN_THE_ROW, BOARD_SIZE);
    }

    /**
     * Пока что метод выискивает последний ход, и передает дальше, для
     * вычисления следующего хода. Перегрузка методов получилась из
     * желания как можно меньше переделывать код, и иметь более гибкий
     * интерфейс для тестирования.
     * @param fieldMatrixObject матрица поля, переданная ядром.
     * @return массив координат клетки, куда выполняется ход.
     */
    public int[]  findMove(Object[][] fieldMatrixObject, Object figure) {

	    FieldMatrixConverter converter = new FieldMatrixConverter();
	    Character[][] fieldMatrixCharacter = converter.convertFieldMatrixToCharacter(fieldMatrixObject);
	    char[][] fieldMatrix = CoordinateConverter.characterToChar(fieldMatrixCharacter);
	    int[] enemyMove = getLastMove(fieldMatrix);   // возвращаем координаты хода противника

        return findMove(enemyMove[X], enemyMove[Y]);
    }

    /**
     * проверяет в соответствии с входящими координатами
     * возможность выигрыша противником, и делает простой рандомный
     * ход. Если противник может выиграть следующим ходом, то он занимает
     * победное поле своей фишкой.
     * @param enemyX координата по X
     * @param enemyY координата по Y
     * @return массив кординаты со значениями X и Y
     */
    public int[] findMove(int enemyX, int enemyY) {


        move[X] = 25;              // обнуляем потенциально выигрышную клетку
        move[Y] = 25;

        if (firstMove){
            move[X] = BOARD_SIZE / 2;
            move[Y] = BOARD_SIZE / 2;
	        firstMove = false;
        } else {
            setChipOnBoard(enemyX, enemyY,enemyChip);

            if (history.size() > NUM_IN_THE_ROW) {
                /*
                Checks last AI move. Maybe it lead to win.
                */
                int[] myMove = CoordinateConverter.getCoordinateFromIndex
                        (history.get(history.size() - 2), BOARD_SIZE);
                /*
				If AI gets win, there is enemy last position will be written.
				*/

                if (checker.isWin(GAME_BOARD, myMove[X], myMove[Y], aiChip)) {
					writePosition(history);
					System.out.println("I win");
                }
                System.arraycopy(checker.getMove(), 0, move, 0, move.length);
            }

            if (move[X] == 25 || move[Y] == 25) {
                if (checker.isWin(GAME_BOARD, enemyX, enemyY, enemyChip)) {
                    /*
                    write last AI position before enemy had to move.
                     */
                    ArrayList<Integer> temp = rewindHistoryBack(history, 1);
                    writePosition(temp);
                }
                System.arraycopy(checker.getMove(), 0, move, 0, move.length);

                if (move[X] != 25 || move[Y] != 25) {
                    setChipOnBoard(move[X], move[Y], aiChip);
                    if (comparePos(history)){
						/*
						write prior AI position
						 */
                        ArrayList<Integer> temp = rewindHistoryBack(history, 2);
						writePosition(temp);
                    }
                    return move;
                }
            }
        }



        while (true){
            ArrayList<Integer> tempHistory = new ArrayList<Integer>(history.size());

	        tempHistory.addAll(history);

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
                    if (move == CoordinateConverter.getCoordinateFromIndex(i, BOARD_SIZE)) {
                        continue start;
                    }
                }
                if (isCellEmpty(move[X], move[Y])) {        //we have to play in empty cell
                    break;
                }
                move[X] = 25;
                move[Y] = 25;
            }


            tempHistory.add(CoordinateConverter.getIndexOfCell(move[X], move[Y], BOARD_SIZE));
            /*
            If there is all cells lead to defeat, AI marks his previous position as illegal.
             */
            if (!comparePos(tempHistory)) break;

            deniedCells.add(CoordinateConverter.getIndexOfCell(move[X], move[Y], BOARD_SIZE));

            if (deniedCells.size() == BOARD_SIZE * BOARD_SIZE - history.size()) {

	            ArrayList<Integer> temporaryHistory = rewindHistoryBack(tempHistory, 2);
	            writePosition(temporaryHistory);

                break;
            }
            move[X] = 25;
            move[Y] = 25;
        }

        /*
        adds AI move
         */
        setChipOnBoard(move[X], move[Y], aiChip);

        return move;
    }

    /**
     * Сверяет новую матрицу доски с предыдущей матрицей и
     * определяет координаты последнего хода. Так же
     * ИИ вычисляет очередь своих ходов и оперирует своим
     * внутренним порядком фишек в зависимости от
     * полученного результата.
     * @param fieldMatrix поле, которое пришло из ядра
     * @return move does by enemy Player.
     */

    public int[] getLastMove(char[][] fieldMatrix) {

        int[] enemyMove = new int[2];
        /*
        Very first move done by AI
        It recognise hwo is doing first move.
        First player gets sign X and second gets O.
         */
        if (firstMove) {
            oldFieldMatrix = CoordinateConverter.copyBoard(fieldMatrix);
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if ((x == 0) && (y == 0)) continue;
                    /*
                    In this case AI moves second;
                     */
                    if (fieldMatrix[0][0] != fieldMatrix[x][y] ) {
                        /*
                        Move might be done in 0-0 sell.
                         */
                        if (fieldMatrix[0][0] !=
                                fieldMatrix[BOARD_SIZE - 1][BOARD_SIZE - 1] &&
                                fieldMatrix[0][0] != fieldMatrix[0][1]) {

                            enemyMove[X] = 0;
                            enemyMove[Y] = 0;
                        } else {
                            enemyMove[X] = x;
                            enemyMove[Y] = y;
                        }
                        aiChip = 'O';
                        enemyChip = 'X';
                        firstMove = false;
                        return enemyMove;
                    }
                }
            }

            /*
            If all cells was empty AI moves first
             */
            aiChip = 'X';
            enemyChip = 'O';
            enemyMove[X] = 100;
            enemyMove[Y] = 100;
            return enemyMove;
        }
        /*
        Gets last move coordinates from incoming game field.
         */
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (fieldMatrix[x][y] != oldFieldMatrix[x][y]) {
                    int num = CoordinateConverter.getIndexOfCell(x, y, BOARD_SIZE);
                    /*
                    Checks if current cell is equal to previous move done by AI
                     */
                    int historyNum = history.get(history.size() - 1);
                    if (num != historyNum) {
                        enemyMove[X] = x;
                        enemyMove[Y] = y;
                        oldFieldMatrix = CoordinateConverter.copyBoard(fieldMatrix);
                        return enemyMove;
                    }
                }
            }
        }
        // Has no new moves. Or move was done in occupied cell.
        return enemyMove;
    }

    // Проверяет - пуста ли ячейка в которую хочет походить ИИ
    public boolean isCellEmpty(int x,int y){
        return GAME_BOARD[x][y] == EMPTY;
    }

    /**
     * String position got from history and sorted with TreeSet.
     * It need for write history in the base,and make comparison with
     * positions already stored in the base.
     * @param history got from moves.
     * @return position in String
     */
    private String sortHistory(ArrayList<Integer> history) {
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
	
	private ArrayList<Integer> rewindHistoryBack(ArrayList<Integer> history, int step)
	{   int tempHistorySize = history.size() - step;
		ArrayList<Integer> temporaryHistory = new ArrayList<Integer>(tempHistorySize);
		for (int i = 0; i < tempHistorySize; i++) {
			temporaryHistory.add(history.get(i));
		}
		return temporaryHistory;
	}
	
	public void	writePosition(ArrayList<Integer> history) {
		String sortedPosition = sortHistory(history);
		FileMaster file = new FileMaster(BASE_DIR, history.size() + "." + FILE_NAME);
        file.writeFile(sortedPosition);
	}
	

	public boolean comparePos(ArrayList<Integer> history) {
		String basePosition;
        String tempPosition;
        FileMaster file;
		ArrayList<Integer> tempHistory = new ArrayList<Integer>(history.size());

		for (int i = 0; i <= 22; i += 11) {

			tempHistory.clear();
			tempHistory.addAll(history);

			if (i != 0) {
				tempHistory = CoordinateConverter.rotateHistory(tempHistory, BOARD_SIZE, i);
			}

			for (int j = 0; j <= 270; j += 90) {

				if (j != 0) {
					tempHistory = CoordinateConverter.rotateHistory(tempHistory, BOARD_SIZE, j);
				}

                tempPosition = sortHistory(tempHistory);
                file = new FileMaster(BASE_DIR, tempHistory.size() + "." + FILE_NAME);
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
			}
		}

        return false;
	}

    void setChipOnBoard(int x, int y, char chip) {
        GAME_BOARD[x][y] = chip;
        history.add(CoordinateConverter.getIndexOfCell(x, y, BOARD_SIZE));
    }

}
