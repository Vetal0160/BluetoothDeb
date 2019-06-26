package com.vetal.bluetoothdeb;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphView extends MainActivity {

    private BarChart chart;
    Thread myThreadGraph;
    XYSeriesRenderer mCurrentRenderer, mCurrentRenderer2;
    public double[] mass = {3.5,3.4,3.6,3.2,3.9,3.3};
    float med = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

<<<<<<< HEAD
        chart = (BarChart) findViewById(R.id.chart);
        Graph();
        myThreadGraph = new Draw();
=======
    protected void setup() {
        layout = (LinearLayout) findViewById(R.id.LinearLayout);
        BtnGr = (Button) findViewById(R.id.Back);
        if (mChart == null) {
                initChart();
                initChart2();
           // addSampleData();
            mChart = ChartFactory.getBarChartView(this, mDataset, mRenderer, BarChart.Type.DEFAULT);
            layout.addView(mChart);
        } else {
            mChart.repaint();
        }
        myThreadGraph = new Graph();
>>>>>>> 183587753fc24632feb3dd0c8f1b2e6b4e1c218a
        myThreadGraph.start();
    }
<<<<<<< HEAD
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
/*
                if (ArrayBattery[1][1] != 0) {
=======
        public void initChart() {
            mCurrentSeries = new XYSeries("");
            mDataset.addSeries(mCurrentSeries);
            mCurrentRenderer = new XYSeriesRenderer();
            mCurrentRenderer.setChartValuesTextSize(20);
            mCurrentRenderer.setColor(Color.BLACK);
            mCurrentRenderer.setChartValuesTextAlign(Paint.Align.CENTER);
            mCurrentRenderer.setFillPoints(false);
            mCurrentRenderer.setLineWidth(5);

          /*  mCurrentRenderer2 = new XYSeriesRenderer();
            mCurrentRenderer2.setChartValuesTextSize(20);
            mCurrentRenderer2.setColor(Color.RED);
            mCurrentRenderer2.setChartValuesTextAlign(Paint.Align.CENTER);
            mCurrentRenderer2.setFillPoints(false);
            mCurrentRenderer2.setLineWidth(5);*/

           /*   *//*  if (ArrayBattery[1][1] != 0) {
>>>>>>> 183587753fc24632feb3dd0c8f1b2e6b4e1c218a
                    if (ArrayBattery[1][1] <= 3.5) {
                        mCurrentRenderer.setColor(Color.BLACK);
                        mCurrentSeries.add(ArrayBattery[1][0]+1, ArrayBattery[1][1]);
                        if (mChart != null) {
                            mChart.repaint();//обновление граф.
                        }
                    } else {
                        mCurrentRenderer.setColor(Color.RED);
                        mCurrentSeries.add(ArrayBattery[1][0]+1, ArrayBattery[1][1]);
                        if (mChart != null) {
                            mChart.repaint();//обновление граф.
                        }
                    }*//*
            }*/
        }
    public void initChart2() {
        mRenderer.addSeriesRenderer(mCurrentRenderer);
     //   mRenderer.addSeriesRenderer(mCurrentRenderer2);
        mRenderer.setBarSpacing(1);
        mRenderer.setXLabels(30);
        mRenderer.setYLabels(30);
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(60);
        mRenderer.setYAxisMin(2);
        mRenderer.setYAxisMax(4.5);
        mRenderer.setLabelsTextSize(30);
        mRenderer.setYLabelsColor(0, Color.RED);
        mRenderer.setXLabelsColor(Color.RED);
        mRenderer.setYAxisAlign(Paint.Align.LEFT, 0);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT, 0);
        mRenderer.setShowGridY(true);
        mRenderer.setShowGridX(true);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setMargins(new int[]{0, 50, 0, 50});
        mRenderer.setPanLimits(new double[]{0, 160, 2, 5});
        mRenderer.setZoomLimits(new double[]{0, 160, 2, 5});
        mRenderer.setClickEnabled(true);
        mRenderer.setShowLegend(false);
        mRenderer.setBarWidth(30);
       // mRenderer.getSeriesRendererAt(0).setGradientEnabled(true);
    }
    public void initColor(int number) {
        if (mass[number] != 0) {
            if (mass[number] <= med/6) {
             //   initChart2();
              //  mCurrentRenderer.setColor(Color.BLACK);
              //  mRenderer.getSeriesRendererAt(0).setGradientStart(med/6, Color.BLACK);
              //  mRenderer.getSeriesRendererAt(0).setGradientStop(med/6, Color.BLUE);
                mCurrentSeries.add(number, mass[number]);
                if (mChart != null) {
                    mChart.repaint();//обновление граф.
                }
            } else {
              //  initChart2();
              //  mCurrentRenderer.setColor(Color.RED);
               // mRenderer.getSeriesRendererAt(0).setGradientStart(med/6, Color.GREEN);
               // mRenderer.getSeriesRendererAt(0).setGradientStop(med/6, Color.RED);
                mCurrentSeries.add(number, mass[number]);
                if (mChart != null) {
                    mChart.repaint();//обновление граф.
                }
            }
        }
<<<<<<< HEAD
        */
  class Draw extends Thread {
      public void run() {
          while (true) {
              Graph();
          }
=======
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Error
        }
    }
    public float GetMed(double data[]){
        for (int i = 0; i<mass.length; i++){
            med += data[i];
        }
        return med;
    }
  class Graph extends Thread {
      public void run() {
          while (true) {
              GetMed(mass);
              for (int  i =0; i<6; i++) {
                  initChart2();
                  initColor(i);
              }
              med = 0;
              if (mDataset != null) {
              mDataset.clear();
              initChart();
              }
>>>>>>> 183587753fc24632feb3dd0c8f1b2e6b4e1c218a
          }
      }
}
