package paket1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {
    Universe universe;
    Cell[][] currentUniverse;
    Cell[][] nextUniverse;
    int k = 0;
    String generation;
    String alive;

    public Controller(int size) throws InterruptedException {
        universe = new Universe(size);
        currentUniverse = Universe.currentUniverse;
        nextUniverse = new Cell[size + 2][size + 2];
        generation = "generation #0";
        alive = "Alive: " + countOfAlive();
    }

    public Controller(int size, long seed) {
        universe = new Universe(size, seed);
        currentUniverse = Universe.currentUniverse;
        nextUniverse = new Cell[size + 2][size + 2];
    }

    public void newGens() throws InterruptedException {
//        for (int k = 0; k < num; k++) {
            for (int i = 1; i < currentUniverse.length - 1; i++) {
                for (int j = 1; j < currentUniverse.length - 1; j++) {
                    nextUniverse[i][j] = cloneCell(currentUniverse[i][j]);
                    int neigbours = getAliveNeighbours(currentUniverse[i][j]).size();
                    if (neigbours < 2 || neigbours > 3) nextUniverse[i][j].isAlive = false;
                    if (neigbours == 3) nextUniverse[i][j].isAlive = true;
                }
            }
            copyNextToCurrent();
            generation = "Generation #" + (k + 1);
            k++;
            alive = "Alive: " + countOfAlive();
            TimeUnit.SECONDS.sleep(1);
//        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int countOfAlive() {
        int count = 0;
        for (int i = 1; i < currentUniverse.length - 1; i++) {
            for (int j = 1; j < currentUniverse.length - 1; j++) {
                if (currentUniverse[i][j].isAlive) count++;
            }
        }
        return count;
    }

    public ArrayList<Cell> getAliveNeighbours(Cell cell) {
        ArrayList<Cell> list = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)) {
                    int y = cell.y + i;
                    int x = cell.x + j;
                    if (currentUniverse[y][x].isAlive) list.add(currentUniverse[y][x]);
                }
            }
        }
        return list;
    }

    public Cell cloneCell(Cell cell) {
        Cell newCell = new Cell(cell.y, cell.x);
        newCell.isAlive = cell.isAlive;
        return newCell;
    }

    public void copyNextToCurrent() {
        for (int i = 1; i < currentUniverse.length - 1; i++) {
            for (int j = 1; j < currentUniverse.length - 1; j++) {
                currentUniverse[i][j] = cloneCell(nextUniverse[i][j]);
            }
        }
        universe.setNeigbours();
    }

    //this method for debugging
    public void print(Cell[][] matrix) {
        for (Cell[] arr: matrix
             ) {
            for (Cell cell: arr
                 ) {
                if (cell.isAlive) System.out.print('O');
                else System.out.print('*');
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print() {
        for (int i = 1; i < currentUniverse.length - 1; i++) {
            for (int j = 1; j < currentUniverse.length - 1; j++) {
                if (currentUniverse[i][j].isAlive) System.out.print('O');
                else System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}
