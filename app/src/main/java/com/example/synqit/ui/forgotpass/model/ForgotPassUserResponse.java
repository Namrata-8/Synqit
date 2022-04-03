package com.example.synqit.ui.forgotpass.model;

import com.example.synqit.ui.register.model.RegisterData;
import com.google.gson.annotations.SerializedName;

public class ForgotPassUserResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("isAvailable")
    private boolean isAvailable;
    @SerializedName("email")
    private String email;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("userID")
    private String userID;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getUserID() {
        return userID;
    }
}
