package com.example.synqit.ui.support.model;

public class ParamCreateTicket {
    private String parentUserID;
    private String title;
    private String description;

    public ParamCreateTicket(String parentUserID, String title, String description) {
        this.parentUserID = parentUserID;
        this.title = title;
        this.description = description;
    }

    public void setParentUserID(String parentUserID) {
        this.parentUserID = parentUserID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
