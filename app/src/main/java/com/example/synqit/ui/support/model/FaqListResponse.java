package com.example.synqit.ui.support.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqListResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<FaqData> data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<FaqData> getData() {
        return data;
    }
}
