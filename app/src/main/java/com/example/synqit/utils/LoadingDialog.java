package com.example.synqit.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.LinearLayout;

import com.example.synqit.R;

public class LoadingDialog {

    static ProgressDialog progressDialog;
    private final Activity activity;
    private Dialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
        }
    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();

    }

    @SuppressLint("LongLogTag")
    public void startLoadingDialog() {
        if (!(dialog != null && dialog.isShowing())) {
            if (activity != null && !activity.isFinishing()) {
                dialog = new Dialog(activity);
                dialog.setContentView(R.layout.loading_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                },100);

            }
        }
    }

    @SuppressLint("LongLogTag")
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            if (activity != null && !activity.isFinishing()) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },100);

            }
        }
    }

}

