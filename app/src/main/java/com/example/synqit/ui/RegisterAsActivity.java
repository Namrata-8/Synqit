package com.example.synqit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.synqit.adapters.ViewPagerAdapter;
import com.example.synqit.R;
import com.example.synqit.customeviews.CustomViewPager;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class RegisterAsActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private CircularProgressIndicator ProgressIndicator;
    private TextView tvSteps, tvBusinessAccount;
    private CustomViewPager viewPager;
    int currentPos;
    private ViewPagerAdapter viewPagerAdapter;
    private String UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        btnBack = findViewById(R.id.btnBack);
        ProgressIndicator = findViewById(R.id.ProgressIndicator);
        tvSteps = findViewById(R.id.tvSteps);
        tvBusinessAccount = findViewById(R.id.tvBusinessAccount);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPos != 0){
                    if (UserType.equalsIgnoreCase("Individual")) {
                        if (currentPos >= 4) {
                            if (currentPos == 4) {
                                viewPager.setCurrentItem(0);
                            }
                            viewPager.setCurrentItem(currentPos - 1);
                        }
                        else
                            finish();
                    }else {
                        viewPager.setCurrentItem(currentPos - 1);
                    }
                }else {
                    finish();
                }
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void setUserType(String type){
        UserType = type;
    }

    public void setPagerFragment(int a)
    {
        viewPager.setCurrentItem(a);
    }

    public void setProgressIndicator(int a, String text)
    {
        ProgressIndicator.setProgress(a);
        tvSteps.setText(text);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPos = position;
            if (position == 0) {
                ProgressIndicator.setProgress(25);
                tvSteps.setText("1/4");
                tvBusinessAccount.setVisibility(View.INVISIBLE);
                tvBusinessAccount.setText("Business account");
            } else if (position == 1) {
                ProgressIndicator.setProgress(50);
                tvSteps.setText("2/4");
                tvBusinessAccount.setVisibility(View.VISIBLE);
                tvBusinessAccount.setText("Business account");
            } else if (position == 2){
                ProgressIndicator.setProgress(75);
                tvSteps.setText("3/4");
                tvBusinessAccount.setVisibility(View.VISIBLE);
                tvBusinessAccount.setText("Business account");
            } else if (position == 3){
                ProgressIndicator.setProgress(100);
                tvSteps.setText("4/4");
                tvBusinessAccount.setVisibility(View.VISIBLE);
                tvBusinessAccount.setText("Business account");
            } else if (position == 4){
                ProgressIndicator.setProgress(66);
                tvSteps.setText("2/3");
                tvBusinessAccount.setVisibility(View.VISIBLE);
                tvBusinessAccount.setText("Individual");
            }else {
                ProgressIndicator.setProgress(100);
                tvSteps.setText("3/3");
                tvBusinessAccount.setVisibility(View.VISIBLE);
                tvBusinessAccount.setText("Individual");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {
        if (currentPos != 0){
            if (UserType.equalsIgnoreCase("Individual")) {
                if (currentPos >= 4) {
                    if (currentPos == 4) {
                        viewPager.setCurrentItem(0);
                    }
                    viewPager.setCurrentItem(currentPos - 1);
                }
                else
                    finish();
            }else {
                viewPager.setCurrentItem(currentPos - 1);
            }
        }else {
            finish();
        }
    }
}