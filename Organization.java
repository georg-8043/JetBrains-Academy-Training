package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Organization extends Record implements Serializable {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    void add() {
        Organization organization = new Organization();
        System.out.println("Enter the organization name:");
        organization.setName(scanner.nextLine());
        System.out.println("Enter the address:");
        organization.setAddress(scanner.nextLine());
        System.out.println("Enter the number: ");
        organization.setNumber(scanner.nextLine());
        Controller.list.add(organization);
        organization.timeCreated = LocalDateTime.now().withNano(0);
        organization.timeEdit = LocalDateTime.now().withNano(0);
        System.out.println("The record added.");
    }

    @Override
    void edit(int index) {
        Organization organization = (Organization) Controller.list.get(index);
            System.out.println("Select a field (name, address, number):");
            String choice = scanner.nextLine();
            switch (choice) {
                case "name":
                    System.out.println("Enter name:");
                    organization.setName(scanner.nextLine());
                    break;
                case "address":
                    System.out.println("Enter address:");
                    organization.setAddress(scanner.nextLine());
                    break;
                case "number":
                    System.out.println("Enter number:");
                    organization.setNumber(scanner.nextLine());
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("No such field. Try again, or type \"quit\" to exit");
                    break;
            }
            organization.timeEdit = LocalDateTime.now().withNano(0);
        System.out.println("Saved");
        info(index);
    }

    @Override
    void info(int index) {
        Organization organization = (Organization) Controller.list.get(index);
        System.out.println("Organization name: " + organization.getName());
        System.out.println("Address: " + organization.getAddress());
        System.out.println("Number: " + organization.getNumber());
        System.out.println("Time created: " + organization.timeCreated);
        System.out.println("Time last edit: " + organization.timeEdit);
        Controller.record(index);
    }

    @Override
    String getAllFields() {
        String string = name + " " + address + " " + number;
        return string;
    }

    @Override
    String getField(String field) {
        return null;
    }
}
