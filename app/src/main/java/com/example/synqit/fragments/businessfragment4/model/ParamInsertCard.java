package com.example.synqit.fragments.businessfragment4.model;

public class ParamInsertCard {

    private String parentUserID;
    private String userID;
    private boolean isBusiness;
    private String displayName;
    private String dob;
    private String gender;
    private int businessType;
    private String displayPicture;
    private String coverPicture;
    private int plan;
    private String qrCode;
    private String country;
    private String city;
    private boolean isPrivate;
    private boolean isDirect;
    private String profileName;
    private String bio;
    private String themeColor;
    private boolean isIconColor;
    private String email;
    private String mobileNumber;

    public ParamInsertCard() {
    }

    public ParamInsertCard(String parentUserID, boolean isBusiness, String displayName, String dob, String gender, int businessType, String displayPicture, String coverPicture, int plan, String qrCode, String country, String city, boolean isPrivate, boolean isDirect, String profileName, String bio, String themeColor, boolean isIconColor) {
        this.parentUserID = parentUserID;
        this.isBusiness = isBusiness;
        this.displayName = displayName;
        this.dob = dob;
        this.gender = gender;
        this.businessType = businessType;
        this.displayPicture = displayPicture;
        this.coverPicture = coverPicture;
        this.plan = plan;
        this.qrCode = qrCode;
        this.country = country;
        this.city = city;
        this.isPrivate = isPrivate;
        this.isDirect = isDirect;
        this.profileName = profileName;
        this.bio = bio;
        this.themeColor = themeColor;
        this.isIconColor = isIconColor;
    }

    public ParamInsertCard(String parentUserID, String userID, boolean isBusiness, String displayName, String dob, String gender, int businessType, String displayPicture, String coverPicture, int plan,
                           String qrCode, String country, String city, boolean isPrivate, boolean isDirect, String profileName, String bio, String themeColor, boolean isIconColor, String email, String mobileNumber) {
        this.parentUserID = parentUserID;
        this.userID = userID;
        this.isBusiness = isBusiness;
        this.displayName = displayName;
        this.dob = dob;
        this.gender = gender;
        this.businessType = businessType;
        this.displayPicture = displayPicture;
        this.coverPicture = coverPicture;
        this.plan = plan;
        this.qrCode = qrCode;
        this.country = country;
        this.city = city;
        this.isPrivate = isPrivate;
        this.isDirect = isDirect;
        this.profileName = profileName;
        this.bio = bio;
        this.themeColor = themeColor;
        this.isIconColor = isIconColor;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setParentUserID(String parentUserID) {
        this.parentUserID = parentUserID;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public void setIconColor(boolean iconColor) {
        isIconColor = iconColor;
    }
}
