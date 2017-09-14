package com.example.yash.resourcify;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Yash on 4/11/2016.
 */
public class MyCustomFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public MyCustomFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return mFormat.format(value) + " ₹"; // e.g. append a dollar-sign
    }
}