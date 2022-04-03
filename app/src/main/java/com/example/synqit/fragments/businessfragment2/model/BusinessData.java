package com.example.synqit.fragments.businessfragment2.model;

import com.google.gson.annotations.SerializedName;

public class BusinessData {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("icon")
    private String icon;

    @SerializedName("isDeleted")
    private String isDeleted;

    @SerializedName("dateCreated")
    private String dateCreated;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
