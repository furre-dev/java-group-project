import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();

        //Logga in
        Tenant tenant = auth.login();

        //Få tillgång till användare vart som helst
        Tenant authedTenant = auth.getAuthenticatedUser();

        //Få tillgång till användarens roll
        boolean tenantIsAdmin = tenant.isAdmin();

    }
}