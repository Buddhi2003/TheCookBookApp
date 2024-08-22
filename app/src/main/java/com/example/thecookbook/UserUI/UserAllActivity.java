package com.example.thecookbook.UserUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Favourites.FavouriteFragment;

import com.example.thecookbook.UserUI.Home.HomeFragment;
import com.example.thecookbook.UserUI.Premium.PremiumFragment;
import com.example.thecookbook.UserUI.Profile.ProfileFragment;
import com.example.thecookbook.UserUI.Search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class UserAllActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    PremiumFragment premiumFragment = new PremiumFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_all);
        Intent intent = getIntent();
        int UserID = intent.getIntExtra("UserID", 0);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", UserID);
        searchFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);
        favouriteFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);
        premiumFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int ItemID = item.getItemId();
                if ( ItemID == R.id.navigation_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                } else if (ItemID == R.id.navigation_search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                    return true;
                } else if (ItemID == R.id.navigation_favourite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favouriteFragment).commit();
                    return true;
                }else if(ItemID == R.id.navigation_premium){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, premiumFragment).commit();
                    return true;
                }
                else if (ItemID == R.id.navigation_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}