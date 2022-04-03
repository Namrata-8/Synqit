package com.example.synqit.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.homefragment.HomeFragmentNavigator;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.SubScribeListResponse;
import com.example.synqit.fragments.homefragment.model.SubscribedData;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.dashboard.model.CardResponse;
import com.example.synqit.ui.dashboard.model.ParamGetCard;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {

    public String parentUserID="";
    public String displayName="";
    public String profileType="";
    public String profileName="";
    public String businessType="";
    public String country="";
    public boolean isBusiness = false;
    private DashboardNavigator dashboardNavigator;
    public MutableLiveData<ArrayList<DashboardViewModel>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<DashboardViewModel> arrayList;
    private List<CardData> cardDataList;
    private CardData cardData;
    public MutableLiveData<ArrayList<DashboardViewModel>> userLinkLiveData = new MutableLiveData<>();
    private ArrayList<DashboardViewModel> arrayList2;
    private List<SubscribedData> subscribedDataList;
    private SubscribedData subscribedData;


    public DashboardViewModel(DashboardNavigator dashboardNavigator) {
        this.dashboardNavigator = dashboardNavigator;
    }

    public DashboardViewModel() {
    }

    public DashboardViewModel(CardData cardData) {
        this.cardData = cardData;
        this.parentUserID = cardData.getParentUserID();
        this.displayName = cardData.getDisplayName();
        this.profileName = cardData.getProfileName();
        this.businessType = String.valueOf(cardData.getBusinessType());
        this.isBusiness = cardData.isBusiness();
        this.country = cardData.getCountry();
        if (cardData.isBusiness()) {
            this.profileType = "Business";
        }else {
            this.profileType = "Individual";
        }
    }

    public CardData getCardData() {
        return cardData;
    }

    public MutableLiveData<ArrayList<DashboardViewModel>> getCardData(ParamGetCard paramGetCard, Activity activity) {
        arrayList=new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<CardResponse> call = RetrofitClient.getInstance().getApi().getCard(paramGetCard);
        call.enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call, Response<CardResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    cardDataList = new ArrayList<>();
                    cardDataList = response.body().getData();
                    for (int i = 0; i < cardDataList.size(); i++){
                        CardData cardData = cardDataList.get(i);
                        DashboardViewModel dashboardViewModel = new DashboardViewModel(cardData);
                        arrayList.add(dashboardViewModel);
                    }

                    mutableLiveData.setValue(arrayList);
                }
            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getCard?onFailure", t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public DashboardViewModel(SubscribedData subscribedData) {
        this.subscribedData = subscribedData;
    }

    public SubscribedData getSubscribedData() {
        return subscribedData;
    }

    public MutableLiveData<ArrayList<DashboardViewModel>> getUserLinkLiveData(ParamGetSubscribedList paramGetSubscribedList, Activity activity){
        arrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SubScribeListResponse> call = RetrofitClient.getInstance().getApi().getSubscribedList(paramGetSubscribedList);
        call.enqueue(new Callback<SubScribeListResponse>() {
            @Override
            public void onResponse(Call<SubScribeListResponse> call, Response<SubScribeListResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    subscribedDataList = new ArrayList<>();
                    subscribedDataList = response.body().getSubscribedData();
                    for (int i=0; i<subscribedDataList.size(); i++){
                        if (!subscribedDataList.get(i).isDisabled()) {
                            SubscribedData subscribedData = subscribedDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(subscribedData);
                            arrayList.add(dashboardViewModel);
                        }else {
                            continue;
                        }
                    }
                }
                userLinkLiveData.setValue(arrayList);
            }

            @Override
            public void onFailure(Call<SubScribeListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getBusinesses?onFailure", t.getMessage());
                userLinkLiveData.setValue(arrayList);
            }
        });

        return userLinkLiveData;
    }

    public void onAddCardClick(){
        dashboardNavigator.addCard();
    }
    public void onDeleteCardClick(){
        dashboardNavigator.deleteCard();
    }
    public void onConnectClick(){
        dashboardNavigator.onConnectClick();
    }
    public void onEyeClick(){
        dashboardNavigator.onEyeClick();
    }
    public void onUploadClick(){
        dashboardNavigator.onUploadClick();
    }
    public void onShareClick(){
        dashboardNavigator.onShareClick();
    }
}
