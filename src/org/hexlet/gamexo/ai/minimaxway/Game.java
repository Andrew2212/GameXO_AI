package org.hexlet.gamexo.ai.minimaxway;

/**
 * User: Mike
 * Date: 09.01.14
 * Time: 11:17
 */
class Game {

   // TODO REFACTOR
   // Maybe it would be useful to place here all static constants from Minimax class

   /**
    *
    * @param field Game field
    * @param stepTaken Last taken step. [0] - row coordinate, [1] - column coordinate
    * @return char _ if No winner
    *              D if Draw
    *              X if X is winner
    *              O if O is winner
    */
   public static char winner(char[][] field, int[] stepTaken) {

      char placedFigure = field[ stepTaken[Minimax.ROW_COORD] ][ stepTaken[Minimax.COL_COORD] ];

      if (isWin(field, stepTaken)) {
         if (placedFigure == Minimax.VALUE_X) {
            return Minimax.VALUE_X;
         }
         else {
            return Minimax.VALUE_O;
         }
      }

      if (hasEmptyCells(field)) {
         return Minimax.DEFAULT_CELL_VALUE;
      }

      return Minimax.VALUE_DRAW;
   }

   private static boolean isWin(char[][] field, int[] stepTaken) throws ArrayIndexOutOfBoundsException {

      if (isHorizWin(field, stepTaken) ) {
         return true;
      }

      if (isVertWin(field, stepTaken) ) {
         return true;
      }

      if (isMainDiagWin(field, stepTaken) ) {
         return true;
      }

      if (isInversDiagWin(field, stepTaken) ) {
         return true;
      }

      return false;
   }


   private static boolean isHorizWin(char[][] field, int[] stepTaken) {
      int col = 1;
      while ((col < field.length) && (field[stepTaken[Minimax.ROW_COORD] ][col - 1] ==
              field[stepTaken[Minimax.ROW_COORD] ][col])) {
         col++;
      }
      if (col == field.length) {
         return true;
      }
      else {
         return false;
      }
   }

   private static boolean isVertWin(char[][] field, int[] stepTaken) {
      int row = 1;
      while ((row < field.length) && (field[row - 1][stepTaken[Minimax.COL_COORD] ] ==
              field[row][stepTaken[Minimax.COL_COORD] ] ) ) {
         row++;
      }
      if (row == field.length) {
         return true;
      }
      else {
         return false;
      }
   }

   private static boolean isMainDiagWin(char[][] field, int[] stepTaken) {
      int row = 1;
      int col = 1;
      if (stepTaken[Minimax.ROW_COORD] == stepTaken[Minimax.COL_COORD] ) {
         while ((row < field.length) && (col < field.length) &&
                 (field[row - 1][col - 1] == field[row][col])) {
            row++;
            col++;
         }
         if ((row == field.length) && (col == field.length)) {
            return true;
         }
         else {
            return false;
         }
      }
      else {
         return false;
      }
   }

   private static boolean isInversDiagWin(char[][] field, int[] stepTaken) {
      int col = field.length - 2; // the coord before the last
      int row = 1; // coord after the first
      final int FIRST_COORD = 0;
      if ( (stepTaken[Minimax.ROW_COORD] + stepTaken[Minimax.COL_COORD] ) == field.length - 1) {
         while ((row < field.length) && (col > FIRST_COORD - 1) &&
                 (field[row - 1][col + 1] == field[row][col])) {
            row++;
            col--;
         }
         if ((row == field.length) && (col == FIRST_COORD - 1)) {
            return true;
         }
         else {
            return false;
         }
      }
      else {
         return false;
      }
   }

   private static boolean hasEmptyCells(char[][] field) throws ArrayIndexOutOfBoundsException {
      for(int row = 0; row < field.length; row++) {
         for (int col = 0; col < field[row].length; col++) {
            if(field[row][col] == Minimax.DEFAULT_CELL_VALUE) {
               return true;
            }
         }
      }
      return false;
   }

}
