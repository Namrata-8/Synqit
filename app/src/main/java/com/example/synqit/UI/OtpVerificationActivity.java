package com.example.synqit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.synqit.R;
import com.google.android.material.button.MaterialButton;

public class OtpVerificationActivity extends AppCompatActivity {

    private MaterialButton btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpVerificationActivity.this, RegisterAsActivity.class));
            }
        });
    }
}