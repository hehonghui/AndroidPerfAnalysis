#coding=utf-8
import sys
import os
import time,datetime,sched

print "len %s " % sys.argv[1]
if len(sys.argv) < 2:
    print "please input app package."
    exit
package_name = sys.argv[1]
print "This is %s " % package_name

s = sched.scheduler(time.time, time.sleep)

def dumpsys_gfxinfo(inc):
    print "dumpsys gfxinfo --->"
    os.system("adb shell dumpsys gfxinfo %s >> %s_perf_%s.txt" % (package_name, package_name, datetime.datetime.now().strftime("%Y-%m-%d-%H:%M:%S")))
    s.enter(inc,0, dumpsys_gfxinfo,(inc,))

#第一个参数是一个可以返回时间戳的函数，第二个参数可以在定时未到达之前阻塞。
def do_task(inc=2):
    s.enter(0,0, dumpsys_gfxinfo,(inc,))
    s.run()

if __name__ == "__main__":
        do_task()
