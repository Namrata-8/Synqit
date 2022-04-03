package com.example.synqit.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.synqit.R;
import com.example.synqit.adapters.SliderAdapter;
import com.example.synqit.models.SliderItems;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private DotsIndicator dots_indicator;
    private MaterialButton btnGetStarted;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

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
        setContentView(R.layout.activity_on_boarding);

        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        dots_indicator = findViewById(R.id.dots_indicator);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.raw.first, getResources().getString(R.string.on_boarding_1)));
        sliderItems.add(new SliderItems(R.raw.second, getResources().getString(R.string.on_boarding_2)));
        sliderItems.add(new SliderItems(R.raw.third, getResources().getString(R.string.on_boarding_3)));
        sliderItems.add(new SliderItems(R.raw.fourth, getResources().getString(R.string.on_boarding_3)));
        sliderItems.add(new SliderItems(R.raw.fifth, getResources().getString(R.string.on_boarding_3)));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2, OnBoardingActivity.this));
        dots_indicator.setViewPager2(viewPager2);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        /*CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);*/

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SessionManager.saveOnBoardingView(OnBoardingActivity.this, false);
                startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));

                Log.e("UserId", SessionManager.readString(OnBoardingActivity.this, SessionManager.BR_basicRegistratinUID, "*"));
                finish();
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 6000); // slide duration 2 seconds
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 16000);
    }
}