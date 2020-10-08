package TicTacToe;

import java.util.ArrayList;

public abstract class Player {
    char xORo;

    public abstract void move(char[][] field);

    public ArrayList<Coordinates> getFreeCells(char[][] field){
        ArrayList<Coordinates> list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    list.add(new Coordinates(i, j));
                }
            }
        }
        return list;
    }
}
