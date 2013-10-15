package org.hexlet.gamexo.ai.gardnerway;

import java.util.ArrayList;

/**
 * User: KOlegA
 * Date: 13.10.13
 * Time: 22:27
 */
public class MiniMax {

    GameStatusChecker checker;
	char player = 'O';
	ArrayList<Integer> winCells = new ArrayList<Integer>();
	ArrayList<Integer> drawCells = new ArrayList<Integer>();
	ArrayList<Integer> loseCells = new ArrayList<Integer>();

    public String miniMax(char[][] gB, int x, int y, char chip,
                        int hiSize, int depth) {

        int movesSum = gB.length * gB.length;
        char[][] tempBoard = CoordinateConverter.copyBoard(gB);
	    tempBoard[x][y] = chip;
	    hiSize++;


        String statusXY;
	    checker = new GameStatusChecker('_', 3, 3); // will change later


        if (checker.isWin(tempBoard, x, y, chip)) {          // We have win or lose status depending on who's move was.
	        return (player == chip) ? "3_Win" : "0_Lose";
        }
        if (hiSize == movesSum) return "2_Draw";  // If all cells filled and nobody win we will get draw.
        if (depth == 0) return "2_Draw";          // If AI move on specified depth without win.

	    chip = (chip == 'X') ? 'O' : 'X';
        String status = (player == chip) ? "0_Lose" : "3_Win";
//	    status = "3_Win";


        for (int yCell = 0; yCell < gB.length; yCell++) {
            for (int xCell = 0; xCell < gB.length; xCell++) {
                if (tempBoard[xCell][yCell] == '_') {                                  // will change later
                    statusXY = miniMax(tempBoard, xCell, yCell, chip, hiSize, depth - 1);


//                    if (statusXY.compareTo(status) > 1) status = statusXY;

	                if (player == chip) {
		                if (statusXY.compareTo(status) > 0) {   //our player choose the bigger move;
			                status = statusXY;
		                }
	                }else {
		                if (statusXY.compareTo(status) < 0) { //enemy player choose the least one;
			                status = statusXY;
		                }
	                }
                }
            }
        }

        return status;
        for (int moveY = 0; moveY < BOARD_SIZE; moveY++) {
            for (int moveX = 0; moveX < BOARD_SIZE; moveX++) {
                if (isCellEmpty(moveX, moveY)) {
                    status = miniMax.miniMax(GAME_BOARD, moveX, moveY, aiChip, history.size(), 2);
                    int returnMove = CoordinateConverter.getIndexOfCell(moveX, moveY, BOARD_SIZE);
                    if (status.equals("0_Lose")) {
                        loseCells.add(returnMove);
                        continue;
                    }
                    if (status.equals("3_Win")) {
                        winCells.add(returnMove);
                        continue;
                    }
                    if (status.equals("2_Draw")) {
                        drawCells.add(returnMove);
                        continue;
                    }
                    noneCells.add(returnMove);

                }
            }
        }
        if (winCells.size() > 0) {
            return setMove(winCells);
        }

        if (drawCells.size() > 0) {
            return setMove(drawCells);
        }

        if (noneCells.size() > 0) {
            return setMove(noneCells);
        }

        if (loseCells.size() > 0) {
            return setMove(loseCells);
        }

        move[X] = (int) Math.floor(Math.random() * BOARD_SIZE);
        move[Y] = (int) Math.floor(Math.random() * BOARD_SIZE);

    }

}
}
