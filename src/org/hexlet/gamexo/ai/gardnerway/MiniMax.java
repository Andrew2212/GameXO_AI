package org.hexlet.gamexo.ai.gardnerway;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: KOlegA
 * Date: 13.10.13
 * Time: 22:27
 */
public class MiniMax {

    GameStatusChecker checker = new GameStatusChecker('_', 3, 3); // will change later
    int movesSum = 9;                               // product of board sides
    private final char EMPTY = '_';
	private  int terminalResult = 0;
	private int[] bestMove = new int[2];
    private int valueMax = 20000;
    private int valueMin = -20000;
    private int valueDraw = 0;
	private final int BOARD_SIZE;
	private final int X = 0;
	private final int Y = 1;
	private final int DEPTH;
    private final char PLR_MAX;

    public MiniMax(int boardSize, char plrMax, int depth) {
        BOARD_SIZE = boardSize;
        PLR_MAX = plrMax;
        DEPTH = depth;

    }

    public int[] getBestMove(char[][] gB, int x, int y, boolean plMax, int hiSize) {
        miniMax(gB, x, y, plMax, hiSize, DEPTH);
        return bestMove;
    }

    public int miniMax(char[][] gB, int x, int y, boolean plMax, int hiSize,int depth) {

	    char chip = (hiSize % 2 == 0) ? 'O' : 'X';

	    if (checkTerminalState(gB, x, y, plMax, hiSize, depth)) {
		    return terminalResult;
	    }

	    /*
	    The code below this comment represents
	    selection of the best values for player.
	     */
	    chip = reverseChip(chip);
        plMax = !plMax;
        int result = plMax ? valueMin : valueMax;

	    for (int moveY = 0; moveY < BOARD_SIZE; moveY++) {
            for (int moveX = 0; moveX < BOARD_SIZE; moveX++) {

                if (!isCellEmpty(gB, moveX, moveY)) {
	                continue;
                }

	            gB[moveX][moveY] = chip;              //take a move

	            int resultM = miniMax(gB, moveX, moveY, plMax, hiSize + 1, depth - 1);

	            gB[moveX][moveY] = EMPTY;             //return previous board state

	            if (depth == DEPTH) System.out.println(moveX + "-" + moveY + " = " + resultM);

	            if (plMax) {
		            if (resultM > result) {   //MAX player choose the bigger value;
			            result = resultM;
			            if (depth == DEPTH) {
				            setBestMove(moveX, moveY);
			            }
		            }
	            } else {
		            if (resultM < result) { //MIN player choose the least one;
			            result = resultM;
			            if (depth == DEPTH) {
				            setBestMove(moveX, moveY);
			            }
		            }
	            }

//	            if (resultM == result) {
//		            if (new java.util.Random().nextInt(10) % 2 == 0) {
//			            setBestMove(moveX, moveY);
//		            }
//	            }

            }
        }

        return (plMax) ? (result - hiSize) : (result + hiSize);
    }

	private boolean isCellEmpty(char[][] gB, int x,int y){
		return gB[x][y] == EMPTY;
	}

	private char reverseChip(char chip) {
		return (chip == 'X') ? 'O' : 'X';
	}

	/**
	 *  Checks if represented variant is end up. If it is, returns
	 *  value depended who's move was in this moment.
	 * @param gB  position on Board
	 * @param x   coordinate X
	 * @param y   coordinate Y
	 * @param plMax  player which play at the moment (MIN or MAX)
	 * @param hiSize
	 * @param depth
	 * @return
	 */
	private boolean checkTerminalState(char[][] gB, int x, int y,
	                                boolean plMax, int hiSize, int depth) {
		char chip = (hiSize % 2 == 0) ? 'O' : 'X';
		terminalResult = valueDraw;
		 /*
        Checks if there is end of variation
        Win or lose status depends on who's move was.
         */
		if (checker.isWin(gB, x, y, chip, false)) {

			terminalResult = (plMax) ? valueMax : valueMin;

            if (checker.getLastMoveStatus().equals("Fork")) {
                terminalResult = terminalResult / 2;
            }

			return true;
		}
		/*
		If all cells filled and nobody win we will get draw.
		If AI move on to specified depth without win we also get draw.
		 */
		return hiSize == movesSum || depth == 0;
	}

	private void setBestMove(int x, int y) {
		System.out.println("I'm here x = " + x + "y = " + y);    //todo: del this later
		bestMove[X] = x;
		bestMove[Y] = y;
	}

}
