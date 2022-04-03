package com.example.synqit.ui.login.model;

import com.example.synqit.ui.dashboard.model.CardData;
import com.google.gson.annotations.SerializedName;

public class LoginData{
    @SerializedName("basicRegistratinUID")
    private String basicRegistratinUID;
    @SerializedName("userUID")
    private String userID;
    @SerializedName("parentUserID")
    private String parentUserID;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
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
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("isPrivate")
    private boolean isPrivate;
    @SerializedName("isFullRegistered")
    private boolean isFullRegistered;
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

    public boolean isIconColor() {
        return isIconColor;
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

    public String getBasicRegistratinUID() {
        return basicRegistratinUID;
    }

    public String getUserID() {
        return userID;
    }

    public String getParentUserID() {
        return parentUserID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isFullRegistered() {
        return isFullRegistered;
    }
}
