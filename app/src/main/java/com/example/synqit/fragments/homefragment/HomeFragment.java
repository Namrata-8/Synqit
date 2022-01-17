package com.example.synqit.fragments.homefragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.synqit.R;
import com.example.synqit.adapters.YourLinkAdapter;
import com.example.synqit.ui.addlink.AddLinkActivity;

public class HomeFragment extends Fragment {

    private RecyclerView rvYourLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvYourLink = view.findViewById(R.id.rvYourLink);
        rvYourLink.setAdapter(new YourLinkAdapter(getActivity()));

        view.findViewById(R.id.textViewSemiBold3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddLinkActivity.class));
            }
        });

        return view;
    }
}