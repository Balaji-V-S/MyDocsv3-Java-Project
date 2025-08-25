package com.mydocs.model;

import java.util.List;

public class Login {
    private int loginId;
    private String userName;
    private String password;
    private List<Role> roles; // A user can have multiple roles

    // Constructors
    public Login() {}

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters and Setters
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // toString method for easy debugging

    @Override
    public String toString() {
        return "Login{" +
                "loginId=" + loginId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
