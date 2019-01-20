package com.example.heidrun.bak_project_learnquest;

public class User {

    private int U_ID;
    private String Name;
    private String Email;
    private String Avatar;

    public User(int U_ID, String Name, String Email, String Avatar) {
        this.Avatar = Avatar;
        this.Email = Email;
        this.Name = Name;
        this.U_ID = U_ID;
    }

    public void setU_ID(int u_ID) {
        U_ID = u_ID;
    }

    public int getU_ID() {
        return U_ID;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }
}
