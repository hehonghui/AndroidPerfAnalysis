#coding=utf-8
# 该文件用于分析 dumpsys gfxinfo产生的UI绘制方面的数据, 统计每帧的平均耗时(单位为ms).
# 使用方法为: 1, 通过dumpsys gfxinfo产生数据,并且保存到文件(例如perf.txt)
# 2, 然后使用该文件分析产生的性能文件, 例如: python perf_analysis.py perf.txt;
import sys

f = open(sys.argv[1])
lines = filter(lambda x: x != '', map(lambda x: x.strip(), f.readlines()))
tag = 'com.'
endtag = 'hierarchy'
index = []
for i, l in enumerate(lines):
    if (tag in l and ' Graphics' not in l):
        index.append(i)
    if endtag in l:
        index.append(i)
        break


def fun(lines):
    head = lines[0]
    lines = lines[1:]
    d1 = []
    d2 = []
    d3 = []
    d4 = []

    def porcess4(line):
        p1, p2, p3, p4 = line.split('\t')
        try:
            d1.append(float(p1))
            d2.append(float(p2))
            d3.append(float(p3))
            d4.append(float(p4))
        except:
            pass

    def porcess3(line):
        p1, p2, p3 = line.split('\t')
        try:
            d1.append(float(p1))
            d2.append(float(p2))
            d3.append(float(p3))
        except:
            pass

    for l in lines[1:]:
        if '\t' in l:
            if l.count('\t') == 2:
                porcess3(l)
            elif l.count('\t') == 3:
                porcess4(l)

    if len(d4) == 0:
        s1 = sum(d1)
        s2 = sum(d2)
        s3 = sum(d3)
        count = len(d1)
        print head
        avg = (s1 + s2 + s3) / count
        print "avg : %.2f ms" % avg
    else:
        s1 = sum(d1)
        s2 = sum(d2)
        s3 = sum(d3)
        s4 = sum(d4)
        count = len(d1)
        print head
        avg = (s1 + s2 + s3 + s4) / count
        print "avg : %.2f ms" % avg


for i in range(len(index) - 1):
    begin = index[i:i + 2][0]
    end = index[i:i + 2][1]
    part = lines[begin:end]
    fun(part)
