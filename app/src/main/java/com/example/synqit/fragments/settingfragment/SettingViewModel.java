package com.example.synqit.fragments.settingfragment;

import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {
    private SettingNavigator settingNavigator;
    public SettingViewModel() {
    }

    public SettingViewModel(SettingNavigator settingNavigator) {
        this.settingNavigator = settingNavigator;
    }

    public void onAccountClick(){
        settingNavigator.onAccountClick();
    }
    public void onPrivacySecurityClick(){
        settingNavigator.onPrivacyClick();
    }
    public void onShopClick(){
        settingNavigator.onShopClick();
    }
    public void onActivateProductClick(){
        settingNavigator.onProductClick();
    }
    public void onHowToUseClick(){
        settingNavigator.onHowUseClick();
    }
    public void onSupportClick(){
        settingNavigator.onSupportClick();
    }
    public void onLogoutClick(){
        settingNavigator.onLogoutClick();
    }
}
