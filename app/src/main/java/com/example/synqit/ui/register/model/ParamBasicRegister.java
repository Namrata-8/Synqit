package com.example.synqit.ui.register.model;

public class ParamBasicRegister {
    private String email;
    private String mobileNumber;
    private String password;

    public ParamBasicRegister(String email, String mobileNumber, String password) {
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
