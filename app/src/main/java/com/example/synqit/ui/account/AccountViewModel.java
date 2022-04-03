package com.example.synqit.ui.account;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountViewModel extends ViewModel {
    private AccountNavigator accountNavigator;
    public String countryName = "";
    public String gender = "";
    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> mutableLiveDataCountry = new MutableLiveData<>();
    private ArrayList<BusinessFragment3ViewModel> arrayListCountry;
    private MutableLiveData<InsertCardResponse> updateCardResponse;

    public AccountViewModel(AccountNavigator accountNavigator) {
        this.accountNavigator = accountNavigator;
    }

    public AccountViewModel() {
        this.updateCardResponse = new MutableLiveData<>();
    }

    public MutableLiveData<InsertCardResponse> onUpdateCard(){
        return updateCardResponse;
    }

    public AccountViewModel(String countryName, String gender) {
        this.countryName = countryName;
        this.gender = gender;
    }

    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> getCountryList(List<String> countryList){
        arrayListCountry = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++){
            BusinessFragment3ViewModel businessFragment3ViewModel = new BusinessFragment3ViewModel(countryList.get(i));
            arrayListCountry.add(businessFragment3ViewModel);
            mutableLiveDataCountry.setValue(arrayListCountry);
        }
        return mutableLiveDataCountry;
    }

    public void onBackClick(){
        accountNavigator.onBackClick();
    }

    public void onGenderClick(){
        accountNavigator.onGenderClick();
    }

    public void onCountryClick(){
        accountNavigator.onCountryClick();
    }

    public void onDateClick(){
        accountNavigator.onDateClick();
    }

    public void onContinueClick(){
        accountNavigator.onContinueClick();
    }

    public void updateCard(ParamInsertCard paramInsertCard, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<InsertCardResponse> call = RetrofitClient.getInstance().getApi().updateCard(paramInsertCard);
        call.enqueue(new Callback<InsertCardResponse>() {
            @Override
            public void onResponse(Call<InsertCardResponse> call, Response<InsertCardResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    updateCardResponse.postValue(response.body());
                }else {
                    updateCardResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<InsertCardResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("updateCard?onFailure", t.getMessage());
                updateCardResponse.postValue(null);
            }
        });
    }
}
