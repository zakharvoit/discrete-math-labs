#!/usr/bin/python
 
TASK = "shooter"
fi = open(TASK + ".in", "r")
n, m, k = map(int, fi.readline().split())
p = map(float, fi.readline().split())
open(TASK + ".out", "w").write("%.15f\n" % (((1-p[k-1])**m)/sum(map(lambda x: (1-x)**m, p)),))
