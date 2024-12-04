import java.util.Scanner; // Import Scanner class for user input.

public class Authentication {
    private Tenant tenant; // Stores the currently authenticated user after a successful login.
    private final Tenant[] userDatabase; // An array to store predefined users (guest and admin).

    // Constructor to initialize the authentication system.
    public Authentication() {
        userDatabase = new Tenant[2]; // Create an array to hold two tenants (guest and admin).

        // Define the guest user with USER role and set username/password as "guest".
        Tenant guest = new Tenant(Tenant.Role.USER, "guest", "guest");

        // Define the admin user with ADMIN role and set username/password as "admin".
        Tenant admin = new Tenant(Tenant.Role.ADMIN, "admin", "admin");

        // Store the guest and admin users in the user database.
        userDatabase[0] = guest;
        userDatabase[1] = admin;
    }

    /**
     * Handles user login by prompting for username and password.
     * Validates credentials against the predefined user database.
     *
     * @return Tenant - The authenticated tenant object.
     */
    public Tenant login() {
        Scanner sc = new Scanner(System.in); // Create a Scanner instance for user input.

        // Loop until a valid user is authenticated.
        while (tenant == null) {
            System.out.print("Please enter your username: "); // Prompt for username.
            String username = sc.next(); // Read username input.
            System.out.print("Please enter your password: "); // Prompt for password.
            String password = sc.next(); // Read password input.

            // Iterate through the user database to validate credentials.
            for (Tenant value : userDatabase) {
                if (username.equals(value.getUsername()) && password.equals(value.getPassword())) {
                    tenant = value; // Set the authenticated tenant if credentials match.
                }
            }

            // Provide feedback to the user about login success or failure.
            if (tenant != null) {
                return tenant; // Return the authenticated tenant.
            } else {
                System.out.println("Could not sign in, try again!"); // Notify on failed login.
            }
        }
        return tenant; // Redundant return, as loop ensures a tenant is returned once authenticated.
    }

    /**
     * Provides access to the currently authenticated user.
     *
     * @return Tenant - The authenticated tenant object.
     */
    public Tenant getAuthenticatedUser() {
        return tenant; // Return the current authenticated tenant.
    }
}