package hqup.gamers;


import hqup.game.GameHistoric;

import java.util.Scanner;

public class GamerMan implements IGamer {

    private static final int X = 0;
    private static final int Y = 1;


    public int[] moveSign() {

        int[] position = new int[2];

        position[X] = getCoordinate(X);
        position[Y] = getCoordinate(Y);
        return position;
    }

    private int getCoordinate(int index) {
        int result = 0;
        String strEnter = "";
        switch (index) {

            case X:
                while (true) {
                    System.out.println("Enter coordinate X");
                    strEnter = new Scanner(System.in).next();
                    if (isEnterValid(strEnter)) break;
                }
                result = setEnteredValue(strEnter);
                break;

            case Y:
                while (true) {
                    System.out.println("Enter coordinate Y");
                    strEnter = new Scanner(System.in).next();
                    if (isEnterValid(strEnter)) break;
                }
                result = setEnteredValue(strEnter);
                break;

            default:
                break;

        }
        return result;
    }
    //    --------------Private Methods----------------------

    private int setEnteredValue(String enter) {

        if (isEnterValid(enter)) {
            return Integer.valueOf(enter);
        }
        return 0;
    }

    private boolean isEnterValid(String strEnter) {
        if (strEnter.matches("[0-9]+")) {
            return true;
        }
        System.out.println("***That'll never fly! Invalid entry");
        return false;
    }

}
