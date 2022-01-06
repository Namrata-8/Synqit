package com.example.synqit.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.synqit.Fragments.BusinessFragment1;
import com.example.synqit.Fragments.BusinessFragment2;
import com.example.synqit.Fragments.BusinessFragment3;
import com.example.synqit.Fragments.BusinessFragment4;
import com.example.synqit.Fragments.IndividualFragment2;
import com.example.synqit.Fragments.IndividualFragment3;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 6;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BusinessFragment1();

            case 1:
                return new BusinessFragment2();

            case 2:
                return new BusinessFragment3();

            case 3:
                return new BusinessFragment4();

            case 4:
                return new IndividualFragment2();

            case 5:
                return new IndividualFragment3();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
