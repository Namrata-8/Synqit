package com.example.synqit.ui.activateproduct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.fragments.homefragment.model.SubscribedData;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.activateproduct.model.ActivateProductData;
import com.example.synqit.ui.activateproduct.model.ActivateProductResponse;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivateProductViewModel extends ViewModel {
    public String productImage = "";
    private ActivateProductData activateProductData;
    public MutableLiveData<ArrayList<ActivateProductViewModel>> productsLiveData = new MutableLiveData<>();
    private ArrayList<ActivateProductViewModel> arrayList;
    private List<ActivateProductData> activateProductDataList;

    public String getProductImage() {
        return activateProductData.getIconLight();
    }

    public ActivateProductViewModel(ActivateProductData activateProductData) {
        this.activateProductData = activateProductData;
    }

    public ActivateProductData getActivateProductData() {
        return activateProductData;
    }

    public ActivateProductViewModel() {
    }

    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String imageUrl){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_business);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl).into(imageView);
        //Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    public MutableLiveData<ArrayList<ActivateProductViewModel>> getActivateProducts(Activity activity){
        arrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<ActivateProductResponse> call = RetrofitClient.getInstance().getApi().getActivateProducts();
        call.enqueue(new Callback<ActivateProductResponse>() {
            @Override
            public void onResponse(Call<ActivateProductResponse> call, Response<ActivateProductResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    activateProductDataList = new ArrayList<>();
                    activateProductDataList = response.body().getData();
                    for (int i=0; i<activateProductDataList.size(); i++){
                        ActivateProductData activateProductData = activateProductDataList.get(i);
                        ActivateProductViewModel activateProductViewModel = new ActivateProductViewModel(activateProductData);
                        arrayList.add(activateProductViewModel);
                        productsLiveData.setValue(arrayList);
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ActivateProductResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getActivateProducts?onFailure", t.getMessage());
            }
        });

        return productsLiveData;
    }
}
