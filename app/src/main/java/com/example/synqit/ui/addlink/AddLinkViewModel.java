package com.example.synqit.ui.addlink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.R;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.SubScribeListResponse;
import com.example.synqit.fragments.homefragment.model.SubscribedData;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.addlink.model.AddLinkResponse;
import com.example.synqit.ui.addlink.model.AddSocialMediaResponse;
import com.example.synqit.ui.addlink.model.Categories;
import com.example.synqit.ui.addlink.model.CommonLinkData;
import com.example.synqit.ui.addlink.model.ParamAddSocialMedia;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.utils.LoadingDialog;
import com.example.synqit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLinkViewModel extends ViewModel {

    public MutableLiveData<ArrayList<AddLinkViewModel>> mostPopularLinkLiveData;
    public MutableLiveData<ArrayList<AddLinkViewModel>> influencersLinkLiveData;
    public MutableLiveData<ArrayList<AddLinkViewModel>> categoriesLinkLiveDataFirst;
    public MutableLiveData<ArrayList<AddLinkViewModel>> categoriesLinkLiveDataSecond;
    public MutableLiveData<ArrayList<AddLinkViewModel>> allAppsLinkLiveData;
    public MutableLiveData<ArrayList<AddLinkViewModel>> categoriesLinkFilterLiveData;
    private AddLinkNavigator addLinkNavigator;
    private ArrayList<AddLinkViewModel> mostPopularArrayList;
    private ArrayList<AddLinkViewModel> influencersArrayList;
    private ArrayList<AddLinkViewModel> allAppsArrayList;
    private ArrayList<AddLinkViewModel> categoriesArrayListFirst;
    private ArrayList<AddLinkViewModel> categoriesArrayListSecond;
    private ArrayList<AddLinkViewModel> categoriesFilterArrayList;

    private List<CommonLinkData> mostPopularList;
    private List<CommonLinkData> influencersList;
    private List<CommonLinkData> allAppsList;
    private List<Categories> categoriesList;
    private CommonLinkData commonLinkData;
    private Categories categories;
    public MutableLiveData<ArrayList<DashboardViewModel>> userLinkLiveData = new MutableLiveData<>();
    private List<SubscribedData> subscribedDataList;
    private SubscribedData subscribedData;
    private ArrayList<DashboardViewModel> arrayList;

    private String filterTitle = "";

    public String getFilterTitle() {
        return filterTitle;
    }

    public AddLinkViewModel(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    private MutableLiveData<AddSocialMediaResponse> addSocialMediaResponse;

    public AddLinkViewModel() {
        this.mostPopularLinkLiveData = new MutableLiveData<>();
        this.influencersLinkLiveData = new MutableLiveData<>();
        this.categoriesLinkLiveDataFirst = new MutableLiveData<>();
        this.categoriesLinkLiveDataSecond = new MutableLiveData<>();
        this.allAppsLinkLiveData = new MutableLiveData<>();
        this.addSocialMediaResponse = new MutableLiveData<>();
        this.categoriesLinkFilterLiveData = new MutableLiveData<>();
    }

    public AddLinkViewModel(AddLinkNavigator addLinkNavigator) {
        this.addLinkNavigator = addLinkNavigator;
    }

    public AddLinkViewModel(CommonLinkData commonLinkData) {
        this.commonLinkData = commonLinkData;
    }

    public AddLinkViewModel(Categories categories) {
        this.categories = categories;
    }

    public CommonLinkData getCommonLinkData() {
        return commonLinkData;
    }

    public Categories getCategories() {
        return categories;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getMostPopularLink() {
        return mostPopularLinkLiveData;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getInfluencersLink() {
        return influencersLinkLiveData;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getCategoryLinkFirst() {
        return categoriesLinkLiveDataFirst;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getCategoryLinkSecond() {
        return categoriesLinkLiveDataSecond;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getAllAppsLink() {
        return allAppsLinkLiveData;
    }

    public MutableLiveData<ArrayList<AddLinkViewModel>> getFilterCategoriesList() {
        return categoriesLinkFilterLiveData;
    }

    public void getLinks(Activity activity) {
        mostPopularArrayList = new ArrayList<>();
        influencersArrayList = new ArrayList<>();
        categoriesArrayListFirst = new ArrayList<>();
        categoriesArrayListSecond = new ArrayList<>();
        allAppsArrayList = new ArrayList<>();
        categoriesFilterArrayList = new ArrayList<>();
        AddLinkViewModel addLinkViewModel = new AddLinkViewModel("All");
        categoriesFilterArrayList.add(addLinkViewModel);
        categoriesLinkFilterLiveData.setValue(categoriesFilterArrayList);

        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();

        Call<AddLinkResponse> call = RetrofitClient.getInstance().getApi().getLinks();
        call.enqueue(new Callback<AddLinkResponse>() {
            @Override
            public void onResponse(Call<AddLinkResponse> call, Response<AddLinkResponse> response) {

                if (response.isSuccessful()) {
                    mostPopularList = new ArrayList<>();
                    influencersList = new ArrayList<>();
                    categoriesList = new ArrayList<>();
                    allAppsList = new ArrayList<>();
                    mostPopularList = response.body().getMostPopularList();
                    influencersList = response.body().getInfluencersList();
                    categoriesList = response.body().getCategoriesList();
                    allAppsList = response.body().getAllApp();
                    if (!mostPopularList.isEmpty()) {
                        for (int i = 0; i < mostPopularList.size(); i++) {
                            CommonLinkData commonLinkData = mostPopularList.get(i);
                            AddLinkViewModel addLinkViewModel = new AddLinkViewModel(commonLinkData);
                            mostPopularArrayList.add(addLinkViewModel);
                            mostPopularLinkLiveData.setValue(mostPopularArrayList);
                        }
                    }else {
                        mostPopularLinkLiveData.setValue(mostPopularArrayList);
                    }
                    if (!influencersList.isEmpty()) {
                        for (int i = 0; i < influencersList.size(); i++) {
                            CommonLinkData commonLinkData = influencersList.get(i);
                            AddLinkViewModel addLinkViewModel = new AddLinkViewModel(commonLinkData);
                            influencersArrayList.add(addLinkViewModel);
                            influencersLinkLiveData.setValue(influencersArrayList);
                        }
                    }else {
                        influencersLinkLiveData.setValue(influencersArrayList);
                    }
                    if (!allAppsList.isEmpty()) {
                        for (int i = 0; i < allAppsList.size(); i++) {
                            CommonLinkData commonLinkData = allAppsList.get(i);
                            AddLinkViewModel addLinkViewModel = new AddLinkViewModel(commonLinkData);
                            allAppsArrayList.add(addLinkViewModel);
                            allAppsLinkLiveData.setValue(allAppsArrayList);
                        }
                    }else {
                        allAppsLinkLiveData.setValue(allAppsArrayList);
                    }
                    if (!categoriesList.isEmpty()) {
                        if (categoriesList.size() > 1) {
                            for (int i = 0; i < 1; i++) {
                                Categories categories = categoriesList.get(i);
                                AddLinkViewModel addLinkViewModel = new AddLinkViewModel(categories);
                                categoriesArrayListFirst.add(addLinkViewModel);
                                categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                            }
                            for (int i = 1; i < categoriesList.size(); i++) {
                                Categories categories = categoriesList.get(i);
                                AddLinkViewModel addLinkViewModel = new AddLinkViewModel(categories);
                                categoriesArrayListSecond.add(addLinkViewModel);
                                categoriesLinkLiveDataSecond.setValue(categoriesArrayListSecond);
                            }
                        } else {
                            categoriesLinkLiveDataSecond.setValue(categoriesArrayListSecond);
                            for (int i = 0; i < categoriesList.size(); i++) {
                                Categories categories = categoriesList.get(i);
                                AddLinkViewModel addLinkViewModel = new AddLinkViewModel(categories);
                                categoriesArrayListFirst.add(addLinkViewModel);
                                categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                            }
                        }
                        for (int i=0; i<categoriesList.size(); i++){
                            Categories categories = categoriesList.get(i);
                            AddLinkViewModel addLinkViewModel = new AddLinkViewModel(categories.getTitle());
                            categoriesFilterArrayList.add(addLinkViewModel);
                            categoriesLinkFilterLiveData.setValue(categoriesFilterArrayList);
                        }
                    }else {
                        categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                        categoriesLinkLiveDataSecond.setValue(categoriesArrayListSecond);
                    }
                    loadingDialog.dismissDialog();
                }else {
                    loadingDialog.dismissDialog();
                    mostPopularLinkLiveData.setValue(mostPopularArrayList);
                    influencersLinkLiveData.setValue(influencersArrayList);
                    categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                    categoriesLinkLiveDataSecond.setValue(categoriesArrayListSecond);
                    categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                }
            }

            @Override
            public void onFailure(Call<AddLinkResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getLinks?onFailure", t.getMessage());
                mostPopularLinkLiveData.setValue(mostPopularArrayList);
                influencersLinkLiveData.setValue(influencersArrayList);
                categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
                categoriesLinkLiveDataSecond.setValue(categoriesArrayListSecond);
                categoriesLinkLiveDataFirst.setValue(categoriesArrayListFirst);
            }
        });
    }

    public MutableLiveData<AddSocialMediaResponse> onSubscribeSocialMedia(){
        return addSocialMediaResponse;
    }

    public void subscribeSocialMedia(ParamAddSocialMedia paramAddSocialMedia, Activity activity){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<AddSocialMediaResponse> call = RetrofitClient.getInstance().getApi().subscribeSocialMedia(paramAddSocialMedia);
        call.enqueue(new Callback<AddSocialMediaResponse>() {
            @Override
            public void onResponse(Call<AddSocialMediaResponse> call, Response<AddSocialMediaResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    addSocialMediaResponse.postValue(response.body());
                }else {
                    addSocialMediaResponse.postValue(null);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<AddSocialMediaResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("subscribeSocialMedia?onFailure", t.getMessage());
                addSocialMediaResponse.postValue(null);
            }
        });
    }

    public AddLinkViewModel(SubscribedData subscribedData) {
        this.subscribedData = subscribedData;
    }

    public SubscribedData getSubscribedData() {
        return subscribedData;
    }

    public MutableLiveData<ArrayList<DashboardViewModel>> getUserLinkLiveData(ParamGetSubscribedList paramGetSubscribedList, Activity activity, int positionCat, ArrayList<AddLinkViewModel> addLinkViewModels, boolean linkCategory, int positionApp){
        arrayList = new ArrayList<>();
        SubscribedData subscribedData = new SubscribedData();
        if (linkCategory) {
            if(SessionManager.readBoolean(activity, SessionManager.IS_LIGHT_DARK, false)) {
                subscribedData.setLogoDark(addLinkViewModels.get(positionCat).getCategories().getAppList().get(positionApp).getLogoDark());
            }else {
                subscribedData.setLogoLight(addLinkViewModels.get(positionCat).getCategories().getAppList().get(positionApp).getLogoLight());
            }
        }else {
            if(SessionManager.readBoolean(activity, SessionManager.IS_LIGHT_DARK, false)) {
                subscribedData.setLogoDark(addLinkViewModels.get(positionCat).getCommonLinkData().getLogoDark());
            }else {
                subscribedData.setLogoLight(addLinkViewModels.get(positionCat).getCommonLinkData().getLogoLight());
            }
        }
        DashboardViewModel dashboardViewModel  = new DashboardViewModel(subscribedData);
        arrayList.add(dashboardViewModel);
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.startLoadingDialog();
        Call<SubScribeListResponse> call = RetrofitClient.getInstance().getApi().getSubscribedList(paramGetSubscribedList);
        call.enqueue(new Callback<SubScribeListResponse>() {
            @Override
            public void onResponse(Call<SubScribeListResponse> call, Response<SubScribeListResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()){
                    subscribedDataList = new ArrayList<>();
                    subscribedDataList = response.body().getSubscribedData();
                    for (int i=0; i<subscribedDataList.size(); i++){
                        if (!subscribedDataList.get(i).isDisabled()) {
                            SubscribedData subscribedData = subscribedDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(subscribedData);
                            arrayList.add(dashboardViewModel);
                        }else {
                            continue;
                        }
                    }
                }
                userLinkLiveData.setValue(arrayList);
            }

            @Override
            public void onFailure(Call<SubScribeListResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.e("getBusinesses?onFailure", t.getMessage());
                userLinkLiveData.setValue(arrayList);
            }
        });

        return userLinkLiveData;
    }


    public void onBackClick() {
        addLinkNavigator.goToBack();
    }
}
