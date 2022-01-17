package com.example.synqit.ui.proupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityProUpgradeBinding;
import com.example.synqit.ui.DashboardActivity;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.utils.SessionManager;

public class ProUpgradeActivity extends AppCompatActivity implements ProUpgradeNavigator {

    private ActivityProUpgradeBinding activityProUpgradeBinding;
    private ProUpgradeViewModel proUpgradeViewModel;

    private int plan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProUpgradeBinding = DataBindingUtil.setContentView(this, R.layout.activity_pro_upgrade);
        activityProUpgradeBinding.setViewModel(new ProUpgradeViewModel(this));
        activityProUpgradeBinding.executePendingBindings();
        initViewModel();

    }

    private void initViewModel() {
        proUpgradeViewModel = new ViewModelProvider(this).get(ProUpgradeViewModel.class);
        proUpgradeViewModel.onFullRegister().observe(this, new Observer<FullRegisterResponse>() {
            @Override
            public void onChanged(FullRegisterResponse fullRegisterResponse) {
                if (fullRegisterResponse != null) {
                    if (fullRegisterResponse.isSuccess()) {
                        Toast.makeText(ProUpgradeActivity.this, fullRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProUpgradeActivity.this, DashboardActivity.class));
                        finish();
                    }else {
                        Toast.makeText(ProUpgradeActivity.this, fullRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProUpgradeActivity.this, DashboardActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onContinue() {
        Log.e("SelectedPlan", plan + "");
        Log.e("RegisterUser", SessionManager.readString(ProUpgradeActivity.this, SessionManager.REGISTER_AS, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.BASIC_REGISTER_ID, "*"));
        if (SessionManager.readString(ProUpgradeActivity.this, SessionManager.REGISTER_AS, "").equals("Business")) {
            ParamFullRegister paramFullRegister = new ParamFullRegister(true, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                    "","", SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, ""),
                    SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                    SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                    plan, SessionManager.readString(ProUpgradeActivity.this, SessionManager.BASIC_REGISTER_ID, ""));

            proUpgradeViewModel.fullRegistration(paramFullRegister);
        }else {
            ParamFullRegister paramFullRegister = new ParamFullRegister(false, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                    "","", SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, ""),
                    SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                    SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                    plan, SessionManager.readString(ProUpgradeActivity.this, SessionManager.BASIC_REGISTER_ID, ""));

            proUpgradeViewModel.fullRegistration(paramFullRegister);
        }
    }

    @Override
    public void onPlanMonthly() {
        activityProUpgradeBinding.rlCardMonthly.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
        activityProUpgradeBinding.ivCheckIconMonthly.setVisibility(View.VISIBLE);
        activityProUpgradeBinding.tvDiscountMonthly.setBackgroundResource(R.drawable.bg_discount_selected);


        activityProUpgradeBinding.rlCardYearly.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
        activityProUpgradeBinding.ivCheckIconYearly.setVisibility(View.GONE);
        activityProUpgradeBinding.tvDiscountYearly.setBackgroundResource(R.drawable.bg_discount_unselected);

        plan = 0;
    }

    @Override
    public void onPlanYearly() {
        activityProUpgradeBinding.rlCardYearly.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
        activityProUpgradeBinding.ivCheckIconYearly.setVisibility(View.VISIBLE);
        activityProUpgradeBinding.tvDiscountYearly.setBackgroundResource(R.drawable.bg_discount_selected);


        activityProUpgradeBinding.rlCardMonthly.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
        activityProUpgradeBinding.ivCheckIconMonthly.setVisibility(View.GONE);
        activityProUpgradeBinding.tvDiscountMonthly.setBackgroundResource(R.drawable.bg_discount_unselected);

        plan = 1;
    }

    @Override
    public void onBack() {
        finish();
    }
}