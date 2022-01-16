package com.example.synqit.ui.login.model;

public class ParamLogin {
    private String username;
    private String password;

    public ParamLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
