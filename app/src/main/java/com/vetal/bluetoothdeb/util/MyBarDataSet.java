package com.vetal.bluetoothdeb.util;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;


public class MyBarDataSet extends BarDataSet {


    MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index)
    {
        float indexVal = getEntryForXIndex(index).getVal();

        if (indexVal <= 3.45)
            return mColors.get(0);
        else if (indexVal > 3.46 && indexVal <= 3.5)
            return mColors.get(1);
        else if (indexVal > 3.51 && indexVal <= 3.55)
            return mColors.get(2);
        else
            return mColors.get(3);
    }

}

