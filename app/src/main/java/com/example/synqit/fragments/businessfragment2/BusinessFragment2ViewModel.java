package com.example.synqit.fragments.businessfragment2;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.synqit.fragments.businessfragment2.model.BusinessData;
import com.example.synqit.fragments.businessfragment2.model.BusinessResponse;
import com.example.synqit.retrofit.RetrofitClient;

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
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    public MutableLiveData<ArrayList<BusinessFragment2ViewModel>> getBusinessesData() {
        arrayList=new ArrayList<>();

        Call<BusinessResponse> call = RetrofitClient.getInstance().getApi().getBusinesses();
        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
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
                Log.e("getBusinesses?onFailure", t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public void onContinueClick(){
        businessFragment2Navigator.onContinueClick();
    }
}
