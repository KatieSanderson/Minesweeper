package minesweeper;

import java.util.Random;

class Minefield {

    private final int fieldHeight;
    private final int fieldWidth;
    private final int numMines;
    private final Mine[][] field;

    private int correctMineCount;
    private int incorrectMineCount;

    Minefield(int fieldHeight, int fieldWidth, int numMines) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.numMines = numMines;
        field = new Mine[fieldHeight][fieldWidth];
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
            int heightToMine = random.nextInt(fieldHeight);
            int widthToMine = random.nextInt(fieldWidth);
            if (!field[heightToMine][widthToMine].isMine()) {
                field[heightToMine][widthToMine].setAsMine();
                minedSpots++;
            }
        }
    }

    void evaluateNeighboringMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                int mineCount = 0;
                mineCount += isNeighborMine(i - 1, j - 1) ? 1 : 0;
                mineCount += isNeighborMine(i - 1, j) ? 1 : 0;
                mineCount += isNeighborMine(i - 1, j + 1) ? 1 : 0;
                mineCount += isNeighborMine(i, j - 1) ? 1 : 0;
                mineCount += isNeighborMine(i, j + 1) ? 1 : 0;
                mineCount += isNeighborMine(i + 1, j - 1) ? 1 : 0;
                mineCount += isNeighborMine(i + 1, j) ? 1 : 0;
                mineCount += isNeighborMine(i + 1, j + 1) ? 1 : 0;
                field[i][j].setNumberNearbyMines(mineCount);
            }
            System.out.println();
        }
    }

    private boolean isNeighborMine(int height, int width) {
        if (height < 0 || width < 0 || height >= fieldHeight || width >= fieldWidth) {
            return false;
        }
        return field[height][width].isMine();
    }

    void printField() {
        System.out.println(" │123456789│");
        System.out.print("—│");
        for (int i = 0; i < fieldWidth; i++) {
            System.out.print("-");
        }
        System.out.println("│");
        for (int i = 0; i < field.length; i++) {
            System.out.print((i + 1) + "│");
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j].printMine());
            }
            System.out.println("│");
        }
        System.out.print("—│");
        for (int i = 0; i < fieldWidth; i++) {
            System.out.print("-");
        }
        System.out.println("│");
    }

    void processMineMark(int[] userInput) {
        Mine spot = field[userInput[0]][userInput[1]];
        switch (spot.getState()) {
            case NUMBER:
                System.out.println("There is a number here!");
                break;
            case BLANK:
                if (spot.isGuessed()) {
                    spot.setAsNotGuessed();
                    incorrectMineCount--;
                } else {
                    spot.setAsGuessed();
                    incorrectMineCount++;
                }
                printField();
                break;
            case MINE:
                if (spot.isGuessed()) {
                    spot.setAsNotGuessed();
                    correctMineCount--;
                } else {
                    spot.setAsGuessed();
                    correctMineCount++;
                }
                printField();
                break;
            default:
                throw new RuntimeException("Invalid state");
        }
    }

    boolean isGameWon() {
        return correctMineCount == numMines && incorrectMineCount == 0;
    }
}