package com.vetal.bluetoothdeb;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphView extends MainActivity {

    private BarChart chart,charttmp;
    Thread myThreadGraph;
    public static float med;
    public static int del;
    public double[] masss = {3.5,3.51,3.49,3.51,3.68,3.52,3.50,3.47,3.5,3.51,3.39,3.51,3.48,3.52,3.50,3.47,
            3.5,3.51,3.49,3.61,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.58,3.52,3.50,3.47,
            3.5,3.31,3.49,3.51,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.48,3.32,3.50,3.47,
            3.5,3.51};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        chart = (BarChart) findViewById(R.id.chart);
        charttmp = (BarChart) findViewById(R.id.charttmp);

        Graph();

        myThreadGraph = new Draw();
        myThreadGraph.start();
    }
 public void Graph(){
     med = 0;
     del = 0;
     final List<Float> dataList = new ArrayList<>();
     for (int i = 1; i < masss.length; i++) {
         dataList.add((float) masss[i]);
         if (masss[i] > 0) {
             med += masss[i];
             del++;
         }
     }

     runOnUiThread(new Runnable() {
         @Override
         public void run() {
     BarData barData2 = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSet(GraphView.this, dataList));
     MPUtil.drawChart(GraphView.this, chart, barData2);
             BarData barData3 = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSet(GraphView.this, dataList));
             MPUtil.drawChart(GraphView.this, charttmp, barData3);
         }
     });
     try {
         Thread.sleep(4000);
     } catch (InterruptedException e) {
         //Error
     }
 }

  class Draw extends Thread {
      public void run() {
          while (true) {
              Graph();
          }
          }
      }
}
