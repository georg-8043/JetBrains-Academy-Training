package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Person extends Record implements Serializable {
    private String surname;
    String birthDate;
    String gender;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate.isEmpty()?"[no data]":birthDate;
    }

    public void setBirthDate(String birthDate) {
        if (dateIsValif(birthDate)) {
            this.birthDate = birthDate;
        } else {
            System.out.println("Bad birth date!");
            this.birthDate = "";
        }
    }

    public String getGender() {
        return gender.isEmpty()?"[no data]":gender;
    }

    public void setGender(String gender) {
        if (genderIsValid(gender)) {
            this.gender = gender;
        } else {
            System.out.println("Bad gender!");
            this.gender = "";
        }
    }

    boolean genderIsValid(String gender) {
        return gender.matches("M|F");
    }

    boolean dateIsValif(String date) {
        return date.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}");
    }

    @Override
    void add() {
        Person person = new Person();
        System.out.println("Enter the name:");
        person.setName(scanner.nextLine());
        System.out.println("Enter the surname:");
        person.setSurname(scanner.nextLine());
        System.out.println("Enter the birth date:");
        person.setBirthDate(scanner.nextLine());
        System.out.println("Enter the gender (M, F):");
        person.setGender(scanner.nextLine());
        System.out.println("Enter the number:");
        person.setNumber(scanner.nextLine());
        Controller.list.add(person);
        person.timeCreated = LocalDateTime.now().withNano(0);
        person.timeEdit = LocalDateTime.now().withNano(0);
        System.out.println("The record added.");
    }

    @Override
    void edit(int index) {
        Person person = (Person) Controller.list.get(index);
            System.out.println("Select a field (name, surname, birth, gender, number):");
            String choice = scanner.nextLine();
            switch (choice) {
                case "name":
                    System.out.println("Enter name:");
                    person.setName(scanner.nextLine());
                    break;
                case "surname":
                    System.out.println("Enter surname:");
                    person.setSurname(scanner.nextLine());
                    break;
                case "birth":
                    System.out.println("Enter birth date:");
                    person.setBirthDate(scanner.nextLine());
                    break;
                case "gender":
                    System.out.println("Enter gender:");
                    person.setGender(scanner.nextLine());
                    break;
                 case "number":
                    System.out.println("Enter number:");
                    person.setNumber(scanner.nextLine());
                    break;
                default:
                    System.out.println("No such field. Try again, or type \"quit\" to exit");
                    break;
            }
           person.timeEdit = LocalDateTime.now().withNano(0);
        System.out.println("Saved");
            info(index);
    }

    @Override
    void info(int index) {
        Person person = (Person) Controller.list.get(index);
        System.out.println("Name: " + person.getName());
        System.out.println("Surname: " + person.getSurname());
        System.out.println("Birth date: " + person.getBirthDate());
        System.out.println("Gender: " + person.getGender());
        System.out.println("Number: " + person.getNumber());
        System.out.println("Time created: " + person.timeCreated);
        System.out.println("Time last edit: " + person.timeEdit);
        Controller.record(index);
    }

    @Override
    String getAllFields() {
        String string = name + " " + surname + " " + birthDate + " " + gender + " " + number;
        return string;
    }

    @Override
    String getField(String field) {
        switch (field) {
            case "surName":
                return surname;
            default:
                return null;
        }
    }
}
