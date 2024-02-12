package com.developer.visionglyco.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.visionglyco.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
public class Report extends Fragment {

    public Report() {
        // Required empty public constructor
    }
//    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
//        barChart = view.findViewById(R.id.bar_chart);
//        setupBarChart();
        return view;
    }
//    private void setupBarChart() {
//        List<BarEntry> entries = new ArrayList<>();
//
//        // Generate random glucose level values and corresponding times
//        for (int i = 0; i < 20; i++) {
//            float glucoseLevel = (float) (Math.random() * 200); // Random glucose level between 0 and 200
//            float time = i; // Assuming time is represented as index
//
//            entries.add(new BarEntry(time, glucoseLevel));
//        }
//
//        BarDataSet dataSet = new BarDataSet(entries, "Glucose Level");
//        dataSet.setColor(Color.BLUE);
//
//        BarData barData = new BarData(dataSet);
//
//        // Set data to the chart
//        barChart.setData(barData);
//
//        // Customize chart appearance
//        Description description = new Description();
//        description.setText("Glucose Level over Time");
//        barChart.setDescription(description);
//        barChart.setDrawGridBackground(false);
//        barChart.setDrawBorders(false);
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setAxisMinimum(0f); // start at zero
//
//        YAxis rightAxis = barChart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        // Invalidate to refresh the chart
//        barChart.invalidate();
//    }
}