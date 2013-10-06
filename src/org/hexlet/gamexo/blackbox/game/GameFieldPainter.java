package org.hexlet.gamexo.blackbox.game;

public class GameFieldPainter {

    //    private Game game;
    private static Character[][] fieldMatrix;

    public static void showFields() {

        fieldMatrix = GameField.getFieldMatrix();

        //Show column numbers
        System.out.print("  ");
        for (int i = 0; i < GameField.FIELD_SIZE; i++) {
            System.out.print(" " + i % 10);
        }
        System.out.println(); //Same as '\n'

        //Show 'row numbers' except last and 'rows' except last
        for (int i = 0; i < GameField.FIELD_SIZE - 1; i++) {

            System.out.print(i % 10 + "  ");// Row numbers
            showLine(i);//Rows
        }

        //Show last 'row number' and last 'row'
        showLineLast();

        System.out.println();
    }

    private static void showLine(int lineNumber) {

        for (int i = 0; i < GameField.FIELD_SIZE - 1; i++) {
            System.out.print(fieldMatrix[i][lineNumber] + "|");
        }
        System.out.print(fieldMatrix[GameField.FIELD_SIZE - 1][lineNumber]);
        System.out.println(); //Same as '\n'
    }

    private static void showLineLast() {

        System.out.print((GameField.FIELD_SIZE - 1) % 10 + "  ");
        for (int i = 0; i < GameField.FIELD_SIZE; i++) {

            char sign = fieldMatrix[i][GameField.FIELD_SIZE - 1];
            if (sign == GameField.getDefaultCellValue()) {
                sign = ' ';
            }

            if (i < GameField.FIELD_SIZE - 1) {
                System.out.print(sign + "|");
            } else {
                System.out.println(sign);
            }
        }
    }
}
