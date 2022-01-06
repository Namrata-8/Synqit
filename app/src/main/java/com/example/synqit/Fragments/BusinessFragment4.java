package com.example.synqit.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.synqit.R;
import com.example.synqit.customeviews.TextViewSemiBold;

public class BusinessFragment4 extends Fragment {

    private TextViewSemiBold tvSkipNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business4, container, false);

        tvSkipNow = view.findViewById(R.id.tvSkipNow);


        return view;
    }
}