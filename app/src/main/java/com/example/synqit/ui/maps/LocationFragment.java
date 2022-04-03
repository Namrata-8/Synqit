package com.example.synqit.ui.maps;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.customeviews.TextViewBold;
import com.example.synqit.customeviews.TextViewRegular;
import com.example.synqit.customeviews.TextViewSemiBold;
import com.example.synqit.fragments.insightfragment.model.UserDataMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocationFragment extends Fragment {
    UserDataMap userData;
    private TextViewBold tvName;
    private TextViewSemiBold tvCountry, tvCity, tvDate;
    private CircleImageView ivImage;
    private TextViewRegular tvProBusiness;
    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(UserDataMap userData) {
        LocationFragment fragment = new LocationFragment();
        fragment.userData = userData;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName= view.findViewById(R.id.tvName);
        tvCountry= view.findViewById(R.id.tvCountry);
        tvCity= view.findViewById(R.id.tvCity);
        tvDate= view.findViewById(R.id.tvDate);
        ivImage= view.findViewById(R.id.ivImage);
        tvProBusiness= view.findViewById(R.id.tvProBusiness);

        tvName.setText(userData.getDisplayName());
        tvCity.setText(userData.getCity());
        tvCountry.setText(userData.getCountry());
        tvDate.setText(userData.getConnectionDate());

        if (userData.getPlan() > 0){
            tvProBusiness.setVisibility(View.VISIBLE);
        }else {
            tvProBusiness.setVisibility(View.GONE);
        }
        RequestOptions requestOptions2 = new RequestOptions();
        requestOptions2.placeholder(R.drawable.ic_add_profile);
        Glide.with(ivImage.getContext())
                .setDefaultRequestOptions(requestOptions2)
                .load(userData.getDisplayPicture()).into(ivImage);
    }
}