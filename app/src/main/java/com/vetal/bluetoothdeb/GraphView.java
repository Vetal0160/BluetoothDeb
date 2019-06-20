package com.vetal.bluetoothdeb;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class GraphView extends MainActivity {

    public GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    LinearLayout layout;
    Button BtnGr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);
        setup();
    }

    protected void setup() {
        layout = (LinearLayout) findViewById(R.id.LinearLayout);
        BtnGr = (Button) findViewById(R.id.Back);
        if (mChart == null) {
                initChart();
           // addSampleData();
            mChart = ChartFactory.getBarChartView(this, mDataset, mRenderer, BarChart.Type.DEFAULT);
            layout.addView(mChart);
        } else {
            mChart.repaint();
        }
        mChart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(GraphView.this, "ON CLICK", Toast.LENGTH_SHORT).show();
                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(GraphView.this, "Пусто", Toast.LENGTH_SHORT)
                            .show();
                } else {

                    Toast.makeText(
                            GraphView.this,
                            " №=" + ((int) seriesSelection.getXValue()) + " : Volt=" + seriesSelection.getValue()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
        View.OnClickListener oclBtnGr = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderer.removeAllRenderers();
                initChart();
                mChart.repaint();
               // finish();
            }
        };
        BtnGr.setOnClickListener(oclBtnGr);
    }
        public  void initChart() {
            mDataset.clear();
            mCurrentSeries = new XYSeries("");
            mDataset.addSeries(mCurrentSeries);
            XYSeriesRenderer mCurrentRenderer = new XYSeriesRenderer();
            mCurrentRenderer.setChartValuesTextSize(20);
            /*mCurrentRenderer.setColor(Color.BLACK);*/
            mCurrentRenderer.setChartValuesTextAlign(Paint.Align.CENTER);
            mCurrentRenderer.setFillPoints(false);
            mCurrentRenderer.setLineWidth(5);

            mRenderer.addSeriesRenderer(mCurrentRenderer);
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
            mRenderer.setBarSpacing(0.15);
            mRenderer.setPanLimits(new double[]{0, 160, 2, 5});
            mRenderer.setZoomLimits(new double[]{0, 160, 2, 5});
            mRenderer.setClickEnabled(true);
            mRenderer.setShowLegend(false);
            mRenderer.setBarWidth(30);
                    for (int i = 1; i < 160; i++) {
                        if (ArrayBattery[i][1] != 0) {
                            if (ArrayBattery[1][1] <= 3.5) {

                                mCurrentRenderer.setColor(Color.BLACK);
                                mCurrentSeries.add(ArrayBattery[i][0], ArrayBattery[i][1]);
                            } else {
                                mCurrentRenderer.setColor(Color.RED);
                                mCurrentSeries.add(ArrayBattery[i][0], ArrayBattery[i][1]);
                            }
                        }
                    }
    }
  /*  public void addSampleData() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i < 160; i++)
                  *//*  if (ArrayBattery[i][1]<= 3.5){
                        mCurrentRenderer.setColor(Color.BLACK);
                    }*//*
                    mCurrentSeries.add(ArrayBattery[i][0], ArrayBattery[i][1]);
            }
        });

    }*/
}
