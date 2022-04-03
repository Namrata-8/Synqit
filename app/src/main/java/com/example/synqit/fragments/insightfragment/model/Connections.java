package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class Connections {
    @SerializedName("currentConnection")
    private int currentConnection;
    @SerializedName("previousWeekConnection")
    private int previousWeekConnection;

    public int getCurrentConnection() {
        return currentConnection;
    }

    public int getPreviousWeekConnection() {
        return previousWeekConnection;
    }
}
