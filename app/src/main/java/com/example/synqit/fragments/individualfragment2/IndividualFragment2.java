package com.example.synqit.fragments.individualfragment2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.synqit.adapters.CountryAdapter;
import com.example.synqit.R;
import com.example.synqit.databinding.FragmentIndividual2Binding;
import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class IndividualFragment2 extends Fragment implements Individual2Navigator{

    private FragmentIndividual2Binding fragmentIndividual2Binding;
    private Individual2ViewModel individual2ViewModel;
    private RegisterAsActivity registerAsActivity;
    private CountryAdapter countryAdapter;
    private String companyName = "";
    private String fullName = "";
    private String countryName = "";
    private String gender = "";
    private String date = "";
    private String city = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentIndividual2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individual2, container, false);
        fragmentIndividual2Binding.setViewModel(new Individual2ViewModel(this));
        fragmentIndividual2Binding.executePendingBindings();

        individual2ViewModel = new ViewModelProvider(this).get(Individual2ViewModel.class);

        registerAsActivity = (RegisterAsActivity) getActivity();

        return fragmentIndividual2Binding.getRoot();
    }

    @Override
    public void goContinue() {
        if (registerAsActivity != null) {
            companyName = fragmentIndividual2Binding.etCompName.getText().toString();
            fullName = fragmentIndividual2Binding.etFullName.getText().toString();
            date = fragmentIndividual2Binding.etDob.getText().toString();
            city = fragmentIndividual2Binding.etCity.getText().toString();
            countryName = fragmentIndividual2Binding.etCountry.getText().toString();
            gender = fragmentIndividual2Binding.etGender.getText().toString();

            if (fragmentIndividual2Binding.etCompName.getText().toString().length() == 0){
                fragmentIndividual2Binding.etCompName.setError(getString(R.string.error_comp_name));
                fragmentIndividual2Binding.etCompName.requestFocus();
            }else if (fragmentIndividual2Binding.etFullName.getText().toString().length() == 0){
                fragmentIndividual2Binding.etFullName.setError(getString(R.string.error_full_name));
                fragmentIndividual2Binding.etFullName.requestFocus();
            }else if (fragmentIndividual2Binding.etDob.getText().toString().length() == 0){
                Toast.makeText(getActivity(), "Please Select Dob", Toast.LENGTH_SHORT).show();
            }else if (fragmentIndividual2Binding.etCity.getText().toString().length() == 0){
                fragmentIndividual2Binding.etCity.setError(getString(R.string.error_city_name));
                fragmentIndividual2Binding.etCity.requestFocus();
            }else if (fragmentIndividual2Binding.etCountry.getText().toString().length() == 0){
                Toast.makeText(getActivity(), "Please Select Country", Toast.LENGTH_SHORT).show();
            }else if (fragmentIndividual2Binding.etGender.getText().toString().length() == 0){
                Toast.makeText(getActivity(), "Please Select gender", Toast.LENGTH_SHORT).show();
            }else {
                SessionManager.writeString(getActivity(), SessionManager.COMPANY_NAME, companyName);
                SessionManager.writeString(getActivity(), SessionManager.FULL_NAME, fullName);
                SessionManager.writeString(getActivity(), SessionManager.DOB, date);
                SessionManager.writeString(getActivity(), SessionManager.CITY, city);
                SessionManager.writeString(getActivity(), SessionManager.COUNTRY_NAME, countryName);
                SessionManager.writeString(getActivity(), SessionManager.GENDER, gender);

                registerAsActivity.setPagerFragment(5);
            }
        }
    }

    @Override
    public void onCountryClick() {
        openCountryBottomSheet(individual2ViewModel);
    }

    @Override
    public void onGenderClick() {
        openGenderBottomSheet(individual2ViewModel);
    }

    @Override
    public void onDateClick() {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
            fragmentIndividual2Binding.etDob.setText( String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/"  + String.format("%d", year));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        dpd.show();
    }

    private void openGenderBottomSheet(Individual2ViewModel individual2ViewModel) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_gender);

        RecyclerView rvGender = dialog.findViewById(R.id.rvGender);
        rvGender.setHasFixedSize(true);
        rvGender.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        String [] strings = new String [] {"Male", "Female", "Non-binary", "Prefer not to say" };
        List<String> countryList = new ArrayList<String>(Arrays.asList(strings));

        individual2ViewModel.getCountryList(countryList).observe(getViewLifecycleOwner(), new Observer<ArrayList<BusinessFragment3ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment3ViewModel> businessFragment3ViewModels) {
                CountryAdapter countryAdapter = new CountryAdapter(getActivity(), businessFragment3ViewModels);
                rvGender.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        fragmentIndividual2Binding.etGender.setText(businessFragment3ViewModels.get(position).countryName);
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

    private void openCountryBottomSheet(Individual2ViewModel individual2ViewModel) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_country);

        EditText etSearch = dialog.findViewById(R.id.etSearch);
        RecyclerView rvCountry = dialog.findViewById(R.id.rvCountry);
        rvCountry.setHasFixedSize(true);
        rvCountry.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        List<String> countryList = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.countries)));

        individual2ViewModel.getCountryList(countryList).observe(getViewLifecycleOwner(), new Observer<ArrayList<BusinessFragment3ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment3ViewModel> businessFragment3ViewModels) {
                countryAdapter = new CountryAdapter(getActivity(), businessFragment3ViewModels);
                rvCountry.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        fragmentIndividual2Binding.etCountry.setText(businessFragment3ViewModels.get(position).countryName);
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