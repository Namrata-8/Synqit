package com.example.synqit.fragments.businessfragment2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessResponse {

    @SerializedName("isSuccess")
    private String isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<BusinessData> data;

    public String getIsSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<BusinessData> getData() {
        return data;
    }
}
