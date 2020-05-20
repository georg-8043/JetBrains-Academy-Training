package contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller implements Serializable {
    private static final long serialVersionUID = 1L;
    static ArrayList<Record> list;
    transient static Scanner scanner = new Scanner(System.in);
    String fileName;

    public Controller() throws IOException {
        this.list = new ArrayList<>();
        this.fileName = "phonebook.db";
        action();
    }

    public Controller(String fileName) throws IOException {
        try {
            this.list = (ArrayList<Record>) deserialize(fileName);
            this.fileName = fileName;
        } catch (IOException e) {
            this.list = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            this.list = new ArrayList<>();
        } finally {
            this.fileName = fileName;
            action();
        }
    }

    void action() throws IOException {
        while (true) {
            System.out.println("\n[menu] Enter action (add, list, search, count, exit):");
            String string = scanner.nextLine();
            switch (string) {
                case "add":
                    add();
                    break;
                case "list":
                    list();
                    break;
                case "search":
                    search();
                    break;
                case "count":
                    count();
                    break;
                case "exit":
                    serialize(list, fileName);
                    System.exit(0);
            }
        }
    }

    void search() {
        while (true) {
            System.out.println("Enter search query:");
            String search = scanner.nextLine().toLowerCase();
            ArrayList<Integer> searchList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAllFields().toLowerCase().contains(search)) {
                    searchList.add(i);
                }
            }
            System.out.println("Found " + searchList.size() + " results:");
            for (int i = 0; i < searchList.size(); i++) {
                if (list.get(searchList.get(i)).getClass().getSimpleName().equals("Person")) {
                    System.out.println((i + 1) + " " + list.get(searchList.get(i)).getName() + " " + list.get(searchList.get(i)).getField("surName"));
                } else {
                    System.out.println((i + 1) + " " + list.get(searchList.get(i)).getName());
                }
            }
            System.out.println("\n[search] Enter action ([number], back, again): ");
            String choice = scanner.nextLine();
            if (choice.matches("[]0-9+]")) {
                int index = searchList.get(Integer.parseInt(choice) - 1);
                list.get(index).info(index);
                return;
            } else if (choice.equals("again")) {
                continue;
            } else {
                return;
            }
        }
    }

    void list() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getName());
        }
        System.out.println("\n[list] Enter action ([number], back):");
        String choice = scanner.nextLine();
        if (choice.matches("[0-9]+")) {
            int index = Integer.parseInt(choice) - 1;
            list.get(index).info(index);
        } else {
            return;
        }
    }

   static void record(int index) {
       System.out.println("\n[record] Enter action (edit, delete, menu):");
       String choice = scanner.nextLine();
       switch (choice) {
           case "edit":
               list.get(index).edit(index);
               break;
           case "delete":
               list.remove(index);
               break;
           case "menu":
               return;
       }
   }

    int recordChoice(String string, ArrayList list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getClass().getSimpleName()) {
                    case "Person":
                        Person person = (Person) list.get(i);
                        System.out.println((i + 1) + ". " + person.getName());
                        break;
                    case "Organization":
                        Organization organization = (Organization) list.get(i);
                        System.out.println((i + 1) + ". " + organization.getName());
                        break;
                }
            }
        } else {
            return -1;
        }
        System.out.println(string);
        int index = Integer.parseInt(scanner.nextLine());
        if (index > list.size() || index <= 0) {
            System.out.println("No such record");
            return -1;
        } else {
            return index - 1;
        }
    }

    void count() {
        System.out.println("The Phone Book has " + list.size() + " records.");
    }

    void add() {
        System.out.println("Enter the type (person, organization):");
        switch (scanner.nextLine().toLowerCase()) {
            case "person":
                new Person().add();
                break;
            case "organization":
                new Organization().add();
                break;
        }

    }

    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
 }
