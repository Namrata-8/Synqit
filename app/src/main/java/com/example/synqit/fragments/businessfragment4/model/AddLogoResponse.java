package com.example.synqit.fragments.businessfragment4.model;

import com.google.gson.annotations.SerializedName;

public class AddLogoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("fileUrl")
    private String fileUrl;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
