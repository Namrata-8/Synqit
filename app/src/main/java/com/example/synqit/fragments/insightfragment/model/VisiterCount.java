package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class VisiterCount {
    @SerializedName("Visit")
    private int Visit;

    @SerializedName("VisitDate")
    private String VisitDate;

    public int getVisit() {
        return Visit;
    }

    public String getVisitDate() {
        return VisitDate;
    }
}
