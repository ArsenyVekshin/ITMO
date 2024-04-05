from tools import *

def solve(f, a, b, k, type):
    print_table_header(["#", "a", "b", "area"])
    area, x = 0, 0
    dx = (b-a)/k
    if (type == "left"):    x = a
    if (type == "right"):   x = a + dx
    if (type == "midle"):   x = a + dx/2

    for i in range(k):
        S = f(x) * dx
        area += S
        print_table_row([i+1, x, x+dx, S])
        x += dx
    return area



