package Book;

public class Main {
    public static void main(String[] args) {
        // Create instances of Authentication and TelephoneBook.
        Authentication auth = new Authentication();
        TelephoneBook telephoneBook = new TelephoneBook();

        // Authenticate the user (login process).
        Tenant tenant = auth.login();

        // Check the role of the logged-in user.
        boolean tenantIsAdmin = tenant.isAdmin();

        // Delegate control to TelephoneBook's main menu.
        telephoneBook.run(tenant);
    }
}