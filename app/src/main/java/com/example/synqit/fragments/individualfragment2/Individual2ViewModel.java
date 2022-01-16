package com.example.synqit.fragments.individualfragment2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Individual2ViewModel extends ViewModel {
    private Individual2Navigator individual2Navigator;
    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> mutableLiveDataCountry = new MutableLiveData<>();
    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> mutableLiveDataGender = new MutableLiveData<>();
    private ArrayList<BusinessFragment3ViewModel> arrayListCountry;
    private ArrayList<BusinessFragment3ViewModel> arrayListGender;
    public String countryName = "";
    public String gender = "";

    public Individual2ViewModel() {
    }

    public Individual2ViewModel(String countryName, String gender) {
        this.countryName = countryName;
        this.gender = gender;
    }

    public Individual2ViewModel(Individual2Navigator individual2Navigator) {
        this.individual2Navigator = individual2Navigator;
    }
    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> getCountryList(List<String> countryList){
        arrayListCountry = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++){
            BusinessFragment3ViewModel businessFragment3ViewModel = new BusinessFragment3ViewModel(countryList.get(i));
            arrayListCountry.add(businessFragment3ViewModel);
            mutableLiveDataCountry.setValue(arrayListCountry);
        }
        return mutableLiveDataCountry;
    }

    public MutableLiveData<ArrayList<BusinessFragment3ViewModel>> getGenderList(List<String> countryList){
        arrayListGender = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++){
            BusinessFragment3ViewModel businessFragment3ViewModel = new BusinessFragment3ViewModel(countryList.get(i));
            arrayListGender.add(businessFragment3ViewModel);
            mutableLiveDataGender.setValue(arrayListGender);
        }
        return mutableLiveDataGender;
    }


    public void onContinueClick(){individual2Navigator.goContinue();}
    public void onCountryClick(){individual2Navigator.onCountryClick();}
    public void onGenderClick(){individual2Navigator.onGenderClick();}
    public void onDateClick(){individual2Navigator.onDateClick();}
}
