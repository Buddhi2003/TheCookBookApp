package com.example.thecookbook.InflUI.Stats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.Classes.SalaryClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    private DBHelper dbHelper;
    private int InfID;
    private PieChart pieChartlikes,pieChartComments;
    TextView Level,Revenue,LastDate,LastPaid;
    List<RecipeClass> recipelist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        Level = view.findViewById(R.id.levelinfstat);
        Revenue = view.findViewById(R.id.revenueinfstat);
        LastDate = view.findViewById(R.id.paiddate);
        LastPaid = view.findViewById(R.id.paidamount);
        pieChartlikes = view.findViewById(R.id.pieChartLikes);
        pieChartComments = view.findViewById(R.id.pieChartComments);
        if(getArguments()!=null){
            InfID = getArguments().getInt("InfID");
        }
        SetupSalary();
        SetuppiechartforLikes();
        SetuppiechartforComments();
        return view;
    }

    private void SetupSalary() {
        Level.setText(String.valueOf(dbHelper.CalculateInfLevel(InfID)));
        Log.d("Inf level",String.valueOf(dbHelper.CalculateInfLevel(InfID)));
        SalaryClass salary = dbHelper.CalculateandGetSalary(InfID);
        if(salary!=null){
            Revenue.setText(String.valueOf(salary.getTotalSalary()));
            LastDate.setText(salary.getLastPaidDate());
            LastPaid.setText(String.valueOf(salary.getLastPaidSalary()));
        }
    }

    private void SetuppiechartforLikes(){
        ArrayList<PieEntry> Likes = new ArrayList<>();
        recipelist=dbHelper.getLikesforPiechart(InfID);
        for (RecipeClass recipe:recipelist){
            Likes.add(new PieEntry(recipe.getLikeCount(),recipe.getRecipeName()));
        }
        PieDataSet pieDataSet = new PieDataSet(Likes,"Likes");
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
    private void SetuppiechartforComments(){
        ArrayList<PieEntry> Comments = new ArrayList<>();
        recipelist=dbHelper.getCommentsforPiechart(InfID);
        for (RecipeClass recipe:recipelist){
            Comments.add(new PieEntry(recipe.getCommentCount(),recipe.getRecipeName()));
        }
        PieDataSet pieDataSet = new PieDataSet(Comments,"Comments");
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
        pieChartComments.setHoleRadius(20f);
        pieChartComments.setTransparentCircleRadius(10f);
        pieChartComments.setEntryLabelTextSize(20f);
        pieChartComments.setEntryLabelTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartComments.setData(pieData);
        pieChartComments.getDescription().setEnabled(false);
        pieChartComments.setCenterText("Comments");
        pieChartComments.setCenterTextSize(16f);
        pieChartComments.setCenterTextTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartComments.getLegend().setTextSize(16f);
        pieChartComments.getLegend().setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        pieChartComments.animate();
    }
}