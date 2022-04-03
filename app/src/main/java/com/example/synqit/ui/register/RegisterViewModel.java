package com.example.synqit.ui.register;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.login.model.ParamSocialLogin;
import com.example.synqit.ui.login.model.SocialLoginResponse;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<BasicRegisterResponse> basicRegisterResponse;
    private RegisterNavigator registerNavigator;
    private MutableLiveData<SocialLoginResponse> googleLoginResponse;
    private MutableLiveData<SocialLoginResponse> facebookLoginResponse;

    public RegisterViewModel(RegisterNavigator registerNavigator){
        this.registerNavigator = registerNavigator;
    }

    public RegisterViewModel() {
        basicRegisterResponse = new MutableLiveData<>();
        this.googleLoginResponse = new MutableLiveData<>();
        this.facebookLoginResponse = new MutableLiveData<>();
    }


    public MutableLiveData<SocialLoginResponse> onGoogleLogin(){
        return googleLoginResponse;
    }

    public MutableLiveData<SocialLoginResponse> onFacebookLogin(){
        return facebookLoginResponse;
    }

    public MutableLiveData<BasicRegisterResponse> getBasicRegisterObserver(){
        return basicRegisterResponse;
    }

    public void basicRegistration(ParamBasicRegister paramBasicRegister, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<BasicRegisterResponse> call = RetrofitClient.getInstance().getApi().basicRegistration(paramBasicRegister);
        call.enqueue(new Callback<BasicRegisterResponse>() {
            @Override
            public void onResponse(Call<BasicRegisterResponse> call, Response<BasicRegisterResponse> response) {
                Log.e("basicRegister?onFailure", response.toString());
                loadingDialog.dismissDialog();
                if(response.isSuccessful()) {
                    basicRegisterResponse.postValue(response.body());
                } else {
                    basicRegisterResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BasicRegisterResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                basicRegisterResponse.postValue(null);
                Log.e("basicRegister?onFailure", t.getMessage());
            }
        });
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

    public void onLoginTextClick(){
        registerNavigator.goToLogin();
    }
    public void onSignupClick(){
        registerNavigator.goToOtpVerification();
    }
    public void onBackClick(){
        registerNavigator.goToBack();
    }
    public void onGoogleClick(){
        registerNavigator.onGoogle();
    }
    public void onAppleClick(){
        registerNavigator.onApple();
    }
    public void onFacebookClick(){
        registerNavigator.onFacebook();
    }
}
