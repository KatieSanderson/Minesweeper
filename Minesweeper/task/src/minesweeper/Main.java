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
        minefield.generateMineSpots();
        minefield.evaluateNeighboringMines();
        minefield.printField();

        boolean foundAllMines = false;
        while (!foundAllMines) {
            minefield.processMineMark(getUserInput(scanner));
            if (minefield.isGameWon()) {
                System.out.println("Congratulations! You founded all mines!");
                foundAllMines = true;
            }
        }
    }

    private static int[] getUserInput(Scanner scanner) {
        System.out.print("Set/delete mines marks (x and y coordinates): ");
        String[] input = scanner.nextLine().split(" ");
        // convert user-scale (1 based) into array-scale (0 based)
        return new int[] {Integer.parseInt(input[1]) - 1, Integer.parseInt(input[0]) - 1};
    }
}
