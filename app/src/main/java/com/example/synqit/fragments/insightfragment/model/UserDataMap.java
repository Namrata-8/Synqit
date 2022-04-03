package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class UserDataMap {
    @SerializedName("userID")
    private String userID;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("displayPicture")
    private String displayPicture;

    @SerializedName("plan")
    private int plan;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("isPrivate")
    private boolean isPrivate;

    @SerializedName("profileName")
    private String profileName;

    @SerializedName("bio")
    private String bio;

    @SerializedName("connectionDate")
    private String connectionDate;

    @SerializedName("Lat")
    private double Lat;

    @SerializedName("Lng")
    private double Lng;

    @SerializedName("StreakLat")
    private double StreakLat;

    @SerializedName("StreakLng")
    private double StreakLng;

    public String getUserID() {
        return userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public int getPlan() {
        return plan;
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

    public String getProfileName() {
        return profileName;
    }

    public String getBio() {
        return bio;
    }

    public String getConnectionDate() {
        return connectionDate;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return Lng;
    }

    public double getStreakLat() {
        return StreakLat;
    }

    public double getStreakLng() {
        return StreakLng;
    }
}
