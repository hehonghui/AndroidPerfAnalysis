#coding=utf-8
# 使用命令: python collect_perf_data.py <app_package>, app_package 为应用包名.
import sys
import os
import time,datetime,sched

if len(sys.argv) < 2:
    print "please input app package."
    exit

package_name = sys.argv[1]

print "collect %s performance data..." % package_name


s = sched.scheduler(time.time, time.sleep)

def dumpsys_gfxinfo(inc):
    print "dumpsys gfxinfo --->"
    os.system("adb shell dumpsys gfxinfo %s >> ./result/perf_result.txt" % (package_name))
    s.enter(inc,0, dumpsys_gfxinfo,(inc,))

#第一个参数是一个可以返回时间戳的函数，第二个参数可以在定时未到达之前阻塞。
def do_task(inc=2):
    s.enter(0,0, dumpsys_gfxinfo,(inc,))
    s.run()

def create_date_dir():
    os.system("rm -rf result")
    os.system("mkdir result")


if __name__ == "__main__":
    create_date_dir()
    do_task()
