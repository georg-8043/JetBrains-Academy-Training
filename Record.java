package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Record implements Serializable {
    transient static Scanner scanner = new Scanner(System.in);
    String name;
    String number;
    LocalDateTime timeCreated;
    LocalDateTime timeEdit;

    abstract String getAllFields();

    abstract String getField(String field);

    abstract void add();

    abstract void edit(int index);

    abstract void info(int index);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        if (phoneValidity(number) && !number.isEmpty()) {
            this.number = number;
        } else {
            System.out.println("Wrong number format!");
            this.number = "";
        }
    }

    public String getNumber() {
        return number.isEmpty()?"[no number]": number;
    }

    boolean phoneValidity(String phone) {
        return phone.matches("(\\+?\\w+)?([-\\s]\\(\\w{2,}\\))?([-\\s]\\w{2,})*|(\\w+([-\\s]\\w{2,})*)|(\\+?\\(\\w+\\))([-\\s]\\w{2,})*");
    }
}
