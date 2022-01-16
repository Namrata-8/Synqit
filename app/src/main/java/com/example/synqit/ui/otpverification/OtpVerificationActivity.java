package com.example.synqit.ui.otpverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityOtpVerificationBinding;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.otpverification.model.GetOtpResponse;
import com.example.synqit.ui.otpverification.model.ParamGetOtp;
import com.example.synqit.ui.otpverification.model.ParamVerifyOtp;
import com.example.synqit.ui.otpverification.model.VerifyOtpResponse;
import com.example.synqit.ui.register.RegisterActivity;
import com.example.synqit.ui.register.RegisterViewModel;
import com.example.synqit.utils.SessionManager;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class OtpVerificationActivity extends AppCompatActivity implements OtpVerificationNavigator{

    private ActivityOtpVerificationBinding activityOtpVerificationBinding;
    private OtpVerificationViewModel otpVerificationViewModel;
    private String signUpFrom ="";
    private String verificationid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification);
        activityOtpVerificationBinding.setViewModel(new OtpVerificationViewModel(this));
        activityOtpVerificationBinding.executePendingBindings();
        initViewModel();

        signUpFrom =  getIntent().getStringExtra("BasicRegisterWith");

        if (signUpFrom.equals("Mail")){
            /*if (!SessionManager.readString(OtpVerificationActivity.this, SessionManager.BASIC_REGISTER_EMAIL, "").equals("")) {*/
                ParamGetOtp paramGetOtp = new ParamGetOtp(SessionManager.readString(OtpVerificationActivity.this, SessionManager.BASIC_REGISTER_EMAIL, "harshsutariya1452@gmail.com"));
                otpVerificationViewModel.getOtp(paramGetOtp);
            /*}*/
        }else {

        }

        activityOtpVerificationBinding.firstPinView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                if (!verificationid.equals("")) {
                    ParamVerifyOtp paramVerifyOtp = new ParamVerifyOtp(verificationid, Integer.parseInt(otp));
                    otpVerificationViewModel.verifyOtp(paramVerifyOtp);
                }
            }
        });

    }

    private void initViewModel() {
        otpVerificationViewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);

        otpVerificationViewModel.GetOtp().observe(this, new Observer<GetOtpResponse>() {
            @Override
            public void onChanged(GetOtpResponse getOtpResponse) {
                if (getOtpResponse != null){
                    Log.e("GetOtp", getOtpResponse.toString());
                    startTimer();
                    if (getOtpResponse.isSuccess()){
                        verificationid = getOtpResponse.getVerificationid();
                        SessionManager.writeString(OtpVerificationActivity.this, SessionManager.OTP_VERIFICATION_ID, getOtpResponse.getVerificationid());
                        Toast.makeText(OtpVerificationActivity.this, getOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        otpVerificationViewModel.VerifyOtp().observe(this, new Observer<VerifyOtpResponse>() {
            @Override
            public void onChanged(VerifyOtpResponse verifyOtpResponse) {
                if (verifyOtpResponse != null){
                    Log.e("VerifyOtp", verifyOtpResponse.toString());
                    if (verifyOtpResponse.isSuccess()){
                        SessionManager.writeBoolean(OtpVerificationActivity.this, SessionManager.IS_BASIC_REGISTER, true);
                        Toast.makeText(OtpVerificationActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OtpVerificationActivity.this, RegisterAsActivity.class));
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
}