package paket1;

import java.util.*;

public class Maze {
    static int height;
    static int width;
    static Cell[][] grid;
    Cell exit;
    HashMap<Cell, ArrayList<Cell>> treeMapOfPassages;
    ArrayList<Cell> listOfPassages;
    ArrayList<Cell> listOfEscapes ;
    LinkedList<Cell> stack;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];
        stack = new LinkedList<>();
        listOfEscapes = new ArrayList<>();
        listOfPassages = new ArrayList<>();
        treeMapOfPassages = new HashMap<>();
    }

    void generate() {
        //step 1: generate grid with odd PASSAGES and even WALLS, and round walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0 || j % 2 == 0 || j == width - 1 || i == height - 1) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                    listOfPassages.add(grid[i][j]);
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j].setNeighbours();
            }
        }
        // step2: generate two escapes
        grid[0][1].type = Cell.Type.PASSAGE;
        int exitColumn = width - 3 + width % 2;
        if (height % 2 == 1) {
            grid[height - 1][exitColumn].type = Cell.Type.PASSAGE;
            exit = grid[height - 1][exitColumn];
        } else {
            grid[height - 1][exitColumn].type = Cell.Type.PASSAGE;
            exit = grid[height - 1][exitColumn];
            grid[height - 2][exitColumn].type = Cell.Type.PASSAGE;
        }

        //step 3: punch passages to exit
        for (int i = 0; i < listOfPassages.size(); i++) {
            Cell cell = listOfPassages.get(i);
            punchPassage(cell);
        }
        treeMapOfPassages.put(grid[0][1], getNeighbours(grid[0][1]));
        setTreeMapOfPassages(grid[1][1]);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = grid[i][j];
                if (cell.type == Cell.Type.PASSAGE && !treeMapOfPassages.containsKey(cell)) {
                    punchPassage(cell);
                    treeMapOfPassages.clear();
                    treeMapOfPassages.put(grid[0][1], getNeighbours(grid[0][1]));
                    setTreeMapOfPassages(grid[1][1]);
                    j--;
                }
            }
        }
    }

    // Operative methods

    public Cell getEscape(Cell cell) {
            if (!cell.visited) {
                stack.offerLast(cell);
                cell.visited = true;
            }
            if (cell == exit) {
                while (stack.size() > 0) {
                    stack.peekLast().type = Cell.Type.ESCAPE;
                    listOfEscapes.add(stack.pollLast());
                }
                return cell;
            }
            ArrayList<Cell> list = getNeighbours(cell);
            if (list.size() == 0) {
                stack.pollLast();
                if (stack.size() > 0)
                    return getEscape(stack.peekLast());
            } else {
                for (Cell c : list
                ) {
                    getEscape(c);
                }
            }
            return cell;
    }

    public ArrayList<Cell> getNeighbours(Cell cell) {
        ArrayList<Cell> listOfNeigbours = new ArrayList<>();
        if (cell.north != null && cell.north.type == Cell.Type.PASSAGE && !cell.north.visited)
            listOfNeigbours.add(cell.north);
        if (cell.east !=null && cell.east.type == Cell.Type.PASSAGE && !cell.east.visited)
            listOfNeigbours.add(cell.east);
        if (cell.south != null && cell.south.type == Cell.Type.PASSAGE && !cell.south.visited)
            listOfNeigbours.add(cell.south);
        if (cell.west != null && cell.west.type == Cell.Type.PASSAGE && !cell.west.visited)
            listOfNeigbours.add(cell.west);
        return listOfNeigbours;
    }

    public ArrayList<Cell> getNeighbourWalls(Cell cell) {
        ArrayList<Cell> listOfNeighbourWalls = new ArrayList<>();
        if (cell.north.type != Cell.Type.PASSAGE && cell.north.y != 0)
            listOfNeighbourWalls.add(cell.north);
        if (cell.south.type != Cell.Type.PASSAGE && cell.south.y != height - 1)
            listOfNeighbourWalls.add(cell.south);
        if (cell.west.type != Cell.Type.PASSAGE && cell.west.x != 0)
            listOfNeighbourWalls.add(cell.west);
        if (cell.east.type != Cell.Type.PASSAGE && cell.east.x != width - 1)
            listOfNeighbourWalls.add(cell.east);
        return listOfNeighbourWalls;
    }

    public void punchPassage(Cell cell) {
        ArrayList<Cell> listOfNeighbourWalls = getNeighbourWalls(cell);
        if (listOfNeighbourWalls.size() !=0) {
            int random = new Random().nextInt(listOfNeighbourWalls.size());
            listOfNeighbourWalls.get(random).type = Cell.Type.PASSAGE;
        }
    }


    public void printPassagesTree() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (treeMapOfPassages.containsKey(grid[i][j])) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void setTreeMapOfPassages (Cell cell) {
        if (treeMapOfPassages.containsKey(cell)) return;
        ArrayList<Cell> list = getNeighbours(cell);
        treeMapOfPassages.put(cell, list);
        for (Cell c: list
             ) {
            setTreeMapOfPassages(c);
        }
    }

     public String toString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].type == Cell.Type.WALL) {
                    mazeString.append("\u2588\u2588");
                } else if (grid[i][j].type == Cell.Type.ESCAPE){
                    mazeString.append("\\\\");
                } else {
                    mazeString.append("  ");
                }
            }
            mazeString.append("\n");
        }
        return mazeString.toString();
    }

}
