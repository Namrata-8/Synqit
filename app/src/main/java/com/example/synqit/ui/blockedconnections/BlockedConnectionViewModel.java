package com.example.synqit.ui.blockedconnections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.connectionsfragment.ConnectionsFragmentViewModel;
import com.example.synqit.fragments.connectionsfragment.model.ConnectedData;
import com.example.synqit.fragments.connectionsfragment.model.ConnectionResponse;
import com.example.synqit.fragments.connectionsfragment.model.ParamGetConnection;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.howtouse.HowToUseViewModel;
import com.example.synqit.ui.howtouse.model.InstructionData;
import com.example.synqit.ui.howtouse.model.InstructionResponse;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedConnectionViewModel extends ViewModel {

    public MutableLiveData<ArrayList<BlockedConnectionViewModel>> blockConnectedMutableLiveData = new MutableLiveData<>();
    private ArrayList<BlockedConnectionViewModel> blockConnectedArrayList;
    private List<ConnectedData> blockConnectedDataList;
    private ConnectedData blockConnectedData;
    public BlockedConnectionViewModel() {
    }

    public ConnectedData getBlockConnectedData() {
        return blockConnectedData;
    }

    public BlockedConnectionViewModel(ConnectedData blockConnectedData) {
        this.blockConnectedData = blockConnectedData;
    }

    public MutableLiveData<ArrayList<BlockedConnectionViewModel>> getBlockConnections(ParamGetConnection paramGetConnection, Activity activity){
        blockConnectedArrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<ConnectionResponse> call = RetrofitClient.getInstance().getApi().getBlockedConnection(paramGetConnection);
        call.enqueue(new Callback<ConnectionResponse>() {
            @Override
            public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    blockConnectedDataList = new ArrayList<>();
                    blockConnectedDataList = response.body().getBlockedList();
                    for (int i=0; i<blockConnectedDataList.size(); i++){
                        ConnectedData connectedData = blockConnectedDataList.get(i);
                        BlockedConnectionViewModel blockedConnectionViewModel = new BlockedConnectionViewModel(connectedData);
                        blockConnectedArrayList.add(blockedConnectionViewModel);
                        blockConnectedMutableLiveData.setValue(blockConnectedArrayList);
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ConnectionResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getInstructions?onFailure", t.getMessage());
            }
        });

        return blockConnectedMutableLiveData;
    }
}
