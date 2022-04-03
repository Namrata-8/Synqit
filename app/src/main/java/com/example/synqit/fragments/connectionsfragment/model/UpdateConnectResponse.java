package com.example.synqit.fragments.connectionsfragment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateConnectResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<UpdateConnectData> updateConnectData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<UpdateConnectData> getUpdateConnectData() {
        return updateConnectData;
    }
}
