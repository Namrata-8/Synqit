package com.example.synqit.ui.activateproduct.model;

import com.google.gson.annotations.SerializedName;

public class ActivateProductData {
    @SerializedName("productUID")
    private String productUID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("iconLight")
    private String iconLight;

    @SerializedName("iconDark")
    private String iconDark;

    @SerializedName("createdDate")
    private String createdDate;

    public String getProductUID() {
        return productUID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIconLight() {
        return iconLight;
    }

    public String getIconDark() {
        return iconDark;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
