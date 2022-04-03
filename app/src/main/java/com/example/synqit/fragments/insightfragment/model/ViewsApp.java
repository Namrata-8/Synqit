package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class ViewsApp {

    @SerializedName("taps")
    private int taps;
    @SerializedName("username")
    private String username;
    @SerializedName("appTitle")
    private String appTitle;
    @SerializedName("logoDark")
    private String logoDark;
    @SerializedName("logoLight")
    private String logoLight;
    @SerializedName("logoColor")
    private String logoColor;

    public int getTaps() {
        return taps;
    }

    public String getUsername() {
        return username;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public String getLogoLight() {
        return logoLight;
    }

    public String getLogoColor() {
        return logoColor;
    }
}
