package com.example.synqit.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.synqit.Adapter.CountryAdapter;
import com.example.synqit.R;
import com.example.synqit.UI.RegisterAsActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class IndividualFragment2 extends Fragment {

    private MaterialButton btnContinue;
    private EditText etDob;
    private EditText etCountry, etGender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual2, container, false);
        RegisterAsActivity registerAsActivity = (RegisterAsActivity) getActivity();
        btnContinue = view.findViewById(R.id.btnContinue);
        etCountry = view.findViewById(R.id.etCountry);
        etDob = view.findViewById(R.id.etDob);
        etGender = view.findViewById(R.id.etGender);
        Calendar cal = Calendar.getInstance();

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {

                    etDob.setText( String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month + 1) + "-"  + String.format("%d", year));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerAsActivity != null) {
                    registerAsActivity.setPagerFragment(5);
                }
            }
        });

        etCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCountryBottomSheet();
            }
        });

        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGenderBottomSheet();
            }
        });

        return view;
    }

    private void openGenderBottomSheet() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_gender);

        RecyclerView rvGender = dialog.findViewById(R.id.rvGender);
        rvGender.setHasFixedSize(true);
        rvGender.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        String [] strings = new String [] {"Male", "Female", "Non-binary", "Prefer not to say" };
        List<String> countryList = new ArrayList<String>(Arrays.asList(strings));
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(), countryList);
        rvGender.setAdapter(countryAdapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void openCountryBottomSheet() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_country);

        EditText etSearch = dialog.findViewById(R.id.etSearch);
        RecyclerView rvCountry = dialog.findViewById(R.id.rvCountry);

        rvCountry.setHasFixedSize(true);
        rvCountry.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        List<String> countryList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.countries)));
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(), countryList);
        rvCountry.setAdapter(countryAdapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}