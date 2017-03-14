package com.codemonkeylabs.fpslibrary.ui;

import android.animation.Animator;
import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.codemonkeylabs.fpslibrary.FPSConfig;
import com.codemonkeylabs.fpslibrary.FpsCalculator;
import com.codemonkeylabs.fpslibrary.R;

import java.util.AbstractMap;
import java.util.List;

public class TinyCoach {
    private FPSConfig fpsConfig;
    private View meterView;
    private final WindowManager windowManager;
    private int shortAnimationDuration = 200, longAnimationDuration = 700;

    // detect double tap so we can hide tinyDancer
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector
            .SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // hide but don't remove view
            hide(false);
            return super.onDoubleTap(e);
        }
    };

    public TinyCoach(Context context, FPSConfig config) {
        fpsConfig = config;
        //create meter view
        meterView = LayoutInflater.from(context).inflate(R.layout.meter_view, null);

        //set initial fps value....might change...
        ((TextView) meterView).setText((int) fpsConfig.refreshRate + "");
        // grab window manager and add view to the window
        windowManager = (WindowManager) meterView.getContext().getSystemService(Service.WINDOW_SERVICE);
        addViewToWindow(meterView);
    }

    private void addViewToWindow(View view) {
        WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        // configure starting coordinates
        paramsF.gravity = fpsConfig.startingGravity;
        paramsF.x = fpsConfig.startingXPosition;
        paramsF.y = fpsConfig.startingYPosition;

        // add view to the window
        windowManager.addView(view, paramsF);
        // create gesture detector to listen for double taps
        GestureDetector gestureDetector = new GestureDetector(view.getContext(), simpleOnGestureListener);
        // attach touch listener
        view.setOnTouchListener(new DancerTouchListener(paramsF, windowManager, gestureDetector));
        // disable haptic feedback
        view.setHapticFeedbackEnabled(false);
        // show the meter
        show();
    }


    public long showFps(FPSConfig fpsConfig, List<Long> dataSet) {
        List<Integer> droppedSet = FpsCalculator.getDroppedSet(fpsConfig, dataSet);
        AbstractMap.SimpleEntry<FpsCalculator.Metric, Long> answer = FpsCalculator.calculateMetric(fpsConfig, dataSet,
                droppedSet);

        if (answer.getKey() == FpsCalculator.Metric.BAD) {
            meterView.setBackgroundResource(R.drawable.fpsmeterring_bad);
        } else if (answer.getKey() == FpsCalculator.Metric.MEDIUM) {
            meterView.setBackgroundResource(R.drawable.fpsmeterring_medium);
        } else {
            meterView.setBackgroundResource(R.drawable.fpsmeterring_good);
        }

        ((TextView) meterView).setText(answer.getValue() + "");

        Log.e("", "####  tiny dancer fps : " + answer.getValue());
        // cache fps values
        return answer.getValue();
    }


    public void destroy() {
        meterView.setOnTouchListener(null);
        hide(true);
    }

    public void show() {
        meterView.setAlpha(0f);
        meterView.setVisibility(View.VISIBLE);
        meterView.animate().alpha(1f).setDuration(longAnimationDuration).setListener(null);
    }

    public void hide(final boolean remove) {
        meterView.animate().alpha(0f).setDuration(shortAnimationDuration).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                meterView.setVisibility(View.GONE);
                if (remove) {
                    windowManager.removeView(meterView);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }
}
