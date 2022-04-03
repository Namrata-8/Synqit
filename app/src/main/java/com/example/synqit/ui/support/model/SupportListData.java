package com.example.synqit.ui.support.model;

import com.google.gson.annotations.SerializedName;

public class SupportListData {

    @SerializedName("ticketUID")
    private String ticketUID;

    @SerializedName("parentUserID")
    private String parentUserID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("resolution")
    private String resolution;

    @SerializedName("isResolved")
    private boolean isResolved;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("resolvedDate")
    private String resolvedDate;

    public String getTicketUID() {
        return ticketUID;
    }

    public String getParentUserID() {
        return parentUserID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getResolution() {
        return resolution;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }
}
