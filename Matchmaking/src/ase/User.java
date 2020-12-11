import java.io.Serializable;

public class User{

    private String username;
    private String password;
    private String role;

    public static String PROVIDER = "provider";
    public static String CUSTOMER = "customer";

    User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRole(){
        return this.role;
    }
}
