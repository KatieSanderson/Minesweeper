package minesweeper;

import java.util.Random;

public class Minefield {

    private final int height;
    private final int width;
    private final int numMines;
    private final Mine[][] field;

    Minefield(int height, int width, int numMines) {
        this.height = height;
        this.width = width;
        this.numMines = numMines;
        field = new Mine[height][width];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Mine();
            }
        }
    }

    void generateMineSpots() {
        Random random = new Random();
        int minedSpots = 0;
        while (minedSpots < numMines) {
            int heightToMine = random.nextInt(height);
            int widthToMine = random.nextInt(width);
            if (!field[heightToMine][widthToMine].isMine()) {
                field[heightToMine][widthToMine].setAsMine();
                minedSpots++;
            }
        }
    }

    void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}