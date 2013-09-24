package virtuals.gardner;

/**
 * User: KOlegA
 * Date: 15.09.13
 * Time: 0:42
 */
public class BoardModifier {

    // копирует доску(поле)
    public static char[][] copyBoard(char[][] board) {
        char[][] cBoard = new char[board.length][board[1].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, cBoard[i], 0, board.length);
        }
        return cBoard;
    }


    // поворачивает доску на заданный угол
    public static char[][] rotate(char[][] board, String degrees) {
        char[][] rBoard = new char[board.length][board[1].length];
        int[] xy = new int[2];
        for (int yy = 0; yy < board.length; yy++) {
            for (int xx = 0; xx < board.length; xx++) {
                if (board[xx][yy] != '\u0000') {
                    xy = rotateXY(xx, yy, board.length, degrees);
                    rBoard[xy[0]][xy[1]] = board[xx][yy];
                }

            }

        }
        return rBoard;
    }

    // поворачивает координату на заданный угол
    public static int[] rotateXY(int x, int y, int size, String degree) {
        int[] xy = new int[2];
        switch (degree) {
            case "90"  :
                xy[0] = y;
                xy[1] = (size - 1) - x;
                break;
            case "180" :
                xy[0] = (size - 1) - x;
                xy[1] = (size - 1) - y;
                break;
            case "270" :
                xy[0] = (size - 1) - y;
                xy[1] = x;
                break;
	        case "vmir" :
		        xy[0] = (size - 1) - x;
		        xy[1] = y;
		        break;
	        case "hmir" :
		        xy[0] = x;
		        xy[1] = (size - 1) - y;
        }
        return xy;
    }

}

