package paket1;

import java.io.IOException;
import java.util.Scanner;

public class Converter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String radixStr1 = scanner.next();
        if (!radixStr1.matches("\\d+")) {
            printError();
        }
        int radix1 = Integer.parseInt(radixStr1);
        if (radix1 > 36 || radix1 <= 0) {
            printError();
        }
        String strNum = scanner.next();
        if (!strNum.matches(".*[a-z]+.*\\..*|.*\\..*[a-z]+.*|\\d+.\\d+|\\d+")) {
            printError();
        }
        long num;
        long numDecinal = 0;
        if (strNum.matches(".*[a-z]+.*\\..*|.*\\..*[a-z]+.*")) {
            String alphaBet = "0123456789abcdefghijklmnopqrstuvwxyz";
            String str = strNum.substring(0, strNum.indexOf("."));
            num = 0;
            for (int i = str.length() - 1, j = 0; i >= 0; i--, j++) {
                num += alphaBet.indexOf((str.charAt(i)) ) * Math.pow(radix1, j);
                numDecinal = radix1 == 1?Long.toString(num).length():num;
            }
        } else {
            num = (long) Double.parseDouble(strNum);
            numDecinal = radix1 == 1?Long.toString(num).length():Long.parseLong(Long.toString(num), radix1);
        }
        String radixStr2 = scanner.next();
        if (!radixStr2.matches("\\d+")) {
            printError();
        }
        int radix2 = Integer.parseInt(radixStr2);
        if (radix2 > 36 || radix2 <= 0) {
            printError();
        }
        if (radix2 == 1) {
            for (int i = 0; i < numDecinal; i++) {
                System.out.print(1);
            }
        }
        else {
            System.out.println(Long.toString(numDecinal, radix2) + convertFractional(strNum, radix1, radix2));
        }
    }

    static void printError() {
        System.out.println("Input error");
        System.exit(0);
    }

    static String convertFractional(String num, int radix1, int radix2) {
        if (!num.contains(".")) {
            return "";
        }
        StringBuilder numStr = new StringBuilder(num);
        String alphaBet = "abcdefghijklmnopqrstuvwxyz";
        numStr.delete(0, numStr.indexOf(".") + 1);
        double a = 0;
        double sum = 0;
        for (int i = 0; i < numStr.length(); i++) {
            if (alphaBet.contains(Character.toString(numStr.charAt(i)))) {
                a = alphaBet.indexOf(numStr.charAt(i)) + 10;
            }
            else {
                a = Integer.parseInt(Character.toString(numStr.charAt(i)));
            }
            sum += a / Math.pow(radix1, i + 1);
        }
        StringBuilder numStrNew = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sum *= radix2;
            if ((int)sum < 10) {
                numStrNew.append((int)sum);
            }
            else numStrNew.append(alphaBet.charAt((int)sum - 10));
            sum -= (int)sum;
        }
        return "." + numStrNew.toString();
    }

}
