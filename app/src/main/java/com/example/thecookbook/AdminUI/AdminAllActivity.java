package com.example.thecookbook.AdminUI;

import static com.example.thecookbook.R.id.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thecookbook.AdminUI.Home.AdminHomeFragment;
import com.example.thecookbook.AdminUI.ManageInf.AdminManageInfFragment;
import com.example.thecookbook.AdminUI.ManageUser.AdminManageUserFragment;
import com.example.thecookbook.AdminUI.Stats.GeneralStatsFragment;
import com.example.thecookbook.AdminUI.Stats.InfluencerStatsFragment;
import com.example.thecookbook.AdminUI.Stats.RecipeStatsFragment;
import com.example.thecookbook.AdminUI.Stats.UserStatsFragment;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.MainUi.UserLoginActivity;
import com.example.thecookbook.R;
import com.google.android.material.navigation.NavigationView;

public class AdminAllActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    private NavigationView navigationView;

    AdminHomeFragment adminHomeFragment = new AdminHomeFragment();
    AdminManageInfFragment adminManageinfFragment = new AdminManageInfFragment();
    AdminManageUserFragment adminManageuserFragment = new AdminManageUserFragment();
    GeneralStatsFragment generalStatsFragment = new GeneralStatsFragment();
    InfluencerStatsFragment influencerStatsFragment = new InfluencerStatsFragment();
    UserStatsFragment userStatsFragment = new UserStatsFragment();
    RecipeStatsFragment recipeStatsFragment = new RecipeStatsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_all);

        Toolbar toolbar = findViewById(R.id.ToolbarAdmin);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Items");
        drawer= findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,adminHomeFragment).commit();
            navigationView.setCheckedItem(R.id.admin_nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();
        if (ID == R.id.admin_nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, adminHomeFragment).commit();
            getSupportActionBar().setTitle("Pending Items");
            navigationView.setCheckedItem(R.id.admin_nav_home);
        } else if (ID == R.id.admin_nav_manage_inf) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, adminManageinfFragment).commit();
            getSupportActionBar().setTitle("Manage Influencers");
            navigationView.setCheckedItem(R.id.admin_nav_manage_inf);
        } else if (ID == R.id.admin_nav_manage_user) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, adminManageuserFragment).commit();
            getSupportActionBar().setTitle("Manage Users");
            navigationView.setCheckedItem(R.id.admin_nav_manage_user);
        } else if (ID == R.id.admin_nav_general_stats) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, generalStatsFragment).commit();
            getSupportActionBar().setTitle("General Stats");
            navigationView.setCheckedItem(R.id.admin_nav_general_stats);
        } else if (ID == R.id.admin_nav_recipe_stats) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, recipeStatsFragment).commit();
            getSupportActionBar().setTitle("Recipe Stats");
            navigationView.setCheckedItem(R.id.admin_nav_recipe_stats);
        } else if (ID == R.id.admin_nav_inf_stats) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, influencerStatsFragment).commit();
            getSupportActionBar().setTitle("Influencer Stats");
            navigationView.setCheckedItem(R.id.admin_nav_inf_stats);
        } else if (ID == R.id.admin_nav_user_stats) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, userStatsFragment).commit();
            getSupportActionBar().setTitle("User Stats");
            navigationView.setCheckedItem(R.id.admin_nav_user_stats);
        } else if (ID == R.id.admin_nav_logout) {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminAllActivity.this, UserLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            return false;
        }
        item.setCheckable(true);
        item.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {

        }
    }

}