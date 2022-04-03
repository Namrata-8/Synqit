package com.example.synqit.ui.addlink.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddSocialMediaResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<AddSocialMediaData> socialMediaDataList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<AddSocialMediaData> getSocialMediaDataList() {
        return socialMediaDataList;
    }
}
