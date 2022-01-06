package com.example.synqit.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.synqit.Adapter.ViewPagerAdapter;
import com.example.synqit.Fragments.BusinessFragment1;
import com.example.synqit.Fragments.BusinessFragment2;
import com.example.synqit.Fragments.BusinessFragment3;
import com.example.synqit.Fragments.BusinessFragment4;
import com.example.synqit.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvLogin;
    private MaterialButton btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnBack);
        tvLogin = findViewById(R.id.tvLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, OtpVerificationActivity.class));
            }
        });
    }
}