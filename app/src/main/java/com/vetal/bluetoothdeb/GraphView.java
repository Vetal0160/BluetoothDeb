package com.vetal.bluetoothdeb;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends MainActivity {

    List<Float> dataListVoltage,dataListTmp;

    private BarChart chartvoltage,charttmp;
    Thread myThreadGraph;
    public static float med,medtmp;
    public static int del,deltmp;
    public double[] masss = {3.5,3.51,3.49,3.51,3.68,3.52,3.50,3.47,3.5,3.51,3.39,3.51,3.48,3.52,3.50,3.47,
            3.5,3.51,3.49,3.61,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.58,3.52,3.50,3.47,
            3.5,3.31,3.49,3.51,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.48,3.32,3.50,3.47,
            3.5,3.51,3.5,3.51,3.49,3.51,3.68,3.52,3.50,3.47,3.5,3.51,3.39,3.51,3.48,3.52,3.50,3.47,
            3.5,3.51,3.49,3.61,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.58,3.52,3.50,3.47,
            3.5,3.31,3.49,3.51,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.48,3.32,3.50,3.47,
            3.5,3.51,3.5,3.51,3.49,3.51,3.68,3.52,3.50,3.47,3.5,3.51,3.39,3.51,3.48,3.52,3.50,3.47,
            3.5,3.51,3.49,3.61,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.58,3.52,3.50,3.47,
            3.5,3.31,3.49,3.51,3.48,3.52,3.50,3.47,3.5,3.51,3.49,3.51,3.48,3.32,3.50,3.47,
            3.5,3.51,3.5,3.51,3.49,3.51,3.68,3.52,3.50,3.47,3.5,3.51};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        chartvoltage = (BarChart) findViewById(R.id.chart);
        charttmp = (BarChart) findViewById(R.id.charttmp);

    //    Graph();

        myThreadGraph = new Draw();
        myThreadGraph.start();
    }
    public void addListVoltage(){
        med = 0;
        del = 0;
        dataListVoltage = new ArrayList<>();
        for (int i = 1; i < ArrayBattery.length; i++) {
            dataListVoltage.add(ArrayBattery[i][1]);
            if (ArrayBattery[i][0] > 0) {
                med += ArrayBattery[i][1];
                del++;
            }
        }
    }
    public void addListTmp(){
        medtmp = 0;
        deltmp = 0;
        dataListTmp = new ArrayList<>();
        for (int i = 1; i < ArrayBattery.length; i++) {
            dataListTmp.add(ArrayBattery[i][2]);
            if (ArrayBattery[i][0] > 0) {
                medtmp += ArrayBattery[i][2];
                deltmp++;
            }
        }
    }
 public void Graph(){
     runOnUiThread(new Runnable() {
         @Override
         public void run() {
     BarData barDataVoltage = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSet(GraphView.this, dataListVoltage));
     MPUtil.drawChart(GraphView.this, chartvoltage, barDataVoltage);

     BarData barDataTmp = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSetTmp(GraphView.this, dataListTmp));
     MPUtil.drawChart(GraphView.this, charttmp, barDataTmp);
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
              addListVoltage();
              addListTmp();
              Graph();
          }
          }
      }
}
