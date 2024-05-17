from tools import *

def solve(f, a, b, k, type):
    #print_table_header(["#", "a", "b", "area"])
    area, x = 0, 0
    dx = (b-a)/k
    if (type == "left"):    x = a
    if (type == "right"):   x = a + dx
    if (type == "middle"):   x = a + dx/2

    for i in range(k):
        area += f(x)
        # print_table_row([i+1, x, x+dx, f(x)])
        x += dx
    return area * dx



