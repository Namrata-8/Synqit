package com.example.synqit.ui.dashboard;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.adapters.CardDrawerAdapter;
import com.example.synqit.adapters.YourLinkActiveAdapter;
import com.example.synqit.databinding.ActivityDashboardBinding;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.dashboard.model.ParamGetCard;
import com.example.synqit.ui.editprofile.EditProfileActivity;
import com.example.synqit.utils.LoadingDialog;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, DashboardNavigator {

    public static NavController navController;
    public static CardDrawerAdapter cardDrawerAdapter;
    private ActivityDashboardBinding activityDashboardBinding;
    private DashboardViewModel dashboardViewModel;
    private Boolean isHome = true;

    @Override
    protected void onStart() {
        super.onStart();
        String nfcData = getIntent().getStringExtra("NfcData");
        if (nfcData != null && !nfcData.equals("")) {
            openNfcProfilleSheet(nfcData);
        }
    }

    private void openNfcProfilleSheet(String nfcData) {
        final Dialog dialog = new Dialog(DashboardActivity.this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SessionManager.readBoolean(DashboardActivity.this, SessionManager.IS_LIGHT_DARK, false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        activityDashboardBinding.setViewModel(new DashboardViewModel(this));
        activityDashboardBinding.executePendingBindings();
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        navController = Navigation.findNavController(DashboardActivity.this, R.id.container);
        activityDashboardBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        activityDashboardBinding.rvCards.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false);
        activityDashboardBinding.rvCards.setLayoutManager(linearLayoutManager);

        ParamGetCard paramGetCard = new ParamGetCard(SessionManager.readString(DashboardActivity.this, SessionManager.parentUserID, ""));
        /*if (this.getWindow().getDecorView().isShown()) {*/
            dashboardViewModel.getCardData(paramGetCard, DashboardActivity.this).observe(this, new Observer<ArrayList<DashboardViewModel>>() {
                @Override
                public void onChanged(ArrayList<DashboardViewModel> dashboardViewModels) {
                    if (!dashboardViewModels.isEmpty()) {
                        cardDrawerAdapter = new CardDrawerAdapter(DashboardActivity.this, dashboardViewModels);
                        activityDashboardBinding.rvCards.setAdapter(cardDrawerAdapter);

                        cardDrawerAdapter.setOnItemClickListener(new CardDrawerAdapter.OnItemChangeListener() {
                            @Override
                            public void onItemClick(int position) {
                                activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                                SessionManager.saveSelectedCardData(DashboardActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(dashboardViewModels.get(position).getCardData()));
                                SessionManager.writeInt(DashboardActivity.this, SessionManager.SELECTED_CARD_POSITION, position);
                                Log.e("Selected_Card", new Gson().toJson(dashboardViewModels.get(position).getCardData()));
                                activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
                                activityDashboardBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                                navController.navigate(R.id.navigation_home);
                            }

                            @Override
                            public void onSwipe(int position, boolean isSwipe) {
                                if (isSwipe) {
                                    activityDashboardBinding.btnDelete.setVisibility(View.VISIBLE);
                                    activityDashboardBinding.btnAdd.setVisibility(View.GONE);
                                } else {
                                    activityDashboardBinding.btnDelete.setVisibility(View.GONE);
                                    activityDashboardBinding.btnAdd.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onEditClick(int position) {
                                startActivity(new Intent(DashboardActivity.this, EditProfileActivity.class).putExtra("CardEditData", new Gson().toJson(dashboardViewModels.get(position).getCardData())));
                            }

                            @Override
                            public void onViewClick(int position) {
                                openProfileBottomSheet(dashboardViewModels.get(position).getCardData());
                            }
                        });
                    }
                }
            });
        /*}*/

        activityDashboardBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
                activityDashboardBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        activityDashboardBinding.rvCards.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                activityDashboardBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        if (getIntent().getBooleanExtra("ISFromConnection", false)) {
            isHome = false;
            activityDashboardBinding.btnEye.setVisibility(View.GONE);
            activityDashboardBinding.btnUpload.setVisibility(View.VISIBLE);
            activityDashboardBinding.btnShare.setVisibility(View.VISIBLE);
            activityDashboardBinding.heading.setText("Connections");
            activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.placeholder);
            navController.navigate(R.id.navigation_connect);
        }

        if (getIntent().getBooleanExtra("IsFromSettings", false)) {
            isHome = false;
            activityDashboardBinding.btnEye.setVisibility(View.GONE);
            activityDashboardBinding.btnUpload.setVisibility(View.GONE);
            activityDashboardBinding.btnShare.setVisibility(View.GONE);
            activityDashboardBinding.heading.setText("Settings");
            activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.navigation_setting);
            navController.navigate(R.id.navigation_setting);
        }
    }

    private void openProfileBottomSheet(CardData cardData) {
        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_profile);

        ImageView ivCover = dialog.findViewById(R.id.ivCover);
        ImageView ivLogo = dialog.findViewById(R.id.ivLogo);
        TextView tvName = dialog.findViewById(R.id.tvName);
        TextView tvLocation = dialog.findViewById(R.id.tvLocation);
        RecyclerView rvSocialSites = dialog.findViewById(R.id.rvSocialSites);
        TextView tvBio = dialog.findViewById(R.id.tvBio);
        ConstraintLayout constraint = dialog.findViewById(R.id.constraint);

        if (cardData != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.demo2);

            RequestOptions requestOptions2 = new RequestOptions();
            requestOptions2.placeholder(R.drawable.ic_add_profile);

            Glide.with(ivCover.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(cardData.getCoverPicture()).into(ivCover);

            Glide.with(ivLogo.getContext())
                    .setDefaultRequestOptions(requestOptions2)
                    .load(cardData.getDisplayPicture()).into(ivLogo);
            tvName.setText(cardData.getDisplayName());
            tvLocation.setText(cardData.getCountry());
            tvBio.setText(cardData.getBio());
            if (cardData.getThemeColor() != null) {
                if (!cardData.getThemeColor().equals("")) {
                    constraint.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66" + cardData.getThemeColor().substring(1))));
                }
            }
        }
        if (cardData != null) {
            ParamGetSubscribedList paramGetSubscribedList;
            if (cardData.getUserUID() != null) {
                paramGetSubscribedList = new ParamGetSubscribedList(cardData.getUserUID());
            } else {
                paramGetSubscribedList = new ParamGetSubscribedList(cardData.getUserID());
            }
            dashboardViewModel.getUserLinkLiveData(paramGetSubscribedList, DashboardActivity.this).observe(this, new Observer<ArrayList<DashboardViewModel>>() {
                @Override
                public void onChanged(ArrayList<DashboardViewModel> dashboardViewModels) {
                    if (!dashboardViewModels.isEmpty()) {
                        YourLinkActiveAdapter yourLinkActiveAdapter = new YourLinkActiveAdapter(DashboardActivity.this, dashboardViewModels);
                        rvSocialSites.setAdapter(yourLinkActiveAdapter);
                    }
                }
            });
        }

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                isHome = true;
                activityDashboardBinding.btnEye.setVisibility(View.VISIBLE);
                activityDashboardBinding.btnUpload.setVisibility(View.GONE);
                activityDashboardBinding.btnShare.setVisibility(View.VISIBLE);
                activityDashboardBinding.heading.setText("Home");
                navController.navigate(R.id.navigation_home);
                return true;

            case R.id.navigation_scan:
                isHome = false;
                activityDashboardBinding.btnEye.setVisibility(View.GONE);
                activityDashboardBinding.btnUpload.setVisibility(View.GONE);
                activityDashboardBinding.btnShare.setVisibility(View.VISIBLE);
                activityDashboardBinding.heading.setText("QR Code");
                navController.navigate(R.id.navigation_scan);
                return true;

            case R.id.navigation_graph:
                isHome = false;
                activityDashboardBinding.btnEye.setVisibility(View.GONE);
                activityDashboardBinding.btnUpload.setVisibility(View.GONE);
                activityDashboardBinding.btnShare.setVisibility(View.GONE);
                activityDashboardBinding.heading.setText("Insights");
                navController.navigate(R.id.navigation_graph);
                return true;

            case R.id.navigation_setting:
                isHome = false;
                activityDashboardBinding.btnEye.setVisibility(View.GONE);
                activityDashboardBinding.btnUpload.setVisibility(View.GONE);
                activityDashboardBinding.btnShare.setVisibility(View.GONE);
                activityDashboardBinding.heading.setText("Settings");
                navController.navigate(R.id.navigation_setting);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (activityDashboardBinding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
            activityDashboardBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            if (!isHome) {
                activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                isHome = true;
                activityDashboardBinding.btnEye.setVisibility(View.VISIBLE);
                activityDashboardBinding.btnUpload.setVisibility(View.GONE);
                activityDashboardBinding.btnShare.setVisibility(View.VISIBLE);
                activityDashboardBinding.heading.setText("SYNQIT");
                navController.navigate(R.id.navigation_home);
            } else {
                super.onBackPressed();
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                finish();
            }
        }
    }

    @Override
    public void addCard() {
        startActivity(new Intent(DashboardActivity.this, RegisterAsActivity.class).putExtra("AddNewCard", true));
    }

    @Override
    public void deleteCard() {

    }

    @Override
    public void onConnectClick() {
        isHome = false;
        activityDashboardBinding.btnEye.setVisibility(View.GONE);
        activityDashboardBinding.btnUpload.setVisibility(View.VISIBLE);
        activityDashboardBinding.btnShare.setVisibility(View.VISIBLE);
        activityDashboardBinding.heading.setText("Connections");
        activityDashboardBinding.bottomNavigationView.setSelectedItemId(R.id.placeholder);
        navController.navigate(R.id.navigation_connect);
    }

    @Override
    public void onEyeClick() {
        if (getSelectedCard(DashboardActivity.this) != null) {
            openProfileBottomSheet(getSelectedCard(DashboardActivity.this));
        }
    }

    @Override
    public void onUploadClick() {

    }

    @Override
    public void onShareClick() {
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = "https://google.com";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "https://google.com");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, "Share Profile Using"));
    }
}