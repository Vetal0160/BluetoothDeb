package com.vetal.bluetoothdeb.util;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vetal.bluetoothdeb.GraphView;

import java.util.List;

public class MyBarDataSetTmp extends BarDataSet {


    MyBarDataSetTmp(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        float indexVal = getEntryForXIndex(index).getVal();

        if (indexVal <= GraphView.medtmp/GraphView.deltmp + 1 && indexVal >= GraphView.med/GraphView.del - 1)
            return mColors.get(0);
        else if (indexVal <= GraphView.medtmp/GraphView.deltmp + 2)
            return mColors.get(1);
        else if (indexVal >= GraphView.medtmp/GraphView.deltmp - 2)
            return mColors.get(3);

        return index;
    }
}
