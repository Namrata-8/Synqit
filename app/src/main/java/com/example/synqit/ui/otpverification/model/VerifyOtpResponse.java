package com.example.synqit.ui.otpverification.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("verificationid")
    private String verificationid;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public String getVerificationid() {
        return verificationid;
    }
}
