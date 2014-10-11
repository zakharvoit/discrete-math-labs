from random import randrange

MAX = 100000
args = [randrange(MAX) for x in range(2 * MAX)]
args1 = [randrange(MAX) for x in range(MAX)]
args2 = [randrange(MAX) + MAX for x in range(MAX)]
def mkdel(s):
   return "delete " + str(s) 
def mkins(s):
   return "insert " + str(s) 
def mknext(s):
   return "next " + str(s) 
print ("\n".join(map(mkins, args1)) \
       + "\n" + "\n".join(map(mkins, args2)) \
       + "\n" + "\n".join(map(mknext, args)))
