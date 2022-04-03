package com.example.synqit.ui.createconnection.model;

import com.example.synqit.ui.proupgrade.model.FullRegisterData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsertConnectionResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<ConnectionData> connectionData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<ConnectionData> getConnectionData() {
        return connectionData;
    }
}
