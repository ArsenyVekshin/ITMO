a, b = -2, 0
h = 0.2
f = lambda x: 4*x / (x**4 +3)

x = a
out = []
while(x<=b):
    out.append([x, f(x)])
    x += h
print(out)