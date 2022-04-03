package com.example.synqit.fragments.insightfragment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfileVisitResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("connections")
    private Connections connections;

    @SerializedName("streakCount")
    private StreakCount streakCount;

    @SerializedName("visitCount")
    private VisitCount visitCount;

    @SerializedName("viewsCategory")
    private List<ViewsCategory> viewsCategory;

    @SerializedName("viewsApps")
    private List<ViewsApp> viewsApps;

    @SerializedName("visiterCount")
    private List<VisiterCount> visiterCount;

    @SerializedName("userData")
    private List<UserDataMap> userData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Connections getConnections() {
        return connections;
    }

    public StreakCount getStreakCount() {
        return streakCount;
    }

    public VisitCount getVisitCount() {
        return visitCount;
    }

    public List<ViewsCategory> getViewsCategory() {
        return viewsCategory;
    }

    public List<ViewsApp> getViewsApps() {
        return viewsApps;
    }

    public List<VisiterCount> getVisiterCount() {
        return visiterCount;
    }

    public List<UserDataMap> getUserData() {
        return userData;
    }
}

