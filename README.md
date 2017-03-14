# Tiny Dancer [![Build Status](https://travis-ci.org/friendlyrobotnyc/TinyDancer.svg?branch=master)](https://travis-ci.org/friendlyrobotnyc/TinyDancer)

A real time frames per second measuring library for Android that also shows a color coded metric.  This metric is based on percentage of time spent when you have dropped 2 or more frames.  If the application spends more than 5% in this state then the color turns yellow, when you have reached the 20% threshold the indicator turns red.  


## Min SDK
Tiny Dancer min sdk is API 16

## Getting started


In your `DebugApplication` class:

```java
public class DebugApplication extends Application {

  @Override public void onCreate() {

   // 自动收集每个 Activity的 fps 数据, 并且dump到本地文件中
   TinyDancer.install(this);

  // 创建 TinyDancer
   TinyDancer.create(this)
             .show();
             
   //alternatively
   TinyDancer.create(this)
      .redFlagPercentage(.1f) // set red indicator for 10%
      .startingGravity(Gravity.TOP)
      .startingXPosition(200)
      .startingYPosition(600)
      .show(this);
      

   //you can add a callback to get frame times and the calculated
   //number of dropped frames within that window
   TinyDancer.create(this)
       .addFrameDataCallback(new FrameDataCallback() {
          @Override
          public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
             //collect your stats here
          }
        })
        .show();
  }
}
```

**You're good to go!** Tiny Dancer will show a small draggable view overlay with FPS as well as a color indicator of when FPS drop.  You can double tap the overlay to explicitly hide it.


See sample application that simulates excessive bind time:

![Tiny Dancer Sample](assets/tinydancer1.gif "Tiny Dancer Sample")

Have an project with performance issues? We'd be happy to help tune it.  
