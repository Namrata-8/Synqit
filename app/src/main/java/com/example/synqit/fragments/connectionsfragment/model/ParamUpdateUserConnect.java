package com.example.synqit.fragments.connectionsfragment.model;

public class ParamUpdateUserConnect {
    private String parentUserUID;
    private String partnerUID;
    private boolean isDeleted;
    private boolean isFavorite;

    public ParamUpdateUserConnect(String parentUserUID, String partnerUID, boolean isDeleted, boolean isFavorite) {
        this.parentUserUID = parentUserUID;
        this.partnerUID = partnerUID;
        this.isDeleted = isDeleted;
        this.isFavorite = isFavorite;
    }

    public void setParentUserUID(String parentUserUID) {
        this.parentUserUID = parentUserUID;
    }

    public void setPartnerUID(String partnerUID) {
        this.partnerUID = partnerUID;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
