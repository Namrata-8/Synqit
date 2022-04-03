package com.example.synqit.ui.otpverification;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.otpverification.model.GetOtpResponse;
import com.example.synqit.ui.otpverification.model.ParamGetOtp;
import com.example.synqit.ui.otpverification.model.ParamVerifyOtp;
import com.example.synqit.ui.otpverification.model.VerifyOtpResponse;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationViewModel extends ViewModel {

    private OtpVerificationNavigator otpVerificationNavigator;
    private MutableLiveData<GetOtpResponse> getOtpResponse;
    private MutableLiveData<VerifyOtpResponse> verifyOtpResponse;

    public OtpVerificationViewModel(OtpVerificationNavigator otpVerificationNavigator) {
        this.otpVerificationNavigator = otpVerificationNavigator;
    }

    public OtpVerificationViewModel() {
        getOtpResponse = new MutableLiveData<>();
        verifyOtpResponse = new MutableLiveData<>();
    }

    public MutableLiveData<GetOtpResponse> GetOtp(){
        return getOtpResponse;
    }

    public MutableLiveData<VerifyOtpResponse> VerifyOtp(){
        return verifyOtpResponse;
    }

    public void getOtp(ParamGetOtp paramGetOtp, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<GetOtpResponse> call = RetrofitClient.getInstance().getApi().getOtp(paramGetOtp);
        call.enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    getOtpResponse.postValue(response.body());
                }else {
                    getOtpResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                getOtpResponse.postValue(null);
                Log.e("getOtp?onFailure", t.getMessage());
            }
        });
    }

    public void verifyOtp(ParamVerifyOtp paramVerifyOtp, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<VerifyOtpResponse> call = RetrofitClient.getInstance().getApi().verifyOtp(paramVerifyOtp);
        call.enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    verifyOtpResponse.postValue(response.body());
                }else {
                    verifyOtpResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                verifyOtpResponse.postValue(null);
                Log.e("verifyOtp?onFailure", t.getMessage());
            }
        });
    }

}
