package com.example.synqit.ui.support.model;

import com.example.synqit.ui.howtouse.model.InstructionData;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class SupportListResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<SupportListData> data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<SupportListData> getData() {
        return data;
    }
}
