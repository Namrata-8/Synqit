package com.example.synqit.ui.editprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityEditProfileBinding;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements EditProfileNavigator{

    private ActivityEditProfileBinding activityEditProfileBinding;
    private EditProfileViewModel editProfileViewModel;
    private CardData cardData;
    private String selectedColor = "";
    private boolean isIconColor = false;
    private String bio = "";
    private ArrayList<DashboardViewModel> arrayList;
    private List<CardData> cardDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        activityEditProfileBinding.setViewModel(new EditProfileViewModel(this));
        activityEditProfileBinding.executePendingBindings();

        cardData = new Gson().fromJson(getIntent().getStringExtra("CardEditData"), CardData.class);

        initViewModel();

        activityEditProfileBinding.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isIconColor = isChecked;
            }
        });

        if (cardData != null){
            activityEditProfileBinding.etEnterBio.setText(cardData.getBio());
            activityEditProfileBinding.switchButton.setChecked(cardData.isIconColor());
            Log.e("ThemeColor", cardData.getThemeColor());
            switch (cardData.getThemeColor()){
                case "#000000":
                    selectBlack();
                    break;
                case "#5D5FEF":
                    selectBlue();
                    break;
                case "#EF5DA8":
                    selectPink();
                    break;
                case "#EB5757":
                    selectRed();
                    break;
                case "#F2994A":
                    selectOrange();
                    break;
                case "#F2C94C":
                    selectYellow();
                    break;
                case "#27AE60":
                    selectGreen();
                    break;
                case "#FFFFFF":
                    selectWhite();
                    break;
                default:
                    int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (nightModeFlags){
                        case Configuration.UI_MODE_NIGHT_YES:
                            selectWhite();
                            break;

                        case Configuration.UI_MODE_NIGHT_NO:

                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            selectBlack();
                            break;
                    }
            }
        }
    }
    private void initViewModel() {
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        editProfileViewModel.onUpdateCard().observe(this, new Observer<InsertCardResponse>() {
            @Override
            public void onChanged(InsertCardResponse insertCardResponse) {
                arrayList=new ArrayList<>();
                if (insertCardResponse != null){
                    if (insertCardResponse.isSuccess()){
                        Toast.makeText(EditProfileActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        cardDataList = new ArrayList<>();
                        cardDataList = insertCardResponse.getData();
                        for (int i = 0; i < cardDataList.size(); i++){
                            CardData cardData = cardDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(cardData);
                            arrayList.add(dashboardViewModel);
                        }
                        DashboardActivity.cardDrawerAdapter.swap(arrayList);
                        SessionManager.saveSelectedCardData(EditProfileActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(insertCardResponse.getData().get(SessionManager.readInt(EditProfileActivity.this, SessionManager.SELECTED_CARD_POSITION, 0))));
                        finish();
                    }else {
                        Toast.makeText(EditProfileActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void selectBlack() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#000000";
    }

    @Override
    public void selectBlue() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#5D5FEF";
    }

    @Override
    public void selectPink() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#EF5DA8";
    }

    @Override
    public void selectRed() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#EB5757";
    }

    @Override
    public void selectOrange() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#F2994A";
    }

    @Override
    public void selectYellow() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#F2C94C";
    }

    @Override
    public void selectGreen() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.VISIBLE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.GONE);
        selectedColor = "#27AE60";
    }

    @Override
    public void selectWhite() {
        activityEditProfileBinding.ivCheckBlack.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckBlue.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckPink.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckRed.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckOrange.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckYellow.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckGreen.setVisibility(View.GONE);
        activityEditProfileBinding.ivCheckWhite.setVisibility(View.VISIBLE);
        selectedColor = "#FFFFFF";
    }

    @Override
    public void onSaveChange() {
        bio = activityEditProfileBinding.etEnterBio.getText().toString();
        if(activityEditProfileBinding.etEnterBio.getText().toString().length() == 0){
            activityEditProfileBinding.etEnterBio.setError(getString(R.string.error_enter_bio));
            activityEditProfileBinding.etEnterBio.requestFocus();
        }else {
            if (cardData != null){
                ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                        cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                        cardData.getCoverPicture(), cardData.getPlan(), cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                        cardData.isPrivate(), cardData.isDirect(), cardData.getProfileName(), bio, selectedColor, isIconColor, cardData.getEmail(), cardData.getMobileNumber());

                editProfileViewModel.updateCard(paramInsertCard, EditProfileActivity.this);
            }
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    public void onView() {

    }
}