package com.example.synqit.ui.activateproduct;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.adapters.ActivateProductAdapter;
import com.example.synqit.customeviews.TextViewSemiBold;
import com.example.synqit.databinding.ActivityActivateProductBinding;
import com.example.synqit.nfc_utils.NFCUtil;
import com.example.synqit.utils.SessionManager;

import java.util.ArrayList;

public class ActivateProductActivity extends AppCompatActivity {

    private ActivityActivateProductBinding activityActivateProductBinding;
    private ActivateProductViewModel activateProductViewModel;
    private ActivateProductAdapter activateProductAdapter;
    private NfcAdapter mNfcAdapter;
    private NFCUtil nfcUtil;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityActivateProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_activate_product);
        activityActivateProductBinding.setViewModel(new ActivateProductViewModel());
        activityActivateProductBinding.executePendingBindings();
        activateProductViewModel = new ViewModelProvider(this).get(ActivateProductViewModel.class);
        nfcUtil = new NFCUtil();

        setNfcWriter();

        activateProductViewModel.getActivateProducts(ActivateProductActivity.this).observe(this, new Observer<ArrayList<ActivateProductViewModel>>() {
            @Override
            public void onChanged(ArrayList<ActivateProductViewModel> activateProductViewModels) {
                if (!activateProductViewModels.isEmpty()){
                    activateProductAdapter = new ActivateProductAdapter(ActivateProductActivity.this, activateProductViewModels);
                    activityActivateProductBinding.rvActivateProduct.setAdapter(activateProductAdapter);
                    activateProductAdapter.setOnItemClickListener(new ActivateProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            dialog = new Dialog(ActivateProductActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.bottom_sheet_activate_product);

                            startNfcWrinting();

                            TextViewSemiBold btnCancel = dialog.findViewById(R.id.btnCancel);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                            dialog.show();
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                        }
                    });

                }
            }
        });

        activityActivateProductBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setNfcWriter() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(ActivateProductActivity.this);
        /*startNfcWrinting();*/
    }

    private void startNfcWrinting() {
        if (mNfcAdapter != null){
            nfcUtil.enableNFCInForeground(mNfcAdapter, ActivateProductActivity.this, getClass());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean messageWrittenSuccessfully = nfcUtil.createNFCMessage("http://app.synqit.in/Test_nfc", intent);
        if (messageWrittenSuccessfully) {
            Toast.makeText(this, "Tag Activated successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else {
            Toast.makeText(this, "Error in Tag Activation", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null){
            nfcUtil.disableNFCInForeground(mNfcAdapter, this);
        }
    }
}