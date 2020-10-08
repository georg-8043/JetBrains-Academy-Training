package TicTacToe;

public class Game {
     char[][] field;
     boolean xWins;
     boolean oWins;
     boolean isFinished;
     Player one;
     Player two;

    public Game(Player one, Player two) {
        this.one = one;
        this.two = two;
        field = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = ' ';
            }
        }
        print();
        while (true) {
            one.move(field);
            print();
            analyzeField();
            analyzeGame();
            if (isFinished) break;
            two.move(field);
            print();
            analyzeField();
            analyzeGame();
            if (isFinished) break;
        }
    }

    void print() {
        System.out.println("---------");
        System.out.println("| " + field[0][0] + " " + field[0][1] + " " + field[0][2] + " |");
        System.out.println("| " + field[1][0] + " " + field[1][1] + " " + field[1][2] + " |");
        System.out.println("| " + field[2][0] + " " + field[2][1] + " " + field[2][2] + " |");
        System.out.println("---------");
    }

    void analyzeGame() {
        if (xWins)  {
            System.out.println("X wins");
            isFinished = true;
        } else if (oWins) {
            System.out.println("O wins");
            isFinished = true;
        } else if (one.getFreeCells(field).size() == 0) {
            System.out.println("Draw");
            isFinished = true;
        }
    }

     void analyzeField() {
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
            if (s.equals("XXX")) xWins = true;
            if (s.equals("OOO")) oWins = true;
        }
    }
}
