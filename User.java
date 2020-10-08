package TicTacToe;

import java.util.Scanner;

public class User extends Player {
    Scanner scanner = new Scanner(System.in);

    public User(char xORo) {
        this.xORo = xORo;
    }

    @Override
    public void move(char[][] field) {
        while (true) {
            System.out.print("Enter the coordinates:");
            String str= scanner.nextLine();
            if (!str.matches("\\d \\d")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            int yCoord = Integer.parseInt(str.substring(0, 1)) - 1;
            int xCoord = Integer.parseInt(str.substring(2, 3)) - 1;
            if (yCoord > 2 || xCoord >2) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (field[yCoord][xCoord] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            else {
                field[yCoord][xCoord] = xORo;
                return;
            }
        }
    }
}
