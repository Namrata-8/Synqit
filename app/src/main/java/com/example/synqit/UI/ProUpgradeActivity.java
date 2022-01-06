package com.example.synqit.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.synqit.R;

public class ProUpgradeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private CardView cardMonthly, cardYearly;
    private ImageView ivCheckIconMonthly, ivCheckIconYearly;
    private TextView tvDiscountMonthly, tvDiscountYearly;
    private RelativeLayout rlCardMonthly, rlCardYearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_upgrade);

        btnBack = findViewById(R.id.btnBack);
        cardMonthly = findViewById(R.id.cardMonthly);
        cardYearly = findViewById(R.id.cardYearly);
        ivCheckIconMonthly = findViewById(R.id.ivCheckIconMonthly);
        ivCheckIconYearly = findViewById(R.id.ivCheckIconYearly);
        tvDiscountMonthly = findViewById(R.id.tvDiscountMonthly);
        tvDiscountYearly = findViewById(R.id.tvDiscountYearly);
        rlCardMonthly = findViewById(R.id.rlCardMonthly);
        rlCardYearly = findViewById(R.id.rlCardYearly);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCardMonthly.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
                ivCheckIconMonthly.setVisibility(View.VISIBLE);
                tvDiscountMonthly.setBackgroundResource(R.drawable.bg_discount_selected);


                rlCardYearly.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
                ivCheckIconYearly.setVisibility(View.GONE);
                tvDiscountYearly.setBackgroundResource(R.drawable.bg_discount_unselected);
            }
        });

        cardYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCardYearly.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
                ivCheckIconYearly.setVisibility(View.VISIBLE);
                tvDiscountYearly.setBackgroundResource(R.drawable.bg_discount_selected);


                rlCardMonthly.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
                ivCheckIconMonthly.setVisibility(View.GONE);
                tvDiscountMonthly.setBackgroundResource(R.drawable.bg_discount_unselected);
            }
        });
    }
}