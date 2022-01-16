package com.example.synqit.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityRegisterBinding;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.ui.otpverification.OtpVerificationActivity;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.utils.CommonUtils;
import com.example.synqit.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity implements RegisterNavigator {

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;
    private String signUpFrom = "";
    private String countryCode = "";
    private String email = "";
    private String mobile = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        activityRegisterBinding.setViewModel(new RegisterViewModel(this));
        activityRegisterBinding.executePendingBindings();
        initViewModel();

        activityRegisterBinding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpFrom = "Mail";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityRegisterBinding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpFrom = "Mobile";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityRegisterBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpFrom = "Mobile";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getBasicRegisterObserver().observe(this, new Observer<BasicRegisterResponse>() {
            @Override
            public void onChanged(BasicRegisterResponse basicRegisterResponse) {
                if (basicRegisterResponse != null){
                    Log.e("getBasicRegister", basicRegisterResponse +"");
                    if (basicRegisterResponse.isSuccess()){
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_ID, basicRegisterResponse.getRegisterData().getId());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_EMAIL, basicRegisterResponse.getRegisterData().getEmail());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_MOBILE, basicRegisterResponse.getRegisterData().getMobileNumber());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_PASSWORD, basicRegisterResponse.getRegisterData().getPassword());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_WITH, signUpFrom);
                        Toast.makeText(RegisterActivity.this, basicRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("BasicRegisterWith", signUpFrom);
                        startActivity(intent);
                    }
                }else {
                    Log.e("Error", "Error");
                }
            }
        });
    }

    @Override
    public void goToLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void goToOtpVerification() {
        countryCode = activityRegisterBinding.countryCodePicker.getSelectedCountryCode();
        email = activityRegisterBinding.etEmail.getText().toString();
        mobile = activityRegisterBinding.etMobile.getText().toString();
        password = activityRegisterBinding.etPassword.getText().toString();

        if (signUpFrom.equals("Mail")){
            if ( activityRegisterBinding.etEmail.getText().toString().length() == 0){
                activityRegisterBinding.etEmail.setError(getString(R.string.error_email));
                activityRegisterBinding.etEmail.requestFocus();
            } else if (!CommonUtils.isEmailValid(email)){
                activityRegisterBinding.etEmail.setError(getString(R.string.error_valid_email));
                activityRegisterBinding.etEmail.requestFocus();
            }else {
                ParamBasicRegister paramBasicRegister = new ParamBasicRegister(email, "+"+countryCode+mobile, password);
                registerViewModel.basicRegistration(paramBasicRegister);
            }
        }else {
            if (activityRegisterBinding.etMobile.getText().toString().length() > 0 && activityRegisterBinding.etMobile.getText().toString().length() != 10){
                activityRegisterBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityRegisterBinding.etMobile.requestFocus();
            }else if (!CommonUtils.isValidPassword(password)){
                activityRegisterBinding.etPassword.setError(getString(R.string.password_msg));
                activityRegisterBinding.etPassword.requestFocus();
            }else {
                ParamBasicRegister paramBasicRegister = new ParamBasicRegister(email, "+"+countryCode+mobile, password);
                registerViewModel.basicRegistration(paramBasicRegister);
            }
        }
        /*Intent intent = new Intent(RegisterActivity.this, OtpVerificationActivity.class);
        intent.putExtra("BasicRegisterWith", "Mail");
        startActivity(intent);*/

    }

    @Override
    public void goToBack() {
        finish();

    }
}