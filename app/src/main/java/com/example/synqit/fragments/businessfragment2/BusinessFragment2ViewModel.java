package com.example.synqit.fragments.businessfragment2;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.example.synqit.fragments.businessfragment2.model.BusinessResponse;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class BusinessFragment2ViewModel extends ViewModel {

    public String id="";
    public String businessName="";
    public String businessIcon="";

    private BusinessFragment2Navigator businessFragment2Navigator;
    public MutableLiveData<ArrayList<BusinessFragment2ViewModel>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<BusinessFragment2ViewModel> arrayList;
    private List<BusinessData> businessDataList;

    public String getBusinessIcon(){
        return businessIcon;
    }

    public BusinessFragment2ViewModel() {
    }

    public BusinessFragment2ViewModel(BusinessFragment2Navigator businessFragment2Navigator) {
        this.businessFragment2Navigator = businessFragment2Navigator;
    }

    public BusinessFragment2ViewModel(BusinessData businessData) {
        this.id = businessData.getId();
        this.businessName = businessData.getTitle();
        this.businessIcon = businessData.getIcon();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String imageUrl){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_business);
        Glide.with(imageView.getContext()).setDefaultRequestOptions(requestOptions).load(imageUrl).into(imageView);
    }

    public MutableLiveData<ArrayList<BusinessFragment2ViewModel>> getBusinessesData(Activity activity) {
        arrayList=new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<BusinessResponse> call = RetrofitClient.getInstance().getApi().getBusinesses();
        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    businessDataList = new ArrayList<>();
                    businessDataList = response.body().getData();
                    for (int i = 0; i < businessDataList.size(); i++){
                        BusinessData businessData = businessDataList.get(i);
                        BusinessFragment2ViewModel businessFragment2ViewModel = new BusinessFragment2ViewModel(businessData);
                        arrayList.add(businessFragment2ViewModel);
                        mutableLiveData.setValue(arrayList);
                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getBusinesses?onFailure", t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public void onContinueClick(){
        businessFragment2Navigator.onContinueClick();
    }
}
