package com.codemonkeylabs.fpslibrary;

import android.view.Gravity;

import com.codemonkeylabs.fpslibrary.callback.DoFrameCallback;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by brianplummer on 8/29/15.
 */
public class FPSConfig implements Serializable {
    public float redFlagPercentage = 0.2f; //
    public float yellowFlagPercentage = 0.05f; //
    // making final for now.....want to be solid on the math before we allow an
    // arbitrary value
    public final long sampleTimeInMs = 1000;//928;//736; // default sample time
    public float refreshRate = 60; //60fps
    public float deviceRefreshRateInMs = 16.6f; //value from device ex 16.6 ms

    // starting coordinates
    public int startingXPosition = 200;
    public int startingYPosition = 600;
    public int startingGravity = Gravity.TOP | Gravity.START;

    // client facing callback that provides frame info
    public DoFrameCallback frameDataCallback = null;
    public boolean dumpFps = true ;


    protected FPSConfig() {
    }

    public long getSampleTimeInNs() {
        return TimeUnit.NANOSECONDS.convert(sampleTimeInMs, TimeUnit.MILLISECONDS);
    }

    public long getDeviceRefreshRateInNs() {
        float value = deviceRefreshRateInMs * 1000000f;
        return (long) value;
    }
}
