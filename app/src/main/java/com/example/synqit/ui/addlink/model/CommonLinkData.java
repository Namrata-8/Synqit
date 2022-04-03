package com.example.synqit.ui.addlink.model;

import com.google.gson.annotations.SerializedName;

public class CommonLinkData {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("logoLight")
    private String logoLight;
    @SerializedName("logoDark")
    private String logoDark;
    @SerializedName("logoColor")
    private String logoColor;
    @SerializedName("profileBaseURL")
    private String profileBaseURL;
    @SerializedName("isAvailableForPro")
    private boolean isAvailableForPro;
    @SerializedName("isMostPopular")
    private boolean isMostPopular;
    @SerializedName("isInfluencers")
    private boolean isInfluencers;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("categoryID")
    private int categoryID;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLogoLight() {
        return logoLight;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public String getLogoColor() {
        return logoColor;
    }

    public String getProfileBaseURL() {
        return profileBaseURL;
    }

    public boolean isAvailableForPro() {
        return isAvailableForPro;
    }

    public boolean isMostPopular() {
        return isMostPopular;
    }

    public boolean isInfluencers() {
        return isInfluencers;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
