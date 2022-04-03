package com.example.synqit.ui.editprofile;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel extends ViewModel {

    private EditProfileNavigator editProfileNavigator;
    private String colorCode = "";
    private MutableLiveData<InsertCardResponse> updateCardResponse;

    public String getColorCode() {
        return colorCode;
    }

    public EditProfileViewModel(EditProfileNavigator editProfileNavigator) {
        this.editProfileNavigator = editProfileNavigator;
    }

    public EditProfileViewModel() {
        this.updateCardResponse = new MutableLiveData<>();
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

    public void onBlackClick(){
        editProfileNavigator.selectBlack();
    }
    public void onBlueClick(){
        editProfileNavigator.selectBlue();
    }
    public void onPinkClick(){
        editProfileNavigator.selectPink();
    }
    public void onRedClick(){
        editProfileNavigator.selectRed();
    }
    public void onOrangeClick(){
        editProfileNavigator.selectOrange();
    }
    public void onYellowClick(){
        editProfileNavigator.selectYellow();
    }
    public void onGreenClick(){
        editProfileNavigator.selectGreen();
    }
    public void onWhiteClick(){
        editProfileNavigator.selectWhite();
    }
    public void onSaveChangeClick(){
        editProfileNavigator.onSaveChange();
    }
    public void onBackClick(){
        editProfileNavigator.onBack();
    }
    public void onEyeClick(){
        editProfileNavigator.onView();
    }
}
