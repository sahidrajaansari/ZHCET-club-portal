package com.example.zcm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.zcm.fragment.Home;
import com.example.zcm.fragment.aboutus;
import com.example.zcm.fragment.addpost;
import com.example.zcm.fragment.clubList;
import com.example.zcm.fragment.profile;

public class ClubPageAdapter extends FragmentPagerAdapter {
    int tabCount;

    public ClubPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            {
                return new addpost();
            }
            case 1:
            {
                return new profile();
            }
            case 2:
            {
                return new aboutus();
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
