package com.example.bookkeepingapp.MainFunction;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class Activity_3_Adapter extends FragmentStatePagerAdapter {

    public static final String TAG = "MyTabFragmentAdapter";

    private String[] titleArray;
    private List<Fragment> listFragments;

    public Activity_3_Adapter(FragmentManager fm, List<Fragment> list, String[] title) {
        super(fm);
    }

    public Activity_3_Adapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        Log.d(TAG,"listFragments.size() = " + listFragments.size());
        return listFragments.size();
    }
    public void addFragment(Fragment fragment){
        this.listFragments.add(fragment);
    }

    public void setFragments(List<Fragment> fragments){
        this.listFragments = fragments;
    }

    public void setTitlesArr (String[] titlesArr) {
        this.titleArray = titlesArr;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}