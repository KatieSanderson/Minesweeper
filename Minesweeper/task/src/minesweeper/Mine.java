package minesweeper;

public class Mine {

    private boolean isMine;
//    private boolean hasBeenChecked;
    private int numberNearbyMines;

    void setAsMine() {
        isMine = true;
    }

    void setNumberNearbyMines(int numberNearbyMines) {
        this.numberNearbyMines = numberNearbyMines;
    }

    boolean isMine() {
        return isMine;
    }

    @Override
    public String toString() {
        if (isMine) {
            return "X";
        } else if (numberNearbyMines > 0 && numberNearbyMines < 9) {
            return "" + numberNearbyMines;
        } else {
            return ".";
        }
    }
}