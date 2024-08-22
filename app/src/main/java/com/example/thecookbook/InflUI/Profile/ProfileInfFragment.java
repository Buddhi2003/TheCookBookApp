package com.example.thecookbook.InflUI.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.InfluencerDetailsTabAdapter;
import com.example.thecookbook.UserUI.Search.InfluencerCheck.RecipesFragment;
import com.example.thecookbook.UserUI.Search.InfluencerCheck.UpdatesFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class ProfileInfFragment extends Fragment {
    private int InfID;
    private boolean AdminEntry = false;
    private boolean InfluencerEntry = false;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DBHelper dbhelper;
    private int UserID=-1;
    private TextView Name,Bio,Likecount,Subcount,RecipeCount,editprof;
    private ShapeableImageView ProfileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_inf, container, false);
        dbhelper = new DBHelper(getContext());
        dbhelper.OpenDB();
        if(getArguments()!=null){
            InfID = getArguments().getInt("InfID");
            InfluencerEntry = getArguments().getBoolean("InfluencerEntry");
            Log.d("ProfileInfFragment", "This is profile fragment"+InfluencerEntry);
        }
        Name=(TextView) view.findViewById(R.id.influencername_profile);
        Bio=(TextView) view.findViewById(R.id.bio_details_profile);
        Likecount=(TextView) view.findViewById(R.id.likecountinfdetailsprofile);
        editprof = (TextView) view.findViewById(R.id.edit_prof);
        Subcount=(TextView) view.findViewById(R.id.Subcountinfdetailsprofile);
        RecipeCount=(TextView) view.findViewById(R.id.recipecountinfdetailsprofile);
        ProfileImage=(ShapeableImageView) view.findViewById(R.id.Influencerpfp_profile);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layoutinfluencerdetailsprofile);
        viewPager = (ViewPager) view.findViewById(R.id.view_pagerInfluencerdetailsprofile);

        SetupUpperDetails();
        InfluencerDetailsTabAdapter adapter = new InfluencerDetailsTabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, UserID,InfID, InfluencerEntry);
        adapter.AddFragment(new RecipesFragment(),"Recipes");
        adapter.AddFragment(new UpdatesFragment(), "Updates");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileInfEditActivity.class);
                intent.putExtra("InfID",InfID);
                startActivity(intent);
            }
        });

        return view;
    }
    private void SetupUpperDetails() {
        InfluencerClass influencer = dbhelper.getInfluencerDetails(InfID);
        Name.setText(influencer.getInfName());
        Bio.setText(influencer.getBio());
        ProfileImage.setImageBitmap(influencer.getInfprofImage());
        Likecount.setText(String.valueOf(influencer.getTotalLikes()));
        Subcount.setText(String.valueOf(influencer.getSubCount()));
        RecipeCount.setText(String.valueOf(dbhelper.getRecipeCount(InfID)));
    }

}