package com.example.synqit.ui.howtouse.model;

import com.example.synqit.ui.activateproduct.model.ActivateProductData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionData {
    @SerializedName("instructionUID")
    private String instructionUID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("media")
    private String media;

    @SerializedName("mediaType")
    private String mediaType;

    @SerializedName("createdDate")
    private String createdDate;

    public String getInstructionUID() {
        return instructionUID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMedia() {
        return media;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
