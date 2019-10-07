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
        switch ((int) MainActivity.ArrayBattery[index+1][3]) {
            case 0:
                if (indexVal > 0) {
                  //  MainActivity.ArrayBattery[index + 1][3]++;
                    if (indexVal <= (GraphView.med / GraphView.del + 0.05) && indexVal >= (GraphView.med / GraphView.del - 0.05))
                        return mColors.get(0);
                    else if (indexVal <= (GraphView.med / GraphView.del + 0.07))
                        return mColors.get(1);
                    else if (indexVal >= GraphView.med / GraphView.del - 0.07)
                        return mColors.get(2);
                }
                break;
            case 1:
                if (indexVal > 0) {
                    //  MainActivity.ArrayBattery[index + 1][3]++;
                    if (indexVal <= (GraphView.med / GraphView.del + 0.05) && indexVal >= (GraphView.med / GraphView.del - 0.05))
                        return mColors.get(0);
                    else if (indexVal <= (GraphView.med / GraphView.del + 0.07))
                        return mColors.get(1);
                    else if (indexVal >= GraphView.med / GraphView.del - 0.07)
                        return mColors.get(2);
                }
                break;
            case 2:
                if (indexVal > 0) {
                    return mColors.get(3);
                }
                break;
            case 3:
                if (indexVal > 0) {
                    return mColors.get(4);
                }
                break;
            case 4:
                if (indexVal > 0) {
                    return mColors.get(5);
                }
                break;
        }

        return  index;
    }
}
