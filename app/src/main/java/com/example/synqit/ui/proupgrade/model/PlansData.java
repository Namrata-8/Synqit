package com.example.synqit.ui.proupgrade.model;

import com.google.gson.annotations.SerializedName;

public class PlansData {
    @SerializedName("id")
    private String id;
    @SerializedName("billingType")
    private String billingType;
    @SerializedName("billingDescription")
    private String billingDescription;
    @SerializedName("planPrice")
    private String planPrice;
    @SerializedName("freeTrial")
    private String freeTrial;
    @SerializedName("specialTag")
    private String specialTag;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("dateCreated")
    private String dateCreated;

    public String getId() {
        return id;
    }

    public String getBillingType() {
        return billingType;
    }

    public String getBillingDescription() {
        return billingDescription;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public String getFreeTrial() {
        return freeTrial;
    }

    public String getSpecialTag() {
        return specialTag;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
