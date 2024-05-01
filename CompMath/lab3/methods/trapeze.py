from tools import *

def solve(f, a, b, k):
    area = (f(a) + f(b))/2
    dx = (b-a)/k
    x = a + dx

    for i in range(k-1):
        area += f(x)
        x += dx
    return area*dx







