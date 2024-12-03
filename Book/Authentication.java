package Book;

import java.util.Scanner;

public class Authentication {
    private Tenant tenant;
    private final Tenant[] userDatabase;

    public Authentication(){
        userDatabase = new Tenant[2];

        Tenant guest = new Tenant(Tenant.Role.USER, "guest", "guest");
        Tenant admin = new Tenant(Tenant.Role.ADMIN, "admin", "admin");

        userDatabase[0] = guest;
        userDatabase[1] = admin;
    }

    public Tenant login(){
        Scanner sc = new Scanner(System.in);

        while(tenant == null){
            System.out.print("Please enter your username:");
            String username = sc.next();
            System.out.print("Please enter your password:");
            String password = sc.next();

            for (Tenant value : userDatabase) {
                if (username.equals(value.getUsername()) && password.equals(value.getPassword())) {
                    tenant = value;
                }
            }

            if(tenant != null){
                return tenant;
            }else {
                System.out.println("Could not sign in, try again!");
            }
        }
        return tenant;
    }

    public Tenant getAuthenticatedUser(){
        return tenant;
    }

}