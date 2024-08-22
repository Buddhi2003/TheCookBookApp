package com.example.thecookbook.UserUI.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.wasabeef.blurry.Blurry;

public class UserProfileDetailActivity extends AppCompatActivity {
    private int UserID;
    private boolean AdminEntry = false;
    private DBHelper dbHelper;
    private TextView Name,Likecount,FavouriteCount,CommentCount;
    private ShapeableImageView ProfileImage,Deleteacc,bgimage,backbtn;
    private PieChart pieChartlikes;
    private Button EditAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile_detail);
        Intent intent = getIntent();
        UserID = intent.getIntExtra("UserID", -1);
        AdminEntry = intent.getBooleanExtra("AdminEntry", false);
        dbHelper =new DBHelper(this);
        dbHelper.OpenDB();
        Name = (TextView) findViewById(R.id.Username_details);
        Likecount = (TextView) findViewById(R.id.likecountUserdetails);
        FavouriteCount = (TextView) findViewById(R.id.FavouriteCountUserdetails);
        CommentCount = (TextView) findViewById(R.id.CmntcountUserdetails);
        ProfileImage = (ShapeableImageView) findViewById(R.id.Userpfp_details);
        Deleteacc = (ShapeableImageView) findViewById(R.id.deleteaccountUser);
        EditAcc = (Button) findViewById(R.id.editpofileinfouser);
        backbtn= (ShapeableImageView) findViewById(R.id.backbutton_detailsUser);
        bgimage = (ShapeableImageView) findViewById(R.id.bg_detailsUser);
        pieChartlikes = (PieChart) findViewById(R.id.pichartuserdetail);
        if(AdminEntry){
            Deleteacc.setVisibility(View.VISIBLE);
            EditAcc.setVisibility(View.GONE);
        } else{
            Deleteacc.setVisibility(View.GONE);
            EditAcc.setVisibility(View.VISIBLE);
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bgimage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgimage.getDrawable() != null) {
                    Blurry.with(UserProfileDetailActivity.this)
                            .radius(25)
                            .sampling(2)
                            .capture(bgimage)
                            .into(bgimage);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
            }
        });
        SetupUpperDetailes();
        setuppiechart();
        EditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileDetailActivity.this, UserEditProfileActivity.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });


        Deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserProfileDetailActivity.this);
                alertDialog.setTitle("Delete Account");
                alertDialog.setMessage("Are you sure you want to delete this account?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.DeleteUser(UserID)){
                            onBackPressed();
                            Toast.makeText(UserProfileDetailActivity.this, "Deleted successfuly", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UserProfileDetailActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserProfileDetailActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

    }

    private void setuppiechart() {
        Map<String,Integer> LikedRecipesbyCategory = dbHelper.getLikedRecipesByCategory(UserID);
        if (LikedRecipesbyCategory == null || LikedRecipesbyCategory.isEmpty()) {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
        Log.d("LikedRecipesbyCategory", LikedRecipesbyCategory.toString());
        ArrayList<PieEntry> Likes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : LikedRecipesbyCategory.entrySet()) {
            Likes.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet pieDataSet = new PieDataSet(Likes,"Likes by Category");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTypeface(Typeface.create( "lato_bold", Typeface.BOLD_ITALIC));
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""+((int)value);
            }
        });
        PieData pieData = new PieData(pieDataSet);
        pieChartlikes.setHoleRadius(20f);
        pieChartlikes.setTransparentCircleRadius(10f);
        pieChartlikes.setEntryLabelTextSize(20f);
        pieChartlikes.setEntryLabelTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartlikes.setData(pieData);
        pieChartlikes.getDescription().setEnabled(false);
        pieChartlikes.setCenterText("Likes");
        pieChartlikes.setCenterTextSize(16f);
        pieChartlikes.setCenterTextTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartlikes.getLegend().setTextSize(16f);
        pieChartlikes.getLegend().setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartlikes.animate();
    }

    private void SetupUpperDetailes() {
        UserClass user = dbHelper.getUserdetailsforComment(UserID);
        Name.setText(user.getUserName());
        ProfileImage.setImageBitmap(user.getProfileImage());
        Likecount.setText(String.valueOf(dbHelper.getTotalLikesUser(UserID)));
        FavouriteCount.setText(String.valueOf(dbHelper.getFavouriteCountUser(UserID)));
        CommentCount.setText(String.valueOf(dbHelper.getCommentCountUser(UserID)));
    }
}