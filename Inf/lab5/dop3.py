import random as r
f = open("dop3-output.csv", "w")
for y in range(r.randint(10,50)):
    for x in range(r.randint(10, 50)):
        s=""
        for i in range(r.randint(0,10)): s+=chr(r.randint(97, 122))
        f.write(s + ";")
    f.write("\n")