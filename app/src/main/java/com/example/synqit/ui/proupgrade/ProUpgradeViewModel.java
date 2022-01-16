package com.example.synqit.ui.proupgrade;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProUpgradeViewModel extends ViewModel {

    private MutableLiveData<FullRegisterResponse> fullRegisterResponse;
    private ProUpgradeNavigator proUpgradeNavigator;

    public ProUpgradeViewModel() {
        this.fullRegisterResponse = new MutableLiveData<>();
    }

    public ProUpgradeViewModel(ProUpgradeNavigator proUpgradeNavigator) {
        this.proUpgradeNavigator = proUpgradeNavigator;
    }

    public MutableLiveData<FullRegisterResponse> onFullRegister(){
        return fullRegisterResponse;
    }

    public void fullRegistration(ParamFullRegister paramFullRegister){
        Call<FullRegisterResponse> call = RetrofitClient.getInstance().getApi().fullRegistration(paramFullRegister);
        call.enqueue(new Callback<FullRegisterResponse>() {
            @Override
            public void onResponse(Call<FullRegisterResponse> call, Response<FullRegisterResponse> response) {
                if (response.isSuccessful()){
                    fullRegisterResponse.postValue(response.body());
                }else {
                    fullRegisterResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<FullRegisterResponse> call, Throwable t) {
                fullRegisterResponse.postValue(null);
            }
        });
    }

    public void onContinueClick(){
        proUpgradeNavigator.onContinue();
    }
    public void onBackClick(){
        proUpgradeNavigator.onBack();
    }
    public void onMonthlyClick(){
        proUpgradeNavigator.onPlanMonthly();
    }
    public void onYearlyClick(){
        proUpgradeNavigator.onPlanYearly();
    }
}
