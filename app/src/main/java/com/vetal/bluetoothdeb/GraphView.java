package com.vetal.bluetoothdeb;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends MainActivity {

    private BarChart chart;
    Thread myThreadGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        chart = (BarChart) findViewById(R.id.chart);

        Graph();

        myThreadGraph = new Draw();
        myThreadGraph.start();
    }
 public void Graph(){
     final List<Float> dataList = new ArrayList<>();
     for (int i = 1; i < 5; i++) {
         dataList.add( (ArrayBattery[i][1]));
     }

     runOnUiThread(new Runnable() {
         @Override
         public void run() {
     BarData barData2 = new BarData(MPUtil.getXAxisValues(20), MPUtil.getDataSet(GraphView.this, dataList));
     MPUtil.drawChart(GraphView.this, chart, barData2);
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
