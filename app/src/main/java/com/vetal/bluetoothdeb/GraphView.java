package com.vetal.bluetoothdeb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;
import com.vetal.bluetoothdeb.util.Setup;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class GraphView extends MainActivity {

    List<Float> dataListVoltage, dataListTmp;

    private Menu mMenuItem;
    private BarChart chartvoltage, charttmp;
    private static boolean keepRunning = true;

    public static int del, deltmp;
    public static int ViewSize = 160;
    public static int DataSize = 160;
    public static float min, max, mintmp, maxtmp, med, medtmp;
    public static float AxisMinValue = 2;
    public static float AxisMaxValue = 4;
    public static float AxisMinValueTmp = 0;
    public static float AxisMaxValueTmp = 35;

    boolean pausegraph;

    Thread myThreadGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        ArrayBattery2 = ArrayBattery.clone();
        myThreadGraph = new Draw();
        myThreadGraph.start();
        keepRunning = true;

        chartvoltage = (BarChart) findViewById(R.id.chart);
        charttmp = (BarChart) findViewById(R.id.charttmp);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        System.out.println("destroy");
        super.onDestroy();
        myThreadGraph.interrupt();
        cancelThread();
    }

    @Override
    protected void onPause() {
        System.out.println("pause");
        super.onPause();
        myThreadGraph.interrupt();
        cancelThread();
    }
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        mMenuItem = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        TextView infoTextView = (TextView) findViewById(R.id.textView);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menu_pause: {
                if (!pausegraph) {
                    mMenuItem.getItem(0).setIcon(R.drawable.play);
                    pausegraph = true;
                    break;
                } else {
                    mMenuItem.getItem(0).setIcon(R.drawable.pause);
                    pausegraph = false;
                    break;
                }
            }
            case R.id.settings:
                Intent intent = new Intent(GraphView.this, Setup.class);
                startActivity(intent);
                cancelThread();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    void cancelThread() {
        keepRunning = false;
    }

    public void addListVoltage() {
        med = 0;
        del = 0;
        dataListVoltage = new ArrayList<>();
        for (int i = 1; i < ArrayBattery2.length; i++) {
            dataListVoltage.add(ArrayBattery2[i][1]);
            if (ArrayBattery2[i][1] > 0) {
                if (ArrayBattery2[i][3] < 2) {
                    med += ArrayBattery2[i][1];
                    del++;

                }
            }
        }
    }

    public void addListTmp() {
        medtmp = 0;
        deltmp = 0;
        dataListTmp = new ArrayList<>();
        for (int i = 1; i < ArrayBattery2.length; i++) {
            dataListTmp.add(ArrayBattery2[i][2]);
            if (ArrayBattery2[i][2] > 0) {
                if (ArrayBattery2[i][3] < 2) {
                    medtmp += ArrayBattery2[i][2];
                    deltmp++;

                }
            }
        }
    }
    // Вывод графика на экран
    public void Graph() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!pausegraph) {
                    ArrayBattery2 = ArrayBattery.clone();
                    BarData barDataVoltage = new BarData(MPUtil.getXAxisValues(getDataSize()), MPUtil.getDataSet(GraphView.this, dataListVoltage));
                    MPUtil.drawChart(GraphView.this, chartvoltage, barDataVoltage);

                    BarData barDataTmp = new BarData(MPUtil.getXAxisValues(getDataSize()), MPUtil.getDataSetTmp(GraphView.this, dataListTmp));
                    MPUtil.drawChartTMP(GraphView.this, charttmp, barDataTmp);
                }
            }
        });
    }
    void Change() {
        min = 5;
        max = 0;
        mintmp = 100;
        maxtmp = 0;
        // получаем мин и макс значения
        for (int i = 1; i < ArrayBattery.length; i++) {
            if (ArrayBattery[i][1] > 0) {
                ArrayBattery2[i][1] = ArrayBattery[i][1];
                ArrayBattery2[i][2] = ArrayBattery[i][2];
                if (ArrayBattery2[i][1] < min) {
                    min = ArrayBattery2[i][1];
                }
                if (ArrayBattery2[i][1] > max) {
                    max = ArrayBattery2[i][1];
                }
                if (ArrayBattery2[i][2] < mintmp) {
                    mintmp = ArrayBattery2[i][2];
                }
                if (ArrayBattery2[i][2] > maxtmp) {
                    maxtmp = ArrayBattery2[i][2];
                }
                setAxisMinValueTmp(mintmp / 2);
            }
        }
        // каждый раз когда мы получили данные с балансировки ,ArrayBattery2[i][3] == 0, если данные не получили
        // когда каждые 4 секунды к ArrayBattery2[i][3] прибавляем 1 , от этого зависит цвет отображения ,чем больше число тем прозрачнее цвет
        for (int i = 1; i < ArrayBattery2.length; i++) {
            if (ArrayBattery2[i][1] > 0) {
                if (ArrayBattery2[i][3] == 0) {
                    ArrayBattery2[i][3]++;
                } else if (ArrayBattery2[i][3] == 1)
                    ArrayBattery2[i][3]++;
                else if (ArrayBattery2[i][3] == 2)
                    ArrayBattery2[i][3]++;
                else if (ArrayBattery2[i][3] == 3)
                    ArrayBattery2[i][3]++;
                else if (ArrayBattery2[i][3] == 4)
                    ArrayBattery2[i][3]++;
            }
        }
    }

    public static void setAxisMinValue(float axisMinValue) {
        AxisMinValue = axisMinValue;
    }

    public static float getAxisMinValue() {
        return AxisMinValue;
    }

    public static void setAxisMaxValue(float axisMaxValue) {
        AxisMaxValue = axisMaxValue;
    }

    public static float getAxisMinValueTmp() {
        return AxisMinValueTmp;
    }

    public static void setAxisMinValueTmp(float axisMinValueTmp) {
        AxisMinValueTmp = axisMinValueTmp;
    }

    public static float getAxisMaxValueTmp() {
        return AxisMaxValueTmp;
    }

    public static void setAxisMaxValueTmp(float axisMaxValueTmp) {
        AxisMaxValueTmp = axisMaxValueTmp;
    }

    public static float getAxisMaxValue() {
        return AxisMaxValue;
    }

    public static int getViewSize() {
        return ViewSize;
    }

    public static void setViewSize(int viewSize) {
        ViewSize = viewSize;
    }

    public static int getDataSize() {
        return DataSize;
    }

    public static void setDataSize(int dataSize) {
        DataSize = dataSize;
    }
// ============================================================================
    // поток который строит график
    public class Draw extends Thread {

        public void run() {
            while (keepRunning) {
                addListVoltage();
                addListTmp();
                Graph();
                try {
                    Thread.sleep(3990);
                } catch (InterruptedException e) {
                    //Error
                }
                Change();
            }
        }
    }
}


