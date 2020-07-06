package paket1;

import java.util.Random;

public class Universe {
    static Cell[][] currentUniverse;
    Random random;
    int size;

    public Universe(int size) {
        this.size = size;
        random = new Random();
        currentUniverse = new Cell[size + 2][size + 2];
        generate();
    }

    public Universe(int size, long seed) {
        this.size = size;
        random = new Random(seed);
        currentUniverse = new Cell[size + 2][size + 2];
        generate();
    }

    public void generate() {
        for (int i = 1; i < currentUniverse.length - 1; i++) {
            for (int j = 1; j < currentUniverse.length - 1; j++) {
                currentUniverse[i][j] = new Cell(i, j);
                currentUniverse[i][j].isAlive = random.nextBoolean();
            }
        }
        setNeigbours();
    }

    public void setNeigbours() {
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse.length; j++) {
                int y = i;
                int x = j;
                if (i == 0) y = currentUniverse.length - 2;
                if (i == currentUniverse.length - 1) y = 1;
                if (j == 0) x = currentUniverse.length - 2;
                if (j == currentUniverse.length - 1) x = 1;
                currentUniverse[i][j] = currentUniverse[y][x];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] arr: currentUniverse
        ) {
            for (Cell cell: arr
            ) {
                if (cell.isAlive) stringBuilder.append('O');
                else stringBuilder.append("*");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
