package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int height = 9;
        int width = 9;
        int numMines = Integer.parseInt(scanner.nextLine());

        Minefield minefield = new Minefield(height, width, numMines);
        minefield.generateMineSpots();
        minefield.printField();
    }
}
