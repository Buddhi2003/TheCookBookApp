package com.example.thecookbook.UserUI.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.UIAdapters.UserSearchAdapter;
import com.example.thecookbook.R;
import com.google.android.material.tabs.TabLayout;


public class SearchFragment extends Fragment {
    private int UserID;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        if (getArguments()!=null) {
            UserID = getArguments().getInt("UserID");
        }
        tabLayout = view.findViewById(R.id.tab_layoutSearchfrag);
        viewPager = view.findViewById(R.id.view_pagerSearchfrag);
        tabLayout.setupWithViewPager(viewPager);
        UserSearchAdapter userSearchAdapter = new UserSearchAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,UserID);
        userSearchAdapter.AddFragment(new SearchInfluencerFragment(), "Influencers");
        userSearchAdapter.AddFragment(new SearchRecipeFragment(), "Recipes");
        viewPager.setAdapter(userSearchAdapter);
        return view;
    }
}