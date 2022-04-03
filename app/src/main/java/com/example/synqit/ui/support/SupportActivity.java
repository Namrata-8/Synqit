package com.example.synqit.ui.support;

import static android.view.View.GONE;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.adapters.FAQAdapter;
import com.example.synqit.adapters.TicketsAdapter;
import com.example.synqit.databinding.ActivitySupportBinding;
import com.example.synqit.ui.account.AccountActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.support.model.CreateTicketResponse;
import com.example.synqit.ui.support.model.ParamCreateTicket;
import com.example.synqit.ui.support.model.ParamSupportList;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.livechatinc.inappchat.ChatWindowActivity;
import com.livechatinc.inappchat.ChatWindowConfiguration;
import com.livechatinc.inappchat.ChatWindowErrorType;
import com.livechatinc.inappchat.ChatWindowEventsListener;
import com.livechatinc.inappchat.ChatWindowView;
import com.livechatinc.inappchat.models.NewMessageModel;

import java.util.ArrayList;

public class SupportActivity extends AppCompatActivity implements SupportNavigator, ChatWindowEventsListener {

    private ActivitySupportBinding activitySupportBinding;
    private SupportViewModel supportViewModel;
    private ChatWindowView chatWindow;
    private int badgeCounter;
    String licenceNumber = "1520";
    ChatWindowConfiguration windowConfig = new ChatWindowConfiguration.Builder()
            .setLicenceNumber(licenceNumber)
            .build();
    ActivityResultLauncher<Intent> editConfigActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activitySupportBinding = DataBindingUtil.setContentView(this, R.layout.activity_support);
        activitySupportBinding.setViewModel(new SupportViewModel(this));
        activitySupportBinding.executePendingBindings();
        supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);

        /*chatWindow = ChatWindowUtils.createAndAttachChatWindowInstance(SupportActivity.this);
        chatWindow.setConfiguration((ChatWindowConfiguration) getIntent().getSerializableExtra("config"));
        chatWindow.setEventsListener(this);
        chatWindow.initialize();*/
        editConfigActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.i("TAG", "coming back from activity" + result.getData());
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            windowConfig = (ChatWindowConfiguration) data.getSerializableExtra("config");
                        }
                    }
                });

        if (getSelectedCard(SupportActivity.this) != null) {
            ParamSupportList paramSupportList = new ParamSupportList(getSelectedCard(SupportActivity.this).getParentUserID());
            supportViewModel.getSupportList(paramSupportList,SupportActivity.this).observe(this, new Observer<ArrayList<SupportViewModel>>() {
                @Override
                public void onChanged(ArrayList<SupportViewModel> supportViewModels) {
                    if (!supportViewModels.isEmpty()){

                        activitySupportBinding.rvTickets.setAdapter(new TicketsAdapter(SupportActivity.this, supportViewModels));
                    }
                }
            });

            supportViewModel.getCreateTicketResponse().observe(this, new Observer<CreateTicketResponse>() {
                @Override
                public void onChanged(CreateTicketResponse createTicketResponse) {
                    if (createTicketResponse != null){
                        if (createTicketResponse.isSuccess()){
                            SupportActivity.this.recreate();
                        }
                    }
                }
            });
        }

        supportViewModel.getFaqsList(SupportActivity.this).observe(this, new Observer<ArrayList<SupportViewModel>>() {
            @Override
            public void onChanged(ArrayList<SupportViewModel> supportViewModels) {
                if (!supportViewModels.isEmpty()){
                    activitySupportBinding.rvFAQ.setAdapter(new FAQAdapter(SupportActivity.this, supportViewModels));
                }
            }
        });
        activitySupportBinding.ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChatActivity();
            }
        });
    }

    private void startChatActivity() {
        Intent intent = new Intent(this, ChatWindowActivity.class);
        intent.putExtras(windowConfig.asBundle());
        startActivity(intent);
    }

    private void showChatWindow() {
        chatWindow.showChatWindow();
    }

    @Override
    public void onAddTicketClick() {
        final Dialog dialog = new Dialog(SupportActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_add_ticket);
        EditText etTitle = dialog.findViewById(R.id.etTitle);
        EditText etDesc = dialog.findViewById(R.id.etDesc);
        MaterialButton btnSaveLink = dialog.findViewById(R.id.btnSaveLink);

        btnSaveLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitle.getText().toString().length() == 0){
                    etTitle.setError(getString(R.string.error_title));
                    etTitle.requestFocus();
                }else if (etDesc.getText().toString().length() == 0){
                    etDesc.setError(getString(R.string.error_desc));
                    etDesc.requestFocus();
                }else {
                    if (getSelectedCard(SupportActivity.this) != null) {
                        ParamCreateTicket paramCreateTicket = new ParamCreateTicket(getSelectedCard(SupportActivity.this).getParentUserID(), etTitle.getText().toString(), etDesc.getText().toString());
                        supportViewModel.onCreateNewTicket(paramCreateTicket,SupportActivity.this);
                    }
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBackClick() {
        startActivity(new Intent(SupportActivity.this, DashboardActivity.class).putExtra("ISFromConnection", false).putExtra("NfcData", "").putExtra("IsFromSettings", true));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackClick();
    }

    @Override
    public void onWindowInitialized() {

    }

    @Override
    public void onChatWindowVisibilityChanged(boolean visible) {
        if (visible) {
            discardBadge();
        } else {

        }
    }

    private void discardBadge() {
        badgeCounter = 0;
        activitySupportBinding.chatBadge.setVisibility(GONE);
        activitySupportBinding.chatBadge.setText("");
    }

    @Override
    public void onNewMessage(NewMessageModel message, boolean windowVisible) {
        if (!windowVisible) {
            badgeCounter++;
            activitySupportBinding.chatBadge.setVisibility(View.VISIBLE);
            activitySupportBinding.chatBadge.setText(String.valueOf(badgeCounter));
        }
    }

    @Override
    public void onStartFilePickerActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestAudioPermissions(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public boolean onError(ChatWindowErrorType errorType, int errorCode, String errorDescription) {
        Toast.makeText(SupportActivity.this, errorDescription, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean handleUri(Uri uri) {
        return false;
    }
}