package com.example.synqit.ui.howtouse;

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
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.activateproduct.ActivateProductViewModel;
import com.example.synqit.ui.activateproduct.model.ActivateProductData;
import com.example.synqit.ui.activateproduct.model.ActivateProductResponse;
import com.example.synqit.ui.howtouse.model.InstructionData;
import com.example.synqit.ui.howtouse.model.InstructionResponse;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HowToUseViewModel extends ViewModel {
    public String media = "";
    private InstructionData instructionData;
    public MutableLiveData<ArrayList<HowToUseViewModel>> instructionsLiveData = new MutableLiveData<>();
    private ArrayList<HowToUseViewModel> arrayList;
    private List<InstructionData> instructionDataList;

    public HowToUseViewModel() {
    }

    public HowToUseViewModel(InstructionData instructionData) {
        this.instructionData = instructionData;
    }

    public String getMedia() {
        return instructionData.getMedia();
    }

    public InstructionData getInstructionData() {
        return instructionData;
    }

    public MutableLiveData<ArrayList<HowToUseViewModel>> getInstructions(Activity activity){
        arrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<InstructionResponse> call = RetrofitClient.getInstance().getApi().getInstructions();
        call.enqueue(new Callback<InstructionResponse>() {
            @Override
            public void onResponse(Call<InstructionResponse> call, Response<InstructionResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    instructionDataList = new ArrayList<>();
                    instructionDataList = response.body().getData();
                    for (int i=0; i<instructionDataList.size(); i++){
                        InstructionData instructionData = instructionDataList.get(i);
                        HowToUseViewModel howToUseViewModel = new HowToUseViewModel(instructionData);
                        arrayList.add(howToUseViewModel);
                        instructionsLiveData.setValue(arrayList);
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<InstructionResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getInstructions?onFailure", t.getMessage());
            }
        });

        return instructionsLiveData;
    }
}
