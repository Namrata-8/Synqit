package com.example.synqit.ui.proupgrade.model;

import com.example.synqit.ui.dashboard.model.CardData;
import com.google.gson.annotations.SerializedName;

public class FullRegisterData {
    @SerializedName("userID")
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
    private String businessType;
    @SerializedName("displayPicture")
    private String displayPicture;
    @SerializedName("coverPicture")
    private String coverPicture;
    @SerializedName("plan")
    private String plan;
    @SerializedName("qrCode")
    private String qrCode;
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("isPrivate")
    private boolean isPrivate;
    @SerializedName("isSocialLogin")
    private boolean isSocialLogin;
    @SerializedName("isDirect")
    private boolean isDirect;
    @SerializedName("isFullRegistered")
    private boolean isFullRegistered;

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public String getBusinessType() {
        return businessType;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public String getPlan() {
        return plan;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getParentUserID() {
        return parentUserID;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public boolean isSocialLogin() {
        return isSocialLogin;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public boolean isFullRegistered() {
        return isFullRegistered;
    }
}
