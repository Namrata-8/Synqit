package com.example.synqit.ui.login.model;

import com.example.synqit.ui.proupgrade.model.FullRegisterData;
import com.google.gson.annotations.SerializedName;

public class SocialLoginResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("isLogin")
    private boolean isLogin;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private FullRegisterData socialLoginData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getMessage() {
        return message;
    }

    public FullRegisterData getSocialLoginData() {
        return socialLoginData;
    }
}
