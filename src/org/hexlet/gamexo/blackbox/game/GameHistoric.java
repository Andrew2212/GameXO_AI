package org.hexlet.gamexo.blackbox.game;


import java.util.ArrayList;
import java.util.List;

public class GameHistoric {

    private static final int X = 0;
    private static final int Y = 1;

    private static List listOfHistory = new ArrayList();

    public static void recordCurrentStep(int x, int y) {
        int[] currentCellPosition = new int[2];
        currentCellPosition[X] = x;
        currentCellPosition[Y] = y;

        listOfHistory.add(currentCellPosition);
    }

    public static int[] removePreviousStep() {

        if (!isListHistoryEmty()) {
            GameField.reverseFlagSign();
            int[] cellPosition = (int[]) listOfHistory.remove(listOfHistory.size() - 1);
            GameField.fillDefaultCurrentCell(cellPosition[X], cellPosition[Y]);
            GameFieldPainter.showFields();
            return cellPosition;
        }
        System.out.println("Steps History is terminated!");
        return null;
    }

    public static boolean isListHistoryEmty(){
        return listOfHistory.isEmpty();
    }

    public static void clearHistory(){
          listOfHistory.clear();
    }

}
