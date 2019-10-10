package com.vetal.bluetoothdeb;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.vetal.bluetoothdeb.util.MPUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphView extends MainActivity {

    List<Float> dataListVoltage,dataListTmp;

    private BarChart chartvoltage,charttmp;
    Thread myThreadGraph;
    public static float med,medtmp;
    public static int del,deltmp;
    public static int ViewSize = 60;
    Button BtnPAUSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.graph_view);
     //   BtnPAUSE = (Button) findViewById(R.id.btnPAUSE);
        chartvoltage = (BarChart) findViewById(R.id.chart);
        charttmp = (BarChart) findViewById(R.id.charttmp);

        //    Graph();
        ArrayBattery2 = ArrayBattery.clone();
        myThreadGraph = new Draw();
        myThreadGraph.start();
      /*  ChangeThread = new Change();
        ChangeThread.start();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        TextView infoTextView = (TextView) findViewById(R.id.textView);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.pause:
                infoTextView.setText("1");
                return true;
            case R.id.settings:
                infoTextView.setText("2");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addListVoltage(){
        med = 0;
        del = 0;
        dataListVoltage = new ArrayList<>();
        for (int i = 1; i < ArrayBattery2.length; i++) {
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
        for (int i = 1; i < ArrayBattery2.length; i++) {
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
     BarData barDataVoltage = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSet(GraphView.this, dataListVoltage));
     MPUtil.drawChart(GraphView.this, chartvoltage, barDataVoltage);

     BarData barDataTmp = new BarData(MPUtil.getXAxisValues(160), MPUtil.getDataSetTmp(GraphView.this, dataListTmp));
     MPUtil.drawChart(GraphView.this, charttmp, barDataTmp);
         }
     });
 }
    void Change()  {
        System.out.println("ВХОД " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        for (int i = 1; i < ArrayBattery.length; i++) {
            if (ArrayBattery[i][1] > 0) {
                ArrayBattery2[i][1] = ArrayBattery[i][1];
                ArrayBattery2[i][2] = ArrayBattery[i][2];
            }
        }
        for (int i = 1; i < ArrayBattery2.length; i++) {
            if (ArrayBattery2[i][1] > 0) {
                if (ArrayBattery2[i][3] == 0){
                    ArrayBattery2[i][3]++;}
                else if (ArrayBattery2[i][3] == 1)
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
  class Draw extends Thread {
      public void run() {
          while (true) {
              addListVoltage();
              addListTmp();
              Graph();
              System.out.println("ДО ПАУЗЫ " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
              try {
                  Thread.sleep(3990);
              } catch (InterruptedException e) {
                  //Error
              }
              System.out.println("ПОСЛЕ ПАУЗЫ " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
              Change();
          }
      }
  }
}

