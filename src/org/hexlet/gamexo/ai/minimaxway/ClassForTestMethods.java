/**
 * This class use for test
 * methods in Minimax
 */

package org.hexlet.gamexo.ai.minimaxway;

import java.util.Scanner;

public class ClassForTestMethods{
    public static void main(String[] args) {
        Heuristic heuristic = Heuristic.createInstance(3,4,4);
        int cnt = 0;
        int res = 0;
        Scanner scan = new Scanner(System.in);
        while(cnt < 9 && res != 5 && res != -5){
            System.out.print("\nEnter x: ");
            int x = scan.nextInt();
            System.out.print("\nEnter y: ");
            int y = scan.nextInt();
            int move;
            if (cnt % 2 == 0){
                move = 1;
            } else {
                move = -1;
            }
            res = heuristic.heuristicRating(x, y, move);
            System.out.println(res);
            heuristic.showField();
            cnt++;
        }




    }
}