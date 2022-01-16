package com.example.synqit.fragments.businessfragment4;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.AddLogoResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamAddLogo;
import com.example.synqit.fragments.businessfragment4.model.ParamUploadCoverPhoto;
import com.example.synqit.fragments.businessfragment4.model.UploadCoverPhotoResponse;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessFragment4ViewModel extends ViewModel {
    private BusinessFragment4Navigator businessFragment4Navigator;
    private MutableLiveData<AddLogoResponse> addLogoResponse;
    private MutableLiveData<UploadCoverPhotoResponse> uploadCoverPhotoResponse;
    private MutableLiveData<FullRegisterResponse> fullRegisterResponse;

    public BusinessFragment4ViewModel() {
        addLogoResponse = new MutableLiveData<>();
        uploadCoverPhotoResponse = new MutableLiveData<>();
        this.fullRegisterResponse = new MutableLiveData<>();
    }

    public MutableLiveData<AddLogoResponse> addLogoApi(){
        return addLogoResponse;
    }

    public void addLogo(MultipartBody.Part logoFile, RequestBody userId){
        Call<AddLogoResponse> call = RetrofitClient.getInstance().getApi().uploadProfileImage(logoFile, userId);
        call.enqueue(new Callback<AddLogoResponse>() {
            @Override
            public void onResponse(Call<AddLogoResponse> call, Response<AddLogoResponse> response) {
                if (response.isSuccessful()){
                    addLogoResponse.postValue(response.body());
                }else {
                    addLogoResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddLogoResponse> call, Throwable t) {
                addLogoResponse.postValue(null);
            }
        });
    }

    public MutableLiveData<UploadCoverPhotoResponse> uploadCoverPhotoApi(){
        return uploadCoverPhotoResponse;
    }

    public void uploadCoverPhoto(MultipartBody.Part coverFile, RequestBody userId){
        Call<UploadCoverPhotoResponse> call = RetrofitClient.getInstance().getApi().uploadCoverPhoto(coverFile, userId);
        call.enqueue(new Callback<UploadCoverPhotoResponse>() {
            @Override
            public void onResponse(Call<UploadCoverPhotoResponse> call, Response<UploadCoverPhotoResponse> response) {
                if (response.isSuccessful()){
                    uploadCoverPhotoResponse.postValue(response.body());
                }else {
                    uploadCoverPhotoResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UploadCoverPhotoResponse> call, Throwable t) {
                uploadCoverPhotoResponse.postValue(null);
            }
        });
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

    public BusinessFragment4ViewModel(BusinessFragment4Navigator businessFragment4Navigator) {
        this.businessFragment4Navigator = businessFragment4Navigator;
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
