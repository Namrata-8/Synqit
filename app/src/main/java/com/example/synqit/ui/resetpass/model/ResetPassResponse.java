package com.example.synqit.ui.resetpass.model;

import com.google.gson.annotations.SerializedName;

public class ResetPassResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
