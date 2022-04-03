package com.example.synqit.ui.activateproduct.model;

import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivateProductResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ActivateProductData> data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<ActivateProductData> getData() {
        return data;
    }
}
