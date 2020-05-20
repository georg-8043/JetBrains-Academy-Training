package contacts;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length == 0) {
            new Controller();
        } else {
            System.out.println(args[0]);
            new Controller(args[0]);
        }
    }
}
