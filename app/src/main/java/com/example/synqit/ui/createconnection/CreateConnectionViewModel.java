package com.example.synqit.ui.createconnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.createconnection.model.InsertConnectionResponse;
import com.example.synqit.ui.createconnection.model.ParamCreateConnection;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.utils.LoadingDialog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateConnectionViewModel extends ViewModel {

    private CreateConnectionNavigator createConnectionNavigator;
    private MutableLiveData<AddImageResponse> addLogoResponse;
    private MutableLiveData<AddImageResponse> uploadCoverPhotoResponse;
    private MutableLiveData<InsertConnectionResponse> insertConnectionResponse;

    public CreateConnectionViewModel(CreateConnectionNavigator createConnectionNavigator) {
        this.createConnectionNavigator = createConnectionNavigator;
    }

    public CreateConnectionViewModel() {
        addLogoResponse = new MutableLiveData<>();
        uploadCoverPhotoResponse = new MutableLiveData<>();
        this.insertConnectionResponse = new MutableLiveData<>();
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
                Log.e("addLogoFail", t.getMessage());
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

            @Override
            public void onFailure(Call<AddImageResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                uploadCoverPhotoResponse.postValue(null);
                Log.e("addCoverFail", t.getMessage());
            }
        });
    }

    public MutableLiveData<InsertConnectionResponse> onCreateConnection(){
        return insertConnectionResponse;
    }

    public void createConnection(ParamCreateConnection paramCreateConnection, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<InsertConnectionResponse> call = RetrofitClient.getInstance().getApi().createConnection(paramCreateConnection);
        call.enqueue(new Callback<InsertConnectionResponse>() {
            @Override
            public void onResponse(Call<InsertConnectionResponse> call, Response<InsertConnectionResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    insertConnectionResponse.postValue(response.body());
                }else {
                    insertConnectionResponse.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<InsertConnectionResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("createConnection?onFailure", t.getMessage());
                insertConnectionResponse.postValue(null);
            }
        });
    }

    public void onClickCoverImg(){
        createConnectionNavigator.uploadCoverImg();
    }

    public void onClickProfileImg(){
        createConnectionNavigator.uploadProfileImg();
    }

    public void onClickCreate(){
        createConnectionNavigator.onCreateConnection();
    }
    public void onBackClick(){
        createConnectionNavigator.goToBack();
    }
}
