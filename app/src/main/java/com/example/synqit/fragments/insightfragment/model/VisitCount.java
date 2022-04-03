package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class VisitCount {
    @SerializedName("currentVisit")
    private int currentVisit;
    @SerializedName("previousVisit")
    private int previousVisit;

    public int getCurrentVisit() {
        return currentVisit;
    }

    public int getPreviousVisit() {
        return previousVisit;
    }
}
