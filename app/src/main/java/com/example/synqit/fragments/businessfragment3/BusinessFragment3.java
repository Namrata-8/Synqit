package com.example.synqit.fragments.businessfragment3;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.adapters.CountryAdapter;
import com.example.synqit.R;
import com.example.synqit.databinding.FragmentBusiness3Binding;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2Navigator;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2ViewModel;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessFragment3 extends Fragment implements BusinessFragment3Navigator {

    private FragmentBusiness3Binding fragmentBusiness3Binding;
    private BusinessFragment3ViewModel fragment3ViewModel;
    private RegisterAsActivity registerAsActivity;
    private String companyName = "";
    private String fullName = "";
    private String countryName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBusiness3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_business3, container, false);
        fragmentBusiness3Binding.setViewModel(new BusinessFragment3ViewModel(this));
        fragmentBusiness3Binding.executePendingBindings();

        fragment3ViewModel = new ViewModelProvider(this).get(BusinessFragment3ViewModel.class);

        registerAsActivity = (RegisterAsActivity) getActivity();

        return fragmentBusiness3Binding.getRoot();
    }

    private void openCountryBottomSheet(BusinessFragment3ViewModel fragment3ViewModel) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_country);

        EditText etSearch = dialog.findViewById(R.id.etSearch);
        RecyclerView rvCountry = dialog.findViewById(R.id.rvCountry);
        rvCountry.setHasFixedSize(true);
        rvCountry.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        List<String> countryList = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.countries)));

        fragment3ViewModel.getCountryList(countryList).observe(getViewLifecycleOwner(), new Observer<ArrayList<BusinessFragment3ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment3ViewModel> businessFragment3ViewModels) {
                CountryAdapter countryAdapter = new CountryAdapter(getActivity(), businessFragment3ViewModels);
                rvCountry.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        fragmentBusiness3Binding.etCountry.setText(businessFragment3ViewModels.get(position).countryName);
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

    @Override
    public void goContinue() {
        if (registerAsActivity != null) {

            companyName = fragmentBusiness3Binding.etCompName.getText().toString();
            fullName = fragmentBusiness3Binding.etFullName.getText().toString();
            countryName = fragmentBusiness3Binding.etCountry.getText().toString();

            if (fragmentBusiness3Binding.etCompName.getText().toString().length() == 0){
                fragmentBusiness3Binding.etCompName.setError(getString(R.string.error_comp_name));
                fragmentBusiness3Binding.etCompName.requestFocus();
            }else if (fragmentBusiness3Binding.etFullName.getText().toString().length() == 0){
                fragmentBusiness3Binding.etFullName.setError(getString(R.string.error_full_name));
                fragmentBusiness3Binding.etFullName.requestFocus();
            }else if (fragmentBusiness3Binding.etCountry.getText().toString().length() == 0){
                Toast.makeText(getActivity(), "Please Select Country", Toast.LENGTH_SHORT).show();
            }else {
                SessionManager.writeString(getActivity(), SessionManager.COMPANY_NAME, companyName);
                SessionManager.writeString(getActivity(), SessionManager.FULL_NAME, fullName);
                SessionManager.writeString(getActivity(), SessionManager.COUNTRY_NAME, countryName);
                Gson gson = new Gson();
                String[] selectedBusiness = gson.fromJson(SessionManager.readString(getActivity(), SessionManager.SELECTED_BUSINESSES_IDS, null), String[].class);

                if (null != selectedBusiness) {
                    Log.e("SelectedBusiness", selectedBusiness.length + "\n" + selectedBusiness[0]);
                    registerAsActivity.setPagerFragment(3);
                } else {
                    Toast.makeText(getActivity(), "Please select business", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCountryClick() {
        openCountryBottomSheet(fragment3ViewModel);
    }
}