package com.example.synqit.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.synqit.R;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

public class BusinessFragment1 extends Fragment {

    private MaterialButton btnContinue;
    private ImageView ivCheckIconIndividual, ivCheckIconBusiness;
    private CardView cardIndividual, cardBusiness;
    private RelativeLayout rlCardIndividual, rlCardBusiness;
    private String UserType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business1, container, false);
        RegisterAsActivity registerAsActivity = (RegisterAsActivity)getActivity();

        btnContinue = view.findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        ivCheckIconIndividual = view.findViewById(R.id.ivCheckIconIndividual);
        ivCheckIconBusiness = view.findViewById(R.id.ivCheckIconBusiness);
        cardIndividual = view.findViewById(R.id.cardIndividual);
        cardBusiness = view.findViewById(R.id.cardBusiness);
        rlCardIndividual = view.findViewById(R.id.rlCardIndividual);
        rlCardBusiness = view.findViewById(R.id.rlCardBusiness);

        cardIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCardIndividual.setBackground(getResources().getDrawable(R.drawable.bg_corner_selected_card));
                ivCheckIconIndividual.setVisibility(View.VISIBLE);

                btnContinue.setEnabled(true);
                UserType = "Individual";
                if (registerAsActivity != null) {
                    registerAsActivity.setProgressIndicator(33, "1/3");
                    registerAsActivity.setUserType(UserType);
                }

                rlCardBusiness.setBackgroundResource(R.drawable.bg_corner_unselected_card);
                ivCheckIconBusiness.setVisibility(View.GONE);
            }
        });

        cardBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCardBusiness.setBackground(getResources().getDrawable(R.drawable.bg_corner_selected_card));
                ivCheckIconBusiness.setVisibility(View.VISIBLE);

                btnContinue.setEnabled(true);
                UserType = "Business";
                if (registerAsActivity != null) {
                    registerAsActivity.setProgressIndicator(25, "1/4");
                    registerAsActivity.setUserType(UserType);
                }

                rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_unselected_card);
                ivCheckIconIndividual.setVisibility(View.GONE);

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerAsActivity != null) {
                    if (UserType.equalsIgnoreCase("Business")) {
                        Log.e("UserId", SessionManager.readString(getActivity(), SessionManager.BASIC_REGISTER_ID,"*"));
                        SessionManager.writeString(getActivity(), SessionManager.REGISTER_AS, UserType);
                        registerAsActivity.setPagerFragment(1);
                    }else {
                        SessionManager.writeString(getActivity(), SessionManager.REGISTER_AS, UserType);
                        registerAsActivity.setPagerFragment(4);
                    }
                }
            }
        });

        return view;
    }
}