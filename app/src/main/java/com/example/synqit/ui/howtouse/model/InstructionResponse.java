package com.example.synqit.ui.howtouse.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionResponse {
    @SerializedName("isSuccess")
    private String isSuccess;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<InstructionData> data;

    public String getIsSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<InstructionData> getData() {
        return data;
    }
}
