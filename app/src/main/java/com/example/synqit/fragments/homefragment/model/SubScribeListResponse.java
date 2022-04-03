package com.example.synqit.fragments.homefragment.model;

import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubScribeListResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<SubscribedData> subscribedData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<SubscribedData> getSubscribedData() {
        return subscribedData;
    }
}
