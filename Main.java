package paket1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static String inputFile = "";
    static String outputFile = "";
    static String resultString = "";
    static Scanner scanner = new Scanner(System.in);

    static void print() {
        if (outputFile.isEmpty()) {
            System.out.println(resultString);
        } else {
            try (FileWriter writer = new FileWriter(new File(outputFile))) {
                writer.write(resultString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void caseLong(String sortingType) {
        ArrayList<Long> list = new ArrayList();
        while (scanner.hasNext()) {
            String string = scanner.next();
            try {
                long num = Long.parseLong(string);
                list.add(num);
            } catch (NumberFormatException e) {
                System.out.println("\"" + string + "\"" + " isn't a long. It's skipped.");
            }
        }

        switch (sortingType) {
            case "natural":
                Collections.sort(list);
                resultString = "Total numbers: " + list.size() + "\n" + "Sorted data: ";
                for (long l: list
                     ) {
                    resultString += l + " ";
                }
                print();
                break;
            case "byCount":
                Map<Long, Integer> map = new HashMap<>();
                for (long c: list
                ) {
                    if (map.containsKey(c)) {
                        map.put(c, map.get(c) + 1);
                    } else {
                        map.put(c, 1);
                    }
                }
                List<Map.Entry<Long, Integer>> listMap = new ArrayList<>(map.entrySet());
                Collections.sort(listMap, new Comparator<Map.Entry<Long, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                        int result = o1.getValue() - o2.getValue();
                        return result == 0?(o1.getKey().compareTo(o2.getKey())):result;
                    }
                });
                resultString = "Total numbers: " + list.size() + ".\n";
                for (Map.Entry<Long, Integer> v: listMap
                     ) {
                   resultString += v.getKey() + ": " + v.getValue() +
                            " time(s), " + getPercent(list.size(), v.getValue()) + "%\n";
                }
                print();
                break;
            default:
                Collections.sort(list);
                resultString = "Total numbers: " + list.size() + "\n" + "Sorted data: ";
                for (long l: list
                ) {
                    resultString += l + " ";
                }
                print();
                break;
        }
    }

    static void caseWord(String sortingType) {
        ArrayList<String> list = new ArrayList();
        while (scanner.hasNext()) {
            list.add(scanner.next());
        }
        switch (sortingType) {
            case "natural":
                Collections.sort(list);
                resultString = "Total words: " + list.size() + "\n" + "Sorted data: ";
                for (String l: list
                ) {
                    resultString += l + " ";
                }
                print();
                break;
            case "byCount":
                Map<String, Integer> map = new HashMap<>();
                for (String c: list
                ) {
                    if (map.containsKey(c)) {
                        map.put(c, map.get(c) + 1);
                    } else {
                        map.put(c, 1);
                    }
                }
                List<Map.Entry<String, Integer>> listMap = new ArrayList<>(map.entrySet());
                Collections.sort(listMap, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        int result = o1.getValue() - o2.getValue();
                        return result == 0?(o1.getKey().compareTo(o2.getKey())) :result;
                    }
                });
                resultString = "Total words: " + list.size() + ".\n";
                for (Map.Entry<String, Integer> v: listMap
                ) {
                    resultString += v.getKey() + ": " + v.getValue() +
                            " time(s), " + getPercent(list.size(), v.getValue()) + "%\n";
                }
                print();
                break;
            default:
                Collections.sort(list);
                resultString = "Total words: " + list.size() + "\n" + "Sorted data: ";
                for (String l: list
                ) {
                    resultString += l + " ";
                }
                print();
                break;
        }
    }

    static void caseLine(String sortingType) {
        ArrayList<String> list = new ArrayList();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }

        switch (sortingType) {
            case "natural":
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.length() - o1.length();
                    }
                });
                resultString = "Total lines: " + list.size() + "\n" + "Sorted data: \n";
                for (String l: list
                ) {
                    resultString += l + "\n";
                }
                print();
                break;
            case "byCount":
                Map<String, Integer> map = new HashMap<>();
                for (String c: list
                ) {
                    if (map.containsKey(c)) {
                        map.put(c, map.get(c) + 1);
                    } else {
                        map.put(c, 1);
                    }
                }
                List<Map.Entry<String, Integer>> listMap = new ArrayList<>(map.entrySet());
                Collections.sort(listMap, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        int friquency = o1.getValue() - o2.getValue();
                        int length = o2.getKey().length() - o1.getKey().length();
                        int hashCode = o1.getKey().hashCode() - o2.getKey().hashCode();
                        return friquency == 0?(length == 0?hashCode:length):friquency;
                    }
                });
                resultString = "Total lines: " + list.size() + ".\n";
                for (Map.Entry<String, Integer> v: listMap
                ) {
                    resultString += v.getKey() + ": " + v.getValue() +
                            " time(s), " + getPercent(list.size(), v.getValue()) + "%\n";
                }
                print();
                break;
            default:
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.length() - o1.length();
                    }
                });
                resultString = "Total lines: " + list.size() + "\n" + "Sorted data: \n";
                for (String l: list
                ) {
                    resultString += l + "\n";
                }
                print();
                break;
        }

    }

    static int getPercent(int size, int count) {
        return (int) Math.round((double) count / (double) size * 100);
    }


    public static void main(final String[] args) {
        String dataType = "";
        String sortingType = "";
        for (int i = 0; i < args.length; i++) {
            if (!args[i].matches("-dataType||-sortingType||long||word||line||" +
                    "byCount||natural||-inputFile||-outputFile||.+\\..+")) {
                System.out.println("\"" + args[i] + "\"" + " isn't a valid parameter. It's skipped.");
            }
            if (args[i].equals("-dataType")) {
                try {
                    dataType = args[i + 1];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No data type defined!");
                    return;
                }
            }
            if (args[i].equals("-sortingType")) {
                try {
                    sortingType = args[i + 1];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No sorting type defined!");
                    return;
                }
            }
            if (args[i].equals("-inputFile")) {
                try {
                    inputFile = args[i + 1];
                    scanner = new Scanner(new File(inputFile));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No input file defined!");
                    return;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                    return;
                }
            }
            if (args[i].equals("-outputFile")) {
                try {
                    outputFile = args[i + 1];
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No output file defined!");
                    return;
                }
            }
        }

        switch (dataType) {
            case "long":
                caseLong(sortingType);
                break;
            case "line":
                caseLine(sortingType);
                break;
            case "word":
                caseWord(sortingType);
                break;
            default:
                caseWord(sortingType);
        }
        scanner.close();
    }
}
