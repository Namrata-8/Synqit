package com.example.synqit.ui.forgotpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.forgotpass.model.ForgotPassUserResponse;
import com.example.synqit.ui.forgotpass.model.ParamGetUserForgotPass;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassViewModel extends ViewModel {

    private ForgotPassNavigator forgotPassNavigator;
    private MutableLiveData<ForgotPassUserResponse> forgotPassUserResponseMutableLiveData;

    public ForgotPassViewModel() {
        forgotPassUserResponseMutableLiveData = new MutableLiveData<>();
    }

    public ForgotPassViewModel(ForgotPassNavigator forgotPassNavigator) {
        this.forgotPassNavigator = forgotPassNavigator;
    }

    public MutableLiveData<ForgotPassUserResponse> getForgotPassUserResponseMutableLiveData(){
        return forgotPassUserResponseMutableLiveData;
    }

    public void getUserForgotPassword(ParamGetUserForgotPass paramGetUserForgotPass, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<ForgotPassUserResponse> call = RetrofitClient.getInstance().getApi().getForgotUserPassword(paramGetUserForgotPass);
        call.enqueue(new Callback<ForgotPassUserResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ForgotPassUserResponse> call, Response<ForgotPassUserResponse> response) {
                Log.e("getForgotUserPassword?onResponse", response.toString());
                loadingDialog.dismissDialog();
                if(response.isSuccessful()) {
                    forgotPassUserResponseMutableLiveData.postValue(response.body());
                } else {
                    forgotPassUserResponseMutableLiveData.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ForgotPassUserResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                forgotPassUserResponseMutableLiveData.postValue(null);
                Log.e("getForgotUserPassword?onFailure", t.getMessage());
            }
        });
    }

    public void onBackClick(){
        forgotPassNavigator.goToBack();
    }
    public void verifyUser(){
        forgotPassNavigator.verifyUser();
    }
}
