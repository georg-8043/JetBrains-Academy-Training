package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {
    static HashMap<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            if (string.matches("\\s*[-+]?[]0-9]+\\s*")) {
                System.out.println(Integer.parseInt(string.trim()));
                continue;
            }
            if (string.matches("[a-zA-Z]+\\s*=\\s*[-+]*(\\d+|[A-Za-z]+)")) {
                mapFilling(string);
            } else if (string.matches("[a-zA-Z]+[0-9]+\\s*=\\s*[-+]*\\d+")) {
                System.out.println("Invalid identifier");
            }
            else if (string.matches("/\\w*")) {
                commandProcessing(string);
            } else if (string.matches("[a-zA-Z]+")) {
                System.out.println(getVariable(string));
            } else if (string.matches("[()]*[+-]?\\w+\\s*(\\s*[()]*\\s*([-+]*[*/]?){1}\\s*[()]*\\s*[+-]?\\w+\\s*[()]*\\s*)+")) {
                if (validBrackets(string)) {
                    System.out.println(calc(string));
                } else {
                    System.out.println("Invalid expression");
                }
            } else if (string.matches("[+-]?\\w+(\\s*[^+-]+\\s*[+-]?\\w+)+")) {
                System.out.println("Invalid expression");
            } else {
                continue;
            }
        }
    }

    static String calc(String string) {
        string = string.replaceAll("\\++", " + ")
                .replaceAll("\\s+\\-{2}\\s+", " + ")
                .replaceAll("\\-+", " - ")
                .replaceAll("\\*", " * ")
                .replaceAll("/", " / ")
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .replaceAll("\\s+", " ").trim();
        Deque<String> stack = new LinkedList<>();
        String[] strArr = string.split(" ");
        String reversePolishNotation = "";
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(")")) {
                while (true){
                    if (stack.isEmpty() || stack.peekFirst().equals("(")) {
                        break;
                    }
                    reversePolishNotation += stack.pollFirst() + " ";
                }
                stack.pollFirst();
                if (i == strArr.length - 1 && !stack.isEmpty()) {
                    while (!stack.isEmpty()) {
                        reversePolishNotation += stack.pollFirst() + " ";
                    }
                    continue;
                }
            } else if (i == strArr.length - 1) {
                if (strArr[i].matches("[A-Za-z]+")) {
                    strArr[i] = map.get(strArr[i]);
                }
                reversePolishNotation += strArr[i] + " ";
                while (!stack.isEmpty()){
                    reversePolishNotation += stack.pollFirst() + " ";
                }
                continue;
            } else if (strArr[i].matches("\\d+")) {
                reversePolishNotation += strArr[i] + " ";
            } else if (strArr[i].matches("[A-Za-z]+")) {
                reversePolishNotation += map.get(strArr[i])  + " ";
            } else if (strArr[i].equals("(")) {
                stack.offerFirst(strArr[i]);
            } else if (strArr[i].matches("\\+|\\-|\\*|/")) {
                if (stack.isEmpty() || stack.peekFirst().equals("(") || getPriority(strArr[i]) > getPriority(stack.peekFirst())) {
                    stack.offerFirst(strArr[i]);
                } else {
                    while (true){
                        if (stack.isEmpty() || stack.peekFirst().equals("(")) {
                            break;
                        }
                        reversePolishNotation += stack.pollFirst() + " ";
                    }
                    stack.offerFirst(strArr[i]);
                }
            } else {
                System.out.println("Invalid expression");
            }
        }
        Queue<String> queue = new LinkedList<>(Arrays.asList(reversePolishNotation.trim().split(" ")));
        while (!queue.isEmpty()) {
            String str = queue.poll();
            if (str.matches("\\d+")) {
                stack.offerFirst(str);
            } else {
                BigInteger arg2 = new BigInteger(stack.pollFirst());
                BigInteger arg1 = new BigInteger(stack.pollFirst());
                stack.offerFirst(arithmeticProcessing(arg1, arg2, str));
            }
        }
        return stack.pollFirst();
    }

    static int getPriority(String str) {
        if (str.matches("\\*|/")) {
            return 2;
        } else {
            return 1;
        }
    }

    static String arithmeticProcessing(BigInteger arg1, BigInteger arg2, String operand) {
        if (operand.equals("+")) {
            return String.valueOf(arg1.add(arg2));
        }
        if (operand.equals("-")) {
            return String.valueOf(arg1.subtract(arg2));
        }
        if (operand.equals("*")) {
            return String.valueOf(arg1.multiply(arg2));
        }
        if (operand.equals("/")) {
            return String.valueOf(arg1.divide(arg2));
        }
        return null;
    }

    static boolean validBrackets(String string) {
        Deque stack = new LinkedList();
        for (int i = 0; i < string.length(); i++) {
            String bracket = string.substring(i, i + 1);
            if (bracket.matches("[\\(]")) {
                stack.offerFirst(bracket);
            }
            if (bracket.matches("[\\)]")) {
                bracket = bracket.replaceAll("\\)","(");
                if (stack.size() == 0) {
                    return false;
                } else {
                    if (stack.peekFirst().equals(bracket)) {
                        stack.pollFirst();
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.size() == 0;
    }

    static String getVariable(String string) {
        if (map.containsKey(string)) {
            return map.get(string).toString();
        } else {
            return "Unknown variable";
        }
    }

    static void commandProcessing(String str) {
        if (str.equals("/exit")) {
            System.out.println("Bye!");
            System.exit(0);
        }
        else if (str.equals("/help")) {
            System.out.println("The program performs addition and/or subtraction of numbers");
        }
        else {
            System.out.println("Unknown command");
        }
    }

    static void mapFilling(String string) {
        String[] strArr = string.replaceAll("\\s+", "").split("=");
        String sum;
        if (strArr[1].matches("[a-zA-Z]+")) {
            if (!map.containsKey(strArr[1])) {
                System.out.println("Unknown variable");
                return;
            }
            sum = map.get(strArr[1]);
        } else {
            sum = strArr[1];
        }
        map.put(strArr[0], sum);
    }
}
