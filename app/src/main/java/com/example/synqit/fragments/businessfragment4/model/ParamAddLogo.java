package com.example.synqit.fragments.businessfragment4.model;

import java.io.File;

public class ParamAddLogo {
    private File Profile;
    private String UserUID;

    public ParamAddLogo(File profile, String userUID) {
        Profile = profile;
        UserUID = userUID;
    }

    public void setProfile(File profile) {
        Profile = profile;
    }

    public void setUserUID(String userUID) {
        UserUID = userUID;
    }
}
