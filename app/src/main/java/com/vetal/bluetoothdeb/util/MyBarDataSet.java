package com.vetal.bluetoothdeb.util;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vetal.bluetoothdeb.GraphView;
import com.vetal.bluetoothdeb.MainActivity;
import java.util.List;


public class MyBarDataSet extends BarDataSet {

    MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        float indexVal = getEntryForXIndex(index).getVal();

        switch ((int) MainActivity.ArrayBattery2[index + 1][3]) {
            case 0:
                if (indexVal > 0) {
                    if (indexVal <= (GraphView.med / GraphView.del + 0.05) && indexVal >= (GraphView.med / GraphView.del - 0.05)) {
                        if (indexVal == GraphView.min)
                            return mColors.get(5);
                        else  if (indexVal == GraphView.max){
                            return mColors.get(6);
                        }
                        else
                        return mColors.get(1);
                    }
                    else if (indexVal < (GraphView.med / GraphView.del)) {
                        MainActivity.ArrayBattery2[index + 1][4] = 1;
                        return mColors.get(9);
                    } else if (indexVal > GraphView.med / GraphView.del) {
                        MainActivity.ArrayBattery2[index + 1][4] = 1;
                        return mColors.get(9);
                    }
                }
                break;
            case 1:
                if (indexVal > 0) {
                    if (indexVal <= (GraphView.med / GraphView.del + 0.05) && indexVal >= (GraphView.med / GraphView.del - 0.05)) {
                        MainActivity.ArrayBattery2[index + 1][4] = 0;
                        if (indexVal == GraphView.min)
                            return mColors.get(5);
                        else  if (indexVal == GraphView.max){
                            return mColors.get(6);
                        }
                        else
                            return mColors.get(1);
                    }
                    else if (indexVal < (GraphView.med / GraphView.del)) {
                        MainActivity.ArrayBattery2[index + 1][4] = 1;
                        return mColors.get(9);
                    } else if (indexVal > GraphView.med / GraphView.del) {
                        MainActivity.ArrayBattery2[index + 1][4] = 1;
                        return mColors.get(9);
                    }
                }
                break;
            case 2:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][4] == 0)
                    return mColors.get(2);
                    else if (MainActivity.ArrayBattery2[index + 1][4] == 1)
                        return mColors.get(10);
                break;
            case 3:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][4] == 0)
                        return mColors.get(3);
                    else if (MainActivity.ArrayBattery2[index + 1][4] == 1)
                        return mColors.get(11);
                break;
            case 4:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery2[index + 1][4] == 0)
                        return mColors.get(4);
                    else if (MainActivity.ArrayBattery2[index + 1][4] == 1)
                        return mColors.get(12);
                break;
        }
        return index;
    }
}