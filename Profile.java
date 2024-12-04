package Book;

import java.util.List;

public class Profile {

    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private List<String> phoneNumbers;

    //Constructor
    public Profile(String firstName, String lastName, int age, String address, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }
    public void updateProfile (String firstName, String lastName, int age, String address, List<String> phoneNumbers){
        if (firstName != null) this.firstName = firstName;
        if (lastName != null) this.lastName = lastName;
        if (age != 0) this.age = age;
        if (address != null) this.address = address;
        if (phoneNumbers != null) this.phoneNumbers = phoneNumbers;

    }

    @Override
    public String toString() {
        return "Book.Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}