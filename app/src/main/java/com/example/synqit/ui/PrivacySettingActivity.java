package com.example.synqit.ui;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.synqit.R;
import com.example.synqit.retrofit.RetrofitClient;
import com.example.synqit.ui.blockedconnections.BlockedConnectionActivity;
import com.example.synqit.ui.resetpass.model.ParamResetPass;
import com.example.synqit.ui.resetpass.model.ResetPassResponse;
import com.example.synqit.utils.LoadingDialog;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacySettingActivity extends AppCompatActivity {

    private MaterialButton btnSave;
    private TextInputEditText etPassword;
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting);

        btnSave = findViewById(R.id.btnSave);
        etPassword = findViewById(R.id.etPassword);
        if (getSelectedCard(PrivacySettingActivity.this) != null){

        }

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    btnSave.setVisibility(View.GONE);
                }else {
                    btnSave.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = etPassword.getText().toString();
                if (/*!CommonUtils.isValidPassword(password)*/etPassword.getText().toString().length() == 0){
                    etPassword.setError(getString(R.string.password_msg));
                    etPassword.requestFocus();
                }else {
                    if (getSelectedCard(PrivacySettingActivity.this) != null) {
                        ParamResetPass paramResetPass = new ParamResetPass(getSelectedCard(PrivacySettingActivity.this).getParentUserID(), password);
                        LoadingDialog loadingDialog = new LoadingDialog(PrivacySettingActivity.this);
                        loadingDialog.startLoadingDialog();
                        Call<ResetPassResponse> call = RetrofitClient.getInstance().getApi().getResetPassword(paramResetPass);
                        call.enqueue(new Callback<ResetPassResponse>() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                                Log.e("getResetPassword?onResponse", response.toString());
                                loadingDialog.dismissDialog();
                                if(response.isSuccessful()) {
                                    if (response.body().isSuccess()) {
                                        etPassword.setText(password);
                                        Toast.makeText(PrivacySettingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onFailure(Call<ResetPassResponse> call, Throwable t) {
                                loadingDialog.dismissDialog();
                                Log.e("getResetPassword?onFailure", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

        findViewById(R.id.rBlocked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacySettingActivity.this, BlockedConnectionActivity.class));
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.rTermCondition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://synqit.co/policies/terms-of-service"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.rPrivacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://synqit.co/pages/privacy-policy"));
                startActivity(browserIntent);
            }
        });
    }
}