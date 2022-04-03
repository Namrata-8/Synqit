package com.example.synqit.ui.addlink.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("title")
    private String title;
    @SerializedName("icon")
    private String icon;
    @SerializedName("description")
    private String description;
    @SerializedName("appList")
    private List<CommonLinkData> appList;

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public List<CommonLinkData> getAppList() {
        return appList;
    }
}
