package com.example.thecookbook.AdminUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.AdminHomeAdapter;
import com.google.android.material.tabs.TabLayout;

public class AdminHomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        tabLayout = view.findViewById(R.id.tab_layoutHomefrag_admin);
        viewPager = view.findViewById(R.id.view_pagerAdminHomefrag);


        AdminHomeAdapter adminHomeAdapter = new AdminHomeAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adminHomeAdapter.AddFragment(new PendingRecipeFragment(), "Pending Recipes");
        adminHomeAdapter.AddFragment(new PendingRequestFragment(), "Pending Requests");
        viewPager.setAdapter(adminHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}