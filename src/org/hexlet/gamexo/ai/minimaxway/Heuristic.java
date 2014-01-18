package org.hexlet.gamexo.ai.minimaxway;


/**
 * Class for estimating raiting (or weight) of the step
 * API: stepRaiting(char[][] field, boolean isX, int[] stepTaken, int stepNumber)
 */
final class Heuristic {

   private static final int X_WINNER_SCORE = 500;

   private static final int O_WINNER_SCORE = -500;

   private static final int DRAW_SCORE = 1;

   private static final int EMPTY_CELLS_SCORE = 0;


   /**
    * Interface for estimating rating (or weight) of the step
    * For win situations it returns nonzeros values.
    * For X it returns positive values,
    * For O - negative.
    * If there are empty cells it returns 0;
    * In case of draw it returns +-1 consequently.
    * @param field      income field with step taken
    * @param isX        true if make heuristic estimate for X figure
    * @param stepTaken  step taken to weight (last taken step) [0] - row coordinate, [1] - column coordinate
    * @param stepNumber number of the taken step
    * @return weight of step. Abs higher is better for each figure
    * @throws ArrayIndexOutOfBoundsException
    */

   public static int stepRaiting(char[][] field, boolean isX, int[] stepTaken, int stepNumber) throws
           ArrayIndexOutOfBoundsException {

      char winner = Game.winner(field, stepTaken);

      if ( (winner == Minimax.VALUE_X) || (winner == Minimax.VALUE_O) ) {
         if (isX) return (X_WINNER_SCORE / stepNumber);
         else     return (O_WINNER_SCORE / stepNumber);
      }
      else if ( (wasPreventLoose(field, stepTaken)) || (winner == Minimax.VALUE_DRAW) ) {
         if (isX)  return DRAW_SCORE;
         else      return -DRAW_SCORE;
      }
      else if (winner == Minimax.DEFAULT_CELL_VALUE) {
         return EMPTY_CELLS_SCORE;
      }
      else throw new UnknownError();
   }

   private static boolean wasPreventLoose(char[][] field, int[] stepTaken) {
      char placedFigure = field[stepTaken[Minimax.ROW_COORD]][stepTaken[Minimax.COL_COORD]];
      // place opposite figure to that place
      try {
         if (placedFigure == Minimax.VALUE_X) {
            field[stepTaken[Minimax.ROW_COORD]][stepTaken[Minimax.COL_COORD]] = Minimax.VALUE_O;
         } else if (placedFigure == Minimax.VALUE_O) {
            field[stepTaken[Minimax.ROW_COORD]][stepTaken[Minimax.COL_COORD]] = Minimax.VALUE_X;
         } else {
            throw new IllegalArgumentException();
         }
      }
      catch (IllegalArgumentException e) {
         e.printStackTrace();
      }

      // watch if it could be win of
      char possibleWinner = Game.winner(field, stepTaken);
      // return placedFigure
      field[stepTaken[Minimax.ROW_COORD]][stepTaken[Minimax.COL_COORD]] = placedFigure;
      if ( ( (possibleWinner == Minimax.VALUE_X) && (placedFigure == Minimax.VALUE_O) ) ||
      ( (possibleWinner == Minimax.VALUE_O) && (placedFigure == Minimax.VALUE_X) ) ) {
         return true;
      }
      else {
         return false;
      }
}


   }