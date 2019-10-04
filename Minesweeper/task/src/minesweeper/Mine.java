package minesweeper;

class Mine {

    private MineState state;
    private boolean isGuessed;
    private int numberNearbyMines;

    Mine() {
        state = MineState.BLANK;
        isGuessed = false;
        numberNearbyMines = 0;
    }

    void setAsMine() {
        state = MineState.MINE;
    }

    boolean isMine() {
        return state == MineState.MINE;
    }

    MineState getState() {
        return state;
    }

    void setAsGuessed() {
        isGuessed = true;
    }

    void setAsNotGuessed() {
        isGuessed = false;
    }

    boolean isGuessed() {
        return isGuessed;
    }

    void setNumberNearbyMines(int numberNearbyMines) {
        if (state != MineState.MINE && numberNearbyMines > 0 && numberNearbyMines < 9) {
            this.numberNearbyMines = numberNearbyMines;
            state = MineState.NUMBER;
        }
    }

    public String printMine() {
        if (state == MineState.NUMBER) {
            return "" + numberNearbyMines;
        } else if (isGuessed) {
            return "*";
        } else {
            return ".";
        }
    }
}