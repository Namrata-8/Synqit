package com.example.synqit.ui.proupgrade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.ui.proupgrade.model.PlansData;
import com.example.synqit.ui.proupgrade.model.PlansResponse;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProUpgradeViewModel extends ViewModel {

    private MutableLiveData<FullRegisterResponse> fullRegisterResponse;
    private MutableLiveData<InsertCardResponse> insertCardResponse;
    private MutableLiveData<InsertCardResponse> updateCardResponse;
    private ProUpgradeNavigator proUpgradeNavigator;
    private String id = "";
    private String billingType = "";
    private String billingDescription = "";
    private String planPrice = "";
    private String freeTrial = "";
    private String specialTag = "";
    private boolean isDeleted = false;
    public MutableLiveData<ArrayList<ProUpgradeViewModel>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<ProUpgradeViewModel> arrayList;
    private List<PlansData> plansList;

    public String getId() {
        return id;
    }

    public ProUpgradeViewModel() {
        this.updateCardResponse = new MutableLiveData<>();
        this.fullRegisterResponse = new MutableLiveData<>();
        this.insertCardResponse = new MutableLiveData<>();
    }

    public ProUpgradeViewModel(ProUpgradeNavigator proUpgradeNavigator) {
        this.proUpgradeNavigator = proUpgradeNavigator;
    }

    public String getBillingType() {
        return billingType;
    }

    public String getBillingDescription() {
        return billingDescription;
    }

    public String getFreeTrial() {
        return freeTrial;
    }

    public String getSpecialTag() {
        return specialTag;
    }

    public ProUpgradeViewModel(PlansData plansData){
        this.id = plansData.getId();
        this.billingType = plansData.getBillingType();
        this.billingDescription = plansData.getBillingDescription();
        this.planPrice = plansData.getPlanPrice();
        this.freeTrial = plansData.getFreeTrial();
        this.specialTag = plansData.getSpecialTag();
        this.isDeleted = plansData.isDeleted();
    }

    public String getPlanPrice() {
        return planPrice;
    }


    public MutableLiveData<FullRegisterResponse> onFullRegister(){
        return fullRegisterResponse;
    }

    public void fullRegistration(ParamFullRegister paramFullRegister, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<FullRegisterResponse> call = RetrofitClient.getInstance().getApi().fullRegistration(paramFullRegister);
        call.enqueue(new Callback<FullRegisterResponse>() {
            @Override
            public void onResponse(Call<FullRegisterResponse> call, Response<FullRegisterResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    fullRegisterResponse.postValue(response.body());
                }else {
                    fullRegisterResponse.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<FullRegisterResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("fullRegistration?onFailure", t.getMessage());
                fullRegisterResponse.postValue(null);
            }
        });
    }

    public MutableLiveData<InsertCardResponse> onInsertCard(){
        return insertCardResponse;
    }

    public void insertCard(ParamInsertCard paramInsertCard, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<InsertCardResponse> call = RetrofitClient.getInstance().getApi().insertCard(paramInsertCard);
        call.enqueue(new Callback<InsertCardResponse>() {
            @Override
            public void onResponse(Call<InsertCardResponse> call, Response<InsertCardResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    insertCardResponse.postValue(response.body());
                }else {
                    insertCardResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<InsertCardResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("insertCard?onFailure", t.getMessage());
                insertCardResponse.postValue(null);
            }
        });
    }

    public MutableLiveData<ArrayList<ProUpgradeViewModel>> getPlansData(Activity activity) {
        arrayList=new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<PlansResponse> call = RetrofitClient.getInstance().getApi().getPlans();
        call.enqueue(new Callback<PlansResponse>() {
            @Override
            public void onResponse(Call<PlansResponse> call, Response<PlansResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    plansList = new ArrayList<>();
                    plansList = response.body().getPlansData();
                    for (int i = 0; i < plansList.size(); i++){
                        PlansData plansData = plansList.get(i);
                        ProUpgradeViewModel proUpgradeViewModel = new ProUpgradeViewModel(plansData);
                        arrayList.add(proUpgradeViewModel);
                        mutableLiveData.setValue(arrayList);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlansResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getPlans?onFailure", t.getMessage());
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<InsertCardResponse> onUpdateCard(){
        return updateCardResponse;
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

    public void onContinueClick(){
        proUpgradeNavigator.onContinue();
    }
    public void onBackClick(){
        proUpgradeNavigator.onBack();
    }
}
