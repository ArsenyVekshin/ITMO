import math as m
a, b = 0.2, 4
h = 0.1
f = lambda x: 0.5 * (x**3)

x = a
out = []
while(x<=b):
    print(round(x, 4), round(f(x), 4))
    out.append([x, f(x)])
    x += h
# print(out)