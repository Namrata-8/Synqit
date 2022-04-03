package com.example.synqit.ui.login.model;

import android.util.Log;

import com.example.synqit.ui.proupgrade.model.FullRegisterData;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private LoginData loginData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public LoginData getLoginData() {
        return loginData;
    }
}
