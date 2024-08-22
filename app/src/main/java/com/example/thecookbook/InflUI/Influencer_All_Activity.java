package com.example.thecookbook.InflUI;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.InflUI.Manage.ManageFragment;
import com.example.thecookbook.InflUI.Profile.ProfileInfFragment;
import com.example.thecookbook.InflUI.Recipes.RecipesInfluencerFragment;
import com.example.thecookbook.InflUI.Stats.StatsFragment;
import com.example.thecookbook.MainActivity;
import com.example.thecookbook.MainUi.UserLoginActivity;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

public class Influencer_All_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    //ActionBarDrawerToggle toggle;
    //ActionBar actionBar;
    int InfID;
    String Name;
    TextView InfName;
    boolean InfluencerEntry = true;
    ShapeableImageView profileimage;
    private DBHelper dbHelper;
    NavigationView navigationView;


    RecipesInfluencerFragment recipesInfluencerFragment = new RecipesInfluencerFragment();
    StatsFragment statsFragment = new StatsFragment();
    ProfileInfFragment profileFragment = new ProfileInfFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_influencer_all);

        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();

        Intent intent = getIntent();
        InfID = intent.getIntExtra("InfID",0);
        Name = intent.getStringExtra("InfName");

        Bundle bundle = new Bundle();
        bundle.putInt("InfID", InfID);
        bundle.putBoolean("InfluencerEntry", true);
        recipesInfluencerFragment.setArguments(bundle);
        statsFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);
        Toolbar toolbar = findViewById(R.id.ToolbarInf);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome "+Name);

        drawer= findViewById(R.id.drawer_layout_inf);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        InfName = navigationView.getHeaderView(0).findViewById(R.id.infname_influencer);
        InfName.setText(Name);

        profileimage = navigationView.getHeaderView(0).findViewById(R.id.Influencerpfp_details);
        profileimage.setImageBitmap(dbHelper.getInfPfp(InfID));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

//        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
//        }

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,recipesInfluencerFragment).commit();
            navigationView.setCheckedItem(R.id.influencer_navigation_Recipes);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.influencer_navigation_Recipes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipesInfluencerFragment).commit();
            getSupportActionBar().setTitle("Recipes");
            navigationView.setCheckedItem(R.id.influencer_navigation_Recipes);
        } else if (id == R.id.influencer_navigation_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
            getSupportActionBar().setTitle("Profile");
            navigationView.setCheckedItem(R.id.influencer_navigation_profile);
        } else if (id == R.id.influencer_navigation_Stats) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, statsFragment).commit();
            getSupportActionBar().setTitle("Stats");
            navigationView.setCheckedItem(R.id.influencer_navigation_Stats);
        } else if (id == R.id.influencer_navigation_logout) {
            Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_influencer, menu);
        MenuItem notificationsItem = menu.findItem(R.id.action_Notifications);
        Drawable icon = notificationsItem.getIcon();
        icon.setTint(getResources().getColor(R.color.md_theme_onSurface));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid= item.getItemId();
        if (itemid == R.id.action_Notifications){
            RecipeHistoryFragment dialog = new RecipeHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("InfID", InfID);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(),"RecipeHistory");
        }
        return super.onOptionsItemSelected(item);
    }
}