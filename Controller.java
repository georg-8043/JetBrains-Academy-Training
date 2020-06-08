package paket1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    static String foundText;
    static ArrayList<Integer> list = new ArrayList();
    static boolean flag;
    static boolean regexFlag;
    static JFileChooser fileChooser = new JFileChooser("./");
    static int index = -1;
    static int matcherLength;

    static ActionListener loadFile = event -> {
       int ret = fileChooser.showDialog(null, "Load file");
       if (ret == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           try (FileInputStream reader = new FileInputStream(file)){
               TextEditor.textArea.setText(new String(reader.readAllBytes()));
               TextEditor.textArea.setCaretPosition(0);
           } catch (FileNotFoundException e) {
               TextEditor.textArea.setText(null);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    };

   static ActionListener saveFile = event -> {
       int ret = fileChooser.showDialog(null, "Save file");
       if (ret == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           TextEditor.text = TextEditor.textArea.getText();
           try (FileOutputStream writer = new FileOutputStream(file)) {
               byte[] buffer = TextEditor.text.getBytes();

               writer.write(buffer, 0, buffer.length);
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
       }
    };

   static ActionListener searchButton = event -> {
       foundText = regexFlag?TextEditor.textField.getText():getPlainText();
       list.clear();
       index = 0;
       try {
           new Background().doInBackground();
       } catch (Exception e) {
           e.printStackTrace();
       }
   };

   static ActionListener searchRegexMenu = event -> {
           foundText = TextEditor.textField.getText();
           list.clear();
           index = 0;
           try {
               new Background().doInBackground();
           } catch (Exception e) {
               e.printStackTrace();
           }
 };

   static ActionListener upButton = event -> {
       if (index > 0) {
           index--;
           cursor(list.get(index));
       } else if (index == 0) {
           index = list.size() - 1;
           cursor(list.get(index));
       }
   };

   static ActionListener downButton = event -> {
       if (list.size() > 0 && index == list.size() - 1) {
           try {
               new Background().doInBackground();
           } catch (Exception e) {
               e.printStackTrace();
           }
           if (flag) {
               index++;
           } else {
               index = 0;
               cursor(list.get(index));
           }
       } else if (list.size() > 0 && index < list.size() - 1) {
           index++;
           cursor(list.get(index));
       }
   };

   static void search() {
       Pattern pattern = Pattern.compile(foundText, Pattern.CASE_INSENSITIVE);
       String text = TextEditor.textArea.getText();
       Matcher matcher = pattern.matcher(text);
       if (list.size() > 0) {
           flag = matcher.find(list.get(index) + matcherLength);
       } else {
           flag = matcher.find();
       }
       if (flag) {
           int start = matcher.start();
           matcherLength = matcher.end() - start;
           list.add(start);
           cursor(start);
       }
   }

   static String getPlainText() {
       StringBuilder plainText = new StringBuilder();
       for (int i = 0; i < TextEditor.textField.getText().length(); i++) {
           String x = String.valueOf(TextEditor.textField.getText().charAt(i));
           if (!x.matches("[\\h]") && x.matches("[\\W]")) {
               plainText.append('\\');
           }
           plainText.append(x);
       }
       return plainText.toString();
   }

   static void cursor(int start) {
       TextEditor.textArea.setCaretPosition(start + matcherLength);
       TextEditor.textArea.select(start, start + matcherLength);
       TextEditor.textArea.grabFocus();
   }

   static ImageIcon newImageIcon(String file) {
        ImageIcon imageIcon = new ImageIcon(file);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }

    static private class Background extends SwingWorker {

        @Override
        protected Object doInBackground() throws Exception {
            search();
            return null;
        }
    }

}
