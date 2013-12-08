package org.hexlet.gamexo.ai.minimaxway;

import org.junit.Test;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for estimating raiting (or weight) of the step
 * API: stepWeight(char[][] field, boolean isX, int[] stepTaken, int stepNumber)
 */
final class Heuristic {

   private static final int X_WINNER_SCORE = 500;

   private static final int O_WINNER_SCORE = -500;

   private static final int DRAW_SCORE = 1;

   private static final int EMPTY_CELLS_SCORE = 0;

//    // массив смещений адресов ячеек по направлениям
//    private static final int[][] OFFSET = {
//            {-1,0},  // up
//            {-1,1},  // up-right
//            {0,1},   // right
//            {1,1},   // right - down
//            {1,0},   // down
//            {1,-1},  // left - down
//            {0,-1},  // left
//            {-1,-1}  // left-up
//    };
//    private static final int FIRST_STEP_RATING = 100;
//    private static Heuristic uniqueInstance = null;
//
//    private final int LENGTH; //длинна линии, необходимая для победы
//    private final int X_SIZE; // количество строк поля
//    private final int Y_SIZE; // количество столбцов поля
//    private int[][] field; //используется для хранения поля
//
//    static Heuristic createInstance(int len, int x, int y) {
//        if (uniqueInstance == null) {
//            uniqueInstance = new  Heuristic(len, x, y);
//        }
//        return uniqueInstance;
//    }
//
//    private Heuristic(int len, int x, int y){
//        this.LENGTH = len;
//        this.X_SIZE = x;
//        this.Y_SIZE = y;
//        this.field = new int[X_SIZE][Y_SIZE];
//        for (int i = 0; i < X_SIZE; i++){
//            for (int j = 0; j < Y_SIZE; j++){
//                this.field[i][j] = 0;
//            }
//        }
//    }
//
//
//    /**
//     * метод для оценки ситуации на поле
//     * @param x      номер стоки
//     * @param y      номер столбца
//     * @param sign   знак хода: -1 - нолик, 1 - крестик
//     * @return       возвращаем -5 при победе ноликов, 5 - крестиков, 2 - ничья, 0 - есть еще ходы
//     */
//     // closer steps have better rating
//     public static int stepWeight(char[][] field, boolean isX, int[] stepTaken, int stepNumber){
//        for (int j = 0; j < OFFSET.length; j++){   //последовательно перебираем все направления по часой стрелке,
//            try {                                   // начиная с верха, на достижение требуемой длинны
//                if (isEnoughLength(x,y,sign,j)){
//                    switch (sign){
//                        case -1: {
//                            return -5 * (FIRST_STEP_RATING / stepNumber);
//                        }
//                        case  1: {
//                            if (stepNumber == 1) {
//                                return Minimax.INFINITY; // the best step. It will be victory.
//                            }
//                            return 5 * (FIRST_STEP_RATING / stepNumber) - 5; // less than when sign = -1, because
//                                                                             // our goal is not loose
//                        }
//                    }
//                }
//            }  catch (ArrayIndexOutOfBoundsException e){
//                continue;                                  //хитрый план для контроля выхода за границы поля при проверке
//            }
//        }
//        if (hasEmptyCell(field)) {
//            return 0;
//        }
//        return 2 * (FIRST_STEP_RATING / stepNumber);
//    }
//
//    /**
//     *  проверяем , достигнута ли требуемая длинна линии в заданном направлении
//     * @param x                                номер строки
//     * @param y                                номер столбца
//     * @param sign                             знак игрока
//     * @param direction                        номер направления
//     * @return                                 true, если длинна достигнута, иначе - false
//     * @throws ArrayIndexOutOfBoundsException  кидаем исключение при попытке вылезти за пределы поля
//     */
//
//    //TODO люто, бешенно рефакторить!
//    private static boolean isEnoughLength(int[][] field, int[] stepTaken, boolean isX, int direction) throws ArrayIndexOutOfBoundsException{
//        int cntLine = 1;
//        int a = stepTaken[0];
//        int b = stepTaken[1];
//        try {
//            while (cntLine < field.length) {                                 //здесь может вылетить   ArrayIndexOutOfBoundsException
//                a +=  OFFSET[direction][0];                            // что помешает нам проверить противополжное направление
//                b +=  OFFSET[direction][1];
//                if (field[a][b] != sign){
//                    break;
//                }
//                cntLine++;
//            }
//        } catch (ArrayIndexOutOfBoundsException e){
//            // мы его перехватываем, и тупо ничего не делаем
//        }
//
//        a = x;
//        b = y;
//        while (cntLine < LENGTH){
//            a += OFFSET[(direction + 4) % 8][0];
//            b += OFFSET[(direction + 4) % 8][1];
//            if (this.field[a][b] != sign){
//                break;
//            }
//            cntLine++;
//        }
//        if (cntLine == LENGTH){
//            return true;
//        }  else {
//            return false;
//        }
//    }
//
//    //TODO проверяем наличие пустых ячеек тупо перебором, что не есть хорошо
//    private static boolean hasEmptyCell(int[][] field){
//        for (int i = 0; i < field.length; i++){
//            for (int j = 0; j < field.length; j++){
//                if (field[i][j] == Minimax.DEFAULT_CELL_VALUE){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    // interface copies to Heuristic class field from parameter
//    // may be IndexOutOfBorder exception and may doesn't work well if size of this field not equal to that field
//    public void copyField(char[][] thatField) {
//        for (int i = 0; i < thatField.length; i++) {
//            for (int j = 0; j < thatField[i].length; j++) {
//                if (thatField[i][j] == Minimax.VALUE_X) {
//                    this.field[i][j] = 1;  // x
//                }
//                else if (thatField[i][j] == Minimax.VALUE_O) {
//                    this.field[i][j] = -1;
//                }
//                else {
//                    this.field[i][j] = 0;
//                }
//            }
//        }
//    }
//
//    //метод используется в клиенте тестирования, в дальнейшем не нужен
//    private void showField(){
//        for (int i = 0; i < X_SIZE; i++){
//            for (int j = 0; j < Y_SIZE; j++){
//                if (j == Y_SIZE - 1){
//                    System.out.println(field[i][j]);
//                } else {
//                    System.out.print(field[i][j] + " ");
//                }
//            }
//        }
//    }

