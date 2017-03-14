package com.codemonkeylabs.fpslibrary;

import android.content.Context;
import android.util.Log;

import com.codemonkeylabs.fpslibrary.callback.DoFrameCallback;

public class TinyDancer {
    /**
     * create a TinyDancer instance
     * @param context
     * @return
     */
    public static TinyDancer create(Context context){
        return create(context, "");
    }

    /**
     * create a TinyDancer for specified activity
     * @param context
     * @param activityName
     * @return
     */
    public static TinyDancer create(Context context, String activityName){
        TinyDancer instance = new TinyDancer();
        Log.e("", "### create noop tinydancer") ;
        return instance;
    }


    /**
     * stops the frame callback and foreground listener
     * nulls out static variables
     * called from FPSLibrary in a static context
     *
     */
    public void hide() {
    }

    // PUBLIC BUILDER METHODS

    /**
     * show fps meter, this regisers the frame callback that
     * collects the fps info and pushes it to the ui
     *
     */
    public void show() {
    }


    /**
     *  show fps meter, this regisers the frame callback that
     * collects the fps info only .
     */
    public void start() {
    }


    /**
     * @return
     */
    public FpsData getFpsData() {
        return new FpsData();
    }


    /**
     * this adds a frame callback that the library will invoke on the
     * each time the choreographer calls us, we will send you the frame times
     * and number of dropped frames.
     *
     * @param callback
     * @return
     */
    public TinyDancer addFrameDataCallback(DoFrameCallback callback) {
        return this;
    }

    /**
     * set red flag percent, default is 20%
     *
     * @param percentage
     * @return
     */
    public TinyDancer redFlagPercentage(float percentage) {
        return this;
    }

    /**
     * set red flag percent, default is 5%
     *
     * @param percentage
     * @return
     */
    public TinyDancer yellowFlagPercentage(float percentage) {
        return this;
    }

    /**
     * starting x position of fps meter default is 200px
     *
     * @param xPosition
     * @return
     */
    public TinyDancer startingXPosition(int xPosition) {
        return this;
    }

    /**
     * starting y positon of fps meter default is 600px
     *
     * @param yPosition
     * @return
     */
    public TinyDancer startingYPosition(int yPosition) {
        return this;
    }

    /**
     * starting gravity of fps meter default is Gravity.TOP | Gravity.START;
     *
     * @param gravity
     * @return
     */
    public TinyDancer startingGravity(int gravity) {
        return this;
    }

    public void stop() {
    }

    public void resume() {
    }

    public void destroy() {
    }
}
