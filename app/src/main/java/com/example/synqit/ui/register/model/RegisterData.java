package com.example.synqit.ui.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("basicRegistratinUID")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("password")
    private String password;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }
}
