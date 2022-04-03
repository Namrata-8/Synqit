package com.example.synqit.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class CardData {

    @SerializedName("parentUserID")
    private String parentUserID;

    @SerializedName("userID")
    private String userID;

    @SerializedName("userUID")
    private String userUID;

    @SerializedName("isBusiness")
    private boolean isBusiness;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("dob")
    private String dob;

    @SerializedName("gender")
    private String gender;

    @SerializedName("businessType")
    private int businessType;

    @SerializedName("displayPicture")
    private String displayPicture;

    @SerializedName("coverPicture")
    private String coverPicture;

    @SerializedName("plan")
    private int plan;

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("isPrivate")
    private boolean isPrivate;

    @SerializedName("isDirect")
    private boolean isDirect;

    @SerializedName("profileName")
    private String profileName;

    @SerializedName("bio")
    private String bio;

    @SerializedName("themeColor")
    private String themeColor;

    @SerializedName("isIconColor")
    private boolean isIconColor;

    @SerializedName("email")
    private String email;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    public String getParentUserID() {
        return parentUserID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public int getBusinessType() {
        return businessType;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public int getPlan() {
        return plan;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getBio() {
        return bio;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public boolean isIconColor() {
        return isIconColor;
    }

    public String getUserUID() {
        return userUID;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
