package com.example.thecookbook.AdminUI.Stats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.Classes.SalaryClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class InfluencerStatsFragment extends Fragment {
    private DBHelper dbHelper;
    private List<String[]> influencerListforRecipes;
    private List<InfluencerClass> influencerListforLikes;
    private List<InfluencerClass> influencerListforSubs;
    private List<SalaryClass> influencerListforSalary;

    TextView TotalInf,TotalSalary;
    BarChart barChartTopInfluencersByRecipes,barChartTopInfluencersByLikes,barChartTopInfluencersBySubs,barChartTopInfluencersBySalary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_influencer_stats, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        TotalInf=view.findViewById(R.id.valueTotalInfluencers);
        TotalSalary=view.findViewById(R.id.valueTotalSalary);
        barChartTopInfluencersByRecipes=view.findViewById(R.id.barChartTopInfluencersRecipes);
        barChartTopInfluencersByLikes=view.findViewById(R.id.barChartTopInfluencersLikes);
        barChartTopInfluencersBySubs=view.findViewById(R.id.barChartTopInfluencersSubs);
        barChartTopInfluencersBySalary=view.findViewById(R.id.barChartTopEarners);
        setUpperpart();
        setupBarchartforRecipes();
        setupBarchartforLikes();
        setupBarchartforSubs();
        setupBarchartforSalary();
        return view;
    }
    private void setUpperpart() {
        TotalInf.setText(String.valueOf(dbHelper.getTotalNumberOfInfluencers()));
        TotalSalary.setText(String.valueOf(dbHelper.getTotalSalary()));
    }
    private void setupBarchartforSalary() {
        influencerListforSalary = dbHelper.getTop5InfluencersBySalary();

            ArrayList<BarEntry> barEntriesSalary = new ArrayList<>();
            ArrayList<String> InfNames = new ArrayList<>();
            for (int i = 0; i < influencerListforSalary.size(); i++) {
                SalaryClass salary = influencerListforSalary.get(i);
                barEntriesSalary.add(new BarEntry(i, (int)salary.getTotalSalary()));
                InfNames.add(dbHelper.getInfName(salary.getInfID()));
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesSalary, "Salary");
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
            XAxis xAxis = barChartTopInfluencersBySalary.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(InfNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(InfNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + InfNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartTopInfluencersBySalary.setFitBars(true);
            barChartTopInfluencersBySalary.setData(barData);
            barChartTopInfluencersBySalary.getDescription().setEnabled(false);
            barChartTopInfluencersBySalary.setDrawGridBackground(false);
            Legend legend = barChartTopInfluencersBySalary.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartTopInfluencersBySalary.animateY(2000);
            barChartTopInfluencersBySalary.invalidate();

    }

    private void setupBarchartforSubs() {
        influencerListforSubs = dbHelper.getTop5InfluencersBySubs();
            ArrayList<BarEntry> barEntriesInfSub = new ArrayList<>();
            ArrayList<String> InfNames = new ArrayList<>();
            for (int i = 0; i < influencerListforSubs.size(); i++) {
                InfluencerClass influencer = influencerListforSubs.get(i);
                barEntriesInfSub.add(new BarEntry(i, (int)influencer.getSubCount()));
                InfNames.add(influencer.getInfName());
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesInfSub, "Subs");
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
            XAxis xAxis = barChartTopInfluencersBySubs.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(InfNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(InfNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + InfNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartTopInfluencersBySubs.setFitBars(true);
            barChartTopInfluencersBySubs.setData(barData);
            barChartTopInfluencersBySubs.getDescription().setEnabled(false);
            barChartTopInfluencersBySubs.setDrawGridBackground(false);
            Legend legend = barChartTopInfluencersBySubs.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartTopInfluencersBySubs.animateY(2000);
            barChartTopInfluencersBySubs.invalidate();
    }

    private void setupBarchartforLikes() {
        influencerListforLikes = dbHelper.getTop5InfluencersByLikes();
            ArrayList<BarEntry> barEntriesInfLikes = new ArrayList<>();
            ArrayList<String> InfNames = new ArrayList<>();
            for (int i = 0; i < influencerListforLikes.size(); i++) {
                InfluencerClass influencer = influencerListforLikes.get(i);
                barEntriesInfLikes.add(new BarEntry(i,influencer.getTotalLikes()));
                InfNames.add(influencer.getInfName());
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesInfLikes, "Likes");
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
            XAxis xAxis = barChartTopInfluencersByLikes.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(InfNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(InfNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + InfNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartTopInfluencersByLikes.setFitBars(true);
            barChartTopInfluencersByLikes.setData(barData);
            barChartTopInfluencersByLikes.getDescription().setEnabled(false);
            barChartTopInfluencersByLikes.setDrawGridBackground(false);
            Legend legend = barChartTopInfluencersByLikes.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartTopInfluencersByLikes.animateY(2000);
            barChartTopInfluencersByLikes.invalidate();
    }

    private void setupBarchartforRecipes() {
        influencerListforRecipes = dbHelper.getTop5InfluencersByRecipes();
            ArrayList<BarEntry> barEntriesInfRecipes = new ArrayList<>();
            ArrayList<String> InfNames = new ArrayList<>();
            for (int i = 0; i < influencerListforRecipes.size(); i++) {
                int RecipeCount = Integer.parseInt(influencerListforRecipes.get(i)[2]);
                barEntriesInfRecipes.add(new BarEntry(i, RecipeCount));
                InfNames.add(influencerListforRecipes.get(i)[1]);
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesInfRecipes, "Recipes");
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
            XAxis xAxis = barChartTopInfluencersByRecipes.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(InfNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(InfNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + InfNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartTopInfluencersByRecipes.setFitBars(true);
            barChartTopInfluencersByRecipes.setData(barData);
            barChartTopInfluencersByRecipes.getDescription().setEnabled(false);
            barChartTopInfluencersByRecipes.setDrawGridBackground(false);
            Legend legend = barChartTopInfluencersByRecipes.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartTopInfluencersByRecipes.animateY(2000);
            barChartTopInfluencersByRecipes.invalidate();

    }
}