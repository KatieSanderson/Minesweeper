package minesweeper;

class Mine {

    public enum MineState {
        NUMBER,
        BLANK,
        MINE
    }
    public enum GuessState {
        NOT_GUESSED,
        FLAGGED,
        FREE
    }

    private final int height;
    private final int width;

    private MineState mineState;
    private GuessState guessState;
    private int numberNearbyMines;

    Mine(int height, int width) {
        this.height = height;
        this.width = width;
        mineState = MineState.BLANK;
        guessState = GuessState.NOT_GUESSED;
        numberNearbyMines = 0;
    }

    void setAsMine() {
        mineState = MineState.MINE;
    }

    boolean isMine() {
        return mineState == MineState.MINE;
    }

    MineState getMineState() {
        return mineState;
    }

    GuessState getGuessState() {
        return guessState;
    }

    void setAsFlagged() {
        guessState = GuessState.FLAGGED;
    }

    void setAsNotGuessed() {
        guessState = GuessState.NOT_GUESSED;
    }

    void setAsFree() {
        guessState = GuessState.FREE;
    }

    void setNumberNearbyMines(int numberNearbyMines) {
        if (mineState != MineState.MINE && numberNearbyMines > 0 && numberNearbyMines < 9) {
            this.numberNearbyMines = numberNearbyMines;
            mineState = MineState.NUMBER;
        }
    }

    int getNumberNearbyMines() {
        return numberNearbyMines;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    String printMine() {
        switch (guessState) {
            case FLAGGED:
                return "*";
            case NOT_GUESSED:
                return ".";
            case FREE:
                switch (mineState) {
                    case BLANK:
                        return "/";
                    case NUMBER:
                        return "" + numberNearbyMines;
                }
            default:
                throw new RuntimeException("Guess state error");
        }
    }
}