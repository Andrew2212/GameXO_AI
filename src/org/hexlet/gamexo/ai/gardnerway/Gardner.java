package org.hexlet.gamexo.ai.gardnerway;


import org.hexlet.gamexo.ai.IBrainAI;

import java.util.HashSet;

/**
 * Date: 03.09.13
 * Time: 15:33
 */
public class Gardner implements IBrainAI {

    private char[][] gameBoard;
    private char[][] rotateBoard;
    private boolean isFirst;
    private char myChip;
    private int numInTheRow;
    private int column;
    private int row;
    private int coordC, coordR;

    private final int[] MOVE = new int[2];
    private static final int X = 0;
    private static final int Y = 1;

// ******************************REMAKE***************************************************
//    public Gardner(int columnAmount, int rowAmount,
//                   int numInTheRow, boolean isFirst) {
   public Gardner(int fieldSize, int numChecked) {

//        gameBoard = new char[columnAmount][rowAmount];
//        this.numInTheRow = numInTheRow;
//        myChip = isFirst ? 'B' : 'W';
    }


// ******************************REMAKE***************************************************делаем ход

//    public int[]  letsPlay(int x, int y)

    /**
     * If you need to have last step you can get it by comparing arrays (old and new)
     * @param fieldMatrix
     * @return
     */
    public int[]  findMove(char[][] fieldMatrix) {

        System.out.println("Gardner::findMove()");
//            throws CellIsNotEmptyException{

//        if (!isCellEmpty(x, y)){
//            String ex = x + " " + y;
//            throw new  CellIsNotEmptyException(ex);
//        }
//
//
//        System.out.println(isWin(0, 0, 'B'));

        MOVE[X] = (int) Math.floor(Math.random() * fieldMatrix.length);
        MOVE[Y] = (int) Math.floor(Math.random() * fieldMatrix.length);

        return MOVE;
    }

    public void setMoveCell(int column, int row){
        this.column = column;
        this.row = row;
    }

    // Проверяет - пуста ли ячейка в которую хочет походить противник
    public boolean isCellEmpty(int column,int row){
        return gameBoard[column][row] == '\u0000';
    }

    // Проверка на победу
    public boolean isWin(int x, int y, char chip) {
        HashSet<String> win = new HashSet<String>();
        for (int i = 0; i < 4; i++) {
            win.addAll(checkRow(x, y, chip, i));

			/*
			Если в куче элементов координат больше, чем один,
			то партия считается выигранной/проигранной, т.к.
			есть миним одна из двух клеток, которую не успевает
			обезвредить противник.
			 */
            if ((win.size()) > 1) {
                return true;
            }
        }


		/*
		Это мне не нравится, надо переделывать.
		Преобразует координаты в удобоваримый вид из стринги,
		которая пришла из кучи.
		 */
        String[] coordinate = win.toArray(new String[0]);
        String[] xy = coordinate[0].split(" ");
        int[] xAndY = new int[2];
        for (int i = 0; i < xAndY.length; i++){
            xAndY[i] = Integer.parseInt(xy[i]);
            System.out.println(xAndY[i]);
        }
        return false;
    }

    /**
     * В данном методе выполняется проверка победы при заполненнии указанной
     * фишкой победного ряда состоящего из numInTheRow количества фишек подряд.
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
    public HashSet<String> checkRow(int xx, int yy, char chip, int direction) {
        int x = 0, y = 0;
        HashSet<String> emptySell = new HashSet<String>();   //сборщик пустых полей от прохода ряда
        HashSet<String> emptySum = new HashSet<String>();;    //сборщик пустых полей от всех проходов

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
			        проверяемого, то куча пустых клеток обнуляется и
			        прекращается дальнейший перебор ряда.
			         */
                    if (gameBoard[x][y] != chip & gameBoard[x][y] != '\u0000') {
                        emptySell.clear();
                        continue start;
                    }
			        /*
			        Здесь мы закидываем пустую клетку в кучу
			         */
                    if (gameBoard[x][y] == '\u0000') {
                        emptySell.add(x + " " + y);
                    }
		        /*
		        Если проверка попала за границы массива, то обнуляем
		        кучу пустых клеток.
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
                    emptySum.add("win" + g);
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

    public int getCoordC() {
        return coordC;
    }

    public int getCoordR() {
        return coordR;
    }

    public class CellIsNotEmptyException extends Exception{

        public CellIsNotEmptyException(String ex) {
            super(ex);
        }

    }


}
