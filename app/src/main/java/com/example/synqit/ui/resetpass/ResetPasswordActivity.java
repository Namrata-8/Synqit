package com.example.synqit.ui.resetpass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityResetPasswordBinding;
import com.example.synqit.ui.forgotpass.ForgotPassViewModel;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.ui.otpverification.OtpVerificationViewModel;
import com.example.synqit.ui.resetpass.model.ParamResetPass;
import com.example.synqit.ui.resetpass.model.ResetPassResponse;
import com.example.synqit.utils.SessionManager;
import com.facebook.login.Login;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPassNavigator{

    private ResetPasswordViewModel resetPasswordViewModel;
    private ActivityResetPasswordBinding activityResetPasswordBinding;
    private String password = "";
    private String confirmPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityResetPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        activityResetPasswordBinding.setViewModel(new ResetPasswordViewModel(this));
        activityResetPasswordBinding.executePendingBindings();
        initViewModel();
    }

    private void initViewModel() {
        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        resetPasswordViewModel.getResetPasswordResponse().observe(this, new Observer<ResetPassResponse>() {
            @Override
            public void onChanged(ResetPassResponse resetPassResponse) {
                if (resetPassResponse != null){
                    if (resetPassResponse.isSuccess()){
                        Toast.makeText(ResetPasswordActivity.this, resetPassResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(ResetPasswordActivity.this, resetPassResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void resetPassword() {
        password = activityResetPasswordBinding.etPassword.getText().toString();
        confirmPassword = activityResetPasswordBinding.etConfirmPassword.getText().toString();

        if (/*!CommonUtils.isValidPassword(password)*/activityResetPasswordBinding.etPassword.getText().toString().length() == 0){
            activityResetPasswordBinding.etPassword.setError(getString(R.string.password_msg));
            activityResetPasswordBinding.etPassword.requestFocus();
        }else if (/*!CommonUtils.isValidPassword(password)*/activityResetPasswordBinding.etConfirmPassword.getText().toString().length() == 0){
            activityResetPasswordBinding.etConfirmPassword.setError(getString(R.string.password_msg));
            activityResetPasswordBinding.etConfirmPassword.requestFocus();
        }else if (!password.equals(confirmPassword)){
            activityResetPasswordBinding.etConfirmPassword.setError(getString(R.string.password_msg_2));
            activityResetPasswordBinding.etConfirmPassword.requestFocus();
        }else {
            ParamResetPass paramResetPass = new ParamResetPass(getIntent().getStringExtra("forgotPassUserId"), password);
            resetPasswordViewModel.getResetPassword(paramResetPass, ResetPasswordActivity.this);
        }
    }
}