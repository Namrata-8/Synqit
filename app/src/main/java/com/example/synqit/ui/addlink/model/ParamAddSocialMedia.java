package com.example.synqit.ui.addlink.model;

public class ParamAddSocialMedia {
    private String userID;
    private int mediaID;
    private String username;
    private String title;

    public ParamAddSocialMedia(String userID, int mediaID, String username, String title) {
        this.userID = userID;
        this.mediaID = mediaID;
        this.username = username;
        this.title = title;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
