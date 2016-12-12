package com.codemonkeylabs.fpslibrary;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * fps data object
 * Created by mrsimple on 12/12/16.
 */
public class FpsData {
    private List<Long> mDataSet = new LinkedList<>();
    private long mMax;
    private long mMin;
    private long mAverage;

    public FpsData() {
        this(Collections.EMPTY_LIST);
    }

    public FpsData(List<Long> dataSet) {
        mDataSet.addAll(dataSet);
        mMax = Collections.max(mDataSet);
        mMin = Collections.min(mDataSet);
        long sum = 0;
        for (Long value : mDataSet) {
            sum += value;
        }
        mAverage = (int) (sum / mDataSet.size());
    }

    public List<Long> getDataSet() {
        return mDataSet;
    }

    public long getMax() {
        return mMax;
    }

    public long getMin() {
        return mMin;
    }

    public long getAverage() {
        return mAverage;
    }

}
