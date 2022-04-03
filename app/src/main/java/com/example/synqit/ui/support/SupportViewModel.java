package com.example.synqit.ui.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.howtouse.HowToUseViewModel;
import com.example.synqit.ui.howtouse.model.InstructionData;
import com.example.synqit.ui.howtouse.model.InstructionResponse;
import com.example.synqit.ui.support.model.CreateTicketResponse;
import com.example.synqit.ui.support.model.FaqData;
import com.example.synqit.ui.support.model.FaqListResponse;
import com.example.synqit.ui.support.model.ParamCreateTicket;
import com.example.synqit.ui.support.model.ParamSupportList;
import com.example.synqit.ui.support.model.SupportListData;
import com.example.synqit.ui.support.model.SupportListResponse;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportViewModel extends ViewModel {
    private SupportNavigator supportNavigator;
    private SupportListData supportListData;
    public MutableLiveData<ArrayList<SupportViewModel>> supportLiveData = new MutableLiveData<>();
    private MutableLiveData<CreateTicketResponse> createTicketResponseMutableLiveData;
    private ArrayList<SupportViewModel> arrayList;
    private List<SupportListData> supportListDataList;

    private FaqData faqData;
    public MutableLiveData<ArrayList<SupportViewModel>> faqsLiveData = new MutableLiveData<>();
    private ArrayList<SupportViewModel> faqsArrayList;
    private List<FaqData> faqDataList;

    public SupportViewModel(SupportNavigator supportNavigator) {
        this.supportNavigator = supportNavigator;
    }

    public SupportViewModel(FaqData faqData) {
        this.faqData = faqData;
    }

    public FaqData getFaqData() {
        return faqData;
    }

    public SupportViewModel() {
        createTicketResponseMutableLiveData = new MutableLiveData<>();
    }

    public SupportViewModel(SupportListData supportListData) {
        this.supportListData = supportListData;
    }

    public SupportListData getSupportListData() {
        return supportListData;
    }

    public MutableLiveData<ArrayList<SupportViewModel>> getSupportList(ParamSupportList paramSupportList, Activity activity){
        arrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SupportListResponse> call = RetrofitClient.getInstance().getApi().getSupportList(paramSupportList);
        call.enqueue(new Callback<SupportListResponse>() {
            @Override
            public void onResponse(Call<SupportListResponse> call, Response<SupportListResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    supportListDataList = new ArrayList<>();
                    supportListDataList = response.body().getData();
                    for (int i=0; i<supportListDataList.size(); i++){
                        SupportListData supportListData = supportListDataList.get(i);
                        SupportViewModel supportViewModel = new SupportViewModel(supportListData);
                        arrayList.add(supportViewModel);
                        supportLiveData.setValue(arrayList);
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<SupportListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getSupportList?onFailure", t.getMessage());

            }
        });

        return supportLiveData;
    }

    public MutableLiveData<CreateTicketResponse> getCreateTicketResponse(){
        return createTicketResponseMutableLiveData;
    }

    public void onCreateNewTicket(ParamCreateTicket paramCreateTicket, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<CreateTicketResponse> call = RetrofitClient.getInstance().getApi().createNewTicket(paramCreateTicket);
        call.enqueue(new Callback<CreateTicketResponse>() {
            @Override
            public void onResponse(Call<CreateTicketResponse> call, Response<CreateTicketResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    createTicketResponseMutableLiveData.postValue(response.body());
                }else {
                    createTicketResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateTicketResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                createTicketResponseMutableLiveData.postValue(null);
            }
        });
    }

    public void onBackClick(){
        supportNavigator.onBackClick();
    }

    public void onAddTicketClick(){
        supportNavigator.onAddTicketClick();
    }

    public MutableLiveData<ArrayList<SupportViewModel>> getFaqsList(Activity activity){
        faqsArrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<FaqListResponse> call = RetrofitClient.getInstance().getApi().getFaqsList();
        call.enqueue(new Callback<FaqListResponse>() {
            @Override
            public void onResponse(Call<FaqListResponse> call, Response<FaqListResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    faqDataList = new ArrayList<>();
                    faqDataList = response.body().getData();
                    for (int i=0; i<faqDataList.size(); i++){
                        FaqData faqData = faqDataList.get(i);
                        SupportViewModel supportViewModel = new SupportViewModel(faqData);
                        faqsArrayList.add(supportViewModel);
                        faqsLiveData.setValue(faqsArrayList);
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<FaqListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getInstructions?onFailure", t.getMessage());
            }
        });

        return faqsLiveData;
    }
}
