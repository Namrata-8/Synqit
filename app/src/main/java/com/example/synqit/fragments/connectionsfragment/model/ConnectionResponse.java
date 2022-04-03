package com.example.synqit.fragments.connectionsfragment.model;

import com.example.synqit.ui.dashboard.model.CardData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConnectionResponse {
    @SerializedName("isSuccess")
    private String isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("favorite")
    private List<FavoriteData> favorite;

    @SerializedName("connected")
    private List<ConnectedData> connected;

    @SerializedName("blocked")
    private List<ConnectedData> blockedList;

    public List<ConnectedData> getBlockedList() {
        return blockedList;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<FavoriteData> getFavorite() {
        return favorite;
    }

    public List<ConnectedData> getConnected() {
        return connected;
    }
}
