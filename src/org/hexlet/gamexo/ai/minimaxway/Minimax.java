package org.hexlet.gamexo.ai.minimaxway;

import org.hexlet.gamexo.ai.IBrainAI;
import org.hexlet.gamexo.ai.utils.GetterLastEnemyMove;
import org.hexlet.gamexo.blackbox.game.GameField;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Minimax implements IBrainAI {

   public static final char DEFAULT_CELL_VALUE = '_';

   public static final char VALUE_X = 'X';

   public static final char VALUE_O = 'O';

   private static final String FILE_NAME = "minimax_test.log";

   private static final int TESTS_NUMBER = 25;

   private static final int DEFAULT_FIELD_SIZE = 3;

   private boolean isAIFigureX = true;

   private final int SEARCH_DEPTH = 5;

   private int bestStepRating = 0;

   private int[] bestStep = {-1, -1};

   // public static final int MINUSINFINITY = -4000000;
   // public static final int INFINITY = 4000000;

   /*
    private final int LENGTH; //длинна линии, необходимая для победы
    private final int X_SIZE; // количество строк поля
    private final int Y_SIZE; // количество столбцов поля
    private char[][] field; //используется для хранения оригинального поля

    private int bestMoveWeight;
    private int worstCurStepWeight;
    private Heuristic heuristic;
    */
//*************REMAKE Random dummy*********************************
   private final int[] MOVE = new int[2];    // координаты следующих ходов
   public static final int ROW_COORD = 0;
   public static final int COL_COORD = 1;
   private GetterLastEnemyMove getterLastEnemyMove;
   private char signBot; // за кого играет бот. Х либо О
/*
    public Minimax(int fieldSize,int numChecked){
        this.LENGTH = numChecked;
        this.X_SIZE = fieldSize;
        this.Y_SIZE = fieldSize;

        getterLastEnemyMove = new GetterLastEnemyMove(fieldSize);

    }

    /**
     * @param fieldMatrix char[][]
     * @return MOVE i.e. int[2] - coordinates of cell
     */

   public int[] findMove(char[][] fieldMatrix) {

      signBot = GameField.getSignForNextMove();
      //        This is what you calculate
      MOVE[ROW_COORD] = (int) Math.floor(Math.random() * fieldMatrix.length);
      MOVE[COL_COORD] = (int) Math.floor(Math.random() * fieldMatrix.length);

      //        checkout for random - it isn't needed for real AI
      if (GameField.isCellValid(MOVE[ROW_COORD], MOVE[COL_COORD])) {
         int[] lastEnemyMove = getterLastEnemyMove.getLastEnemyMove(fieldMatrix);
         if (null != lastEnemyMove) {
            //do something
         }

         System.out.println("Spare::findMove MOVE[ROW_COORD] = " + MOVE[ROW_COORD] + " findMove MOVE[COL_COORD] = " + MOVE[COL_COORD] + " signBot = " + signBot);
         getterLastEnemyMove.setMyOwnMove(MOVE[ROW_COORD], MOVE[COL_COORD], signBot);
      }
      return MOVE;
   }

/*

//    **********End of random dummy******************************************

    /**
     * конструктор, инициализирует все клетки нулями
     * @param len длинна линии, необходимая для победы
     * @param x   количество строк поля
     * @param y   количество столбцов на поле
     */
   /*
    public Minimax(int len, int x, int y) {
        this.LENGTH = len;
        this.X_SIZE = x;
        this.Y_SIZE = y;
        this.field = new char[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                this.field[i][j] = DEFAULT_CELL_VALUE;
            }
        }
        bestMoveWeight = MINUSINFINITY;
        worstCurStepWeight = INFINITY;
        heuristic = Heuristic.createInstance(len,x,y);
    }

    // Begin
    public int[] findMoveMiniMax(char[][] fieldMatrix) {
        int[] curBestMove = {0, 0};
        MOVE[ROW_COORD] = 0;
        MOVE[COL_COORD] = 0;
        bestMoveWeight = MINUSINFINITY;
        maxMinSearch(4, 4, fieldMatrix, 0, curBestMove); // maxMin on depth 2. Only for ROW_COORD!!!
        //  best move didn't find. Will use random
        // TODO add check if the cell is not empty.
        if  (bestMoveWeight == MINUSINFINITY)  {
            //(int) Math.floor(Math.random() * fieldMatrix.length);
            //(int) Math.floor(Math.random() * fieldMatrix.length);
        }
        System.out.println("Coords of MiniMax is: " + MOVE[ROW_COORD] + ' ' + MOVE[COL_COORD]);
        System.out.println("Weight = " + bestMoveWeight);
        return MOVE;  // returns garbage. Need to return to this, when will finish findMove procedure
    }

    private char[][] copyField(char[][] fieldMatrix) {
        char[][] temp = new char[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                temp[i][j] = fieldMatrix[i][j];
            }
        }
        return temp;
    }

    /* ***** ОПИСАЛОВО *******
            Программа строит дерево рекурсий на основе максиминной стратегии на глубину, задаваемую при вызове функции в
         параметре depth. Процедура осуществляет поиск в глубину. Она не отлажена, не протестирована, должна работать
         пока только для Х. Но зато должна работать для любого поля, на любую глубину. Еще она будет работать довольно
         медленно, сложность O(2^n), и использовать много памяти для стека вызовов.

         Идея: на каждой итерации проходимся по всему массиву в поисках пустой ячейки. При нахождении проверяем, какую
         фигуру надо поставить. Соответственно ставим ее. Если мы на самой вершине дерева, то мы запоминаем позицию,
         куда поставили Х, так как, возможно, это лучший ход. Оцениваем эвристику данного хода, записываем в deltaWeight.
         Далее идет рекурсивный вызов, при котором глубина уменьшается на 1, передается поле с установленной на нее
         фигурой (в первом случае Х), передается текущий вес ходов по пути от вершины до листа (на глубину depth),
         увеличенный на deltaWeight, и запомненный лучший ход.

         Когда добираемся до 0 глубины, оцениваем вес проделанного пути. Соответственно запоминаем максимальный вес и
         лучший ход. Здесь проиходит завершение рекурсивной функции, делается следующая итерация, поле и все веса
         возвращаются на шаг назад.

         TODO протестировать, отладить и сделать возможным работу для O
         НОВОЕ!!!
         На глубине 4 в целом работает неплохо.

         *****************
         Известные баги:

         На глубине больше 4 не видит проигрышной ситуации, например, при ходах:
         1 1; 0 2. Определяет вес как 7 и ходит не туда.
         ******************
     */


   // search in the depth
   //
   /*
    private void maxMinSearch(int curDepth, final int depthMax, char[][] curField, int curStepWeight, int[] curBestMove) {
        if (curDepth != 0) {
            for (int j = 0; j < X_SIZE; j++) {  // looking for empty cell. Maybe it will be the best step.
                for (int k = 0; k < Y_SIZE; k++) {
                    if (curField[j][k] == DEFAULT_CELL_VALUE) {
                        int deltaWeight = 0;
                        if ((depthMax - curDepth) % 2 == 0) {
                            curField[j][k] = VALUE_X;
                            if (curDepth == depthMax) {   // @ToDo Here we should choose the right depthMax to search
                                curBestMove[ROW_COORD] = j; // save temp best step
                                curBestMove[COL_COORD] = k;
                                worstCurStepWeight = INFINITY;
                            }
                            // copy to heuristic field our current field and get heuristic rating of of the step
                            heuristic.copyField(curField);
                            deltaWeight = heuristic.heuristicRating(j, k, 1, (depthMax - curDepth +1));
                        }
                        else {
                            curField[j][k] = VALUE_O;
                            heuristic.copyField(curField);
                            deltaWeight = heuristic.heuristicRating(j, k, -1, (depthMax - curDepth +1));
                        }
                        // end of operators before recursive call
                        maxMinSearch(curDepth - 1, depthMax, curField, curStepWeight + deltaWeight, curBestMove);
                        // operators after recursive call
                        // reverse all changes
                        curField[j][k] = DEFAULT_CELL_VALUE;  // reverse curField back, cancel our current step
                        // other operators (depth, curStepWeight) reverse themselves, because we haven't changed them
                    }
                }
            }
            // if truth then we have never gone o the depthMax
            if (worstCurStepWeight == INFINITY) {
                worstCurStepWeight = curStepWeight;
            }
            rememberNewBestMove(curBestMove);
        }
        else { // look if it is the worst weight
            if (curStepWeight < worstCurStepWeight) {
                worstCurStepWeight = curStepWeight;
            }
        }
    }

    private void rememberNewBestMove(int[] curBestMove) {
        if (bestMoveWeight < worstCurStepWeight) {
            bestMoveWeight = worstCurStepWeight;
            MOVE[ROW_COORD] = curBestMove[ROW_COORD];
            MOVE[COL_COORD] = curBestMove[COL_COORD];
        }
    }

    //метод используется в клиенте тестирования, в дальнейшем не нужен
    public void showField() {
        for (int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                if (j == Y_SIZE - 1) {
                    System.out.println(field[i][j]);
                } else {
                    System.out.print(field[i][j] + " ");
                }
            }
        }
    }
*/

   /**
    *
    * @param isAIFigureX boolean - true if
    */
   public Minimax(boolean isAIFigureX) {
      this.isAIFigureX = isAIFigureX;
   }

   /**
    * Returns computer step using MaxMin strategy
    * @param fieldMatrix  char[][] - current field.
    * @return int[] with coords of computer step {row coordinate, column coordinate}.
    */

   public int[] findMoveMiniMax(char[][] fieldMatrix) {
      searchSimpleSolutions(fieldMatrix);
      if (bestStepRating > 0) return bestStep;
      minimaxRecursiveSearch(fieldMatrix, SEARCH_DEPTH, bestStepRating, bestStep);
      if (bestStepRating == 0) randomStep(fieldMatrix);
      return bestStep;
   }

   // search for situations winning in one step or loosing in one step
   private void searchSimpleSolutions(char[][] field) {
      char aiFigure = VALUE_O;
      char rivalFigure = VALUE_X;
      if (isAIFigureX) {
         aiFigure = VALUE_X;
         rivalFigure = VALUE_O;
      }
      int[] aiWinnerStep = {-1, -1};
      int[] rivalWinnerStep = {-1, -1};

      for (int row = 0; row < field.length; row++) {
         for (int col = 0; col < field[row].length; col++) {
            if (field[row][col] == DEFAULT_CELL_VALUE) {
               int[] stepTaken = {row, col};
               field[row][col] = aiFigure;
               if (Game.winner(field, stepTaken) == aiFigure) aiWinnerStep = stepTaken;
               field[row][col] = rivalFigure;
               if (Game.winner(field, stepTaken) == rivalFigure) rivalWinnerStep = stepTaken;
               field[row][col] = DEFAULT_CELL_VALUE;
            }
         }
      }

      if (aiWinnerStep[ROW_COORD] != -1) {
         bestStep = aiWinnerStep;
         bestStepRating = Heuristic.stepRaiting(field, isAIFigureX, aiWinnerStep, 1);
      }
      else if (rivalWinnerStep[ROW_COORD] != -1) {
         bestStep = rivalWinnerStep;
         bestStepRating = Heuristic.stepRaiting(field, isAIFigureX, rivalWinnerStep, 1);
      }
   }

   // Algorithm
   // With recursion create tree of possible fields and estimate them using Heuristic class (heuristic coefficient)
   // Choose the best step based on the future best possible field
   private void minimaxRecursiveSearch(char[][] field, int depth, int rating, int[] bestStep) {
      if (depth != 0) {
         boolean defaultCellWasFound = false;
         for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
               if (field[row][col] == DEFAULT_CELL_VALUE) {
                  defaultCellWasFound = true;
                  int[] step = {row, col};
                  if ( (SEARCH_DEPTH - depth) % 2 == 0) {
                     // AI figure's step
                     if (isAIFigureX) field[row][col] = VALUE_X;
                     else field[row][col] = VALUE_O;
                     // if it is first step then remember it
                     if (SEARCH_DEPTH == depth) bestStep = step;
                  }
                  else {
                     // NOT AI figure's step
                     if (isAIFigureX) field[row][col] = VALUE_O;
                     else field[row][col] = VALUE_X;
                  }
                  int stepRating = Heuristic.stepRaiting(field, isAIFigureX, step, SEARCH_DEPTH - depth + 1);
                  //
                  if (Math.abs(stepRating) > 1) {
                     rememberBestStep(rating + stepRating, bestStep);
                     return;
                  }
                  minimaxRecursiveSearch(field, depth - 1, rating + stepRating, bestStep);
                  // reverse field changes
                  field[row][col] = DEFAULT_CELL_VALUE;
               }
            }
         }
         if (!defaultCellWasFound) {
            rememberBestStep(rating, bestStep);
         }
      }
      rememberBestStep(rating, bestStep);
   }

   private void rememberBestStep(int rating, int[] bestStep) {
      // check for different figures
      if ( ( (isAIFigureX) && (rating > this.bestStepRating) ) ||
      ( (!isAIFigureX) && (rating < this.bestStepRating) ) ) {
         bestStepRating = rating;
         this.bestStep = bestStep;
      }
   }

   // do random if minimax hasn't find anything
   private void randomStep(char[][] field) {
      Random random = new Random();
      int[] stepTaken = new int[2];
      while (true) {
         stepTaken[ROW_COORD] = random.nextInt(field.length);
         stepTaken[COL_COORD] = random.nextInt(field.length);
         if (field[ stepTaken[COL_COORD] ][ stepTaken[COL_COORD] ] == DEFAULT_CELL_VALUE) {
            bestStep = stepTaken;
            return;
         }
      }
   }



   @Test
   public static void main(String[] args) {
      System.out.println("Input test mode: \n1. Random testing to log file.\n2. User testing");
      Scanner scan = new Scanner(System.in);
      try {
         if (scan.nextInt() == 1) {
            autoLogTesting();
         } else if (scan.nextInt() == 2) {
            userTesting();
         }
         else {
            return;
         }
      }
      catch (IOException e) {
         e.printStackTrace();
         return;
      }
      catch (Exception e) {
         e.printStackTrace();
         return;
      }
   }

   private static void autoLogTesting() throws IOException {
      File file = new File(FILE_NAME);
      file.createNewFile();
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);

      for (int testNum = 0; testNum < TESTS_NUMBER; testNum++) {
         try {
            autoTest(bw, testNum);
         }
         finally {
            bw.close();
         }
      }
      System.out.println("Log test is done successfully!");
   }

   private static void autoTest(BufferedWriter bw, int testNum) throws IOException {
      char[][] field = new char[DEFAULT_FIELD_SIZE][DEFAULT_FIELD_SIZE];
      clearField(field);

      bw.write("////////////// TEST NUMBER " + testNum + " //////////////");
      bw.write( (char) 13 + (char) 10 );
      boolean isMinimaxStep = true;
      char minimaxFigure = VALUE_X;
      Minimax minimax = new Minimax(true);
      if (testNum % 2 == 0) {
         isMinimaxStep = false;
         minimaxFigure = VALUE_O;
         minimax = new Minimax(false);
      }

      int stepNum = 1;
      char winner = DEFAULT_CELL_VALUE;
      int[] stepTaken = new int[2];
      while (winner == DEFAULT_CELL_VALUE) {
         bw.write("Step  " + stepNum + ")");
         bw.write( (char) 13 + (char) 10 );

         if (isMinimaxStep) {
            stepTaken[ROW_COORD] = minimax.findMoveMiniMax(field)[ROW_COORD];
            stepTaken[COL_COORD] = minimax.findMoveMiniMax(field)[COL_COORD];
            bw.write("Minimax goes to [ " + stepTaken[ROW_COORD] + " ] [ " + stepTaken[COL_COORD] + " ]");
            bw.write( (char) 13 + (char) 10 );
         }
         else {
            while (true) {
               Random random = new Random();
               stepTaken[ROW_COORD] = random.nextInt(field.length);
               stepTaken[COL_COORD] = random.nextInt(field.length);
               if (field[ stepTaken[COL_COORD] ][ stepTaken[COL_COORD] ] == DEFAULT_CELL_VALUE) {
                  if (minimaxFigure == VALUE_X) {
                     field[ stepTaken[ROW_COORD] ][ stepTaken[COL_COORD] ] = VALUE_O;
                  }
                  else {
                     field[ stepTaken[ROW_COORD] ][ stepTaken[COL_COORD] ] = VALUE_X;
                  }
                  bw.write("Random goes to [ " + stepTaken[ROW_COORD] + " ] [ " + stepTaken[COL_COORD] + " ]");
                  bw.write( (char) 13 + (char) 10 );
                  break;
               }
            }
            fieldToFile(field, bw);
            bw.write( (char) 13 + (char) 10 );
         }

         stepNum++;
         winner = Game.winner(field, stepTaken);
      }

      bw.write(winner + " wins!");
      bw.write( (char) 13 + (char) 10 );
      bw.write( (char) 13 + (char) 10 );
      bw.write( (char) 13 + (char) 10 );

   }

   private static void fieldToFile(char[][] field, BufferedWriter bw) throws IOException {
      for (int row = 0; row < field.length; row++) {
         for (int col = 0; col < field.length; col++) {
            bw.write("[ " + field[row][col] + " ] ");
         }
         bw.write( (char) 13 + (char) 10 );
      }
      bw.write( (char) 13 + (char) 10 );
   }

   private static void userTesting() {
      char[][] field = {
              {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
              {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
              {Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE, Minimax.DEFAULT_CELL_VALUE},
      };
      Minimax minimax = new Minimax(true);
      System.out.println(minimax.findMoveMiniMax(field)[ROW_COORD] + " " + minimax.findMoveMiniMax(field)[COL_COORD]);

      field[0][0] = VALUE_X;
      field[1][1] = VALUE_X;
      field[0][2] = VALUE_O;
      field[1][2] = VALUE_O;
      System.out.println(minimax.findMoveMiniMax(field)[ROW_COORD] + " " + minimax.findMoveMiniMax(field)[COL_COORD]);
      clearField(field);

      field[2][2] = VALUE_X;
      field[2][0] = VALUE_X;
      field[0][0] = VALUE_X;
      field[1][0] = VALUE_O;
      field[1][2] = VALUE_O;

      System.out.println(minimax.findMoveMiniMax(field)[ROW_COORD] + " " + minimax.findMoveMiniMax(field)[COL_COORD]);
      clearField(field);

      System.out.println("/nEnter field size: ");
      Scanner scan = new Scanner(System.in);
      int fieldSize = scan.nextInt();
      field = new char[fieldSize][fieldSize];
      minimax = new Minimax(true);
      clearField(field);
      printField(field);

      int cnt = 1;
      int[] stepTaken = new int[2];
      while (cnt <= (fieldSize * fieldSize)) {
         if (cnt % 2 == 1) {
            stepTaken = minimax.findMoveMiniMax(field);
            System.out.println("Computer has stepped to: " + "{" +
                    stepTaken[ROW_COORD] + "," + stepTaken[COL_COORD] + "}");
            field[stepTaken[0]][stepTaken[1]] = 'X';
         }
         else {
            try {
               System.out.print("\nEnter row: ");
               stepTaken[0] = scan.nextInt();
               System.out.print("\nEnter col: ");
               stepTaken[1] = scan.nextInt();
            }
            catch (InputMismatchException e) {
               return;
            }
            field[stepTaken[0]][stepTaken[1]] = 'O';
         }
         printField(field);
         cnt++;
      }
   }

   private static void  clearField(char[][] field) {
      for (int row = 0; row < field.length; row++) {
         for (int col = 0; col < field[row].length; col++) {
            field[row][col] = DEFAULT_CELL_VALUE;
         }
      }
   }

   private static void printField(char[][] field) {
      for (int row = 0; row < field.length; row++) {
         for (int col = 0; col < field.length; col++) {
            System.out.print("[" + field[row][col] + "]");
         }
         System.out.println();
      }
   }

}