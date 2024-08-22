package com.example.thecookbook.UIAdapters;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class InfluencerDetailsTabAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();
    int UserID=-1;
    int InfID;
    boolean InfluencerEntry = false;

    public InfluencerDetailsTabAdapter(@NonNull FragmentManager fm, int behavior,int userID,int infID, boolean influencerEntry) {
        super(fm, behavior);
        this.UserID=userID;
        this.InfID=infID;
        InfluencerEntry=influencerEntry;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentArrayList.get(position);
        Bundle bundle = new Bundle();
        if(UserID!=-1){
            bundle.putInt("UserID", UserID);
        }else if (InfluencerEntry){
            bundle.putBoolean("InfluencerEntry",true);
        }else{
            bundle.putBoolean("AdminEntry",true);
        }
        bundle.putInt("InfID", InfID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentTitle.size();
    }

    public void AddFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
