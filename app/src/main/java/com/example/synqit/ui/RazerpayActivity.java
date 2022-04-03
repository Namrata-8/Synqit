package com.example.synqit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

import com.example.synqit.R;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.SessionManager;
import com.razorpay.PaymentResultListener;

public class RazerpayActivity extends AppCompatActivity implements PaymentResultListener {

    private double amount = 0;
    private CardData cardData;
    private boolean isAddNewCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razerpay);

        isAddNewCard = getIntent().getBooleanExtra("AddNewCard", false);
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}