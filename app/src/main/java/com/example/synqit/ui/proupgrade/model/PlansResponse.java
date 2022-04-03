package com.example.synqit.ui.proupgrade.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlansResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<PlansData> plansData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<PlansData> getPlansData() {
        return plansData;
    }
}
