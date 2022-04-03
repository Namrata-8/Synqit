package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

public class StreakCount {
    @SerializedName("currentStreak")
    private int currentStreak;
    @SerializedName("previousStreak")
    private int previousStreak;

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getPreviousStreak() {
        return previousStreak;
    }
}
