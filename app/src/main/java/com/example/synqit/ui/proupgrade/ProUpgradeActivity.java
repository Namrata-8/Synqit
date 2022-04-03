package com.example.synqit.ui.proupgrade;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.adapters.PlansAdapter;
import com.example.synqit.databinding.ActivityProUpgradeBinding;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.ui.account.AccountActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.login.LoginActivity;
import com.example.synqit.ui.login.model.LoginData;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.ui.register.model.RegisterData;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProUpgradeActivity extends AppCompatActivity implements ProUpgradeNavigator , PaymentResultListener {

    private ActivityProUpgradeBinding activityProUpgradeBinding;
    private ProUpgradeViewModel proUpgradeViewModel;
    private PlansAdapter plansAdapter;

    private int Plan = 0;
    private double planAmount = 0;
    private boolean isAddNewCard = false;
    private boolean isFromSplash = false;
    private boolean isFromScan = false;
    private String qrCode = "";
    private boolean isPrivate = false;
    private boolean isDirect = false;
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
        activityProUpgradeBinding = DataBindingUtil.setContentView(this, R.layout.activity_pro_upgrade);
        activityProUpgradeBinding.setViewModel(new ProUpgradeViewModel(this));
        activityProUpgradeBinding.executePendingBindings();

        isAddNewCard = getIntent().getBooleanExtra("AddNewCard", false);
        isFromSplash = getIntent().getBooleanExtra("ISFromSplash", false);
        isFromScan = getIntent().getBooleanExtra("ISFromScan", false);

        initViewModel();

    }

    private void initViewModel() {
        proUpgradeViewModel = new ViewModelProvider(this).get(ProUpgradeViewModel.class);

        proUpgradeViewModel.getPlansData(ProUpgradeActivity.this).observe(this, new Observer<ArrayList<ProUpgradeViewModel>>() {
            @Override
            public void onChanged(ArrayList<ProUpgradeViewModel> proUpgradeViewModels) {
                if (!proUpgradeViewModels.isEmpty()){
                    plansAdapter = new PlansAdapter(ProUpgradeActivity.this, proUpgradeViewModels);
                    activityProUpgradeBinding.rvPlans.setAdapter(plansAdapter);
                    String selectedPlan = proUpgradeViewModels.get(0).getPlanPrice();
                    planAmount = Double.parseDouble(selectedPlan.replace("₹ ",""));
                    Plan = Integer.parseInt(proUpgradeViewModels.get(0).getId());

                    Log.e("PlanID", Plan + "\n" + planAmount);

                    plansAdapter.setOnItemClickListener(new PlansAdapter.OnItemChangeListener() {
                        @Override
                        public void onItemClick(int position, int plan) {
                            Plan = plan;
                            String selectedPlan = proUpgradeViewModels.get(position).getPlanPrice();
                            planAmount = Double.parseDouble(selectedPlan.replace("₹ ",""));
                        }
                    });
                }
            }
        });

        proUpgradeViewModel.onFullRegister().observe(this, new Observer<FullRegisterResponse>() {
            @Override
            public void onChanged(FullRegisterResponse fullRegisterResponse) {
                if (fullRegisterResponse != null) {
                    if (fullRegisterResponse.isSuccess()) {
                        SessionManager.saveFullRegisterData(ProUpgradeActivity.this, SessionManager.FR_FullRegisterData, fullRegisterResponse.getFullRegisterData());
                        SessionManager.writeString(ProUpgradeActivity.this, SessionManager.FR_userID, fullRegisterResponse.getFullRegisterData().getUserID());
                        SessionManager.writeString(ProUpgradeActivity.this, SessionManager.parentUserID, fullRegisterResponse.getFullRegisterData().getParentUserID());
                        SessionManager.saveSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(fullRegisterResponse.getFullRegisterData()));
                        Toast.makeText(ProUpgradeActivity.this, fullRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        SessionManager.saveAutoLogin(ProUpgradeActivity.this, true);

                        if(null != SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "") && !SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "").isEmpty()){
                            File file = new File(SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""));
                            file.delete();
                            SessionManager.writeString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "");
                        }

                        if(null != SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "") && !SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "").isEmpty()){
                            File file = new File(SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""));
                            file.delete();
                            SessionManager.writeString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "");
                        }

                        startActivity(new Intent(ProUpgradeActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }else {
                        Toast.makeText(ProUpgradeActivity.this, fullRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        proUpgradeViewModel.onInsertCard().observe(this, new Observer<InsertCardResponse>() {
            @Override
            public void onChanged(InsertCardResponse insertCardResponse) {
                if (insertCardResponse != null){
                    if (insertCardResponse.isSuccess()){
                        Toast.makeText(ProUpgradeActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if(null != SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "") && !SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "").isEmpty()){
                            File file = new File(SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""));
                            file.delete();
                            SessionManager.writeString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "");
                        }

                        if(null != SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "") && !SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "").isEmpty()){
                            File file = new File(SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""));
                            file.delete();
                            SessionManager.writeString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "");
                        }

                        startActivity(new Intent(ProUpgradeActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }else {
                        Toast.makeText(ProUpgradeActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        proUpgradeViewModel.onUpdateCard().observe(this, new Observer<InsertCardResponse>() {
            @Override
            public void onChanged(InsertCardResponse insertCardResponse) {
                arrayList=new ArrayList<>();
                if (insertCardResponse != null){
                    if (insertCardResponse.isSuccess()){
                        Toast.makeText(ProUpgradeActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        cardDataList = new ArrayList<>();
                        cardDataList = insertCardResponse.getData();
                        for (int i = 0; i < cardDataList.size(); i++){
                            CardData cardData = cardDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(cardData);
                            arrayList.add(dashboardViewModel);
                        }
                        DashboardActivity.cardDrawerAdapter.swap(arrayList);
                        SessionManager.saveSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(insertCardResponse.getData().get(SessionManager.readInt(ProUpgradeActivity.this, SessionManager.SELECTED_CARD_POSITION, 0))));
                        if (isFromScan){
                            finish();
                        }else {
                            startActivity(new Intent(ProUpgradeActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        }
                    }else {
                        Toast.makeText(ProUpgradeActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onContinue() {
        Log.e("SelectedPlan", Plan + "");
        Log.e("RegisterUser", SessionManager.readString(ProUpgradeActivity.this, SessionManager.REGISTER_AS, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, "*") + "\n" +
                SessionManager.readString(ProUpgradeActivity.this, SessionManager.BR_basicRegistratinUID, "*"));
        if (planAmount != 0){
            startPayment(planAmount);
        }
    }

    private void startPayment(double planAmount) {
        String email = "";
        String phone = "";
        if (SessionManager.readBasicRegisterData(ProUpgradeActivity.this, SessionManager.BR_BasicRegisterData, "") != null) {
            RegisterData basicRegisterData = SessionManager.readBasicRegisterData(ProUpgradeActivity.this, SessionManager.BR_BasicRegisterData, "");
            if (basicRegisterData.getEmail()== null){
                email = "test@gmail.com";
            }else {
                email = basicRegisterData.getEmail();
            }if(basicRegisterData.getMobileNumber()== null){
                phone = "+919898987878";
            }else {
                phone = basicRegisterData.getMobileNumber();
            }
            final Activity activity = this;
            final Checkout co = new Checkout();
            //co.setKeyID(paymentItem.getmAttributes());
            co.setKeyID("rzp_test_yLYtFf2Dx73rpt");
            try {
                JSONObject options = new JSONObject();
                options.put("name", getResources().getString(R.string.app_name));
                options.put("currency", "INR");
                double total = planAmount;
                total = total * 100;
                options.put("amount", total);
                JSONObject preFill = new JSONObject();
                preFill.put("email", email);
                preFill.put("contact", phone);
                options.put("prefill", preFill);
                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else if (SessionManager.readLoginData(ProUpgradeActivity.this, SessionManager.L_LoginData, "") != null){
            LoginData loginData = SessionManager.readLoginData(ProUpgradeActivity.this, SessionManager.L_LoginData, "");
            if (loginData.getEmail() == null){
                email = "test@gmail.com";
            }else {
                email = loginData.getEmail();
            }if(loginData.getMobileNumber() == null){
                phone = "+919898987878";
            }else {
                phone = loginData.getMobileNumber();
            }
            final Activity activity = this;
            final Checkout co = new Checkout();
            //co.setKeyID(paymentItem.getmAttributes());
            co.setKeyID("rzp_test_yLYtFf2Dx73rpt");
            try {
                JSONObject options = new JSONObject();
                options.put("name", getResources().getString(R.string.app_name));
                options.put("currency", "INR");
                double total = planAmount;
                total = total * 100;
                options.put("amount", total);
                JSONObject preFill = new JSONObject();
                preFill.put("email", email);
                preFill.put("contact", phone);
                options.put("prefill", preFill);
                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    public void onPaymentSuccess(String s) {
        if (isFromSplash){
            if (getSelectedCard(ProUpgradeActivity.this) != null) {
                CardData cardData = getSelectedCard(ProUpgradeActivity.this);
                ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                        cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                        cardData.getCoverPicture(), Plan, cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                        cardData.isPrivate(), cardData.isDirect(), cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                proUpgradeViewModel.updateCard(paramInsertCard, ProUpgradeActivity.this);
            }

        }else if (isFromScan){
            if (getSelectedCard(ProUpgradeActivity.this) != null) {
                CardData cardData = getSelectedCard(ProUpgradeActivity.this);
                ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                        cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                        cardData.getCoverPicture(), Plan, cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                        cardData.isPrivate(), cardData.isDirect(), cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                proUpgradeViewModel.updateCard(paramInsertCard, ProUpgradeActivity.this);
            }
        }else if (isAddNewCard){
            Log.e("IsADDNewCard", isAddNewCard + "");

            if (new Gson().fromJson(SessionManager.readSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class).getQrCode() != null) {
                qrCode = new Gson().fromJson(SessionManager.readSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class).getQrCode();
            }
            isPrivate = new Gson().fromJson(SessionManager.readSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class).isPrivate();
            isDirect = new Gson().fromJson(SessionManager.readSelectedCardData(ProUpgradeActivity.this, SessionManager.Selected_Card_Data, ""), CardData.class).isDirect();

            if (SessionManager.readString(ProUpgradeActivity.this, SessionManager.REGISTER_AS, "").equals("Business")) {
                ParamInsertCard paramInsertCard = new ParamInsertCard(SessionManager.readString(ProUpgradeActivity.this, SessionManager.parentUserID, ""),true,
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                        "01/01/2022","NA", Integer.parseInt(SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, "")),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                        Plan,qrCode, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COUNTRY_NAME, ""), "NA",
                        isPrivate, isDirect, SessionManager.readString(ProUpgradeActivity.this, SessionManager.FULL_NAME, ""),"NA","NA",true);
                proUpgradeViewModel.insertCard(paramInsertCard,ProUpgradeActivity.this);
            }else {
                ParamInsertCard paramInsertCard = new ParamInsertCard(SessionManager.readString(ProUpgradeActivity.this, SessionManager.parentUserID, ""),false,
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.DOB, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.GENDER, ""),
                        Integer.parseInt(SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, "")),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                        Plan,qrCode, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COUNTRY_NAME, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.CITY, ""),
                        isPrivate, isDirect, SessionManager.readString(ProUpgradeActivity.this, SessionManager.FULL_NAME, ""),"","",true);
                proUpgradeViewModel.insertCard(paramInsertCard,ProUpgradeActivity.this);
            }
        }else {
            if (SessionManager.readString(ProUpgradeActivity.this, SessionManager.REGISTER_AS, "").equals("Business")) {
                ParamFullRegister paramFullRegister = new ParamFullRegister(SessionManager.readString(ProUpgradeActivity.this, SessionManager.BR_basicRegistratinUID, ""),
                        true, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                        "", "", SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.CITY, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COUNTRY_NAME, ""),
                        Plan);
                proUpgradeViewModel.fullRegistration(paramFullRegister,ProUpgradeActivity.this);
            } else {
                ParamFullRegister paramFullRegister = new ParamFullRegister(SessionManager.readString(ProUpgradeActivity.this, SessionManager.BR_basicRegistratinUID, ""),
                        false, SessionManager.readString(ProUpgradeActivity.this, SessionManager.COMPANY_NAME, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.DOB, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.GENDER, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.SELECTED_BUSINESSES_IDS, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.LOGO_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COVER_FILE_URL, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.CITY, ""),
                        SessionManager.readString(ProUpgradeActivity.this, SessionManager.COUNTRY_NAME, ""),
                        Plan);
                proUpgradeViewModel.fullRegistration(paramFullRegister,ProUpgradeActivity.this);
            }
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s + "\n" + i, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}