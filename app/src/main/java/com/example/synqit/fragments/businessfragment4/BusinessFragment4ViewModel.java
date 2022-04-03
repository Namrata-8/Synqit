package com.example.synqit.fragments.businessfragment4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.utils.LoadingDialog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessFragment4ViewModel extends ViewModel {
    private BusinessFragment4Navigator businessFragment4Navigator;
    private MutableLiveData<AddImageResponse> addLogoResponse;
    private MutableLiveData<AddImageResponse> uploadCoverPhotoResponse;
    private MutableLiveData<FullRegisterResponse> fullRegisterResponse;
    private MutableLiveData<InsertCardResponse> insertCardResponse;

    public BusinessFragment4ViewModel() {
        addLogoResponse = new MutableLiveData<>();
        uploadCoverPhotoResponse = new MutableLiveData<>();
        this.fullRegisterResponse = new MutableLiveData<>();
        this.insertCardResponse = new MutableLiveData<>();
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

    public MutableLiveData<AddImageResponse> uploadCoverPhotoApi(){
        return uploadCoverPhotoResponse;
    }

    public void uploadCoverPhoto(MultipartBody.Part coverFile, RequestBody userId, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<AddImageResponse> call = RetrofitClient.getInstance().getApi().uploadCoverPhoto(coverFile, userId);
        call.enqueue(new Callback<AddImageResponse>() {
            @Override
            public void onResponse(Call<AddImageResponse> call, Response<AddImageResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    uploadCoverPhotoResponse.postValue(response.body());
                }else {
                    uploadCoverPhotoResponse.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<AddImageResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                uploadCoverPhotoResponse.postValue(null);
                Log.e("uploadCoverPhoto?onFailure", t.getMessage());
            }
        });
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

    public BusinessFragment4ViewModel(BusinessFragment4Navigator businessFragment4Navigator) {
        this.businessFragment4Navigator = businessFragment4Navigator;
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

    public void onAddLogoClick(){
        businessFragment4Navigator.addLogo();
    }
    public void onCoverPhotoClick(){
        businessFragment4Navigator.uploadCoverPhoto();
    }
    public void onFinishClick(){
        businessFragment4Navigator.onFinish();
    }
    public void onSkipNowClick(){
        businessFragment4Navigator.onSkipNow();
    }
}
