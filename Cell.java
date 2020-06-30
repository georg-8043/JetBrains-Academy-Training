package paket1;

public class Cell {
    int y;
    int x;
    Cell.Type type;
    Cell north;
    Cell south;
    Cell west;
    Cell east;
    boolean visited = false;

    public Cell(int row, int column, Cell.Type type) {
        this.y = row;
        this.x = column;
        this.type = type;
    }

    void setNeighbours() {
        Maze.grid[0][1].south = Maze.grid[1][1];
        if (y != 0 && y != Maze.height - 1 && x != 0 && x != Maze.width - 1) {
            north = Maze.grid[y - 1][x];
            south = Maze.grid[y + 1][x];
            west = Maze.grid[y][x - 1];
            east = Maze.grid[y][x + 1];
        }
    }

    public enum Type {
        PASSAGE,
        WALL,
        ESCAPE;
    }

}
