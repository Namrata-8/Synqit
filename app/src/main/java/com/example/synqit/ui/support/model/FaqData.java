package com.example.synqit.ui.support.model;

import com.google.gson.annotations.SerializedName;

public class FaqData {
    @SerializedName("faqUID")
    private String faqUID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("createdDate")
    private String createdDate;

    public String getFaqUID() {
        return faqUID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
