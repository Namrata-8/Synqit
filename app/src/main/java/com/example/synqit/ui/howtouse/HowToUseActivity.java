package com.example.synqit.ui.howtouse;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.synqit.R;
import com.example.synqit.adapters.ActivateProductAdapter;
import com.example.synqit.adapters.HowToUseAdapter;
import com.example.synqit.databinding.ActivityHowToUseBinding;
import com.example.synqit.ui.account.AccountActivity;
import com.example.synqit.ui.activateproduct.ActivateProductActivity;
import com.example.synqit.ui.activateproduct.ActivateProductViewModel;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.utils.SessionManager;
import com.livechatinc.inappchat.ChatWindowActivity;
import com.livechatinc.inappchat.ChatWindowConfiguration;

import java.util.ArrayList;

public class HowToUseActivity extends AppCompatActivity {

    private ActivityHowToUseBinding activityHowToUseBinding;
    private HowToUseViewModel howToUseViewModel;
    String licenceNumber = "1520";
    ChatWindowConfiguration windowConfig = new ChatWindowConfiguration.Builder()
            .setLicenceNumber(licenceNumber)
            .build();
    ActivityResultLauncher<Intent> editConfigActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityHowToUseBinding = DataBindingUtil.setContentView(this, R.layout.activity_how_to_use);
        activityHowToUseBinding.setViewModel(new HowToUseViewModel());
        activityHowToUseBinding.executePendingBindings();
        howToUseViewModel = new ViewModelProvider(this).get(HowToUseViewModel.class);

        editConfigActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.i("TAG", "coming back from activity" + result.getData());
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            windowConfig = (ChatWindowConfiguration) data.getSerializableExtra("config");
                        }
                    }
                });

        howToUseViewModel.getInstructions(HowToUseActivity.this).observe(this, new Observer<ArrayList<HowToUseViewModel>>() {
            @Override
            public void onChanged(ArrayList<HowToUseViewModel> howToUseViewModels) {
                if (!howToUseViewModels.isEmpty()){
                    activityHowToUseBinding.rvHowToUse.setAdapter(new HowToUseAdapter(HowToUseActivity.this, howToUseViewModels));
                }
            }
        });

        activityHowToUseBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HowToUseActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
                finish();
            }
        });

        activityHowToUseBinding.ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChatActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HowToUseActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
        finish();
    }

    private void startChatActivity() {
        Intent intent = new Intent(this, ChatWindowActivity.class);
        intent.putExtras(windowConfig.asBundle());
        startActivity(intent);
    }
}