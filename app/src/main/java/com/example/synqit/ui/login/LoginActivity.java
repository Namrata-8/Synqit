package com.example.synqit.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityLoginBinding;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.forgotpass.ForgotPassActivity;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.login.model.ParamSocialLogin;
import com.example.synqit.ui.login.model.SocialLoginResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginNavigator {

    private static final int RC_SIGN_IN = 101;
    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;
    private String loginFrom = "";
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
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(new LoginViewModel(this));
        activityLoginBinding.executePendingBindings();

        callbackManager = CallbackManager.Factory.create();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("207224312411-sbug3s4q59e3pelgirnoluga5mai7ajb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        initViewModel();
        facebookLoginEnable();

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
                                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.onLogin().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    if (loginResponse.isSuccess() && loginResponse.getLoginData() != null) {
                        Log.e("getLoginData", loginResponse.getLoginData().getUserID() + "\n" + new Gson().toJson(loginResponse.getLoginData()));
                        if (loginResponse.getLoginData().getBasicRegistratinUID() != null) {
                            SessionManager.writeString(LoginActivity.this, SessionManager.BR_basicRegistratinUID, loginResponse.getLoginData().getBasicRegistratinUID());
                        }
                        SessionManager.saveLoginData(LoginActivity.this, SessionManager.L_LoginData, loginResponse.getLoginData());
                        SessionManager.writeString(LoginActivity.this, SessionManager.L_userUID, loginResponse.getLoginData().getUserID());
                        SessionManager.writeString(LoginActivity.this, SessionManager.parentUserID, loginResponse.getLoginData().getParentUserID());
                        SessionManager.saveSelectedCardData(LoginActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(loginResponse.getLoginData()));

                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if (!loginResponse.getLoginData().isFullRegistered()) {
                            startActivity(new Intent(LoginActivity.this, RegisterAsActivity.class)
                                    .putExtra("AddNewCard", false));
                        } else {
                            SessionManager.saveAutoLogin(LoginActivity.this, true);
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginViewModel.onGoogleLogin().observe(this, new Observer<SocialLoginResponse>() {
            @Override
            public void onChanged(SocialLoginResponse socialLoginResponse) {
                if (socialLoginResponse.isSuccess()) {
                    SessionManager.writeBoolean(LoginActivity.this, SessionManager.SL_IsLogin, socialLoginResponse.isLogin());
                    SessionManager.saveSocialLoginData(LoginActivity.this, SessionManager.SL_LoginData, socialLoginResponse.getSocialLoginData());
                    SessionManager.writeString(LoginActivity.this, SessionManager.L_userUID, socialLoginResponse.getSocialLoginData().getUserID());
                    SessionManager.writeString(LoginActivity.this, SessionManager.parentUserID, socialLoginResponse.getSocialLoginData().getParentUserID());
                    SessionManager.saveSelectedCardData(LoginActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(socialLoginResponse.getSocialLoginData()));
                    Toast.makeText(LoginActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (!socialLoginResponse.getSocialLoginData().isFullRegistered()) {
                        SessionManager.writeString(LoginActivity.this, SessionManager.BR_basicRegistratinUID, socialLoginResponse.getSocialLoginData().getUserID());
                        startActivity(new Intent(LoginActivity.this, RegisterAsActivity.class)
                                .putExtra("AddNewCard", false));
                    } else {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginViewModel.onFacebookLogin().observe(this, new Observer<SocialLoginResponse>() {
            @Override
            public void onChanged(SocialLoginResponse socialLoginResponse) {
                if (socialLoginResponse.isSuccess()) {
                    SessionManager.writeBoolean(LoginActivity.this, SessionManager.SL_IsLogin, socialLoginResponse.isLogin());
                    SessionManager.saveSocialLoginData(LoginActivity.this, SessionManager.SL_LoginData, socialLoginResponse.getSocialLoginData());
                    SessionManager.writeString(LoginActivity.this, SessionManager.L_userUID, socialLoginResponse.getSocialLoginData().getUserID());
                    SessionManager.writeString(LoginActivity.this, SessionManager.parentUserID, socialLoginResponse.getSocialLoginData().getParentUserID());
                    SessionManager.saveSelectedCardData(LoginActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(socialLoginResponse.getSocialLoginData()));
                    Toast.makeText(LoginActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (!socialLoginResponse.getSocialLoginData().isFullRegistered()) {
                        SessionManager.writeString(LoginActivity.this, SessionManager.BR_basicRegistratinUID, socialLoginResponse.getSocialLoginData().getUserID());
                        startActivity(new Intent(LoginActivity.this, RegisterAsActivity.class)
                                .putExtra("AddNewCard", false));
                    } else {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, socialLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (loginFrom.equals("Mail")) {
            if (activityLoginBinding.etEmail.getText().toString().length() == 0) {
                activityLoginBinding.etEmail.setError(getString(R.string.error_email));
                activityLoginBinding.etEmail.requestFocus();
            } else if (!CommonUtils.isEmailValid(email)) {
                activityLoginBinding.etEmail.setError(getString(R.string.error_valid_email));
                activityLoginBinding.etEmail.requestFocus();
            } else if (/*!CommonUtils.isValidPassword(password)*/activityLoginBinding.etPassword.getText().toString().length() == 0) {
                activityLoginBinding.etPassword.setError(getString(R.string.password_msg));
                activityLoginBinding.etPassword.requestFocus();
            } else {
                ParamLogin paramLogin = new ParamLogin(email, password);

                loginViewModel.loginUser(paramLogin,LoginActivity.this);
            }
        } else if (loginFrom.equals("")) {
            if (activityLoginBinding.etEmail.getText().toString().length() == 0) {
                activityLoginBinding.etEmail.setError(getString(R.string.error_email));
                activityLoginBinding.etEmail.requestFocus();
            } else if (activityLoginBinding.etMobile.getText().toString().length() > 0 && activityLoginBinding.etMobile.getText().toString().length() != 10) {
                activityLoginBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityLoginBinding.etMobile.requestFocus();
            }
        } else {
            if (activityLoginBinding.etMobile.getText().toString().length() > 0 && activityLoginBinding.etMobile.getText().toString().length() != 10) {
                activityLoginBinding.etMobile.setError(getString(R.string.error_enter_mobile));
                activityLoginBinding.etMobile.requestFocus();
            } else if (/*!CommonUtils.isValidPassword(password)*/activityLoginBinding.etPassword.getText().toString().length() == 0) {
                activityLoginBinding.etPassword.setError(getString(R.string.password_msg));
                activityLoginBinding.etPassword.requestFocus();
            } else {
                ParamLogin paramLogin = new ParamLogin("+" + countryCode + mobile, password);
                loginViewModel.loginUser(paramLogin,LoginActivity.this);
            }
        }
    }

    @Override
    public void goToForgotPass() {
        startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
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
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
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
                loginViewModel.googleLogin(paramSocialLogin,LoginActivity.this);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("GoogleSignin", "Google sign in failed", e);
            }
        }
    }
}