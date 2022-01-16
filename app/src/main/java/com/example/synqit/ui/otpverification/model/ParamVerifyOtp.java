package com.example.synqit.ui.otpverification.model;

public class ParamVerifyOtp {
    private String verificationid;
    private int OTP;

    public ParamVerifyOtp(String verificationid, int OTP) {
        this.verificationid = verificationid;
        this.OTP = OTP;
    }

    public void setVerificationid(String verificationid) {
        this.verificationid = verificationid;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }
}
