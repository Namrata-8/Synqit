package com.example.synqit.fragments.scanfragment;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragmentViewModel extends ViewModel {
    private MutableLiveData<AddImageResponse> addLogoResponse;
    private MutableLiveData<InsertCardResponse> updateCardResponse;

    private ScanFragmentNavigator scanFragmentNavigator;

    public ScanFragmentViewModel(ScanFragmentNavigator scanFragmentNavigator) {
        this.scanFragmentNavigator = scanFragmentNavigator;
    }

    public ScanFragmentViewModel() {
        addLogoResponse = new MutableLiveData<>();
        this.updateCardResponse = new MutableLiveData<>();
    }
    public MutableLiveData<AddImageResponse> addLogoApi(){
        return addLogoResponse;
    }

    public void addLogo(MultipartBody.Part logoFile, RequestBody userId, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<AddImageResponse> call = RetrofitClient.getInstance().getApi().uploadImage(logoFile, userId);
        call.enqueue(new Callback<AddImageResponse>() {
            @Override
            public void onResponse(Call<AddImageResponse> call, Response<AddImageResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    addLogoResponse.postValue(response.body());
                }else {
                    addLogoResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddImageResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                addLogoResponse.postValue(null);
                Log.e("uploadImage?onFailure", t.getMessage());
            }
        });
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
}
