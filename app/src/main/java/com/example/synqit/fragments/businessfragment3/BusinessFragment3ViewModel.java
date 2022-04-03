package com.example.synqit.fragments.businessfragment3;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessFragment3ViewModel extends ViewModel {
    private BusinessFragment3Navigator businessFragment3Navigator;
    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<BusinessFragment3ViewModel> arrayList;
    public String countryName = "";

    public BusinessFragment3ViewModel() {
    }

    public BusinessFragment3ViewModel(String countryName){
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public BusinessFragment3ViewModel(BusinessFragment3Navigator businessFragment3Navigator) {
        this.businessFragment3Navigator = businessFragment3Navigator;
    }

    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> getCountryList(List<String> countryList){
        arrayList = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++){
            BusinessFragment3ViewModel businessFragment3ViewModel = new BusinessFragment3ViewModel(countryList.get(i));
            arrayList.add(businessFragment3ViewModel);
            mutableLiveData.setValue(arrayList);
        }
        return mutableLiveData;
    }

    public void onContinueClick(){businessFragment3Navigator.goContinue();}
    public void onCountryClick(){businessFragment3Navigator.onCountryClick();}
}
