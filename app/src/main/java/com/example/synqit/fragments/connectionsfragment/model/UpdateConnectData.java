package com.example.synqit.fragments.connectionsfragment.model;

import com.google.gson.annotations.SerializedName;

public class UpdateConnectData {
    @SerializedName("id")
    private String id;

    @SerializedName("userUID")
    private String userUID;

    @SerializedName("partnerUID")
    private String partnerUID;

    @SerializedName("connectionDate")
    private String connectionDate;

    @SerializedName("isFavorite")
    private boolean isFavorite;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    public String getId() {
        return id;
    }

    public String getUserUID() {
        return userUID;
    }

    public String getPartnerUID() {
        return partnerUID;
    }

    public String getConnectionDate() {
        return connectionDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
