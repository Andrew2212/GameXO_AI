package hqup.gamers;


import hqup.game.GameField;

public class GamerBot implements IGamer {

    private static final int X = 0;
    private static final int Y = 1;

    public int[] moveSign() {

        int[] position = getCoordinate();

        while (GameField.getFieldMatrix()[position[X]][position[Y]] != GameField.getDefaultCellValue()) {
            position = getCoordinate();
        }

        return position;
    }

//    ---------Private Methods-----------------

    private int[] getCoordinate() {

        int[] position = new int[2];

//      II could be here but so far there is 'Math.random()'
        position[X] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);
        position[Y] = (int) Math.floor(Math.random() * GameField.FIELD_SIZE);

        return position;
    }

}
