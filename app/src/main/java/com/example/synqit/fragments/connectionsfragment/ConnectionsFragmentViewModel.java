package com.example.synqit.fragments.connectionsfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.connectionsfragment.model.ConnectedData;
import com.example.synqit.fragments.connectionsfragment.model.ConnectionResponse;
import com.example.synqit.fragments.connectionsfragment.model.FavoriteData;
import com.example.synqit.fragments.connectionsfragment.model.ParamUpdateUserConnect;
import com.example.synqit.fragments.connectionsfragment.model.UpdateConnectResponse;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.fragments.connectionsfragment.model.ParamGetConnection;
import com.example.synqit.ui.createconnection.model.InsertConnectionResponse;
import com.example.synqit.ui.createconnection.model.ParamCreateConnection;
import com.example.synqit.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionsFragmentViewModel extends ViewModel {

    private ConnectionsFragmentNavigator connectionsFragmentNavigator;
    public MutableLiveData<ArrayList<ConnectionsFragmentViewModel>> connectedMutableLiveData = new MutableLiveData<>();
    private ArrayList<ConnectionsFragmentViewModel> connectedArrayList;
    public MutableLiveData<ArrayList<ConnectionsFragmentViewModel>> favoriteMutableLiveData = new MutableLiveData<>();
    private ArrayList<ConnectionsFragmentViewModel> favoriteArrayList;
    private MutableLiveData<InsertConnectionResponse> updateConnectResponseMutableLiveData;
    private List<FavoriteData> favoriteDataList;
    private List<ConnectedData> connectedDataList;
    private FavoriteData favoriteData;
    private ConnectedData connectedData;

    public ConnectionsFragmentViewModel(ConnectionsFragmentNavigator connectionsFragmentNavigator) {
        this.connectionsFragmentNavigator = connectionsFragmentNavigator;
    }

    public ConnectionsFragmentViewModel() {
        this.connectedMutableLiveData = new MutableLiveData<>();
        this.favoriteMutableLiveData = new MutableLiveData<>();
        this.updateConnectResponseMutableLiveData = new MutableLiveData<>();
    }

    public ConnectionsFragmentViewModel(FavoriteData favoriteData) {
        this.favoriteData = favoriteData;
    }

    public ConnectionsFragmentViewModel(ConnectedData connectedData) {
        this.connectedData = connectedData;
    }

    public FavoriteData getFavoriteData() {
        return favoriteData;
    }

    public ConnectedData getConnectedData() {
        return connectedData;
    }

    public MutableLiveData<ArrayList<ConnectionsFragmentViewModel>> getFavoriteConnections(){
        return favoriteMutableLiveData;
    }

    public MutableLiveData<ArrayList<ConnectionsFragmentViewModel>> getConnectedConnections(){
        return connectedMutableLiveData;
    }

    public void getConnections(ParamGetConnection paramGetConnection, Activity activity){
        connectedArrayList = new ArrayList<>();
        favoriteArrayList = new ArrayList<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<ConnectionResponse> call = RetrofitClient.getInstance().getApi().getConnection(paramGetConnection);
        call.enqueue(new Callback<ConnectionResponse>() {
            @Override
            public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    favoriteDataList = new ArrayList<>();
                    connectedDataList = new ArrayList<>();

                    favoriteDataList = response.body().getFavorite();
                    connectedDataList = response.body().getConnected();

                    if (!favoriteDataList.isEmpty()){
                        for (int i=0; i<favoriteDataList.size(); i++){
                            FavoriteData favoriteData = favoriteDataList.get(i);
                            ConnectionsFragmentViewModel connectionsFragmentViewModel = new ConnectionsFragmentViewModel(favoriteData);
                            favoriteArrayList.add(connectionsFragmentViewModel);
                            favoriteMutableLiveData.setValue(favoriteArrayList);
                        }
                    }else {
                        favoriteMutableLiveData.setValue(favoriteArrayList);
                    }

                    if (!connectedDataList.isEmpty()){
                        for (int i=0; i<connectedDataList.size(); i++){
                            ConnectedData connectedData = connectedDataList.get(i);
                            ConnectionsFragmentViewModel connectionsFragmentViewModel = new ConnectionsFragmentViewModel(connectedData);
                            connectedArrayList.add(connectionsFragmentViewModel);
                            connectedMutableLiveData.setValue(connectedArrayList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ConnectionResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getConnection?onFailure", t.getMessage());
            }
        });
    }

    public MutableLiveData<InsertConnectionResponse> getUpdateConnectResponseMutableLiveData() {
        return updateConnectResponseMutableLiveData;
    }

    public void updateConnect(ParamCreateConnection paramCreateConnection, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<InsertConnectionResponse> call = RetrofitClient.getInstance().getApi().updateManualConnection(paramCreateConnection);
        call.enqueue(new Callback<InsertConnectionResponse>() {
            @Override
            public void onResponse(Call<InsertConnectionResponse> call, Response<InsertConnectionResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    updateConnectResponseMutableLiveData.postValue(response.body());
                }else {
                    updateConnectResponseMutableLiveData.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<InsertConnectionResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("updateManualConnection?onFailure", t.getMessage());
                updateConnectResponseMutableLiveData.postValue(null);
            }
        });
    }
}
