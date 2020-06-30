package paket1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
   static Maze maze;
   static Scanner scanner = new Scanner(System.in);
   static String fileName;

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");
            System.out.println("0. Exit");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Incorrect option. Please try again");
                continue;
            }
            switch (choice) {
                case 1:
                    generateMaze();
                    break;
                case 2:
                    loadMaze();
                    break;
                case 0:
                    exit();
                default:
                    System.out.println("Incorrect option. Please try again");
            }

            if (maze != null) {
                while (true) {
                    System.out.println("=== Menu ===");
                    System.out.println("1. Generate a new maze");
                    System.out.println("2. Load a maze");
                    System.out.println("3. Save the maze");
                    System.out.println("4. Display the maze");
                    System.out.println("5. Find the escape");
                    System.out.println("0. Exit");
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect option. Please try again");
                        continue;
                    }
                    switch (choice) {
                        case 1:
                            generateMaze();
                            break;
                        case 2:
                            loadMaze();
                            break;
                        case 3:
                            save();
                            break;
                        case 4:
                            System.out.println(maze);
                            break;
                        case 5:
                            findEscape();
                            break;
                        case 0:
                            exit();
                        default:
                            System.out.println("Incorrect option. Please try again");
                    }
                }
            }
        }
    }

    static void findEscape() {
        maze.getEscape(Maze.grid[0][1]);
        System.out.println(maze);
    }

    static void save() {
        fileName = scanner.nextLine();
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write(maze.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void generateMaze() {
        System.out.println("Enter the size of a new maze");
        try {
            int value = Integer.parseInt(scanner.nextLine());
            maze = new Maze(value, value);
            maze.generate();
            System.out.println(maze);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect option. Please try again");
        }
    }

    static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    static void loadMaze() {
        fileName = scanner.nextLine();
        try (Scanner fileScan = new Scanner(new File(fileName))) {
            ArrayList<String> list = new ArrayList();
            while (fileScan.hasNextLine()) {
                list.add(fileScan.nextLine());
            }
            try {
                int height = list.size();
                int width = list.get(0).length() / 2;
                maze = new Maze(height, width);
                for (int i = 0; i < height; i++) {
                    for (int j = 0, k = 0; j < width; j++, k += 2) {
                        Maze.grid[i][j] = list.get(i).charAt(k) == '\u2588'?
                                new Cell(i, j, Cell.Type.WALL):
                                new Cell(i, j, Cell.Type.PASSAGE);
                        if (i == height - 1 && list.get(i).charAt(k) == ' ') {
                            maze.exit = Maze.grid[i][j];
                        }
                    }
                }
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                       Maze.grid[i][j].setNeighbours();
                    }
                }
            } catch (Exception e) {
                System.out.println("Cannot load the maze. It has an invalid format");
            }
//            System.out.println(maze);
        } catch (FileNotFoundException e) {
            System.out.println("The file " + fileName + " does not exist");
        }
    }
}
