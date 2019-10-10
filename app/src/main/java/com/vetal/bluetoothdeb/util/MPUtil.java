package com.vetal.bluetoothdeb.util;

import android.content.Context;


import com.vetal.bluetoothdeb.GraphView;
import com.vetal.bluetoothdeb.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class MPUtil{

    private static int textSize = 10;

    public static ArrayList<BarDataSet> getDataSet(Context context, List<Float> dataList) {

        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            valueSet.add(new BarEntry(dataList.get(i), i));
        }
        int[] colors = {context.getResources().getColor(R.color.colorPrimaryDark),
                context.getResources().getColor(R.color.color1),
                context.getResources().getColor(R.color.color2),
                context.getResources().getColor(R.color.color3),
                context.getResources().getColor(R.color.color4),
                context.getResources().getColor(R.color.color10),
                context.getResources().getColor(R.color.color11),
                context.getResources().getColor(R.color.color12),
                context.getResources().getColor(R.color.color13),
                context.getResources().getColor(R.color.color20),
                context.getResources().getColor(R.color.color21),
                context.getResources().getColor(R.color.color22),
                context.getResources().getColor(R.color.color23)};

        BarDataSet barDataSet = new MyBarDataSet(valueSet, "");
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(textSize);
        barDataSet.setBarSpacePercent(3);

        dataSets.add(barDataSet);

        return dataSets;
    }

    public static ArrayList<BarDataSet> getDataSetTmp(Context context, List<Float> dataList) {

        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            valueSet.add(new BarEntry(dataList.get(i), i));
        }
        int[] colors = {context.getResources().getColor(R.color.colorPrimaryDark),
                context.getResources().getColor(R.color.color1),
                context.getResources().getColor(R.color.color2),
                context.getResources().getColor(R.color.color3),
                context.getResources().getColor(R.color.color4),
                context.getResources().getColor(R.color.color10),
                context.getResources().getColor(R.color.color11),
                context.getResources().getColor(R.color.color12),
                context.getResources().getColor(R.color.color13),
                context.getResources().getColor(R.color.color20),
                context.getResources().getColor(R.color.color21),
                context.getResources().getColor(R.color.color22),
                context.getResources().getColor(R.color.color23)};

        BarDataSet barDataSetTmp = new MyBarDataSetTmp(valueSet, "");
        barDataSetTmp.setColors(colors);
        barDataSetTmp.setValueTextSize(textSize);
        barDataSetTmp.setBarSpacePercent(3);

        dataSets.add(barDataSetTmp);

        return dataSets;
    }

    public static ArrayList<String> getXAxisValues(int dataSize) {

        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {
            xAxis.add(String.valueOf(i + 1));
        }
        return xAxis;
    }


    public static void drawChart(final Context context, BarChart chart, BarData data) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineWidth(2);  // x ashix line size
        xAxis.setAxisLineColor(context.getResources().getColor(android.R.color.black));
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(textSize);
        xAxis.setTextColor(context.getResources().getColor(android.R.color.black));

        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setTextSize(textSize);
        yAxis.setTextColor(context.getResources().getColor(android.R.color.black));
        yAxis.setAxisLineColor(context.getResources().getColor(android.R.color.black));
        yAxis.setAxisLineWidth(2);  // y ashix line size

        data.setValueTextColor(context.getResources().getColor(android.R.color.black)); // bar data level
        data.setValueTextSize(6); // bar data size

        // bar data size

        chart.setData(data);
        chart.setDescription("");
        chart.invalidate();
        chart.notifyDataSetChanged();
        chart.setHorizontalScrollBarEnabled(true);
        chart.setVisibleXRange(GraphView.ViewSize);
        chart.getAxisLeft().setStartAtZero(false);
        chart.getAxisRight().setStartAtZero(false);
        chart.getAxisLeft().setAxisMinValue((float) 2.5);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(false);
    }
}
