package com.example.synqit.fragments.businessfragment4.model;

import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddImageResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AddImageData data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public AddImageData getData() {
        return data;
    }
}
