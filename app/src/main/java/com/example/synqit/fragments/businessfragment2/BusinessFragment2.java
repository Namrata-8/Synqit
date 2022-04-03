package com.example.synqit.fragments.businessfragment2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.adapters.BusinessAdapter;
import com.example.synqit.databinding.FragmentBusiness2Binding;
import com.example.synqit.ui.RegisterAsActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BusinessFragment2 extends Fragment implements BusinessFragment2Navigator{

    private BusinessAdapter businessAdapter;

    private FragmentBusiness2Binding fragmentBusiness2Binding;
    private BusinessFragment2ViewModel fragment2ViewModel;
    private RegisterAsActivity registerAsActivity;

    //private ArrayList<String> selectedBusinessID = new ArrayList<>();
    private String selectedBusinessID = "0";
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBusiness2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_business2, container, false);
        fragmentBusiness2Binding.setViewModel(new BusinessFragment2ViewModel(this));
        fragmentBusiness2Binding.executePendingBindings();

        fragment2ViewModel = new ViewModelProvider(this).get(BusinessFragment2ViewModel.class);

        registerAsActivity = (RegisterAsActivity) getActivity();
        fragmentBusiness2Binding.rvBusiness.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        fragmentBusiness2Binding.rvBusiness.setLayoutManager(gridLayoutManager);

        fragment2ViewModel.getBusinessesData(getActivity()).observe(getViewLifecycleOwner(), new Observer<ArrayList<BusinessFragment2ViewModel>>() {
            @Override
            public void onChanged(ArrayList<BusinessFragment2ViewModel> businessFragment2ViewModels) {
                businessAdapter = new BusinessAdapter(getActivity(),businessFragment2ViewModels);
                fragmentBusiness2Binding.rvBusiness.setAdapter(businessAdapter);

                businessAdapter.setOnItemClickListener(new BusinessAdapter.OnItemClickListener() {
                    @Override
                    public void onItemAddClick(int position) {
                        selectedBusinessID = businessFragment2ViewModels.get(position).id;
                    }

                });
            }
        });

        return fragmentBusiness2Binding.getRoot();
    }

    @Override
    public void onContinueClick() {
        if (registerAsActivity != null) {
            if (selectedBusinessID.equals("")){
                Toast.makeText(getActivity(), "Please Select business", Toast.LENGTH_SHORT).show();
            }else {
                //String selectedBusiness = gson.toJson(selectedBusinessID);
                SessionManager.writeString(getActivity(), SessionManager.SELECTED_BUSINESSES_IDS, selectedBusinessID);
                registerAsActivity.setPagerFragment(2);
            }
        }
    }
}