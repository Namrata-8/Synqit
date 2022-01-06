package com.example.synqit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.synqit.R;
import com.example.synqit.UI.RegisterAsActivity;
import com.google.android.material.button.MaterialButton;

public class BusinessFragment2 extends Fragment {

    private MaterialButton btnContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business2, container, false);
        RegisterAsActivity registerAsActivity = (RegisterAsActivity) getActivity();
        btnContinue = view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerAsActivity != null) {
                    registerAsActivity.setPagerFragment(2);
                }
            }
        });
        return view;
    }
}