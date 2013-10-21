package org.hexlet.gamexo.ai.gardnerway;

import java.util.TreeSet;

/**
 * User: KOlegA
 * Date: 13.10.13
 * Time: 0:36
 */
public class GameStatusChecker {

    private int[] move = new int[2];
    private String lastMoveStatus = "None";
    private final int X = 0;
    private final int Y = 1;
    private final int NUM_IN_THE_ROW;
    private final char EMPTY;
    private final int BOARD_SIZE;

    GameStatusChecker(char emptyCell, int numInTheRow, int boardSize){
        EMPTY = emptyCell;
        NUM_IN_THE_ROW = numInTheRow;
        BOARD_SIZE = boardSize;
    }

    // Проверка на победу
    public boolean isWin(char[][] gB, int x, int y, char chip, boolean isFork) {
        move[X] = 25;
        move[Y] = 25;
        boolean result = false;
        TreeSet<Integer> win = new TreeSet<Integer>();

		/*
		Проход победных рядов по четырем направлениям
		 */
        for (int i = 0; i < 4; i++) {
            win.addAll(checkRow(gB, x, y, chip, i));

			/*
			Если в Set элементов координат больше, чем один,
			то партия считается выигранной/проигранной, т.к.
			есть минимум одна из двух клеток, которую не успевает
			обезвредить противник.
			 */
            if (win.size() > 1) {
                if (isFork) result = true;
                lastMoveStatus = "Fork";
                if (win.size() == 3) {
                    lastMoveStatus = "Win";
                    return true;
                }
                break;
            }
        }
		/*
		Получаем координату потенциалной победной клетки
		 */
        if (win.size() != 0) {
            move = CoordinateConverter.getCoordinateFromIndex(win.last(), BOARD_SIZE);
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
     * @return TreeSet координат пустых потенциально победных клеток
     * @exception IndexOutOfBoundsException выкидывается при попадании
     * проверочных координат за границы массива.
     */
    public TreeSet<Integer> checkRow(char[][] gB, int xx, int yy, char chip, int direction) {
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
                    if (gB[x][y] != chip & gB[x][y] != EMPTY) {
                        emptySell.clear();
                        continue start;
                    }
			        /*
			         Здесь мы закидываем порядковый номер пустой
			         клетки в Set, который получаем из координат.
			         */
                    if (gB[x][y] == EMPTY) {
                        Integer number = CoordinateConverter.getIndexOfCell(x, y, BOARD_SIZE);
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

    public int[] getMove() {
        return move;
    }

    public String getLastMoveStatus() {
        return lastMoveStatus;
    }
}
