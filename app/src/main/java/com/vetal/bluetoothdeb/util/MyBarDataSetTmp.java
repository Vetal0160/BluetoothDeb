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

  ///  @Override
   /* public int getColor(int index) {
        float indexVal = getEntryForXIndex(index).getVal();

        if (indexVal <= GraphView.medtmp/GraphView.deltmp + 1 && indexVal >= GraphView.med/GraphView.del - 1)
            return mColors.get(0);
        else if (indexVal <= GraphView.medtmp/GraphView.deltmp + 2)
            return mColors.get(1);
        else if (indexVal >= GraphView.medtmp/GraphView.deltmp - 2)
            return mColors.get(3);

        return index;
    }*/
    @Override
    public int getColor(int index) {
        float indexVal = getEntryForXIndex(index).getVal();

        switch ((int) MainActivity.ArrayBattery[index + 1][3]) {
            case 0:
                if (indexVal > 0) {
                    //  MainActivity.ArrayBattery[index + 1][3]++;
                    if (indexVal <= (GraphView.medtmp / GraphView.del + 3) && indexVal >= (GraphView.medtmp / GraphView.del - 3))
                        return mColors.get(1);
                    else if (indexVal < (GraphView.medtmp / GraphView.del)) {
                        MainActivity.ArrayBattery[index + 1][5] = 1;
                        return mColors.get(5);
                    } else if (indexVal > GraphView.medtmp / GraphView.del) {
                        MainActivity.ArrayBattery[index + 1][5] = 2;
                        return mColors.get(9);
                    }
                }
                break;
            case 1:
                if (indexVal > 0) {
                    //  MainActivity.ArrayBattery[index + 1][3]++;
                    if (indexVal <= (GraphView.medtmp / GraphView.del + 3) && indexVal >= (GraphView.medtmp / GraphView.del - 3))
                        return mColors.get(1);
                    else if (indexVal < (GraphView.medtmp / GraphView.del)) {
                        MainActivity.ArrayBattery[index + 1][5] = 1;
                        return mColors.get(5);
                    } else if (indexVal > GraphView.medtmp / GraphView.del) {
                        MainActivity.ArrayBattery[index + 1][5] = 2;
                        return mColors.get(9);
                    }
                }
                break;
            case 2:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(2);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(6);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(10);
                break;
            case 3:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(2);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(6);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(10);
                break;
            case 4:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(3);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(7);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(11);
                break;
            case 5:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(3);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(7);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(11);
                break;
            case 6:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(4);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(8);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(12);
                break;
            case 7:
                if (indexVal > 0)
                    if (MainActivity.ArrayBattery[index + 1][5] == 0)
                        return mColors.get(4);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 1)
                        return mColors.get(8);
                    else if (MainActivity.ArrayBattery[index + 1][5] == 2)
                        return mColors.get(12);
                break;

        }

        return index;
    }
}
