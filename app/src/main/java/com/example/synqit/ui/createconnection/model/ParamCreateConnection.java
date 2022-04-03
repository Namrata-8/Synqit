package com.example.synqit.ui.createconnection.model;

public class ParamCreateConnection {
    private String id;
    private String userUID;
    private String partnerUID;
    private String email;
    private String displayName;
    private String website;
    private String location;
    private String socialMedia;
    private String mobileNumber;
    private String jobTitle;
    private String notes;
    private String displayPicture;
    private String coverPicture;
    private String gender;
    private boolean isDeleted;
    private boolean isFavorite;
    private boolean isBusiness;
    private boolean isBlocked;
    private boolean isVerified;
    private boolean isManualConnect;
    private int plan;
    private String connectedDate;
    private String dob;

    public ParamCreateConnection() {
    }

    public ParamCreateConnection(String id, String userUID, String partnerUID, String email, String displayName, String website, String location, String socialMedia, String mobileNumber, String jobTitle, String notes, String displayPicture, String coverPicture, String gender, boolean isDeleted, boolean isFavorite, boolean isBusiness, boolean isBlocked, boolean isVerified, boolean isManualConnect, int plan, String connectedDate, String dob) {
        this.id = id;
        this.userUID = userUID;
        this.partnerUID = partnerUID;
        this.email = email;
        this.displayName = displayName;
        this.website = website;
        this.location = location;
        this.socialMedia = socialMedia;
        this.mobileNumber = mobileNumber;
        this.jobTitle = jobTitle;
        this.notes = notes;
        this.displayPicture = displayPicture;
        this.coverPicture = coverPicture;
        this.gender = gender;
        this.isDeleted = isDeleted;
        this.isFavorite = isFavorite;
        this.isBusiness = isBusiness;
        this.isBlocked = isBlocked;
        this.isVerified = isVerified;
        this.isManualConnect = isManualConnect;
        this.plan = plan;
        this.connectedDate = connectedDate;
        this.dob = dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setConnectedDate(String connectedDate) {
        this.connectedDate = connectedDate;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public void setPartnerUID(String partnerUID) {
        this.partnerUID = partnerUID;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setManualConnect(boolean manualConnect) {
        isManualConnect = manualConnect;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }
}
