package Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelephoneBook {

    private final List <Profile> profiles; // List to store profiles in the telephone book.

    public TelephoneBook() {
        this.profiles = new ArrayList<>(); // Initialize an empty list of profiles.
    }

    // Adds a new profile to the telephone book.
    public void addProfile(Profile profile) {
        profiles.add(profile); // Add the profile to the list.
        System.out.println("Profile added successfully!"); // Confirm the addition.
    }

    // Search by first name - returns all profiles with the matching first name.
    public List<Profile> searchByFirstName(String firstName) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(firstName)) { // Match on first name.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles;
    }

    // Search by last name - returns all profiles with the matching last name.
    public List<Profile> searchByLastName(String lastName) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(lastName)) { // Match on last name.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles;
    }

    // Search by address - returns all profiles on the same street.
    public List<Profile> searchByAddress(String address) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(address)) { // Match on address.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles;
    }

    // Free search - searches over all attributes for relevant information.
    public List<Profile> freeSearch(String query) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().toLowerCase().contains(query.toLowerCase())) { // Case-insensitive match.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles;
    }
    // Updates an existing profile in the telephone book.
    public void updateProfile(String firstName, String lastName, int age, String address, List<String> phoneNumbers) {
        for (Profile profile : profiles) { // Iterate over profiles.
            if (profile.toString().contains(firstName)) { // Check for matching first name.
                profile.updateProfile(firstName, lastName, age, address, phoneNumbers); // Call the Profile's method.
                System.out.println("Profile updated successfully!");
                return;
            }
        }
        System.out.println("Profile not found!"); // If no profile matches.
    }

    // Displays all profiles in the telephone book.
    public void displayAllProfiles() {
        if (profiles.isEmpty()) { // Check if the list is empty.
            System.out.println("No profiles available in the telephone book."); // Notify if no profiles exist.
        } else {
            for (Profile profile : profiles) { // Iterate and display each profile.
                System.out.println(profile);
            }
        }
    }

    // Helper method to display search results.
    public static void displaySearchResults(List<Profile> profiles, String searchType, String query) {
        if (profiles.isEmpty()) {
            System.out.println("No profiles found matching " + searchType + ": " + query);
        } else {
            System.out.println("Profiles found:");
            for (Profile profile : profiles) {
                System.out.println(profile);
            }
        }
    }

    public static void main(String[] args) {
        TelephoneBook telephoneBook = new TelephoneBook(); // Create a TelephoneBook instance.
        Authentication auth = new Authentication(); // Create an Authentication instance.

        Tenant tenant = auth.login(); // Authenticate the user.
        Scanner sc = new Scanner(System.in); // Scanner for user input.

        while (true) { // Continuous loop for admin or guest menu.
            if (tenant.isAdmin()) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Search Profiles");
                System.out.println("3. Update Profile");
                System.out.println("4. Display All Profiles");
                System.out.println("Type 'quit' to quit.");
            } else {
                System.out.println("\nGuest Menu:");
                System.out.println("1. Search Profiles");
                System.out.println("Type 'quit' to quit.");
            }

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().toLowerCase();

            if (choice.equals("quit")) { // Exit the program.
                System.out.println("Exiting...");
                sc.close();
                return;
            }

            switch (choice) {
                case "1": // Add Profile (Admin only) or Search Profiles.
                    if (tenant.isAdmin()) {
                        // Admin: Add Profile
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
                        Profile newProfile = new Profile(firstName, lastName, age, address, java.util.List.of(phoneNumbersStr.split(",")));
                        telephoneBook.addProfile(newProfile); // Add the profile.
                    } else {
                        // Guest or Admin: Search Profiles
                        displaySearchMenu(sc, telephoneBook); // Invoke the search menu.
                    }
                    break;

                case "2": // Search Profiles for Admin.
                    if (tenant.isAdmin()) {
                        displaySearchMenu(sc, telephoneBook);
                    } else {
                        System.out.println("Invalid choice! Only admins can add profiles.");
                    }
                    break;

                case "3": // Update Profile (Admin only).
                    if (tenant.isAdmin()) {
                        String updateName = getValidString(sc, "Enter the first name of the profile to update: ");
                        String newLastName = getValidString(sc, "Enter new last name (leave blank to skip): ", true);
                        int newAge = getValidInt(sc, "Enter new age (0 to skip):", 0);
                        String newAddress = getValidString(sc, "Enter new address (leave blank to skip): ", true);
                        System.out.print("Enter new phone numbers (comma-separated, leave blank to skip): ");
                        String newPhoneNumbersStr = sc.nextLine();
                        List<String> newPhoneNumbers = newPhoneNumbersStr.isEmpty() ? null : java.util.List.of(newPhoneNumbersStr.split(","));
                        telephoneBook.updateProfile(updateName, newLastName, newAge, newAddress, newPhoneNumbers);
                    } else {
                        System.out.println("Invalid choice! Only admins can update profiles.");
                    }
                    break;

                case "4": // Display All Profiles (Admin only).
                    if (tenant.isAdmin()) {
                        telephoneBook.displayAllProfiles();
                    } else {
                        System.out.println("Invalid choice! Only admins can view all profiles.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Displays the search menu and handles search options.
    private static void displaySearchMenu(Scanner sc, TelephoneBook telephoneBook) {
        while (true) {
            System.out.println("\nSearch Menu:");
            System.out.println("1. Search by First Name");
            System.out.println("2. Search by Last Name");
            System.out.println("3. Search by Address");
            System.out.println("4. Free Search");
            System.out.println("Type 'back' to return to the main menu.");

            System.out.print("Enter your search choice: ");
            String searchChoice = sc.nextLine().toLowerCase();

            if (searchChoice.equals("back")) { // Return to the main menu.
                return;
            }

            switch (searchChoice) {
                case "1": // Search by First Name.
                    String firstName = getValidString(sc, "Enter the first name to search: ");
                    List<Profile> profilesByFirstName = telephoneBook.searchByFirstName(firstName);
                    displaySearchResults(profilesByFirstName, "first name", firstName);
                    break;

                case "2": // Search by Last Name.
                    String lastName = getValidString(sc, "Enter the last name to search: ");
                    List<Profile> profilesByLastName = telephoneBook.searchByLastName(lastName);
                    displaySearchResults(profilesByLastName, "last name", lastName);
                    break;

                case "3": // Search by Address.
                    String address = getValidString(sc, "Enter the address to search: ");
                    List<Profile> profilesByAddress = telephoneBook.searchByAddress(address);
                    displaySearchResults(profilesByAddress, "address", address);
                    break;

                case "4": // Free Search.
                    String query = getValidString(sc, "Enter your search query: ");
                    List<Profile> profilesByFreeSearch = telephoneBook.freeSearch(query);
                    displaySearchResults(profilesByFreeSearch, "query", query);
                    break;

                default:
                    System.out.println("Invalid search choice! Please try again.");
            }
        }
    }

    // Helper function to get a valid integer input.
    private static int getValidInt(Scanner sc, String prompt, int min) {
        int result;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                result = sc.nextInt();
                sc.nextLine(); // Consume the newline character.
                if (result >= min) { // Check if the input meets the minimum requirement.
                    return result; // Return the valid integer.
                }
            } else {
                sc.nextLine(); // Consume invalid input.
            }
            System.out.println("Please enter a valid number greater than or equal to " + min + ".");
        }
    }

    // Helper function to get a valid string input.
    private static String getValidString(Scanner sc, String prompt) {
        return getValidString(sc, prompt, false); // Default to not allowing empty input.
    }

    // Overloaded helper function to get a valid string input with an option to allow empty input.
    private static String getValidString(Scanner sc, String prompt, boolean allowEmpty) {
        while (true) { // Loop until a valid input is provided.
            System.out.print(prompt);
            String input = sc.nextLine();
            if (!input.isEmpty() || allowEmpty) { // Allow empty input if specified.
                return input; // Return the valid string.
            }
            System.out.println("Please enter a valid input."); // Prompt user again for invalid input.
        }
    }
}