package com.example.synqit.ui.maps;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.synqit.fragments.insightfragment.model.UserDataMap;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterMap extends FragmentPagerAdapter {

    List<UserDataMap> userList ;
    List<LocationFragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapterMap(FragmentManager fm, List<UserDataMap> userList) {
        super(fm);
        this.userList = userList;
    }

    @Override
    public Fragment getItem(int i) {
        LocationFragment fragment = LocationFragment.newInstance(userList.get(i));
        fragmentList.add(fragment);
        return  fragment;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

}
