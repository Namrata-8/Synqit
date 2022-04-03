package com.example.synqit.fragments.settingfragment;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.adapters.PlansAdapter;
import com.example.synqit.databinding.FragmentSettingBinding;
import com.example.synqit.ui.MainActivity;
import com.example.synqit.ui.PrivacySettingActivity;
import com.example.synqit.ui.account.AccountActivity;
import com.example.synqit.ui.activateproduct.ActivateProductActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.howtouse.HowToUseActivity;
import com.example.synqit.ui.membership.MembershipActivity;
import com.example.synqit.ui.proupgrade.ProUpgradeViewModel;
import com.example.synqit.ui.support.SupportActivity;
import com.example.synqit.utils.SessionManager;
import com.suke.widget.SwitchButton;

public class SettingFragment extends Fragment implements SettingNavigator{

    private FragmentSettingBinding fragmentSettingBinding;
    private SettingViewModel settingViewModel;
    private ProUpgradeViewModel proUpgradeViewModel;
    private PlansAdapter plansAdapter;
    private int Plan = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        fragmentSettingBinding.setViewModel(new SettingViewModel(this));
        fragmentSettingBinding.executePendingBindings();

        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        proUpgradeViewModel = new ViewModelProvider(this).get(ProUpgradeViewModel.class);

        if (SessionManager.readBoolean(getActivity(), SessionManager.IS_LIGHT_DARK, false)){
            fragmentSettingBinding.dayNightSwitch.setChecked(true);
        }else {
            fragmentSettingBinding.dayNightSwitch.setChecked(false);
        }

        fragmentSettingBinding.switchButtonPro.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    startActivity(new Intent(getActivity(), MembershipActivity.class));

                }
            }
        });

        fragmentSettingBinding.dayNightSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    SessionManager.writeBoolean(getActivity(), SessionManager.IS_LIGHT_DARK, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }*/
                    /*Intent intent = getActivity().getIntent();
                    getActivity().overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(intent);*/
                    startActivity(new Intent(getActivity(), DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
                }else {
                    SessionManager.writeBoolean(getActivity(), SessionManager.IS_LIGHT_DARK, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }*/
                    /*Intent intent = getActivity().getIntent();
                    getActivity().overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(intent);*/

                    startActivity(new Intent(getActivity(), DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
                }
            }
        });

        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onAccountClick() {
        startActivity(new Intent(getActivity(), AccountActivity.class));
    }

    @Override
    public void onPrivacyClick() {
        startActivity(new Intent(getActivity(), PrivacySettingActivity.class));
    }

    @Override
    public void onShopClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://synqit.co/collections/all-products"));
        startActivity(browserIntent);
    }

    @Override
    public void onProductClick() {
        startActivity(new Intent(getActivity(), ActivateProductActivity.class));
    }

    @Override
    public void onHowUseClick() {
        startActivity(new Intent(getActivity(), HowToUseActivity.class));
    }

    @Override
    public void onSupportClick() {
        startActivity(new Intent(getActivity(), SupportActivity.class));
    }

    @Override
    public void onLogoutClick() {
        SessionManager.clearAutoLogin(getActivity());
        SessionManager.clearOnBoardingView(getActivity());
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }
}