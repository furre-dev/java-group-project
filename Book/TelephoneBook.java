package Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelephoneBook {

    private List<Profile> profiles;

    public TelephoneBook() {
        this.profiles = new ArrayList<>();
    }

    // Add a new profile to the telephone book
    public void addProfile(Profile profile) {
        profiles.add(profile);
        System.out.println("Profile added successfully!");
    }

    // Search for a profile by first name
    public Profile searchProfile(String firstName) {
        for (Profile profile : profiles) {
            if (profile.toString().contains(firstName)) {
                return profile;
            }
        }
        return null;
    }

    // Update an existing profile in the telephone book
    public void updateProfile(String firstName, String lastName, int age, String address, List<String> phoneNumbers) {
        for (Profile profile : profiles) {
            if (profile.toString().contains(firstName)) {
                profile.updateProfile(firstName, lastName, age, address, phoneNumbers);
                System.out.println("Profile updated successfully!");
                return;
            }
        }
        System.out.println("Profile not found!");
    }

    // Display all profiles (only for admin)
    public void displayAllProfiles() {
        if (profiles.isEmpty()) {
            System.out.println("No profiles available in the telephone book.");
        } else {
            for (Profile profile : profiles) {
                System.out.println(profile);
            }
        }
    }

    public static void main(String[] args) {
        TelephoneBook telephoneBook = new TelephoneBook();
        Authentication auth = new Authentication();

        Tenant tenant = auth.login();
        Scanner sc = new Scanner(System.in);

        if (tenant.isAdmin()) {
            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Search Profile");
                System.out.println("3. Update Profile");
                System.out.println("4. Display All Profiles");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                int choice = getValidInt(sc, "Enter a valid choice (1-5):");

                switch (choice) {
                    case 1:
                        String firstName = getValidString(sc, "Enter first name: ");
                        String lastName = getValidString(sc, "Enter last name: ");
                        int age = getValidInt(sc, "Enter age (a positive number):", 1);
                        String address = getValidString(sc, "Enter address: ");
                        System.out.print("Enter phone numbers (comma-separated): ");
                        String phoneNumbersStr = sc.nextLine();
                        while (phoneNumbersStr.isEmpty()) {
                            System.out.print("Please enter valid phone numbers (comma-separated): ");
                            phoneNumbersStr = sc.nextLine();
                        }
                        List<String> phoneNumbers = List.of(phoneNumbersStr.split(","));
                        Profile newProfile = new Profile(firstName, lastName, age, address, phoneNumbers);
                        telephoneBook.addProfile(newProfile);
                        break;

                    case 2:
                        String searchName = getValidString(sc, "Enter the first name to search: ");
                        Profile foundProfile = telephoneBook.searchProfile(searchName);
                        if (foundProfile != null) {
                            System.out.println("Profile found: " + foundProfile);
                        } else {
                            System.out.println("Profile not found!");
                        }
                        break;

                    case 3:
                        String updateName = getValidString(sc, "Enter the first name of the profile to update: ");
                        String newLastName = getValidString(sc, "Enter new last name (leave blank to skip): ", true);
                        int newAge = getValidInt(sc, "Enter new age (0 to skip):", 0);
                        String newAddress = getValidString(sc, "Enter new address (leave blank to skip): ", true);
                        System.out.print("Enter new phone numbers (comma-separated, leave blank to skip): ");
                        String newPhoneNumbersStr = sc.nextLine();
                        List<String> newPhoneNumbers = newPhoneNumbersStr.isEmpty() ? null : List.of(newPhoneNumbersStr.split(","));
                        telephoneBook.updateProfile(updateName, newLastName, newAge, newAddress, newPhoneNumbers);
                        break;

                    case 4:
                        telephoneBook.displayAllProfiles();
                        break;

                    case 5:
                        System.out.println("Quitting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } else {
            while (true) {
                System.out.println("\nGuest Menu:");
                System.out.println("1. Search Profile");
                System.out.println("2. Quit");
                System.out.print("Enter your choice: ");
                int choice = getValidInt(sc, "Enter a valid choice (1-2):");

                switch (choice) {
                    case 1:
                        String searchName = getValidString(sc, "Enter the first name to search: ");
                        Profile foundProfile = telephoneBook.searchProfile(searchName);
                        if (foundProfile != null) {
                            System.out.println("Profile found: " + foundProfile);
                        } else {
                            System.out.println("Profile not found!");
                        }
                        break;

                    case 2:
                        System.out.println("Quitting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }

    // Helper function to get a valid integer input
    private static int getValidInt(Scanner sc, String prompt) {
        return getValidInt(sc, prompt, Integer.MIN_VALUE);
    }

    private static int getValidInt(Scanner sc, String prompt, int min) {
        int result;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                result = sc.nextInt();
                sc.nextLine(); // Consume newline
                if (result >= min) {
                    return result;
                }
            } else {
                sc.nextLine(); // Consume invalid input
            }
            System.out.println("Please enter a valid number" + (min > Integer.MIN_VALUE ? " greater than or equal to " + min : "") + ".");
        }
    }

    // Helper function to get a valid string input
    private static String getValidString(Scanner sc, String prompt) {
        return getValidString(sc, prompt, false);
    }

    private static String getValidString(Scanner sc, String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (!input.isEmpty() || allowEmpty) {
                return input;
            }
            System.out.println("Please enter a valid input.");
        }
    }
}