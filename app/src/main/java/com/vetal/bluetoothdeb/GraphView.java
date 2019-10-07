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
    public static double[][] masss = {{3.57,4},{3.54,4},{3.51,3},{3.49,3},{3.51,4},{3.68,4},{3.57,2},{3.54,3},{3.51,1},{3.49,3},{3.51,4},{3.68,4}};
    public float[][] ArrayBattery2;

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
        for (int i = 1; i < 10; i++) {
            dataListVoltage.add((float) ArrayBattery2[i][1]);
            if (ArrayBattery2[i][1] > 0) {
                med += ArrayBattery2[i][1];
                del++;
            }
        }
    }
    public void addListTmp(){
        medtmp = 0;
        deltmp = 0;
        dataListTmp = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            dataListTmp.add(ArrayBattery2[i][2]);
            if (ArrayBattery2[i][0] > 0) {
                medtmp += ArrayBattery2[i][2];
                deltmp++;
            }
        }
    }
 public void Graph(){
     runOnUiThread(new Runnable() {
         @Override
         public void run() {
     BarData barDataVoltage = new BarData(MPUtil.getXAxisValues(10), MPUtil.getDataSet(GraphView.this, dataListVoltage));
     MPUtil.drawChart(GraphView.this, chartvoltage, barDataVoltage);

     BarData barDataTmp = new BarData(MPUtil.getXAxisValues(10), MPUtil.getDataSetTmp(GraphView.this, dataListTmp));
     MPUtil.drawChart(GraphView.this, charttmp, barDataTmp);
         }
     });
    /* try {
         Thread.sleep(4000);
     } catch (InterruptedException e) {
         //Error
     }*/
 }

  class Draw extends Thread {
      public void run() {
          while (true) {
              ArrayBattery2 = ArrayBattery.clone();
              addListVoltage();
              addListTmp();
              Graph();

              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  //Error
              }
              for (int i = 1; i < ArrayBattery2.length; i++) {
                  if (ArrayBattery2[i][1] > 0) {
                      if (ArrayBattery2[i][3] == 0)
                          ArrayBattery2[i][3]++;
                      else if (ArrayBattery2[i][3] == 1)
                          ArrayBattery2[i][3]++;
                      else if (ArrayBattery2[i][3] == 2)
                          ArrayBattery2[i][3]++;
                      else if (ArrayBattery2[i][3] == 3)
                          ArrayBattery2[i][3]++;
                  }
              }
            /*  for (int i = 1; i < ArrayBattery.length; i++) {
                  dataListTmp.add(ArrayBattery[i][2]);
                  if (ArrayBattery[i][0] > 0) {
                      ArrayBattery[i][3]--;
                  }*/
            //  }
          }
          }
      }
}
