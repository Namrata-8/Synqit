package com.example.synqit.ui.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityOtpVerificationBinding;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.otpverification.model.GetOtpResponse;
import com.example.synqit.ui.otpverification.model.ParamGetOtp;
import com.example.synqit.ui.otpverification.model.ParamVerifyOtp;
import com.example.synqit.ui.otpverification.model.VerifyOtpResponse;
import com.example.synqit.ui.resetpass.ResetPasswordActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity implements OtpVerificationNavigator {

    private ActivityOtpVerificationBinding activityOtpVerificationBinding;
    private OtpVerificationViewModel otpVerificationViewModel;
    private String signUpFrom = "";
    private String verificationid = "";
    private String codeBySystem;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        activityOtpVerificationBinding.firstPinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    activityOtpVerificationBinding.tvResend.setEnabled(true);
                    activityOtpVerificationBinding.tvResend.setAlpha(1f);
                    Toast.makeText(OtpVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onVerificationFailed", e.getMessage());
                    //Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityOtpVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification);
        activityOtpVerificationBinding.setViewModel(new OtpVerificationViewModel(this));
        activityOtpVerificationBinding.executePendingBindings();
        initViewModel();

        signUpFrom = getIntent().getStringExtra("BasicRegisterWith");

        if (signUpFrom.equals("Mail")) {
            if (!getIntent().getBooleanExtra("isFromForgotPass", false)) {
                if (SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "") != null) {
                    if (SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "").getEmail() != null) {
                        ParamGetOtp paramGetOtp = new ParamGetOtp(SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "").getEmail());
                        otpVerificationViewModel.getOtp(paramGetOtp, OtpVerificationActivity.this);
                    }
                }
            } else {
                if (!getIntent().getStringExtra("forgotPassEmail").equals("")) {
                    ParamGetOtp paramGetOtp = new ParamGetOtp(getIntent().getStringExtra("forgotPassEmail"));
                    otpVerificationViewModel.getOtp(paramGetOtp, OtpVerificationActivity.this);
                }
            }
        } else {
            if (!getIntent().getBooleanExtra("isFromForgotPass", false)) {
                if (SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "") != null) {
                    Log.e("MobileNumber", SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "").getMobileNumber() + "\n ****");
                    sendVerificationCodeToUser(SessionManager.readBasicRegisterData(OtpVerificationActivity.this, SessionManager.BR_BasicRegisterData, "").getMobileNumber());
                }
            } else {
                if (!getIntent().getStringExtra("forgotPassMobile").equals("")) {
                    Toast.makeText(this, getIntent().getStringExtra("forgotPassMobile") + " ***", Toast.LENGTH_SHORT).show();
                    sendVerificationCodeToUser(getIntent().getStringExtra("forgotPassMobile"));
                }
            }
        }

        activityOtpVerificationBinding.firstPinView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                if (signUpFrom.equals("Mail")) {
                    if (!verificationid.equals("")) {
                        ParamVerifyOtp paramVerifyOtp = new ParamVerifyOtp(verificationid, Integer.parseInt(otp));
                        otpVerificationViewModel.verifyOtp(paramVerifyOtp, OtpVerificationActivity.this);
                    }
                } else {
                    verifyCode(activityOtpVerificationBinding.firstPinView.getText().toString());
                }
            }
        });

        activityOtpVerificationBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendVerificationCodeToUser(String mobileNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,
                60,
                TimeUnit.SECONDS,
                OtpVerificationActivity.this,
                mCallbacks);
        startTimer();
    }

    private void initViewModel() {
        otpVerificationViewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);

        otpVerificationViewModel.GetOtp().observe(this, new Observer<GetOtpResponse>() {
            @Override
            public void onChanged(GetOtpResponse getOtpResponse) {
                if (getOtpResponse != null) {
                    Log.e("GetOtp", getOtpResponse.toString());
                    startTimer();
                    if (getOtpResponse.isSuccess()) {
                        verificationid = getOtpResponse.getVerificationid();
                        SessionManager.writeString(OtpVerificationActivity.this, SessionManager.GO_verificationId, getOtpResponse.getVerificationid());
                        Toast.makeText(OtpVerificationActivity.this, getOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        otpVerificationViewModel.VerifyOtp().observe(this, new Observer<VerifyOtpResponse>() {
            @Override
            public void onChanged(VerifyOtpResponse verifyOtpResponse) {
                if (verifyOtpResponse != null) {
                    Log.e("VerifyOtp", verifyOtpResponse.toString());
                    if (verifyOtpResponse.isSuccess()) {
                        Toast.makeText(OtpVerificationActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!getIntent().getBooleanExtra("isFromForgotPass", false)) {
                            startActivity(new Intent(OtpVerificationActivity.this, RegisterAsActivity.class)
                                    .putExtra("AddNewCard", false));
                        } else {
                            startActivity(new Intent(OtpVerificationActivity.this, ResetPasswordActivity.class)
                                    .putExtra("forgotPassUserId", getIntent().getStringExtra("forgotPassUserId")));
                        }
                        finish();
                    }
                }
            }
        });
    }

    private void startTimer() {
        new CountDownTimer(90000, 1000) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
                if (Ssec < 10) {
                    activityOtpVerificationBinding.tvTimer.setText("" + Mmin + ":0" + Ssec);
                } else activityOtpVerificationBinding.tvTimer.setText("" + Mmin + ":" + Ssec);
            }

            public void onFinish() {
                activityOtpVerificationBinding.tvTimer.setText("00:00");
            }

        }.start();
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OtpVerificationActivity.this, "Otp Verified Successfully", Toast.LENGTH_SHORT).show();
                            if (!getIntent().getBooleanExtra("isFromForgotPass", false)) {
                                startActivity(new Intent(OtpVerificationActivity.this, RegisterAsActivity.class)
                                        .putExtra("AddNewCard", false));
                            } else {
                                startActivity(new Intent(OtpVerificationActivity.this, ResetPasswordActivity.class)
                                        .putExtra("forgotPassUserId", getIntent().getStringExtra("forgotPassUserId")));
                            }
                            finish();
                        }
                    }
                });
    }
}