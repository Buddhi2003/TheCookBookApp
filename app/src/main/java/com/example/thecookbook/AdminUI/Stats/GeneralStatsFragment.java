package com.example.thecookbook.AdminUI.Stats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.github.mikephil.charting.charts.PieChart;

public class GeneralStatsFragment extends Fragment {
    private DBHelper dbHelper;
    TextView TotalRecipes,TotalUsers,TotalInfluencers,TotalComments,TotalLikes,AvgLikesPerRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_stats, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        TotalRecipes = view.findViewById(R.id.statTotalRecipesValue);
        TotalUsers = view.findViewById(R.id.statTotalUsersValue);
        TotalInfluencers = view.findViewById(R.id.statTotalInfluencersValue);
        TotalComments = view.findViewById(R.id.statTotalCommentsValue);
        TotalLikes = view.findViewById(R.id.statTotalLikesValue);
        AvgLikesPerRecipe = view.findViewById(R.id.statAvgLikesPerRecipeValue);
        setupStats();
        return view;
    }

    private void setupStats() {
        TotalRecipes.setText(String.valueOf(dbHelper.getTotalNumberOfRecipes()));
        TotalUsers.setText(String.valueOf(dbHelper.getTotalNumberOfUsers()));
        TotalInfluencers.setText(String.valueOf(dbHelper.getTotalNumberOfInfluencers()));
        TotalComments.setText(String.valueOf(dbHelper.getTotalNumberOfComments()));
        TotalLikes.setText(String.valueOf(dbHelper.getTotalNumberOfLikes()));
        AvgLikesPerRecipe.setText(String.valueOf(dbHelper.getAverageLikesPerRecipe()));
    }
}