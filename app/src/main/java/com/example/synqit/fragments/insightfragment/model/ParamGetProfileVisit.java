package com.example.synqit.fragments.insightfragment.model;

public class ParamGetProfileVisit {
    private String userUID;
    private String startDate;
    private String endDate;

    public ParamGetProfileVisit(String userUID, String startDate, String endDate) {
        this.userUID = userUID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
