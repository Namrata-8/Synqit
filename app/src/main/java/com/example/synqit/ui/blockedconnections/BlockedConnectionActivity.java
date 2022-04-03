package com.example.synqit.ui.blockedconnections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.adapters.BlockedConnectionAdapter;
import com.example.synqit.databinding.ActivityBlockedConnectionBinding;
import com.example.synqit.fragments.connectionsfragment.model.ParamGetConnection;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.howtouse.HowToUseActivity;
import com.example.synqit.utils.SessionManager;

import java.util.ArrayList;

public class BlockedConnectionActivity extends AppCompatActivity {

    private ActivityBlockedConnectionBinding activityBlockedConnectionBinding;
    private BlockedConnectionViewModel blockedConnectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityBlockedConnectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_blocked_connection);
        activityBlockedConnectionBinding.setViewModel(new BlockedConnectionViewModel());
        activityBlockedConnectionBinding.executePendingBindings();
        blockedConnectionViewModel = new ViewModelProvider(this).get(BlockedConnectionViewModel.class);

        if (!SessionManager.readString(BlockedConnectionActivity.this, SessionManager.parentUserID, "").equals("")) {
            ParamGetConnection paramGetConnection = new ParamGetConnection(SessionManager.readString(BlockedConnectionActivity.this, SessionManager.parentUserID, ""));
            blockedConnectionViewModel.getBlockConnections(paramGetConnection, BlockedConnectionActivity.this).observe(this, new Observer<ArrayList<BlockedConnectionViewModel>>() {
                @Override
                public void onChanged(ArrayList<BlockedConnectionViewModel> blockedConnectionViewModels) {
                    if (!blockedConnectionViewModels.isEmpty()){
                        activityBlockedConnectionBinding.rvBlockedConnections.setAdapter(new BlockedConnectionAdapter(BlockedConnectionActivity.this, blockedConnectionViewModels));
                    }
                }
            });
        }

        activityBlockedConnectionBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlockedConnectionActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BlockedConnectionActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
        finish();
    }
}