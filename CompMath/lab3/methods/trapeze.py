from tools import *

def solve(f, a, b, k):
    print_table_header(["#", "a", "b", "area"])
    area, x = 0, a
    dx = (b-a)/k

    for i in range(k):
        S = (f(x) + f(x+dx))/2
        area += S
        print_table_row([i+1, x, x+dx, S])
        x += dx
    return area



