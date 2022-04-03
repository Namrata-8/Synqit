package com.example.synqit.fragments.homefragment.model;

public class ParamUpdateSocialMedia {
    private int id;
    private String linkTitle;
    private String username;
    private boolean isDisabled;

    public ParamUpdateSocialMedia(int id, String linkTitle, String username, boolean isDisabled) {
        this.id = id;
        this.linkTitle = linkTitle;
        this.username = username;
        this.isDisabled = isDisabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
