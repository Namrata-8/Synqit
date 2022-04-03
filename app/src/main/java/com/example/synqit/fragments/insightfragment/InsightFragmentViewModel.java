package com.example.synqit.fragments.insightfragment;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.insightfragment.model.GetProfileVisitResponse;
import com.example.synqit.fragments.insightfragment.model.ParamGetProfileVisit;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightFragmentViewModel  extends ViewModel {

    private MutableLiveData<GetProfileVisitResponse> getProfileVisitResponseMutableLiveData;

    public InsightFragmentViewModel() {
        getProfileVisitResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<GetProfileVisitResponse> getProfileVisit(){
        return getProfileVisitResponseMutableLiveData;
    }

    public void getProfile(ParamGetProfileVisit paramGetProfileVisit, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<GetProfileVisitResponse> call = RetrofitClient.getInstance().getApi().profileVisit(paramGetProfileVisit);
        call.enqueue(new Callback<GetProfileVisitResponse>() {
            @Override
            public void onResponse(Call<GetProfileVisitResponse> call, Response<GetProfileVisitResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    getProfileVisitResponseMutableLiveData.postValue(response.body());
                }else {
                    Toast.makeText(activity, "Here ...", Toast.LENGTH_SHORT).show();
                    getProfileVisitResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetProfileVisitResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("profileVisit?onFailure", t.getMessage());
                getProfileVisitResponseMutableLiveData.postValue(null);
            }
        });
    }
}
