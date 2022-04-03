package com.example.synqit.ui.addlink.model;

import com.google.gson.annotations.SerializedName;

public class AddSocialMediaData {
    @SerializedName("id")
    private int id;
    @SerializedName("profileLink")
    private String profileLink;
    @SerializedName("title")
    private String title;
    @SerializedName("linkTitle")
    private String linkTitle;
    @SerializedName("logoLight")
    private String logoLight;
    @SerializedName("logoDark")
    private String logoDark;
    @SerializedName("username")
    private String username;
    @SerializedName("logoColor")
    private String logoColor;
    @SerializedName("mediaID")
    private int mediaID;
    @SerializedName("isDisabled")
    private boolean isDisabled;
    @SerializedName("isInfluencers")
    private boolean isInfluencers;
    @SerializedName("isMostPopular")
    private boolean isMostPopular;

    public int getId() {
        return id;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public String getLogoLight() {
        return logoLight;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public String getUsername() {
        return username;
    }

    public String getLogoColor() {
        return logoColor;
    }

    public int getMediaID() {
        return mediaID;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public boolean isInfluencers() {
        return isInfluencers;
    }

    public boolean isMostPopular() {
        return isMostPopular;
    }
}
