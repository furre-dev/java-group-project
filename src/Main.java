import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();
        Tenant tenant = auth.login();

    }
}