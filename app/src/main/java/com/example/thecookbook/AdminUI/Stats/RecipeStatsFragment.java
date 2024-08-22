package com.example.thecookbook.AdminUI.Stats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class RecipeStatsFragment extends Fragment {
    private DBHelper dbHelper;
    private List<RecipeClass> recipeListforLikes;
    private List<RecipeClass> recipeListforComments;
    private BarChart barChartlikes,barChartcomments;

    TextView TotalRecipes,VegRecipes,NonVegRecipes,BasicRecipes,PremiumRecipes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_stats, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        barChartlikes=view.findViewById(R.id.barChartPopularRecipesLikes);
        barChartcomments=view.findViewById(R.id.barChartCommentedRecipes);
        TotalRecipes=view.findViewById(R.id.valueTotalRecipes);
        VegRecipes=view.findViewById(R.id.valueRecipesVeg);
        NonVegRecipes=view.findViewById(R.id.valueRecipesNonVeg);
        BasicRecipes=view.findViewById(R.id.valueRecipesBasic);
        PremiumRecipes=view.findViewById(R.id.valueRecipesPremium);
        setUpperpart();
        setupBarchartforlikes();
        setupBarchartforcomments();
        return view;
    }

    private void setUpperpart() {
        TotalRecipes.setText(String.valueOf(dbHelper.getAllRecipeCount()));
        VegRecipes.setText(String.valueOf(dbHelper.getRecipeCountByCategory("Veg")));
        NonVegRecipes.setText(String.valueOf(dbHelper.getRecipeCountByCategory("Non-Veg")));
        BasicRecipes.setText(String.valueOf(dbHelper.getRecipeCountBySubscription("Basic")));
        PremiumRecipes.setText(String.valueOf(dbHelper.getRecipeCountBySubscription("Premium")));
    }

    private void setupBarchartforlikes() {
        recipeListforLikes = dbHelper.getTop5RecipesByLikes();
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            ArrayList<String> recipeNames = new ArrayList<>();
            for (int i = 0; i < recipeListforLikes.size(); i++) {
                RecipeClass recipe = recipeListforLikes.get(i);
                barEntries.add(new BarEntry(i, recipe.getLikeCount()));
                recipeNames.add(recipe.getRecipeName());
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "Likes");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(14f);
            barDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "" + ((int) value);
                }
            });
            BarData barData = new BarData(barDataSet);
            XAxis xAxis = barChartlikes.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(recipeNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(recipeNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + recipeNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartlikes.setFitBars(true);
            barChartlikes.setData(barData);
            barChartlikes.getDescription().setEnabled(false);
            barChartlikes.setDrawGridBackground(false);
            Legend legend = barChartlikes.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartlikes.animateY(2000);
            barChartlikes.invalidate();
    }

    private void setupBarchartforcomments() {
        recipeListforComments = dbHelper.getTop5RecipesByComments();
            ArrayList<BarEntry> barEntriesComments = new ArrayList<>();
            ArrayList<String> recipeNames = new ArrayList<>();
            for (int i = 0; i < recipeListforComments.size(); i++) {
                RecipeClass recipe = recipeListforComments.get(i);
                barEntriesComments.add(new BarEntry(i, recipe.getCommentCount()));
                recipeNames.add(recipe.getRecipeName());
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesComments, "Comments");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(14f);
            barDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "" + ((int) value);
                }
            });
            BarData barData = new BarData(barDataSet);
            XAxis xAxis = barChartcomments.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(recipeNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(recipeNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + recipeNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartcomments.setFitBars(true);
            barChartcomments.setData(barData);
            barChartcomments.getDescription().setEnabled(false);
            barChartcomments.setDrawGridBackground(false);
            Legend legend = barChartcomments.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartcomments.animateY(2000);
            barChartcomments.invalidate();
    }
}