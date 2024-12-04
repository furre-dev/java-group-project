package Book; // Package declaration for organization.

public class Tenant {

    // Enum to define roles for the Tenant.
    public enum Role {
        USER, // A regular user with limited permissions.
        ADMIN // An admin with full permissions.
    }

    private final Role role; // The role of the tenant (USER or ADMIN).
    private final String username; // The username of the tenant for authentication.
    private final String password; // The password of the tenant for authentication.

    // Constructor to initialize a Tenant with a role, username, and password.
    public Tenant(Role role, String username, String password) {
        this.role = role; // Assign the role to the tenant.
        this.username = username; // Assign the username to the tenant.
        this.password = password; // Assign the password to the tenant.
    }

    // Method to check if the tenant is an admin.
    public boolean isAdmin() {
        return role == Role.ADMIN; // Return true if the tenant's role is ADMIN.
    }

    // Getter for the tenant's username.
    public String getUsername() {
        return username; // Return the username of the tenant.
    }

    // Getter for the tenant's password.
    public String getPassword() {
        return password; // Return the password of the tenant.
    }
}