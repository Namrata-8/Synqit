package com.example.synqit.ui.register.model;

import com.google.gson.annotations.SerializedName;

public class BasicRegisterResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private RegisterData registerData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public RegisterData getRegisterData() {
        return registerData;
    }
}
