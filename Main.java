package paket1;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a mode:");
        String string = scanner.nextLine();
        switch (string) {
            case "encode":
                encode();
                break;
            case "send":
                send();
                break;
            case "decode":
                decode();
                break;
        }
    }

    static void decode() {
        StringBuilder inputBinStr = new StringBuilder();
        byte[] bufer = readFile("received.txt");

        for (byte b: bufer
        ) {
            inputBinStr.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        StringBuilder decodedStr = new StringBuilder();
        for (int i = 0; i < inputBinStr.length(); i += 8) {
            StringBuilder subStr = new StringBuilder(inputBinStr.substring(i, i + 8));
            int misIndex = -1;
            boolean misParity1 = ((int) subStr.charAt(2) + (int) subStr.charAt(4) + (int) subStr.charAt(6)) % 2 != Character.getNumericValue(subStr.charAt(0));
            boolean misParity2 = ((int) subStr.charAt(2) + (int) subStr.charAt(5) + (int) subStr.charAt(6)) % 2 != Character.getNumericValue(subStr.charAt(1));
            boolean misParity3 = ((int) subStr.charAt(4) + (int) subStr.charAt(5) + (int) subStr.charAt(6)) % 2 != Character.getNumericValue(subStr.charAt(3));

            if (misParity1 && misParity2 && misParity3) misIndex = 6;
            else if (misParity1 && misParity2) misIndex = 2;
            else if (misParity1 && misParity3) misIndex = 4;
            else if (misParity2 && misParity3) misIndex = 5;

            if (misIndex > 0) {
                char newChar = subStr.charAt(misIndex) == '0'?'1':'0';
                subStr.setCharAt(misIndex, newChar);
            }
            decodedStr.append(subStr.charAt(2));
            decodedStr.append(subStr.substring(4, 7));
        }

        writeFile(stringToByte(decodedStr.toString()), "decoded.txt");
    }

    static void encode() {
        StringBuilder inputBinStr = new StringBuilder();
        byte[] bufer = readFile("send.txt");

        for (byte b: bufer
        ) {
            inputBinStr.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        StringBuilder enecodedStr = new StringBuilder();
        for (int i = 0; i < inputBinStr.length(); i += 4) {
            String subStr = inputBinStr.substring(i, i + 4);
            int parity1 = ((int) subStr.charAt(0) + (int) subStr.charAt(1) + (int) subStr.charAt(3)) % 2;
            int parity2 = ((int) subStr.charAt(0) + (int) subStr.charAt(2) + (int) subStr.charAt(3)) % 2;
            int parity3 = ((int) subStr.charAt(1) + (int) subStr.charAt(2) + (int) subStr.charAt(3)) % 2;
            enecodedStr.append(parity1);
            enecodedStr.append(parity2);
            enecodedStr.append(subStr.charAt(0));
            enecodedStr.append(parity3);
            enecodedStr.append(subStr.substring(1, 4));
            enecodedStr.append("0");
        }
        writeFile(stringToByte(enecodedStr.toString()), "encoded.txt");
    }

    static byte[] stringToByte(String str) {
        int size = str.length() % 8 == 0?str.length() / 8:
                str.length() / 8 + 1;
        byte[] bytes = new byte[size];
        for (int i = 0, j = 0; i < str.length(); i += 8, j++) {
            String string = i + 8 > str.length()?str.substring(i,str.length()):
                    str.substring(i, i + 8);
            bytes[j] = (byte) (int)Integer.valueOf(string, 2);
        }
        return bytes;
    }

    static byte[] readFile(String fileName) {
        byte[] buffer = new byte[0];
        try (FileInputStream reader = new FileInputStream(new File(fileName))) {
            buffer = reader.readAllBytes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    static void writeFile(byte[] bytes, String fileName) {
        try (FileOutputStream writer = new FileOutputStream(new File(fileName))) {
            writer.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void send() {
        byte[] bytes = readFile("encoded.txt");
        int[] array = {1, 2, 4, 8, 16, 32, 64, 128};
        for (int i = 0; i < bytes.length; i++) {
            int random = new Random().nextInt(8);
            bytes[i] ^= array[random];
        }
        writeFile(bytes, "received.txt");
    }
}
