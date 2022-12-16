package com.example.zcm.adapter;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.zcm.fragment.loginclub_tabfragment;
import com.example.zcm.fragment.loginstudent_tabfragment;

public class pageadapter extends FragmentPagerAdapter{
    private Context context;
    int totaltabs;
    public pageadapter(FragmentManager fm,Context context,int totaltabs){
        super(fm);
        this.context=context;
        this.totaltabs=totaltabs;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
    public Fragment getItem(int position){
        switch(position){
            case 0:
                loginclub_tabfragment loginclubtabfragment=new loginclub_tabfragment();
                return loginclubtabfragment;
            case 1:
                loginstudent_tabfragment loginstudenttabfragment=new loginstudent_tabfragment();
                return loginstudenttabfragment;
            default:
                return null;
        }
    }
}
