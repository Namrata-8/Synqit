package com.example.synqit.fragments.homefragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2ViewModel;
import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.ParamUpdateSocialMedia;
import com.example.synqit.fragments.homefragment.model.SubScribeListResponse;
import com.example.synqit.fragments.homefragment.model.SubscribedData;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentViewModel extends ViewModel {

    public int id = 0;
    public String linkName = "";
    public String linkImage = "";
    public boolean isLinkActive = false;
    private MutableLiveData<InsertCardResponse> updateCardResponse;
    private MutableLiveData<SubScribeListResponse> updateSocialMediaLiveData;
    private List<SubscribedData> subscribedDataList;
    private SubscribedData subscribedData;
    private Context context;

    private HomeFragmentNavigator homeFragmentNavigator;
    public MutableLiveData<ArrayList<HomeFragmentViewModel>> userLinkLiveData = new MutableLiveData<>();
    private ArrayList<HomeFragmentViewModel> arrayList;

    public String getLinkImage(){
        return subscribedData.getLogoDark();
    }

    public boolean isLinkActive() {
        return isLinkActive;
    }

    public SubscribedData getSubscribedData() {
        return subscribedData;
    }

    public HomeFragmentViewModel() {
        this.updateCardResponse = new MutableLiveData<>();
        this.updateSocialMediaLiveData = new MutableLiveData<>();
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

    public void updateSocialMedia(ParamUpdateSocialMedia paramUpdateSocialMedia, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SubScribeListResponse> call = RetrofitClient.getInstance().getApi().updateSocialMedia(paramUpdateSocialMedia);
        call.enqueue(new Callback<SubScribeListResponse>() {
            @Override
            public void onResponse(Call<SubScribeListResponse> call, Response<SubScribeListResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    updateSocialMediaLiveData.postValue(response.body());
                }else {
                    Log.e("updateSocialMedia", response.body().getMessage());
                    updateSocialMediaLiveData.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<SubScribeListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("updateSocialMedia?onFailure", t.getMessage());
                updateSocialMediaLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<SubScribeListResponse> onUpdateSocialMedia(){
        return updateSocialMediaLiveData;
    }

    public HomeFragmentViewModel(HomeFragmentNavigator homeFragmentNavigator) {
        this.homeFragmentNavigator = homeFragmentNavigator;
    }

    public HomeFragmentViewModel(SubscribedData subscribedData) {
        this.subscribedData = subscribedData;
    }

    public HomeFragmentViewModel(int id, String linkName, String linkImage, boolean isLinkActive) {
        this.id = id;
        this.linkName = linkName;
        this.linkImage = linkImage;
        this.isLinkActive = isLinkActive;
    }

    public MutableLiveData<ArrayList<HomeFragmentViewModel>> getUserLinkLiveData(ParamGetSubscribedList paramGetSubscribedList, Activity activity){
        arrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SubScribeListResponse> call = RetrofitClient.getInstance().getApi().getSubscribedList(paramGetSubscribedList);
        call.enqueue(new Callback<SubScribeListResponse>() {
            @Override
            public void onResponse(Call<SubScribeListResponse> call, Response<SubScribeListResponse> response) {
                if (response.isSuccessful()){
                    subscribedDataList = new ArrayList<>();
                    subscribedDataList = response.body().getSubscribedData();
                    for (int i=0; i<subscribedDataList.size(); i++){
                        SubscribedData subscribedData = subscribedDataList.get(i);
                        HomeFragmentViewModel homeFragmentViewModel = new HomeFragmentViewModel(subscribedData);
                        arrayList.add(homeFragmentViewModel);
                        userLinkLiveData.postValue(arrayList);
                    }
                }else {
                    userLinkLiveData.postValue(arrayList);
                }

                loadingDialog.dismissDialog();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<SubScribeListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getSubscribedList?onFailure", t.getMessage());
                userLinkLiveData.postValue(arrayList);
            }
        });

        return userLinkLiveData;
    }

    public void onAddLinkClick(){
        homeFragmentNavigator.gotoAddLink();
    }
}
