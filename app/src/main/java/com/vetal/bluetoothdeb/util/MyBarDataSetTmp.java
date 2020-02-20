package com.vetal.bluetoothdeb.util;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vetal.bluetoothdeb.GraphView;
import com.vetal.bluetoothdeb.MainActivity;

import java.util.List;

public class MyBarDataSetTmp extends BarDataSet {


    MyBarDataSetTmp(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }
    // тут для графика температуры получаем цвет отображения.
    @Override
    public int getColor(int index) {
        float indexVal = getEntryForXIndex(index).getVal();

        switch ((int) MainActivity.ArrayBattery2[index + 1][3]) {
            case 0:
                if (indexVal > 0) {
                    if (indexVal <= (GraphView.medtmp / GraphView.del + 3) && indexVal >= (GraphView.medtmp / GraphView.del - 3))
                        if (indexVal == GraphView.mintmp)
                            return mColors.get(5);
                        else if (indexVal == GraphView.maxtmp) {
                            return mColors.get(6);
                        } else
                            return mColors.get(1);
                    else if (indexVal < (GraphView.medtmp / GraphView.del)) {
                        MainActivity.ArrayBattery2[index + 1][5] = 1;
                        return mColors.get(9);
                    } else if (indexVal > GraphView.medtmp / GraphView.del) {
                        MainActivity.ArrayBattery2[index + 1][5] = 2;
                        return mColors.get(9);
                    }
                }
                break;
            case 1:
                if (indexVal > 0) {
                    if (indexVal <= (GraphView.medtmp / GraphView.del + 3) && indexVal >= (GraphView.medtmp / GraphView.del - 3))
                        if (indexVal == GraphView.mintmp)
                            return mColors.get(5);
                        else if (indexVal == GraphView.maxtmp) {
                            return mColors.get(6);
                        } else
                            return mColors.get(1);
                    else if (indexVal < (GraphView.medtmp / GraphView.del)) {
                        MainActivity.ArrayBattery2[index + 1][5] = 1;
                        return mColors.get(9);
                    } else if (indexVal > GraphView.medtmp / GraphView.del) {
                        MainActivity.ArrayBattery2[index + 1][5] = 2;
                        return mColors.get(9);
                    }
                }
                break;
            case 2:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][5] == 0)
                        return mColors.get(2);
                    else if (MainActivity.ArrayBattery2[index + 1][5] == 2)
                        return mColors.get(10);
                break;
            case 3:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][5] == 0)
                        return mColors.get(3);
                    else if (MainActivity.ArrayBattery2[index + 1][5] == 2)
                        return mColors.get(11);
                break;
            case 4:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][5] == 0)
                        return mColors.get(4);
                    else if (MainActivity.ArrayBattery2[index + 1][5] == 2)
                        return mColors.get(12);
                break;
        }

        return index;
    }
}
