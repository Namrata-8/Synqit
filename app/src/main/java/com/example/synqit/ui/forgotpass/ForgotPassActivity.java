package com.example.synqit.ui.forgotpass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityForgotPassBinding;
import com.example.synqit.ui.forgotpass.model.ForgotPassUserResponse;
import com.example.synqit.ui.forgotpass.model.ParamGetUserForgotPass;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.otpverification.OtpVerificationActivity;
import com.example.synqit.ui.register.RegisterViewModel;
import com.example.synqit.utils.CommonUtils;
import com.example.synqit.utils.SessionManager;

public class ForgotPassActivity extends AppCompatActivity implements ForgotPassNavigator{

    private ForgotPassViewModel forgotPassViewModel;
    private ActivityForgotPassBinding activityForgotPassBinding;
    private String countryCode = "";
    private String email = "";
    private String mobile = "";
    private String verifyFrom = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityForgotPassBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pass);
        activityForgotPassBinding.setViewModel(new ForgotPassViewModel(this));
        activityForgotPassBinding.executePendingBindings();
        initViewModel();

        activityForgotPassBinding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyFrom = "Mail";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityForgotPassBinding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyFrom = "Mobile";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        forgotPassViewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);
        forgotPassViewModel.getForgotPassUserResponseMutableLiveData().observe(this, new Observer<ForgotPassUserResponse>() {
            @Override
            public void onChanged(ForgotPassUserResponse forgotPassUserResponse) {
                if (forgotPassUserResponse != null){
                    if (forgotPassUserResponse.isSuccess()){
                        if (forgotPassUserResponse.isAvailable()){
                            startActivity(new Intent(ForgotPassActivity.this, OtpVerificationActivity.class)
                                    .putExtra("BasicRegisterWith", verifyFrom)
                                    .putExtra("isFromForgotPass", true)
                                    .putExtra("forgotPassUserId", forgotPassUserResponse.getUserID())
                                    .putExtra("forgotPassEmail", forgotPassUserResponse.getEmail())
                                    .putExtra("forgotPassMobile", forgotPassUserResponse.getMobileNumber()));
                        }else {
                            Toast.makeText(ForgotPassActivity.this, "Given Detail is not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void goToBack() {
        finish();
    }

    @Override
    public void verifyUser() {
        countryCode = activityForgotPassBinding.countryCodePicker.getSelectedCountryCode();
        email = activityForgotPassBinding.etEmail.getText().toString();
        mobile = activityForgotPassBinding.etMobile.getText().toString();

        if (verifyFrom.equals("")) {
            if (activityForgotPassBinding.etEmail.getText().toString().length() == 0) {
                activityForgotPassBinding.etEmail.setError(getString(R.string.error_email));
                activityForgotPassBinding.etEmail.requestFocus();
            } else if (activityForgotPassBinding.etMobile.getText().toString().length() > 0 && activityForgotPassBinding.etMobile.getText().toString().length() != 10) {
                activityForgotPassBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityForgotPassBinding.etMobile.requestFocus();
            }
        }else if (verifyFrom.equals("Mail")) {
            if (activityForgotPassBinding.etEmail.getText().toString().length() == 0) {
                activityForgotPassBinding.etEmail.setError(getString(R.string.error_email));
                activityForgotPassBinding.etEmail.requestFocus();
            } else if (!CommonUtils.isEmailValid(email)) {
                activityForgotPassBinding.etEmail.setError(getString(R.string.error_valid_email));
                activityForgotPassBinding.etEmail.requestFocus();
            } else {
                ParamGetUserForgotPass paramGetUserForgotPass = new ParamGetUserForgotPass(email);
                forgotPassViewModel.getUserForgotPassword(paramGetUserForgotPass, ForgotPassActivity.this);
            }
        }else {
            if (activityForgotPassBinding.etMobile.getText().toString().length() > 0 && activityForgotPassBinding.etMobile.getText().toString().length() != 10) {
                activityForgotPassBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityForgotPassBinding.etMobile.requestFocus();
            }else {
                ParamGetUserForgotPass paramGetUserForgotPass = new ParamGetUserForgotPass("+" + countryCode + mobile);
                forgotPassViewModel.getUserForgotPassword(paramGetUserForgotPass,ForgotPassActivity.this);
            }
        }
    }
}