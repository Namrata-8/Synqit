package com.example.synqit.ui.proupgrade.model;

public class ParamFullRegister {

    private boolean isBusiness;
    private String displayName;
    private String dob;
    private String gender;
    private String businessType;
    private String displayPicture;
    private String coverPicture;
    private String city;
    private String country;
    private int plan;
    private String basicRegistratinUID;

    public ParamFullRegister(String basicRegistratinUID, boolean isBusiness, String displayName, String dob, String gender, String businessType, String displayPicture, String coverPicture, String city, String country, int plan) {
        this.basicRegistratinUID = basicRegistratinUID;
        this.isBusiness = isBusiness;
        this.displayName = displayName;
        this.dob = dob;
        this.gender = gender;
        this.businessType = businessType;
        this.displayPicture = displayPicture;
        this.coverPicture = coverPicture;
        this.city = city;
        this.country = country;
        this.plan = plan;
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

    public void setBusinessType(String businessType) {
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

    public void setBasicRegistratinUID(String basicRegistratinUID) {
        this.basicRegistratinUID = basicRegistratinUID;
    }
}
