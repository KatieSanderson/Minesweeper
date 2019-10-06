package minesweeper;

public class Input {

    enum Message {
        FREE ("free"),
        MINE ("mine");

        Message(String messsage) {}
    }

    private final int xCoordinate;
    private final int yCoordinate;
    private final Message message;

    Input(int xCoordinate, int yCoordinate, String message) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        try {
            this.message = Message.valueOf(message);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Expected input of free or mine");
        }
    }

    int getXCoordinate() {
        return xCoordinate;
    }

    int getYCoordinate() {
        return yCoordinate;
    }

    Message getMessage() {
        return message;
    }

}
