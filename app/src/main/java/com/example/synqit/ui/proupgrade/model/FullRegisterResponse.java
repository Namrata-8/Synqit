package com.example.synqit.ui.proupgrade.model;

import com.example.synqit.ui.register.model.RegisterData;
import com.google.gson.annotations.SerializedName;

public class FullRegisterResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private FullRegisterData fullRegisterData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public FullRegisterData getFullRegisterData() {
        return fullRegisterData;
    }
}
