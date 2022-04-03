package com.example.synqit.ui.resetpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.forgotpass.model.ForgotPassUserResponse;
import com.example.synqit.ui.forgotpass.model.ParamGetUserForgotPass;
import com.example.synqit.ui.resetpass.model.ParamResetPass;
import com.example.synqit.ui.resetpass.model.ResetPassResponse;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends ViewModel {

    private ResetPassNavigator resetPassNavigator;
    private MutableLiveData<ResetPassResponse> resetPassNavigatorMutableLiveData;

    public ResetPasswordViewModel(ResetPassNavigator resetPassNavigator) {
        this.resetPassNavigator = resetPassNavigator;
    }

    public ResetPasswordViewModel() {
        resetPassNavigatorMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ResetPassResponse> getResetPasswordResponse(){
        return resetPassNavigatorMutableLiveData;
    }

    public void getResetPassword(ParamResetPass paramResetPass, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<ResetPassResponse> call = RetrofitClient.getInstance().getApi().getResetPassword(paramResetPass);
        call.enqueue(new Callback<ResetPassResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                Log.e("getResetPassword?onResponse", response.toString());
                loadingDialog.dismissDialog();
                if(response.isSuccessful()) {
                    resetPassNavigatorMutableLiveData.postValue(response.body());
                } else {
                    resetPassNavigatorMutableLiveData.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ResetPassResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                resetPassNavigatorMutableLiveData.postValue(null);
                Log.e("getResetPassword?onFailure", t.getMessage());
            }
        });
    }

    public void onBackClick(){
        resetPassNavigator.goToBack();
    }
    public void onResetPassword(){
        resetPassNavigator.resetPassword();
    }
}
