package com.example.synqit.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.proupgrade.ProUpgradeActivity;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.livechatinc.inappchat.ChatWindowConfiguration;

import kotlin.jvm.internal.Intrinsics;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMER = 3000;
    private CardData cardData;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        setContentView(R.layout.activity_splash);

        if (new Gson().fromJson(SessionManager.readSelectedCardData(SplashActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class) != null) {
            cardData = new Gson().fromJson(SessionManager.readSelectedCardData(SplashActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class);
        }

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SessionManager.getAutoLogin(SplashActivity.this)){
                    if (cardData != null){
                        if (cardData.isBusiness()){
                            if (cardData.getPlan() == 0) {
                                startActivity(new Intent(SplashActivity.this, ProUpgradeActivity.class).putExtra("AddNewCard", false).putExtra("ISFromSplash", true));
                            }else {
                                startActivity(new Intent(SplashActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", readNfc()).putExtra("IsFromSettings", false));
                            }
                        }else {
                            startActivity(new Intent(SplashActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", readNfc()).putExtra("IsFromSettings", false));
                        }
                    }
                }else {
                    if (SessionManager.getOnBoardingView(SplashActivity.this)){
                        startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                }
                finish();
            }
        },SPLASH_TIMER);
    }

    private void openProfileBottomSheet(String nfcData) {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_profile);
        TextView tvBio = dialog.findViewById(R.id.tvBio);

        tvBio.setText(nfcData);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private final String readNfc() {
        String mAction = getIntent().getAction();
        Uri data = getIntent().getData();
        if (data == null || !Intrinsics.areEqual("android.intent.action.VIEW", mAction) && !Intrinsics.areEqual("android.nfc.action.NDEF_DISCOVERED", mAction)) {
            return null;
        } else {
            Toast.makeText(this,data.toString(), Toast.LENGTH_LONG).show();
            return data.toString();
        }
    }
}