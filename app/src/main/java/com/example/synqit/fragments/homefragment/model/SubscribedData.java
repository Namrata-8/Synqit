package com.example.synqit.fragments.homefragment.model;

import com.google.gson.annotations.SerializedName;

public class SubscribedData {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public void setLogoLight(String logoLight) {
        this.logoLight = logoLight;
    }

    public void setLogoDark(String logoDark) {
        this.logoDark = logoDark;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLogoColor(String logoColor) {
        this.logoColor = logoColor;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public void setInfluencers(boolean influencers) {
        isInfluencers = influencers;
    }

    public void setMostPopular(boolean mostPopular) {
        isMostPopular = mostPopular;
    }
}
