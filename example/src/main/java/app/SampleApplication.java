package app;

import android.app.Application;

import com.codemonkeylabs.fpslibrary.TinyDancer;

/**
 * Created by mrsimple on 14/3/17.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 自动收集每个 Activity的 fps 数据, 并且dump到本地文件中
        TinyDancer.install(this);
    }
}