   /**
    * Interface for estimating rating (or weight) of the step
    * For win situations it returns nonzeros values.
    * For X it returns positive values,
    * For O - negative.
    * If there are empty cells it returns 0;
    * In case of draw it returns +-1 consequently.
    * @param field      income field with step taken
    * @param isX        true if step taken was X, false if O
    * @param stepTaken  step taken to weight (last taken step)
    * @param stepNumber number of the taken step
    * @return weight of step. Abs higher is better for each figure
    * @throws ArrayIndexOutOfBoundsException
    */

   public static int stepWeight(char[][] field, boolean isX, int[] stepTaken, int stepNumber) throws
           ArrayIndexOutOfBoundsException {

      if (isWin(field, stepTaken)) {
         if (isX) {
            return (X_WINNER_SCORE / stepNumber);
         } else {
            return (O_WINNER_SCORE / stepNumber);
         }
      }

      if (isHasEmptyCells(field)) {
         return EMPTY_CELLS_SCORE;
      }

      if (isX) {
         return DRAW_SCORE;
      } else {
         return -DRAW_SCORE;
      }
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

   private static boolean isHasEmptyCells(char[][] field) throws ArrayIndexOutOfBoundsException {
      for(int row = 0; row < field.length; row++) {
         for (int col = 0; col < field[row].length; col++) {
            if(field[row][col] == Minimax.DEFAULT_CELL_VALUE) {
               return true;
            }
         }
      }
      return false;
   }
      /**
       * Unit tests for Heuristic
       * @param args Don't use it
       */

      @Test
      public static void main(String[] args) {
         char[][] field = {
                 {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
                 {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
                 {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
         };

         int cnt = 1;
         Scanner scan = new Scanner(System.in);
         int[] stepTaken = new int[2];
         // without check of victory!
         while (cnt <= (field.length * field.length)) {
            try {
               System.out.print("\nEnter row: ");
               stepTaken[0] = scan.nextInt();
               System.out.print("\nEnter col: ");
               stepTaken[1] = scan.nextInt();
            }
            catch (InputMismatchException e) {
               return;
            }
            int rating;
            if (cnt % 2 == 1) {
               field[stepTaken[0]][stepTaken[1]] = 'X';
               rating = Heuristic.stepWeight(field, true, stepTaken, cnt);
            } else {
               field[stepTaken[0]][stepTaken[1]] = 'O';
               rating = Heuristic.stepWeight(field, false, stepTaken, cnt);
            }
            for (int row = 0; row < field.length; row++) {
               for (int col = 0; col < field.length; col++) {
                  System.out.print("[" + field[row][col] + "]");
               }
               System.out.println();
            }
            System.out.println("Current rating = " + rating);
            cnt++;
         }

      }
   }