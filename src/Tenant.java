public class Tenant {

    public enum Role {
        USER, ADMIN
    }

    private final Role role;
    private final String username;
    private final String password;

    public Tenant(Role role, String username, String password){
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin(){
        return role == Role.ADMIN;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
