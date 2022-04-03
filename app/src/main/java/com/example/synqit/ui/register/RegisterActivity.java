package com.example.synqit.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityRegisterBinding;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.ui.login.model.ParamSocialLogin;
import com.example.synqit.ui.login.model.SocialLoginResponse;
import com.example.synqit.ui.otpverification.OtpVerificationActivity;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.utils.CommonUtils;
import com.example.synqit.utils.SessionManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity implements RegisterNavigator {

    private static final int RC_SIGN_IN = 101;
    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;
    private String signUpFrom = "";
    private String countryCode = "";
    private String email = "";
    private String mobile = "";
    private String password = "";
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        activityRegisterBinding.setViewModel(new RegisterViewModel(this));
        activityRegisterBinding.executePendingBindings();
        callbackManager = CallbackManager.Factory.create();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("207224312411-sbug3s4q59e3pelgirnoluga5mai7ajb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initViewModel();
        facebookLoginEnable();

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
    }

    private void facebookLoginEnable() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("facebbokData", jsonObject + "\n" +graphResponse +"\n"+ loginResult);
                                    /*ParamSocialLogin paramSocialLogin = new ParamSocialLogin(AccessToken.getCurrentAccessToken().getToken());
                                    showLoading(LoginActivity.this);
                                    loginViewModel.facebookLogin(paramSocialLogin);*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
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
                        SessionManager.saveBasicRegisterData(RegisterActivity.this, SessionManager.BR_BasicRegisterData, basicRegisterResponse.getRegisterData());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BR_basicRegistratinUID, basicRegisterResponse.getRegisterData().getId());
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BASIC_REGISTER_WITH, signUpFrom);
                        Toast.makeText(RegisterActivity.this, basicRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, OtpVerificationActivity.class)
                                .putExtra("isFromForgotPass", false)
                                .putExtra("forgotPassUserId", "")
                                .putExtra("forgotPassEmail", "")
                                .putExtra("forgotPassMobile", "");
                        intent.putExtra("BasicRegisterWith", signUpFrom);
                        startActivity(intent);
                    }else {
                        Toast.makeText(RegisterActivity.this, basicRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Error", "Error");
                }
            }
        });

        registerViewModel.onGoogleLogin().observe(this, new Observer<SocialLoginResponse>() {
            @Override
            public void onChanged(SocialLoginResponse socialLoginResponse) {
                if (socialLoginResponse.isSuccess()) {
                    SessionManager.writeBoolean(RegisterActivity.this, SessionManager.SL_IsLogin, socialLoginResponse.isLogin());
                    SessionManager.saveSocialLoginData(RegisterActivity.this, SessionManager.SL_LoginData, socialLoginResponse.getSocialLoginData());
                    SessionManager.writeString(RegisterActivity.this, SessionManager.L_userUID, socialLoginResponse.getSocialLoginData().getUserID());
                    SessionManager.writeString(RegisterActivity.this, SessionManager.parentUserID, socialLoginResponse.getSocialLoginData().getParentUserID());
                    SessionManager.saveSelectedCardData(RegisterActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(socialLoginResponse.getSocialLoginData()));
                    Toast.makeText(RegisterActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (!socialLoginResponse.getSocialLoginData().isFullRegistered()) {
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BR_basicRegistratinUID, socialLoginResponse.getSocialLoginData().getUserID());
                        startActivity(new Intent(RegisterActivity.this, RegisterAsActivity.class)
                                .putExtra("AddNewCard", false));
                    } else {
                        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerViewModel.onFacebookLogin().observe(this, new Observer<SocialLoginResponse>() {
            @Override
            public void onChanged(SocialLoginResponse socialLoginResponse) {
                if (socialLoginResponse.isSuccess()) {
                    SessionManager.writeBoolean(RegisterActivity.this, SessionManager.SL_IsLogin, socialLoginResponse.isLogin());
                    SessionManager.saveSocialLoginData(RegisterActivity.this, SessionManager.SL_LoginData, socialLoginResponse.getSocialLoginData());
                    SessionManager.writeString(RegisterActivity.this, SessionManager.L_userUID, socialLoginResponse.getSocialLoginData().getUserID());
                    SessionManager.writeString(RegisterActivity.this, SessionManager.parentUserID, socialLoginResponse.getSocialLoginData().getParentUserID());
                    SessionManager.saveSelectedCardData(RegisterActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(socialLoginResponse.getSocialLoginData()));
                    Toast.makeText(RegisterActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (!socialLoginResponse.getSocialLoginData().isFullRegistered()) {
                        SessionManager.writeString(RegisterActivity.this, SessionManager.BR_basicRegistratinUID, socialLoginResponse.getSocialLoginData().getUserID());
                        startActivity(new Intent(RegisterActivity.this, RegisterAsActivity.class)
                                .putExtra("AddNewCard", false));
                    } else {
                        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            }else if (/*!CommonUtils.isValidPassword(password)*/activityRegisterBinding.etPassword.getText().toString().length() == 0){
                activityRegisterBinding.etPassword.setError(getString(R.string.password_msg));
                activityRegisterBinding.etPassword.requestFocus();
            }else {
                ParamBasicRegister paramBasicRegister = new ParamBasicRegister(email, "", password);
                registerViewModel.basicRegistration(paramBasicRegister, RegisterActivity.this);
            }
        }else if (signUpFrom.equals("")){
            if ( activityRegisterBinding.etEmail.getText().toString().length() == 0){
                activityRegisterBinding.etEmail.setError(getString(R.string.error_email));
                activityRegisterBinding.etEmail.requestFocus();
            } else if (activityRegisterBinding.etMobile.getText().toString().length() > 0 && activityRegisterBinding.etMobile.getText().toString().length() != 10){
                activityRegisterBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityRegisterBinding.etMobile.requestFocus();
            }
        } else {
            if (activityRegisterBinding.etMobile.getText().toString().length() > 0 && activityRegisterBinding.etMobile.getText().toString().length() != 10){
                activityRegisterBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityRegisterBinding.etMobile.requestFocus();
            }else if (/*!CommonUtils.isValidPassword(password)*/activityRegisterBinding.etPassword.getText().toString().length() == 0){
                activityRegisterBinding.etPassword.setError(getString(R.string.password_msg));
                activityRegisterBinding.etPassword.requestFocus();
            }else {
                ParamBasicRegister paramBasicRegister = new ParamBasicRegister("", "+"+countryCode+mobile, password);
                registerViewModel.basicRegistration(paramBasicRegister, RegisterActivity.this);
            }
        }

    }

    @Override
    public void goToBack() {
        finish();

    }

    @Override
    public void onApple() {

    }

    @Override
    public void onGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onFacebook() {

        LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("GoogleId", "firebaseAuthWithGoogle:" + account.getIdToken());
                ParamSocialLogin paramSocialLogin = new ParamSocialLogin(account.getIdToken());
                registerViewModel.googleLogin(paramSocialLogin,RegisterActivity.this);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("GoogleSignin", "Google sign in failed", e);
            }
        }
    }
}