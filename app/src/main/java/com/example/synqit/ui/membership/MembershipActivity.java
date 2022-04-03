package com.example.synqit.ui.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.adapters.PlansAdapter;
import com.example.synqit.adapters.ProSliderAdapter;
import com.example.synqit.databinding.ActivityMembershipBinding;
import com.example.synqit.models.SliderItems;
import com.example.synqit.ui.blockedconnections.BlockedConnectionViewModel;
import com.example.synqit.ui.proupgrade.ProUpgradeNavigator;
import com.example.synqit.ui.proupgrade.ProUpgradeViewModel;
import com.example.synqit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.Observer;

public class MembershipActivity extends AppCompatActivity implements ProUpgradeNavigator {

    private ActivityMembershipBinding activityMembershipBinding;
    private MembershipViewModel membershipViewModel;
    private ProUpgradeViewModel proUpgradeViewModel;
    private PlansAdapter plansAdapter;
    private int Plan = 0;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityMembershipBinding = DataBindingUtil.setContentView(this, R.layout.activity_membership);
        activityMembershipBinding.setViewModel(new ProUpgradeViewModel(this));
        activityMembershipBinding.executePendingBindings();
        proUpgradeViewModel = new ViewModelProvider(this).get(ProUpgradeViewModel.class);

        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.ic_demo_pro, getResources().getString(R.string.pro_title_1), getResources().getString(R.string.pro_desc_1)));
        sliderItems.add(new SliderItems(R.drawable.ic_demo_pro, getResources().getString(R.string.pro_title_1), getResources().getString(R.string.pro_desc_1)));
        sliderItems.add(new SliderItems(R.drawable.ic_demo_pro, getResources().getString(R.string.pro_title_1), getResources().getString(R.string.pro_desc_1)));

        activityMembershipBinding.viewPagerImageSlider.setAdapter(new ProSliderAdapter(sliderItems,activityMembershipBinding.viewPagerImageSlider, MembershipActivity.this));

        activityMembershipBinding.viewPagerImageSlider.setClipToPadding(false);
        activityMembershipBinding.viewPagerImageSlider.setClipChildren(false);
        activityMembershipBinding.viewPagerImageSlider.setOffscreenPageLimit(3);
        activityMembershipBinding.viewPagerImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        activityMembershipBinding.viewPagerImageSlider.setPageTransformer(compositePageTransformer);

        activityMembershipBinding.viewPagerImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // slide duration 2 seconds
            }
        });

        activityMembershipBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityMembershipBinding.btnGetPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MembershipActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.bottom_sheet_pro_upgrade);

                    RecyclerView rvPlans = dialog.findViewById(R.id.rvPlans);

                    proUpgradeViewModel.getPlansData(MembershipActivity.this).observe(MembershipActivity.this, new Observer<ArrayList<ProUpgradeViewModel>>() {
                        @Override
                        public void onChanged(ArrayList<ProUpgradeViewModel> proUpgradeViewModels) {
                            if (!proUpgradeViewModels.isEmpty()){
                                plansAdapter = new PlansAdapter(MembershipActivity.this, proUpgradeViewModels);
                                rvPlans.setAdapter(plansAdapter);

                                plansAdapter.setOnItemClickListener(new PlansAdapter.OnItemChangeListener() {
                                    @Override
                                    public void onItemClick(int position, int plan) {
                                        Plan = plan;
                                    }
                                });
                            }
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            activityMembershipBinding.viewPagerImageSlider.setCurrentItem(activityMembershipBinding.viewPagerImageSlider.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    public void onContinue() {

    }

    @Override
    public void onBack() {

    }
}