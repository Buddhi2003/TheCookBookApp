package com.example.thecookbook.UserUI.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.ListAdapters.AdminInfManageAdapter;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class SubbedInfListActivity extends AppCompatActivity {
    private int UserID;
    private DBHelper dbHelper;
    private ShapeableImageView bgimage;
    private RecyclerView recyclerView;
    private List<InfluencerClass> influencerlist;
    private AdminInfManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subbed_inf_list);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        bgimage=(ShapeableImageView) findViewById(R.id.bguserprofsubbed);
        recyclerView = findViewById(R.id.subbedinflistrecycler);
        if (getIntent().hasExtra("UserID")) {
            UserID = getIntent().getIntExtra("UserID", 0);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        setupInfluencers();
        bgimage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgimage.getDrawable() != null) {
                    Blurry.with(SubbedInfListActivity.this)
                            .radius(15)
                            .sampling(2)
                            .capture(bgimage)
                            .into(bgimage);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
            }
        });


    }

    private void setupInfluencers() {
        influencerlist = dbHelper.getSubbedInfluencers(UserID);
        Log.d("InfluencerList", String.valueOf(influencerlist.size()));
        if(influencerlist.isEmpty()){
            Toast.makeText(this, "No subscribed influencers", Toast.LENGTH_SHORT).show();
        }else {
            adapter = new AdminInfManageAdapter(influencerlist,this,UserID);
            recyclerView.setAdapter(adapter);
        }
    }
}