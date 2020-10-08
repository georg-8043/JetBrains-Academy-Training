package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player{
    boolean xWins;
    boolean oWins;
    String level;

    public Computer(char xORo, String level) {
        this.xORo = xORo;
        this.level = level;
    }

    public void easyMove(char[][] field) {
        System.out.println("Making move level \"easy\"");
        Coordinates move = randomMove(field);
        field[move.y][move.x] = xORo;
    }

    public void mediumMove(char[][] field) {
        System.out.println("Making move level \"medium\"");
        Coordinates move = bestMove(field);
        if (move != null) {
            field[move.y][move.x] = xORo;
        } else {
            move = randomMove(field);
            field[move.y][move.x] = xORo;
        }
    }

    public void hardMove(char[][] field) {
        System.out.println("Making move level \"hard\"");
        ArrayList<Coordinates> list = getFreeCells(field);
        Coordinates bestMove = null;
        int bestvalue = -10;
        for (Coordinates c: list
             ) {
            field[c.y][c.x] = xORo;
            char xORoNew = xORo == 'X'?'O':'X';
            int value = miniMax(field, xORoNew, false, 0);
            if (value > bestvalue) {
                bestvalue = value;
                bestMove = c;
            }
            field[c.y][c.x] = ' ';
        }

        field[bestMove.y][bestMove.x] = xORo;
    }

    public int miniMax(char[][] field, char xORo, boolean isMaximizing, int depth) {
        int bestValue;
        analyzeField(field);
        if ((xWins || oWins) && !isMaximizing) {
            xWins = false;
            oWins = false;
            return 10 - depth;
        }
        analyzeField(field);
        if ((xWins || oWins) && isMaximizing) {
            xWins = false;
            oWins = false;
            return -10 + depth;
        }

        ArrayList<Coordinates> list = getFreeCells(field);
        if (list.size() == 0) return 0;

        if (isMaximizing) {
            bestValue = -100;
            for (Coordinates c: list
                 ) {
                field[c.y][c.x] = xORo;
                int value = miniMax(field, xORo == 'X'?'O':'X', false, depth + 1);
                bestValue = Math.max(value, bestValue);
                field[c.y][c.x] = ' ';
            }
        } else {
            bestValue = 100;
            for (Coordinates c: list) {
                field[c.y][c.x] = xORo;
                int value = miniMax(field, xORo == 'X'?'O':'X', true, depth + 1);
                bestValue = Math.min(value, bestValue);
                field[c.y][c.x] = ' ';
            }
        }
        return bestValue;
    }

    public Coordinates bestMove(char[][] field) {
        xWins = false;
        oWins = false;
        ArrayList<Coordinates> list = getFreeCells(field);
        //checking if computer can wins
        for (Coordinates c: list
             ) {
            field[c.y][c.x] = xORo;
            analyzeField(field);
            field[c.y][c.x] = ' ';
            if (xWins || oWins) return c;
        }
        //checking if opponent can wins
        char xORoTmp = xORo == 'X'?'O':'X';
        for (Coordinates c: list
        ) {
            field[c.y][c.x] = xORoTmp;
            analyzeField(field);
            field[c.y][c.x] = ' ';
            if (xWins || oWins) return c;
        }
        return null;
    }

    public Coordinates randomMove(char[][] field) {
        ArrayList<Coordinates> list = getFreeCells(field);
        int random = new Random().nextInt(list.size());
        return list.get(random);
    }

    @Override
    public void move(char[][] field) {
        switch (level) {
            case "easy": easyMove(field);
            break;
            case "medium": mediumMove(field);
            break;
            case "hard": hardMove(field);
            break;
        }
    }

    void analyzeField(char[][] field) {
        String[] strings = new String[8];
        strings[0] = field[0][0] + "" + field[0][1] + field[0][2];
        strings[1] = field[1][0] + "" + field[1][1] + field[1][2];
        strings[2] = field[2][0] + "" + field[2][1] + field[2][2];
        strings[3] = field[0][0] + "" + field[1][0] + field[2][0];
        strings[4] = field[0][1] + "" + field[1][1] + field[2][1];
        strings[5] = field[0][2] + "" + field[1][2] + field[2][2];
        strings[6] = field[0][0] + "" + field[1][1] + field[2][2];
        strings[7] = field[0][2] + "" + field[1][1] + field[2][0];
        for (String s: strings
        ) {
            if (s.equals("XXX")) {
                xWins = true;
            }
            if (s.equals("OOO")) {
                oWins = true;
            }
        }
    }
}
