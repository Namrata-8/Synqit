package com.example.synqit.ui.register;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<BasicRegisterResponse> basicRegisterResponse;
    private RegisterNavigator registerNavigator;

    public RegisterViewModel(RegisterNavigator registerNavigator){
        this.registerNavigator = registerNavigator;
    }

    public RegisterViewModel() {
        basicRegisterResponse = new MutableLiveData<>();
    }

    public MutableLiveData<BasicRegisterResponse> getBasicRegisterObserver(){
        return basicRegisterResponse;
    }

    public void basicRegistration(ParamBasicRegister paramBasicRegister){
        Call<BasicRegisterResponse> call = RetrofitClient.getInstance().getApi().basicRegistration(paramBasicRegister);
        call.enqueue(new Callback<BasicRegisterResponse>() {
            @Override
            public void onResponse(Call<BasicRegisterResponse> call, Response<BasicRegisterResponse> response) {
                if(response.isSuccessful()) {
                    basicRegisterResponse.postValue(response.body());
                } else {
                    basicRegisterResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BasicRegisterResponse> call, Throwable t) {
                basicRegisterResponse.postValue(null);
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
}
