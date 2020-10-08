package TicTacToe;

import java.util.Scanner;

public class Main {
    static Player one;
    static Player two;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command:");
            String[] command = scanner.nextLine().split(" ");
            if (command[0].equals("exit")) System.exit(0);
            else if (command[0].equals("start") && command.length == 3) {
                if (command[1].equals("user")) one = new User('X');
                if (command[1].equals("easy")) one = new Computer('X', "easy");
                if (command[1].equals("medium")) one = new Computer('X', "medium");
                if (command[1].equals("hard")) one = new Computer('X', "hard");
                if (command[2].equals("user")) two = new User('O');
                if (command[2].equals("easy")) two = new Computer('O', "easy");
                if (command[2].equals("medium")) two = new Computer('O', "medium");
                if (command[2].equals("hard")) two = new Computer('O', "hard");
                new Game(one, two);
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }
}
