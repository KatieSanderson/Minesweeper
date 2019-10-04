package minesweeper;

public class Mine {

    private boolean isMine;
//    private boolean hasBeenChecked;
//    private int numberNearbyMines;

    public void setAsMine() {
        isMine = true;
    }

    public boolean isMine() {
        return isMine;
    }

    @Override
    public String toString() {
        return isMine ? "X" : ".";
    }
}