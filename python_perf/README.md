# 性能测试python脚本.

> 基本原理:     
> 在操作应用的过程中，通过 adb shell dumpsys gfxinfo <应用包名> 会将该应用128帧的绘制耗时输出来，我们通过 collect_perf_data.py 脚本每隔2秒dump一次数据(120帧), 这些数据会追加到同一个文件中。结束数据采集之后，我们通过 analysis.py 脚本分析每次dump得到每帧的平均耗时，从而可以得到帧率;  例如在新闻详情页面我们操作了6秒，python脚本就会执行三次dump操作，通过分析数据之后，我们得到详情页面的每帧平均耗时分别为 20 ms 、13.67 ms 、14.33 ms, 那么我们可以知道在这个页面每帧的平均耗时为 (20 + 13.67 + 14.33) / 3 = 16ms. 因此每秒帧率为 1000 / 16 = 62.5 , 即 62.5帧。通常移动设备每秒最多60帧，因此在详情页面我们的应用是流畅的. 假如我们的新闻详情页面每帧的平均耗时为 30 ms, 那么 每秒的帧率就是 1000 / 30 = 33.3 , 即每秒为 33 帧。 此时UI会感觉卡顿.


## 第一步 相关的开发者选项

在设置中选择: 开发者选项 -> GPU 呈现模式 -> 在 `adb shell dumpsys gfxinfo` 中.


## 第二步 生成数据

运行应用，进入要测试的页面，然后马上执行 python 脚本。

```
python collect_perf_data.py 应用包名
```

脚本执行之后，你需要操作你要测试的页面，此时adb就会每隔2秒执行 `adb dumpsys gfxinfo`命令dump出性能方面的数据，数据存储在 `result/perf_result.txt` 文件中; 操作完毕之后中止 python 脚本 即可.


## 第三步 分析数据

得到 `result/perf_result.txt` 之后，再次执行分析数据的脚本:

```
python analysis.py 应用包名
```

然后就会在命令行中输出每个页面每帧的平均耗时, 单位为毫秒;

结果如下所示: 


```
.FavoriteNewsActivity/android.view.ViewRootImpl@cbc8211 
39.55
NewsDetailActivity/android.view.ViewRootImpl@68a560 
21.65
NewsDetailActivity/android.view.ViewRootImpl@68a560 
9.98
NewsDetailActivity/android.view.ViewRootImpl@68a560
18.57
```

得到的数据代表FavoriteNewsActivity 以及 三个 NewsDetailActivity的在每两秒的时间内的每帧耗时分别为 `39.55 、21.65、9.98、18.57`毫秒;  以此就可以知道每个页面的流畅度以及大致帧率;

