package Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelephoneBook {

    // A list to store profiles in the telephone book.
    private final List<Profile> profiles;

    // Constructor: Initializes an empty list of profiles.
    public TelephoneBook() {
        this.profiles = new ArrayList<>();
    }

    // Adds a new profile to the telephone book.
    public void addProfile(Profile profile) {
        profiles.add(profile); // Add the profile to the list.
        System.out.println("Profile added successfully!"); // Confirm the addition.
    }

    // Deletes a profile by the first name.
    public void deleteProfile(String firstName) {
        for (Profile profile : profiles) { // Iterate over profiles to find a match.
            if (profile.toString().contains(firstName)) { // Match by first name.
                profiles.remove(profile); // Remove the profile from the list.
                System.out.println("Profile deleted successfully!");
                return; // Exit the method after deleting.
            }
        }
        System.out.println("Profile not found!"); // Notify if no profile matches.
    }

    // Searches for profiles by first name and returns all matches.
    public List<Profile> searchByFirstName(String firstName) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(firstName)) { // Match on first name.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles; // Return all matching profiles.
    }

    // Searches for profiles by last name and returns all matches.
    public List<Profile> searchByLastName(String lastName) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(lastName)) { // Match on last name.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles; // Return all matching profiles.
    }

    // Searches for profiles by address and returns all matches.
    public List<Profile> searchByAddress(String address) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().contains(address)) { // Match on address.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles; // Return all matching profiles.
    }

    // Performs a free search across all attributes (case-insensitive).
    public List<Profile> freeSearch(String query) {
        List<Profile> matchingProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (profile.toString().toLowerCase().contains(query.toLowerCase())) { // Match across all fields.
                matchingProfiles.add(profile);
            }
        }
        return matchingProfiles; // Return all matching profiles.
    }

    // Updates an existing profile based on the first name.
    public void updateProfile(String firstName, String lastName, int age, String address, List<String> phoneNumbers) {
        for (Profile profile : profiles) { // Iterate over profiles to find a match.
            if (profile.toString().contains(firstName)) { // Match on first name.
                profile.updateProfile(firstName, lastName, age, address, phoneNumbers); // Call update on the profile.
                System.out.println("Profile updated successfully!");
                return; // Exit after updating.
            }
        }
        System.out.println("Profile not found!"); // Notify if no profile matches.
    }

    // Displays all profiles in the telephone book.
    public void displayAllProfiles() {
        if (profiles.isEmpty()) { // Check if there are no profiles.
            System.out.println("No profiles available in the telephone book.");
        } else {
            for (Profile profile : profiles) { // Print each profile.
                System.out.println(profile);
            }
        }
    }

    // Helper method to display search results.
    public static void displaySearchResults(List<Profile> profiles, String searchType, String query) {
        if (profiles.isEmpty()) { // No results found.
            System.out.println("No profiles found matching " + searchType + ": " + query);
        } else {
            System.out.println("Profiles found:");
            for (Profile profile : profiles) { // Print each matching profile.
                System.out.println(profile);
            }
        }
    }

    // Main run method to handle admin and guest operations.
    public void run(Tenant tenant) {
        Scanner sc = new Scanner(System.in);

        // Continuous menu loop for admin or guest.
        while (true) {
            if (tenant.isAdmin()) { // Admin menu.
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Search Profiles");
                System.out.println("3. Update Profile");
                System.out.println("4. Display All Profiles");
                System.out.println("5. Delete Profile");
                System.out.println("Type 'quit' to quit.");
            } else { // Guest menu.
                System.out.println("\nGuest Menu:");
                System.out.println("1. Search Profiles");
                System.out.println("Type 'quit' to quit.");
            }

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().toLowerCase();

            if (choice.equals("quit")) { // Quit the application.
                System.out.println("Exiting...");
                sc.close();
                return;
            }

            switch (choice) {
                case "1":
                    if (tenant.isAdmin()) {
                        // Admin adds a profile.
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
                        Profile newProfile = new Profile(firstName, lastName, age, address, List.of(phoneNumbersStr.split(",")));
                        addProfile(newProfile);
                    } else {
                        // Guest searches profiles.
                        displaySearchMenu(sc, this);
                    }
                    break;

                case "2": // Admin searches profiles.
                    if (tenant.isAdmin()) {
                        displaySearchMenu(sc, this);
                    } else {
                        System.out.println("Invalid choice! Only admins can add profiles.");
                    }
                    break;

                case "3": // Admin updates a profile.
                    if (tenant.isAdmin()) {
                        String updateName = getValidString(sc, "Enter the first name of the profile to update: ");
                        String newLastName = getValidString(sc, "Enter new last name (leave blank to skip): ", true);
                        int newAge = getValidInt(sc, "Enter new age (0 to skip):", 0);
                        String newAddress = getValidString(sc, "Enter new address (leave blank to skip): ", true);
                        System.out.print("Enter new phone numbers (comma-separated, leave blank to skip): ");
                        String newPhoneNumbersStr = sc.nextLine();
                        List<String> newPhoneNumbers = newPhoneNumbersStr.isEmpty() ? null : List.of(newPhoneNumbersStr.split(","));
                        updateProfile(updateName, newLastName, newAge, newAddress, newPhoneNumbers);
                    } else {
                        System.out.println("Invalid choice! Only admins can update profiles.");
                    }
                    break;

                case "4": // Admin displays all profiles.
                    if (tenant.isAdmin()) {
                        displayAllProfiles();
                    } else {
                        System.out.println("Invalid choice! Only admins can view all profiles.");
                    }
                    break;

                case "5": // Admin deletes a profile.
                    if (tenant.isAdmin()) {
                        String deleteName = getValidString(sc, "Enter the first name of the profile to delete: ");
                        deleteProfile(deleteName);
                    } else {
                        System.out.println("Invalid choice! Only admins can delete profiles.");
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

    // Helper function to get a valid integer input with a minimum value constraint.
    private static int getValidInt(Scanner sc, String prompt, int min) {
        int result;
        while (true) { // Loop until a valid input is provided.
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                result = sc.nextInt();
                sc.nextLine(); // Consume the newline character.
                if (result >= min) { // Validate against the minimum value.
                    return result; // Return valid input.
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

    // Overloaded helper function to get a valid string input, with an option to allow empty input.
    private static String getValidString(Scanner sc, String prompt, boolean allowEmpty) {
        while (true) { // Loop until valid input is provided.
            System.out.print(prompt);
            String input = sc.nextLine();
            if (!input.isEmpty() || allowEmpty) { // Allow empty input if specified.
                return input; // Return the valid string.
            }
            System.out.println("Please enter a valid input."); // Prompt the user for valid input.
        }
    }
}