package ase;

import java.io.Serializable;
import java.util.List;

public class User{

    private String username;
    private String password;
    private String role;
    private String skill;

    public static final String PROVIDER = "provider";
    public static final String CUSTOMER = "customer";

    User(String username, String password, String role, String skill){
        this.username = username;
        this.password = password;
        this.role = role;
        this.skill = skill;
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
