package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int height = 9;
        int width = 9;
        System.out.print("How many mines do you want on the field?");
        int numMines = Integer.parseInt(scanner.nextLine());

        Minefield minefield = new Minefield(height, width, numMines);
        minefield.printField();
        minefield.buildBoardFromInput(getUserInput(scanner));
        minefield.printField();

        boolean isGameWon = false;
        while (!isGameWon) {
            minefield.processMineMark(getUserInput(scanner));
            if (minefield.isGameWon()) {
                System.out.println("Congratulations! You founded all mines!");
                isGameWon = true;
            } else {
                minefield.printField();
            }
        }
    }

    private static Input getUserInput(Scanner scanner) {
        System.out.print("Set/delete mines marks (x and y coordinates): ");
        String[] input = scanner.nextLine().split(" ");
        // convert user-scale (1 based) into array-scale (0 based)
        return new Input(Integer.parseInt(input[1]) - 1, Integer.parseInt(input[0]) - 1, input[2]);
    }
}
