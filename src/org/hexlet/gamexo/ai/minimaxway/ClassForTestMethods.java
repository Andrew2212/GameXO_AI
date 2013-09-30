/**
 * This class use for test
 * methods in Minimax
 */

package org.hexlet.gamexo.ai.minimaxway;

import java.util.Scanner;

public class ClassForTestMethods{
    public static void main(String[] args) {
//        Heuristic heuristic = Heuristic.createInstance(3,4,4);
//        int cnt = 0;
//        int res = 0;
//        Scanner scan = new Scanner(System.in);
//        while(cnt < 9 && res != 5 && res != -5){
//            System.out.print("\nEnter x: ");
//            int x = scan.nextInt();
//            System.out.print("\nEnter y: ");
//            int y = scan.nextInt();
//            int move;
//            if (cnt % 2 == 0){
//                move = 1;
//            } else {
//                move = -1;
//            }
//            res = heuristic.heuristicRating(x, y, move);
//            System.out.println(res);
//            heuristic.showField();
//            cnt++;
//        }
        Minimax minimax = new Minimax(3, 3, 3);
        char[][] field = new char[3][3];
        int cnt = 0;
        Scanner scan = new Scanner(System.in);
        int[] move = {0, 0};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = '_';
            }
        }
        while(cnt < 9){
            if (cnt % 2 == 0){
                move = minimax.findMoveMiniMax(field);
                field[move[0]][move[1]] = 'X';
                System.out.println(move[0] + " " + move[1]);
            } else {
                System.out.print("\nEnter x: ");
                int x = scan.nextInt();
                System.out.print("\nEnter y: ");
                int y = scan.nextInt();
                field[x][y] = 'O';
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print("[" + field[i][j] + "]");
                }
                System.out.println();
            }
            cnt++;
        }

    }
}