package com.example.synqit.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityLoginBinding;
import com.example.synqit.ui.DashboardActivity;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.register.RegisterViewModel;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.utils.CommonUtils;
import com.example.synqit.utils.SessionManager;

public class LoginActivity extends AppCompatActivity implements LoginNavigator{

    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;
    private String loginFrom = "";
    private String countryCode = "";
    private String email = "";
    private String mobile = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(new LoginViewModel(this));
        activityLoginBinding.executePendingBindings();
        initViewModel();

        activityLoginBinding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginFrom = "Mail";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityLoginBinding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginFrom = "Mobile";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityLoginBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginFrom = "Mobile";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.onLogin().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null){
                    if (loginResponse.isSuccess()){
                        SessionManager.writeString(LoginActivity.this, SessionManager.USER_UID, loginResponse.getLoginData().getUserUID());
                        SessionManager.writeString(LoginActivity.this, SessionManager.BASIC_REGISTER_ID, loginResponse.getLoginData().getUserUID());
                        SessionManager.writeString(LoginActivity.this, SessionManager.EMAIL, loginResponse.getLoginData().getEmail());
                        SessionManager.writeString(LoginActivity.this, SessionManager.PASSWORD, loginResponse.getLoginData().getPassword());
                        SessionManager.writeBoolean(LoginActivity.this, SessionManager.IS_BUSINESS, loginResponse.getLoginData().isBusiness());
                        SessionManager.writeString(LoginActivity.this, SessionManager.DISPLAY_NAME, loginResponse.getLoginData().getDisplayName());
                        SessionManager.writeString(LoginActivity.this, SessionManager.DOB, loginResponse.getLoginData().getDob());
                        SessionManager.writeString(LoginActivity.this, SessionManager.GENDER, loginResponse.getLoginData().getGender());
                        SessionManager.writeString(LoginActivity.this, SessionManager.BUSINESS_TYPE, String.valueOf(loginResponse.getLoginData().getBusinessType()));
                        SessionManager.writeString(LoginActivity.this, SessionManager.LOGO_FILE_URL, loginResponse.getLoginData().getDisplayPicture());
                        SessionManager.writeString(LoginActivity.this, SessionManager.COVER_FILE_URL, loginResponse.getLoginData().getCoverPicture());
                        SessionManager.writeString(LoginActivity.this, SessionManager.PLAN,String.valueOf(loginResponse.getLoginData().getPlan()));
                        SessionManager.writeString(LoginActivity.this, SessionManager.MOBILE_NO, loginResponse.getLoginData().getMobileNumber());
                        SessionManager.writeBoolean(LoginActivity.this, SessionManager.IS_PRIVATE, loginResponse.getLoginData().isPrivate());
                        SessionManager.writeBoolean(LoginActivity.this, SessionManager.IS_FULL_REGISTERED, loginResponse.getLoginData().isFullRegistered());
                        SessionManager.writeBoolean(LoginActivity.this, SessionManager.Is_BASIC_LOGIN, true);

                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if (!loginResponse.getLoginData().isFullRegistered()){
                            startActivity(new Intent(LoginActivity.this, RegisterAsActivity.class));
                        }else {
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void goToOtpVerification() {
        countryCode = activityLoginBinding.countryCodePicker.getSelectedCountryCode();
        email = activityLoginBinding.etEmail.getText().toString();
        mobile = activityLoginBinding.etMobile.getText().toString();
        password = activityLoginBinding.etPassword.getText().toString();

        if (loginFrom.equals("Mail")){
            if ( activityLoginBinding.etEmail.getText().toString().length() == 0){
                activityLoginBinding.etEmail.setError(getString(R.string.error_email));
                activityLoginBinding.etEmail.requestFocus();
            } else if (!CommonUtils.isEmailValid(email)){
                activityLoginBinding.etEmail.setError(getString(R.string.error_valid_email));
                activityLoginBinding.etEmail.requestFocus();
            }else {
                ParamLogin paramLogin = new ParamLogin(email, password);
                loginViewModel.loginUser(paramLogin);
            }
        }else {
            if (activityLoginBinding.etMobile.getText().toString().length() > 0 && activityLoginBinding.etMobile.getText().toString().length() != 10){
                activityLoginBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityLoginBinding.etMobile.requestFocus();
            }else if (!CommonUtils.isValidPassword(password)){
                activityLoginBinding.etPassword.setError(getString(R.string.password_msg));
                activityLoginBinding.etPassword.requestFocus();
            }else {
                ParamLogin paramLogin = new ParamLogin("+"+countryCode+mobile, password);
                loginViewModel.loginUser(paramLogin);
            }
        }
    }

    @Override
    public void goToForgotPass() {

    }

    @Override
    public void onApple() {

    }

    @Override
    public void onGoogle() {

    }

    @Override
    public void onFacebook() {

    }
}