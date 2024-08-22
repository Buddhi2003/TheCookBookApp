package com.example.thecookbook.UserUI.Search.InfluencerCheck;

import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.thecookbook.UIAdapters.InfluencerDetailsTabAdapter;
import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class InfluencerDetailsActivity extends AppCompatActivity {
    private int UserID;
    private int InfID;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DBHelper dbHelper;
    private TextView Name,Bio,Likecount,Subcount,RecipeCount;
    private ShapeableImageView ProfileImage,backbtn,Deleteacc;
    private Button subscribebtn;
    boolean isSubscribed=false;
    boolean AdminEntry = false;
    boolean InfluencerEntry = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_influencer_details);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        Intent intent = getIntent();

        UserID = intent.getIntExtra("UserID",-1);
        InfID = intent.getIntExtra("InfID", -1);
        AdminEntry = intent.getBooleanExtra("AdminEntry", false);

        Name=(TextView) findViewById(R.id.influencername_details);
        Bio=(TextView) findViewById(R.id.bio_details);
        Likecount=(TextView) findViewById(R.id.likecountinfdetails);
        Subcount=(TextView) findViewById(R.id.Subcountinfdetails);
        RecipeCount=(TextView) findViewById(R.id.recipecountinfdetails);
        backbtn = (ShapeableImageView) findViewById(R.id.backbutton_details);
        ProfileImage=(ShapeableImageView) findViewById(R.id.Influencerpfp_details);
        Deleteacc=(ShapeableImageView)findViewById(R.id.deleteaccountinfluencer);
        subscribebtn=(Button) findViewById(R.id.subscribeinfluencer_details);
        tabLayout = (TabLayout) findViewById(R.id.tab_layoutinfluencerdetails);
        viewPager = (ViewPager) findViewById(R.id.view_pagerInfluencerdetails);
        if(AdminEntry){
            subscribebtn.setVisibility(View.GONE);
            Deleteacc.setVisibility(View.VISIBLE);
        }else{
            Deleteacc.setVisibility(View.GONE);
            CheckSubscriptionStatus();
        }
        SetupUpperDetails();
        InfluencerDetailsTabAdapter adapter = new InfluencerDetailsTabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, UserID,InfID,InfluencerEntry);
        adapter.AddFragment(new RecipesFragment(),"Recipes");
        adapter.AddFragment(new UpdatesFragment(), "Updates");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        subscribebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSubscribed){
                    if(dbHelper.UnsubscribefromInfluencer(UserID,InfID)){
                        isSubscribed = false;
                        subscribebtn.setText("Subscribe");
                        Toast.makeText(InfluencerDetailsActivity.this, "Unsubscribed successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InfluencerDetailsActivity.this, "Unsubscribing failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(dbHelper.SubscribetoInfluencer(UserID,InfID)){
                        isSubscribed = true;
                        subscribebtn.setText("Unsubscribe");
                        Toast.makeText(InfluencerDetailsActivity.this, "Subscribed successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InfluencerDetailsActivity.this, "Subscribing failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        Deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InfluencerDetailsActivity.this);
                alertDialog.setTitle("Delete Account");
                alertDialog.setMessage("Are you sure you want to delete this account?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.DeleteInfluencer(InfID)){
                            onBackPressed();
                            Toast.makeText(InfluencerDetailsActivity.this, "Deleted successfuly", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(InfluencerDetailsActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(InfluencerDetailsActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void SetupUpperDetails() {
        InfluencerClass influencer = dbHelper.getInfluencerDetails(InfID);
        Name.setText(influencer.getInfName());
        Bio.setText(influencer.getBio());
        ProfileImage.setImageBitmap(influencer.getInfprofImage());
        Likecount.setText(String.valueOf(influencer.getTotalLikes()));
        Subcount.setText(String.valueOf(influencer.getSubCount()));
        RecipeCount.setText(String.valueOf(dbHelper.getRecipeCount(InfID)));
    }
    private void CheckSubscriptionStatus() {
        isSubscribed = dbHelper.isSubscribed(UserID, InfID);
        if(isSubscribed){
            subscribebtn.setText("Unsubscribe");
        }else{
            subscribebtn.setText("Subscribe");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InfluencerDetailsTabAdapter adapter = new InfluencerDetailsTabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, UserID,InfID,InfluencerEntry);
        adapter.AddFragment(new RecipesFragment(),"Recipes");
        adapter.AddFragment(new UpdatesFragment(), "Updates");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}