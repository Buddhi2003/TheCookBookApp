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
import com.example.thecookbook.Classes.UserClass;
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

public class UserStatsFragment extends Fragment {
    private DBHelper dbHelper;
    private List<String[]> userListbyLikes;
    TextView TotalUsers;
    BarChart barChartMostActiveUsers,barChartUsersSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        TotalUsers=view.findViewById(R.id.valueTotalUsersstats);
        barChartMostActiveUsers=view.findViewById(R.id.barChartMostActiveUsers);
        barChartUsersSubscription=view.findViewById(R.id.barChartUsersSubscription);
        setupUpperPart();
        setupBarchartforLikes();
        setupBarchartforSubscription();


        return view;
    }

    private void setupUpperPart() {
        TotalUsers.setText(String.valueOf(dbHelper.getTotalNumberOfUsers()));
    }

    private void setupBarchartforLikes() {
        userListbyLikes = dbHelper.getTop5UsersByLikes();
            ArrayList<BarEntry> barEntriesUsersbyLikes = new ArrayList<>();
            ArrayList<String> UserNames = new ArrayList<>();
            for (int i = 0; i < userListbyLikes.size(); i++) {
                int LikeCount = Integer.parseInt(userListbyLikes.get(i)[2]);
                barEntriesUsersbyLikes.add(new BarEntry(i, LikeCount));
                UserNames.add(userListbyLikes.get(i)[1]);
            }
            BarDataSet barDataSet = new BarDataSet(barEntriesUsersbyLikes, "Likes");
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
            XAxis xAxis = barChartMostActiveUsers.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(UserNames));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(16f);
            xAxis.setLabelCount(UserNames.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "    " + UserNames.get((int) value);
                }
            });
            xAxis.setLabelRotationAngle(-45f);
            barChartMostActiveUsers.setFitBars(true);
            barChartMostActiveUsers.setData(barData);
            barChartMostActiveUsers.getDescription().setEnabled(false);
            barChartMostActiveUsers.setDrawGridBackground(false);
            Legend legend = barChartMostActiveUsers.getLegend();
            legend.setTextSize(12f);
            legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
            barChartMostActiveUsers.animateY(2000);
            barChartMostActiveUsers.invalidate();

    }

    private void setupBarchartforSubscription() {
        int basicUsers = dbHelper.getUserCountBySubscription("Basic");
        int premiumUsers = dbHelper.getUserCountBySubscription("Premium");

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, basicUsers));
        barEntries.add(new BarEntry(1, premiumUsers));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Subscription Status");
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

        barChartUsersSubscription.setFitBars(true);
        barChartUsersSubscription.setData(barData);
        barChartUsersSubscription.getDescription().setEnabled(false);
        barChartUsersSubscription.setDrawGridBackground(false);
        String[] labels = new String[]{"Basic", "Premium"};
        XAxis xAxis = barChartUsersSubscription.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(16f);
        xAxis.setLabelRotationAngle(-45f);
        Legend legend = barChartUsersSubscription.getLegend();
        legend.setTextSize(12f);
        legend.setTypeface(Typeface.create("lato_bold", Typeface.BOLD_ITALIC));
        barChartUsersSubscription.animateY(2000);
        barChartUsersSubscription.invalidate();

    }
}