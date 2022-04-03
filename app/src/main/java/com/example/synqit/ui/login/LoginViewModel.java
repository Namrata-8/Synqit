package com.example.synqit.ui.login;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.login.model.ParamSocialLogin;
import com.example.synqit.ui.login.model.SocialLoginResponse;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private LoginNavigator loginNavigator;
    private MutableLiveData<LoginResponse> loginResponse;
    private MutableLiveData<SocialLoginResponse> googleLoginResponse;
    private MutableLiveData<SocialLoginResponse> facebookLoginResponse;

    public LoginViewModel(LoginNavigator loginNavigator) {
        this.loginNavigator = loginNavigator;
    }

    public LoginViewModel() {
        this.loginResponse = new MutableLiveData<>();
        this.googleLoginResponse = new MutableLiveData<>();
        this.facebookLoginResponse = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> onLogin(){
        return loginResponse;
    }

    public MutableLiveData<SocialLoginResponse> onGoogleLogin(){
        return googleLoginResponse;
    }

    public MutableLiveData<SocialLoginResponse> onFacebookLogin(){
        return facebookLoginResponse;
    }

    public void googleLogin(ParamSocialLogin paramSocialLogin, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SocialLoginResponse> call = RetrofitClient.getInstance().getApi().googleLoginUser(paramSocialLogin);
        call.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    googleLoginResponse.postValue(response.body());
                }else {
                    googleLoginResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                googleLoginResponse.postValue(null);
                Log.e("googleLogin?onFailure", t.getMessage());
            }
        });
    }

    public void facebookLogin(ParamSocialLogin paramSocialLogin, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SocialLoginResponse> call = RetrofitClient.getInstance().getApi().facebookLoginUser(paramSocialLogin);
        call.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    facebookLoginResponse.postValue(response.body());
                }else {
                    facebookLoginResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                facebookLoginResponse.postValue(null);
                Log.e("facebookLogin?onFailure", t.getMessage());
            }
        });
    }

    public void loginUser(ParamLogin paramLogin, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().loginUser(paramLogin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    Log.e("loginUser?onResponse", response.toString());
                    loginResponse.postValue(response.body());
                }else {
                    loginResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                loginResponse.postValue(null);
                Log.e("loginUser?onFailure", t.getMessage());
            }
        });
    }

    public void onBackClick(){
        loginNavigator.goToBack();
    }
    public void onLoginClick(){
        loginNavigator.goToOtpVerification();
    }
    public void onForgotClick(){
        loginNavigator.goToForgotPass();
    }
    public void onGoogleClick(){
        loginNavigator.onGoogle();
    }
    public void onAppleClick(){
        loginNavigator.onApple();
    }
    public void onFacebookClick(){
        loginNavigator.onFacebook();
    }
}
