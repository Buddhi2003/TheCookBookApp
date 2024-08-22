package com.example.thecookbook.UserUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.UIAdapters.UserHomeAdapter;
import com.example.thecookbook.R;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int UserID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);
        if (getArguments()!=null) {
            UserID = getArguments().getInt("UserID");
        }
        tabLayout = view.findViewById(R.id.tab_layoutHomefrag);
        viewPager = view.findViewById(R.id.view_pagerHomefrag);

        UserHomeAdapter userHomeAdapter = new UserHomeAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, UserID);

        userHomeAdapter.AddFragment(new ExploreFragment(), "Explore");
        userHomeAdapter.AddFragment(new FollowingFragment(), "Following");


        viewPager.setAdapter(userHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}