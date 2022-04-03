package com.example.synqit.ui.account;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.adapters.CountryAdapter;
import com.example.synqit.databinding.ActivityAccountBinding;
import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.ui.addlink.AddLinkActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements AccountNavigator {

    private ActivityAccountBinding activityAccountBinding;
    private AccountViewModel accountViewModel;
    private CountryAdapter countryAdapter;
    private ArrayList<DashboardViewModel> arrayList;
    private List<CardData> cardDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        activityAccountBinding.setViewModel(new AccountViewModel(this));
        activityAccountBinding.executePendingBindings();
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        if (getSelectedCard(AccountActivity.this) != null) {
            CardData cardData = getSelectedCard(AccountActivity.this);

            if (cardData.isBusiness()) {
                activityAccountBinding.rlGender.setVisibility(View.GONE);
                activityAccountBinding.rlDob.setVisibility(View.GONE);
                activityAccountBinding.rlUserName.setVisibility(View.GONE);
                activityAccountBinding.rlcompany.setVisibility(View.VISIBLE);
            } else {
                activityAccountBinding.rlGender.setVisibility(View.VISIBLE);
                activityAccountBinding.rlDob.setVisibility(View.VISIBLE);
                activityAccountBinding.rlUserName.setVisibility(View.VISIBLE);
                activityAccountBinding.rlcompany.setVisibility(View.GONE);
            }

            activityAccountBinding.etEmail.setEnabled(cardData.getEmail().length() == 0);
            activityAccountBinding.etMobile.setEnabled(cardData.getMobileNumber().length() == 0);
            activityAccountBinding.countryCodePicker.setEnabled(cardData.getMobileNumber().length() == 0);
            activityAccountBinding.countryCodePicker.setFocusable(cardData.getMobileNumber().length() == 0);
            activityAccountBinding.countryCodePicker.setClickable(cardData.getMobileNumber().length() == 0);

            String numberStr = cardData.getMobileNumber();
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(numberStr, "");
                //This prints "Country code: 91"
                Log.e("Country_Code", "***"+"\n" + numberProto.getCountryCode());

                activityAccountBinding.countryCodePicker.setCountryForPhoneCode(numberProto.getCountryCode());
                activityAccountBinding.etMobile.setText(numberProto.getNationalNumber()+"");
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }

            activityAccountBinding.etName.setText(cardData.getDisplayName());
            activityAccountBinding.etUserName.setText(cardData.getProfileName());
            activityAccountBinding.etCompanyName.setText(cardData.getDisplayName());
            activityAccountBinding.etGender.setText(cardData.getGender());
            activityAccountBinding.etDob.setText(cardData.getDob());
            activityAccountBinding.etCity.setText(cardData.getCity());
            activityAccountBinding.etCountry.setText(cardData.getCountry());
            activityAccountBinding.etEmail.setText(cardData.getEmail());
        }
        /*if (SessionManager.readFullRegisterData(AccountActivity.this, SessionManager.FR_FullRegisterData, "") != null) {
            if (SessionManager.readFullRegisterData(AccountActivity.this, SessionManager.FR_FullRegisterData, "").getEmail().length() != 0) {
                activityAccountBinding.etEmail.setText(SessionManager.readFullRegisterData(AccountActivity.this, SessionManager.FR_FullRegisterData, "").getEmail());
                activityAccountBinding.etEmail.setEnabled(false);
            } else {
                activityAccountBinding.etEmail.setEnabled(true);
            }
            if (SessionManager.readFullRegisterData(AccountActivity.this, SessionManager.FR_FullRegisterData, "").getMobileNumber().length() != 0) {
                activityAccountBinding.etMobile.setText(SessionManager.readFullRegisterData(AccountActivity.this, SessionManager.FR_FullRegisterData, "").getMobileNumber());
                activityAccountBinding.etMobile.setEnabled(false);
            } else {
                activityAccountBinding.etMobile.setEnabled(true);
            }

        } else if (SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "") != null) {
            if (SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "").getEmail().length() != 0) {
                activityAccountBinding.etEmail.setText(SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "").getEmail());
                activityAccountBinding.etEmail.setEnabled(false);
            } else {
                activityAccountBinding.etEmail.setEnabled(true);
            }
            if (SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "").getMobileNumber().length() != 0) {
                String _mobileNumber = SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "").getMobileNumber();
                String __mobileNumber = SessionManager.readLoginData(AccountActivity.this, SessionManager.L_LoginData, "").getMobileNumber();
                _mobileNumber = _mobileNumber.substring(_mobileNumber.length() - 10);
                String _countrycode = __mobileNumber.replace(_mobileNumber, "");
                _countrycode = _countrycode.replace("+", "");
                activityAccountBinding.countryCodePicker.setCountryForPhoneCode(Integer.parseInt(_countrycode));
                activityAccountBinding.etMobile.setText(_mobileNumber);
                activityAccountBinding.etMobile.setEnabled(false);
                activityAccountBinding.countryCodePicker.setEnabled(false);
                activityAccountBinding.countryCodePicker.setFocusable(false);
                activityAccountBinding.countryCodePicker.setClickable(false);
            } else {
                activityAccountBinding.etMobile.setEnabled(true);
                activityAccountBinding.countryCodePicker.setEnabled(true);
            }
        }*/

        accountViewModel.onUpdateCard().observe(this, new Observer<InsertCardResponse>() {
            @Override
            public void onChanged(InsertCardResponse insertCardResponse) {
                arrayList=new ArrayList<>();
                if (insertCardResponse != null){
                    Toast.makeText(AccountActivity.this, insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if (insertCardResponse.isSuccess()){
                        cardDataList = new ArrayList<>();
                        cardDataList = insertCardResponse.getData();
                        for (int i = 0; i < cardDataList.size(); i++){
                            CardData cardData = cardDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(cardData);
                            arrayList.add(dashboardViewModel);
                        }
                        DashboardActivity.cardDrawerAdapter.swap(arrayList);
                        SessionManager.saveSelectedCardData(AccountActivity.this, SessionManager.Selected_Card_Data, new Gson().toJson(insertCardResponse.getData().get(SessionManager.readInt(AccountActivity.this, SessionManager.SELECTED_CARD_POSITION, 0))));
                    }
                }
            }
        });

    }

    @Override
    public void onBackClick() {
        startActivity(new Intent(AccountActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackClick();
    }

    @Override
    public void onGenderClick() {
        openGenderBottomSheet(accountViewModel);
    }

    @Override
    public void onCountryClick() {
        openCountryBottomSheet(accountViewModel);
    }

    @Override
    public void onContinueClick() {
        if (getSelectedCard(AccountActivity.this) != null) {
            if (fieldsValid()) {
                CardData cardData = getSelectedCard(AccountActivity.this);
                ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                        activityAccountBinding.etName.getText().toString(), activityAccountBinding.etDob.getText().toString(), activityAccountBinding.etGender.getText().toString(),
                        cardData.getBusinessType(), cardData.getDisplayPicture(), cardData.getCoverPicture(), cardData.getPlan(), cardData.getQrCode(),
                        activityAccountBinding.etCountry.getText().toString(), activityAccountBinding.etCity.getText().toString(),
                        cardData.isPrivate(), cardData.isDirect(), activityAccountBinding.etName.getText().toString(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(),
                        activityAccountBinding.etEmail.getText().toString(), "+"+activityAccountBinding.countryCodePicker.getSelectedCountryCode()+activityAccountBinding.etMobile.getText().toString());

                accountViewModel.updateCard(paramInsertCard, AccountActivity.this);

            }
        }
    }

    private boolean fieldsValid() {
        if (activityAccountBinding.etName.getText().toString().length() == 0) {
            activityAccountBinding.etName.setError(getString(R.string.error_full_name));
            activityAccountBinding.etName.requestFocus();
            return false;
        } else if (activityAccountBinding.etEmail.getText().toString().length() == 0) {
            activityAccountBinding.etEmail.setError(getString(R.string.error_email));
            activityAccountBinding.etEmail.requestFocus();
            return false;
        } else if (activityAccountBinding.etMobile.getText().toString().length() > 0 && activityAccountBinding.etMobile.getText().toString().length() != 10) {
            activityAccountBinding.etMobile.setError(getString(R.string.error_enter_mobile));
            activityAccountBinding.etMobile.requestFocus();
            return false;
        } else if (activityAccountBinding.etCity.getText().toString().length() == 0) {
            activityAccountBinding.etCity.setError(getString(R.string.error_city_name));
            activityAccountBinding.etCity.requestFocus();
            return false;
        } else {
            if (getSelectedCard(AccountActivity.this).isBusiness()) {
                if (activityAccountBinding.etCompanyName.getText().toString().length() == 0) {
                    activityAccountBinding.etCompanyName.setError(getString(R.string.error_comp_name));
                    activityAccountBinding.etCompanyName.requestFocus();
                    return false;
                } else {
                    return true;
                }
            } else {
                if (activityAccountBinding.etUserName.getText().toString().length() == 0) {
                    activityAccountBinding.etUserName.setError(getString(R.string.error_username));
                    activityAccountBinding.etUserName.requestFocus();
                    return false;
                } else if (activityAccountBinding.etGender.getText().toString().length() == 0) {
                    Toast.makeText(AccountActivity.this, "Please Select gender", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (activityAccountBinding.etDob.getText().toString().length() == 0) {
                    Toast.makeText(AccountActivity.this, "Please Select Dob", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (activityAccountBinding.etCountry.getText().toString().length() == 0) {
                    Toast.makeText(AccountActivity.this, "Please Select Country", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    @Override
    public void onDateClick() {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(AccountActivity.this, (view1, year, month, dayOfMonth) -> {
            activityAccountBinding.etDob.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%d", year));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        dpd.show();
    }

    private void openGenderBottomSheet(AccountViewModel accountViewModel) {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_gender);

        RecyclerView rvGender = dialog.findViewById(R.id.rvGender);
        rvGender.setHasFixedSize(true);
        rvGender.setLayoutManager(new LinearLayoutManager(AccountActivity.this, LinearLayoutManager.VERTICAL, false));
        String[] strings = new String[]{"Male", "Female", "Non-binary", "Prefer not to say"};
        List<String> countryList = new ArrayList<String>(Arrays.asList(strings));

        accountViewModel.getCountryList(countryList).observe(this, new Observer<ArrayList<BusinessFragment3ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment3ViewModel> businessFragment3ViewModels) {
                CountryAdapter countryAdapter = new CountryAdapter(AccountActivity.this, businessFragment3ViewModels);
                rvGender.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        activityAccountBinding.etGender.setText(businessFragment3ViewModels.get(position).countryName);
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void openCountryBottomSheet(AccountViewModel accountViewModel) {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_country);

        EditText etSearch = dialog.findViewById(R.id.etSearch);
        RecyclerView rvCountry = dialog.findViewById(R.id.rvCountry);
        rvCountry.setHasFixedSize(true);
        rvCountry.setLayoutManager(new LinearLayoutManager(AccountActivity.this, LinearLayoutManager.VERTICAL, false));
        List<String> countryList = new ArrayList<String>(Arrays.asList(AccountActivity.this.getResources().getStringArray(R.array.countries)));

        accountViewModel.getCountryList(countryList).observe(this, new Observer<ArrayList<BusinessFragment3ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment3ViewModel> businessFragment3ViewModels) {
                countryAdapter = new CountryAdapter(AccountActivity.this, businessFragment3ViewModels);
                rvCountry.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        activityAccountBinding.etCountry.setText(businessFragment3ViewModels.get(position).countryName);
                        dialog.dismiss();
                    }
                });
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
