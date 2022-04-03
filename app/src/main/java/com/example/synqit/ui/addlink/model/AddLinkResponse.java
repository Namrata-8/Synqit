package com.example.synqit.ui.addlink.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddLinkResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("mostPopular")
    private List<CommonLinkData> mostPopularList;
    @SerializedName("influencers")
    private List<CommonLinkData> influencersList;
    @SerializedName("allApp")
    private List<CommonLinkData> allApp;
    @SerializedName("categories")
    private List<Categories> categoriesList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<CommonLinkData> getMostPopularList() {
        return mostPopularList;
    }

    public List<CommonLinkData> getInfluencersList() {
        return influencersList;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public List<CommonLinkData> getAllApp() {
        return allApp;
    }
}
