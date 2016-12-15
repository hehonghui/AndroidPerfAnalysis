#coding=utf-8
import sys

raw_file = "./result/perf_result.txt"
# if len(sys.argv) > 1:
#     raw_file = sys.argv[1]
    
f = open(raw_file)
lines = filter(lambda x: x != '', map(lambda x: x.strip(), f.readlines()))
tag = 'com.newsdog'
if len(sys.argv) > 1:
    tag = sys.argv[1]

endtag = 'hierarchy'
index = []
endindex = []
for i, l in enumerate(lines):
    if (tag in l and ' Graphics' not in l):
        index.append(i)
    if endtag in l:
        endindex.append(i)

index.append(endindex[-1])


def fun(lines):
    if not lines:
        return
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

    if len(d4) == 0 and len(d1) != 0:
        s1 = sum(d1)
        s2 = sum(d2)
        s3 = sum(d3)
        count = len(d1)
        print head
        print "%.2f" % ((s1 + s2 + s3) / count)

    elif len(d4) != 0 and len(d1) != 0:
        s1 = sum(d1)
        s2 = sum(d2)
        s3 = sum(d3)
        s4 = sum(d4)
        count = len(d1)
        print head
        print "%.2f" % ((s1 + s2 + s3 + s4) / count)


for i in range(len(index) - 1):
    begin = index[i:i + 2][0]
    end = index[i:i + 2][1]
    part = lines[begin:end]
    fun(part)
