package com.example.synqit.ui.addlink;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.adapters.AddLinkFilterAdapter;
import com.example.synqit.adapters.AllAppLinkAdapter;
import com.example.synqit.adapters.InfluencersAdapter;
import com.example.synqit.adapters.MostPopularLinkAdapter;
import com.example.synqit.adapters.SwipeCardAdapter;
import com.example.synqit.adapters.YourLinkActiveAdapter;
import com.example.synqit.databinding.ActivityAddLinkBinding;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.SubscribedData;
import com.example.synqit.ui.addlink.model.AddSocialMediaResponse;
import com.example.synqit.ui.addlink.model.CommonLinkData;
import com.example.synqit.ui.addlink.model.ParamAddSocialMedia;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.proupgrade.ProUpgradeViewModel;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddLinkActivity extends AppCompatActivity implements AddLinkNavigator{

    private ActivityAddLinkBinding activityAddLinkBinding;
    private AddLinkViewModel addLinkViewModel;
    private AllAppLinkAdapter allAppLinkAdapter;
    private boolean isHideKeyboard = true;
    /*private CardData cardData;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        View view = window.getDecorView();
        new WindowInsetsControllerCompat(window, view).setAppearanceLightStatusBars(false);
        window.setStatusBarColor(MaterialColors.getColor(view, R.attr.text_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        activityAddLinkBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_link);
        activityAddLinkBinding.setViewModel(new AddLinkViewModel(this));
        activityAddLinkBinding.executePendingBindings();

        initViewModel();

    }

    private void initViewModel() {
        addLinkViewModel = new ViewModelProvider(this).get(AddLinkViewModel.class);

        addLinkViewModel.getLinks(AddLinkActivity.this);

        addLinkViewModel.getMostPopularLink().observe(this, new Observer<ArrayList<AddLinkViewModel>>() {
            @Override
            public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                if (!addLinkViewModels.isEmpty()){
                    for (int i=0; i<addLinkViewModels.size(); i++) {
                        Log.e("MostPopluarLink", addLinkViewModels.get(i).getCommonLinkData().getTitle());
                        MostPopularLinkAdapter mostPopularLinkAdapter = new MostPopularLinkAdapter(AddLinkActivity.this, addLinkViewModels);
                        activityAddLinkBinding.rvMostPopular.setAdapter(mostPopularLinkAdapter);
                        mostPopularLinkAdapter.setOnItemClickListener(new MostPopularLinkAdapter.OnItemClickListener() {
                            @Override
                            public void onItemAddLinkClick(int position) {
                                openAddLinkBottomSheet(position, addLinkViewModels, false, 0);
                            }
                        });
                    }

                    activityAddLinkBinding.tvMostPopular.setVisibility(View.VISIBLE);
                    activityAddLinkBinding.rvMostPopular.setVisibility(View.VISIBLE);
                }else {
                    activityAddLinkBinding.tvMostPopular.setVisibility(View.GONE);
                    activityAddLinkBinding.rvMostPopular.setVisibility(View.GONE);
                }
            }
        });

        addLinkViewModel.getInfluencersLink().observe(this, new Observer<ArrayList<AddLinkViewModel>>() {
            @Override
            public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                if (!addLinkViewModels.isEmpty()){
                    for (int i=0; i<addLinkViewModels.size(); i++) {
                        Log.e("MostInfluencersLink", addLinkViewModels.get(i).getCommonLinkData().getTitle());
                        InfluencersAdapter influencersAdapter = new InfluencersAdapter(AddLinkActivity.this, addLinkViewModels);
                        activityAddLinkBinding.rv2.setAdapter(influencersAdapter);
                        influencersAdapter.setOnItemClickListener(new InfluencersAdapter.OnItemClickListener() {
                            @Override
                            public void onItemAddLinkClick(int position) {
                                openAddLinkBottomSheet(position, addLinkViewModels, false, 0);
                            }
                        });
                    }

                    activityAddLinkBinding.constraintInfluencers.setVisibility(View.VISIBLE);
                }else {
                    activityAddLinkBinding.constraintInfluencers.setVisibility(View.GONE);
                }
            }
        });

        addLinkViewModel.getCategoryLinkFirst().observe(this, new Observer<ArrayList<AddLinkViewModel>>() {
            @Override
            public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                if (!addLinkViewModels.isEmpty()){
                    for (int i=0; i<addLinkViewModels.size(); i++) {
                        Log.e("CategoriesLink", addLinkViewModels.get(i).getCategories().getTitle());
                        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter(AddLinkActivity.this, addLinkViewModels);
                        activityAddLinkBinding.rv.setAdapter(swipeCardAdapter);
                        swipeCardAdapter.setOnItemClickListener(new SwipeCardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemAddLinkClick(int positionApp, int position) {
                                openAddLinkBottomSheet(position, addLinkViewModels, true, positionApp);
                            }
                        });
                    }

                    activityAddLinkBinding.constraintSwipe1.setVisibility(View.VISIBLE);
                }else {
                    activityAddLinkBinding.constraintSwipe1.setVisibility(View.GONE);
                }
            }
        });

        addLinkViewModel.getCategoryLinkSecond().observe(this, new Observer<ArrayList<AddLinkViewModel>>() {
            @Override
            public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                if (!addLinkViewModels.isEmpty()){
                    for (int i=0; i<addLinkViewModels.size(); i++) {
                        Log.e("CategoriesLink", addLinkViewModels.get(i).getCategories().getTitle());
                        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter(AddLinkActivity.this, addLinkViewModels);
                        activityAddLinkBinding.rv4.setAdapter(swipeCardAdapter);
                        swipeCardAdapter.setOnItemClickListener(new SwipeCardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemAddLinkClick(int positionApp, int position) {
                                Log.e("SwipeCardItemClick2", addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getTitle() + "\n" + addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getProfileBaseURL());
                                openAddLinkBottomSheet(position, addLinkViewModels, true, positionApp);
                            }
                        });
                    }

                    activityAddLinkBinding.constraintSwipe2.setVisibility(View.VISIBLE);
                }else {
                    activityAddLinkBinding.constraintSwipe2.setVisibility(View.GONE);
                }
            }
        });

        addLinkViewModel.getAllAppsLink().observe(this, new Observer<ArrayList<AddLinkViewModel>>() {
            @Override
            public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                if (!addLinkViewModels.isEmpty()){
                    for (int i=0; i<addLinkViewModels.size(); i++) {
                        Log.e("MostInfluencersLink", addLinkViewModels.get(i).getCommonLinkData().getTitle());
                        allAppLinkAdapter = new AllAppLinkAdapter(AddLinkActivity.this, addLinkViewModels);
                        activityAddLinkBinding.rvSearch.setAdapter(allAppLinkAdapter);
                        allAppLinkAdapter.setOnItemClickListener(new AllAppLinkAdapter.OnItemClickListener() {
                            @Override
                            public void onItemAddLinkClick(int position) {
                                openAddLinkBottomSheet(position, addLinkViewModels, false, 0);
                            }
                        });
                    }
                }
            }
        });

        activityAddLinkBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isHideKeyboard = false;
                if (s.length() == 0){
                    hideKeyboard();
                    activityAddLinkBinding.rvSearch.setVisibility(View.GONE);
                }else {
                    activityAddLinkBinding.rvSearch.setVisibility(View.VISIBLE);
                    allAppLinkAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityAddLinkBinding.llFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddLinkActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_add_link_filter);
                RecyclerView rvFilter = dialog.findViewById(R.id.rvFilter);
                rvFilter.setHasFixedSize(true);
                rvFilter.setLayoutManager(new LinearLayoutManager(AddLinkActivity.this, LinearLayoutManager.VERTICAL, false));

                addLinkViewModel.getFilterCategoriesList().observe(AddLinkActivity.this, new Observer<ArrayList<AddLinkViewModel>>() {
                    @Override
                    public void onChanged(ArrayList<AddLinkViewModel> addLinkViewModels) {
                        if (!addLinkViewModels.isEmpty()){
                            AddLinkFilterAdapter addLinkFilterAdapter = new AddLinkFilterAdapter(AddLinkActivity.this, addLinkViewModels);
                            rvFilter.setAdapter(addLinkFilterAdapter);

                            addLinkFilterAdapter.setOnItemClickListener(new AddLinkFilterAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    activityAddLinkBinding.rvSearch.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        addLinkViewModel.onSubscribeSocialMedia().observe(this, new Observer<AddSocialMediaResponse>() {
            @Override
            public void onChanged(AddSocialMediaResponse addSocialMediaResponse) {
                if (addSocialMediaResponse != null){
                    if (addSocialMediaResponse.isSuccess()){
                        startActivity(new Intent(AddLinkActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }
                }
            }
        });
    }

    private void openAddLinkBottomSheet(int position, ArrayList<AddLinkViewModel> addLinkViewModels, boolean linkCategory, int positionApp) {
        final Dialog dialog = new Dialog(AddLinkActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_add_link);

        TextView tvLinkName = dialog.findViewById(R.id.tvLinkName);
        ImageButton ivInfo = dialog.findViewById(R.id.ivInfo);
        ImageButton imageButton = dialog.findViewById(R.id.imageButton);
        ImageButton btnEye = dialog.findViewById(R.id.btnEye);
        EditText etTitle = dialog.findViewById(R.id.etTitle);
        EditText etUserName = dialog.findViewById(R.id.etUserName);
        ImageView ivLogo = dialog.findViewById(R.id.ivLogo);
        MaterialButton btnSaveLink = dialog.findViewById(R.id.btnSaveLink);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_facebook);
        if (linkCategory) {
            tvLinkName.setText(addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getTitle());
            etTitle.setText(addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getTitle());
            if(SessionManager.readBoolean(AddLinkActivity.this, SessionManager.IS_LIGHT_DARK, false)) {
                Glide.with(ivLogo.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getLogoDark()).into(ivLogo);
            }else {
                Glide.with(ivLogo.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getLogoLight()).into(ivLogo);
            }
        }else {
            tvLinkName.setText(addLinkViewModels.get(position).getCommonLinkData().getTitle());
            etTitle.setText(addLinkViewModels.get(position).getCommonLinkData().getTitle());
            if(SessionManager.readBoolean(AddLinkActivity.this, SessionManager.IS_LIGHT_DARK, false)) {
                Glide.with(ivLogo.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(addLinkViewModels.get(position).getCommonLinkData().getLogoDark()).into(ivLogo);
            }else {
                Glide.with(ivLogo.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(addLinkViewModels.get(position).getCommonLinkData().getLogoLight()).into(ivLogo);
            }
        }
        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSaveLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSelectedCard(AddLinkActivity.this) != null) {
                    if (etUserName.getText().toString().length() == 0) {
                        etUserName.setError(getString(R.string.error_username));
                        etUserName.requestFocus();
                    } else if (etTitle.getText().toString().length() == 0) {
                        etTitle.setError(getString(R.string.error_title));
                        etTitle.requestFocus();
                    } else {
                        ParamAddSocialMedia paramAddSocialMedia;
                        if (linkCategory) {
                            paramAddSocialMedia = new ParamAddSocialMedia(getSelectedCard(AddLinkActivity.this).getUserID(), Integer.parseInt(addLinkViewModels.get(position).getCategories().getAppList().get(positionApp).getId()),
                                    etUserName.getText().toString(), etTitle.getText().toString());

                        } else {
                            paramAddSocialMedia = new ParamAddSocialMedia(getSelectedCard(AddLinkActivity.this).getUserID(), Integer.parseInt(addLinkViewModels.get(position).getCommonLinkData().getId()),
                                    etUserName.getText().toString(), etTitle.getText().toString());

                        }
                        addLinkViewModel.subscribeSocialMedia(paramAddSocialMedia, AddLinkActivity.this);
                    }
                }
            }
        });

        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSelectedCard(AddLinkActivity.this) != null){

                        openProfileBottomSheet(getSelectedCard(AddLinkActivity.this), position, addLinkViewModels, linkCategory, positionApp);

                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void openProfileBottomSheet(CardData cardData, int positionCat, ArrayList<AddLinkViewModel> addLinkViewModels, boolean linkCategory, int positionApp) {
        final Dialog dialog = new Dialog(AddLinkActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_profile);

        ImageView ivCover = dialog.findViewById(R.id.ivCover);
        ImageView ivLogo = dialog.findViewById(R.id.ivLogo);
        TextView tvName = dialog.findViewById(R.id.tvName);
        TextView tvLocation = dialog.findViewById(R.id.tvLocation);
        RecyclerView rvSocialSites = dialog.findViewById(R.id.rvSocialSites);
        TextView tvBio = dialog.findViewById(R.id.tvBio);
        ConstraintLayout constraint = dialog.findViewById(R.id.constraint);

        if(cardData != null) {
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
            if(cardData.getThemeColor() != null) {
                if (!cardData.getThemeColor().equals("")) {
                    constraint.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66" + cardData.getThemeColor().substring(1))));
                }
            }
        }
        if (cardData != null) {
            ParamGetSubscribedList paramGetSubscribedList;
            if (cardData.getUserUID() != null) {
                paramGetSubscribedList = new ParamGetSubscribedList(cardData.getUserUID());
            }else {
                paramGetSubscribedList = new ParamGetSubscribedList(cardData.getUserID());
            }
            addLinkViewModel.getUserLinkLiveData(paramGetSubscribedList, AddLinkActivity.this, positionCat, addLinkViewModels, linkCategory, positionApp).observe(this, new Observer<ArrayList<DashboardViewModel>>() {
                @Override
                public void onChanged(ArrayList<DashboardViewModel> dashboardViewModels) {
                    if (!dashboardViewModels.isEmpty()) {
                        YourLinkActiveAdapter yourLinkActiveAdapter = new YourLinkActiveAdapter(AddLinkActivity.this, dashboardViewModels);
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

    private void openInfoBottomSheet() {
        final Dialog dialog2 = new Dialog(AddLinkActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.setContentView(R.layout.dialog_info_add_link);

        MaterialButton btnDone = dialog2.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void goToBack() {
        if (!isHideKeyboard) {
            hideKeyboard();
        }else {
            startActivity(new Intent(AddLinkActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToBack();
    }

    public void hideKeyboard() {
        try {
            isHideKeyboard = true;
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }
}