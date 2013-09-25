package org.hexlet.gamexo.ai.minimaxway;

import java.util.Scanner;
// клиент для тестирования
public class Main {
    public static void main(String[] args) {

        Minimax mx = new Minimax(3,3,3);
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
            res = mx.heuristic(x,y,move);
            System.out.println(res);
            mx.showField();
            cnt++;
        }




    }
}
