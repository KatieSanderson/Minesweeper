package minesweeper;

import java.util.Random;
import java.util.Stack;

class Minefield {

    private final int fieldHeight;
    private final int fieldWidth;
    private final Mine[][] field;
    private final int numMines;

    private int correctMineCount;
    private int incorrectMineCount;
    private int freeCount;
    private boolean isGameLost;

    Minefield(int fieldHeight, int fieldWidth, int numMines) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.numMines = numMines;
        field = new Mine[fieldHeight][fieldWidth];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Mine(i, j);
            }
        }

        correctMineCount = 0;
        incorrectMineCount = 0;
        freeCount = 0;
        isGameLost = false;
    }

    void buildBoardFromInput(Input input) {
        Random random = new Random();
        int inputYCoordinate = input.getYCoordinate();
        int inputXCoordinate = input.getXCoordinate();
        int minedSpots = 0;
        while (minedSpots < numMines) {
            int heightToMine = random.nextInt(fieldHeight);
            int widthToMine = random.nextInt(fieldWidth);
            if (heightToMine != inputYCoordinate &&
                    widthToMine != inputXCoordinate &&
                    !field[heightToMine][widthToMine].isMine()) {
                field[heightToMine][widthToMine].setAsMine();
                minedSpots++;
            }
        }
        evaluateNeighboringMines();
    }

    private void evaluateNeighboringMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                // evaluate mines on all sides (top, right, bottom, left) of mine to get mine count
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

    void processMineMark(Input userInput) {
        Mine spot = field[userInput.getXCoordinate()][userInput.getYCoordinate()];
        Input.Message message = userInput.getMessage();
        switch (message) {
            case FREE:
                switch (spot.getMineState()) {
                    case MINE:
                        // if mine is freed, game is lost
                        isGameLost = true;
                        return;
                    case NUMBER:
                        // if number, show number and end turn
                        spot.setAsFree();
                        return;
                    case BLANK:
                        // if blank, free neighbors (blank +
                        spot.setAsFree();
                        freeCount++;
                        freeNeighbors(spot);
                        return;
                }
            case MINE:
                // flag un-flagged spot, un-flag flagged spot and update counts accordingly
                switch (spot.getMineState()) {
                    case MINE:
                        correctMineCount = updateMineStateAndCount(spot, correctMineCount);
                    case NUMBER:
                        incorrectMineCount = updateMineStateAndCount(spot, incorrectMineCount);
                    case BLANK:
                        incorrectMineCount = updateMineStateAndCount(spot, incorrectMineCount);
                }
                // if all cells with mines are marked, user wins (and no empty cells marked)
        }
    }

    private void freeNeighbors(Mine spot) {
        Stack<Mine> spotsToFree = new Stack<>();
        spotsToFree.push(spot);
        while (!spotsToFree.isEmpty()) {
            Mine nextSpot = spotsToFree.pop();
            freeCount++;
            nextSpot.setAsFree();
            int nextSpotHeight = nextSpot.getHeight();
            int nextSpotWidth = nextSpot.getWidth();
            if (nextSpot.getNumberNearbyMines() == 0) {
                spotsToFree.push(getMineFromField(nextSpotHeight - 1, nextSpotWidth - 1));
                spotsToFree.push(getMineFromField(nextSpotHeight - 1, nextSpotWidth));
                spotsToFree.push(getMineFromField(nextSpotHeight - 1, nextSpotWidth + 1));
                spotsToFree.push(getMineFromField(nextSpotHeight, nextSpotWidth - 1));
                spotsToFree.push(getMineFromField(nextSpotHeight, nextSpotWidth + 1));
                spotsToFree.push(getMineFromField(nextSpotHeight + 1, nextSpotWidth - 1));
                spotsToFree.push(getMineFromField(nextSpotHeight + 1, nextSpotWidth));
                spotsToFree.push(getMineFromField(nextSpotHeight + 1, nextSpotWidth + 1));
            }
        }
    }

    private Mine getMineFromField(int height, int width) {
        return field[height][width];
    }

    private int updateMineStateAndCount(Mine spot, int count) {
        if (spot.getGuessState().equals(Mine.GuessState.FLAGGED)) {
            spot.setAsNotGuessed();
            count--;
        } else {
            spot.setAsFlagged();
            count++;
        }
        return count;
    }

    boolean isGameWon() {
        if (isGameLost) {
            return false;
        }
        // if all non-mine spots are freed, game is won
        // if all mines are flagged and no non-mines are flagged, game is won
        return (freeCount + numMines == fieldHeight * fieldWidth) ||
                (correctMineCount == numMines && incorrectMineCount == 0);
    }
}