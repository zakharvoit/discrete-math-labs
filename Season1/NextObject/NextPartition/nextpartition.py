#!/usr/bin/python2

from sys import exit

TASK = "nextpartition"

fi = open(TASK + ".in", "r")
fo = open(TASK + ".out", "w")

sum, rest = fi.readline().split("=")
sum = int(sum)
a = map(int, rest.split("+"))

add = 0
for i in xrange(len(a) - 1, -1, -1):
    add += a[i]
    for j in xrange(a[i] + 1, add + a[i] + 1):
        newAdd = add - j 
        if newAdd == 0 or newAdd >= j:
            a[-1] = j
            while newAdd >= j:
                a.append(j)
                newAdd -= j
            a[len(a) - 1] += newAdd
            fo.write(str(sum) + "=" + "+".join(map(str, a)) + "\n")
            exit(0)
    del a[-1]

fo.write("No solution\n")

