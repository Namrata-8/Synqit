package com.example.synqit.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private LoginNavigator loginNavigator;
    private MutableLiveData<LoginResponse> loginResponse;

    public LoginViewModel(LoginNavigator loginNavigator) {
        this.loginNavigator = loginNavigator;
    }

    public LoginViewModel() {
        this.loginResponse = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> onLogin(){
        return loginResponse;
    }

    public void loginUser(ParamLogin paramLogin){
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().loginUser(paramLogin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.postValue(response.body());
                }else {
                    loginResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.postValue(null);
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
