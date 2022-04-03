package com.example.synqit.ui.createconnection.model;

import com.google.gson.annotations.SerializedName;

public class ConnectionData {
    @SerializedName("id")
    private String id;
    @SerializedName("userUID")
    private String userUID;
    @SerializedName("partnerUID")
    private String partnerUID;
    @SerializedName("email")
    private String email;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("website")
    private String website;
    @SerializedName("location")
    private String location;
    @SerializedName("socialMedia")
    private String socialMedia;
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("jobTitle")
    private String jobTitle;
    @SerializedName("notes")
    private String notes;
    @SerializedName("displayPicture")
    private String displayPicture;
    @SerializedName("coverPicture")
    private String coverPicture;
    @SerializedName("gender")
    private String gender;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("isFavorite")
    private boolean isFavorite;
    @SerializedName("isBusiness")
    private boolean isBusiness;
    @SerializedName("isBlocked")
    private boolean isBlocked;
    @SerializedName("isVerified")
    private boolean isVerified;
    @SerializedName("isManualConnect")
    private boolean isManualConnect;
    @SerializedName("plan")
    private int plan;
    @SerializedName("connectedDate")
    private String connectedDate;
    @SerializedName("dob")
    private String dob;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getConnectedDate() {
        return connectedDate;
    }

    public String getUserUID() {
        return userUID;
    }

    public String getPartnerUID() {
        return partnerUID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public String getGender() {
        return gender;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isManualConnect() {
        return isManualConnect;
    }

    public int getPlan() {
        return plan;
    }

    public String getDob() {
        return dob;
    }
}
