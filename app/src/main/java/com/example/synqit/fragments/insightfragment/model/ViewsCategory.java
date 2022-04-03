package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class ViewsCategory {

    @SerializedName("visit")
    private String visit;
    @SerializedName("categoryName")
    private String categoryName;

    public String getVisit() {
        return visit;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
