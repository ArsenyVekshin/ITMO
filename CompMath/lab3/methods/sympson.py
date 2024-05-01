from tools import *

def solve(f, a, b, k):
    #print_table_header(["#", "x", "y"])
    x = a
    dx = (b-a)/k
    area = f(x)
    #print_table_row([1, x, f(x)])
    for i in range(1, k):
        x += dx
        if i % 2 == 1:  area += (4 * f(x))
        else:           area += (2 * f(x))
        #print_table_row([i+1, x, f(x)])

    x += dx
    area += f(x)
    #print_table_row([1, x, f(x)])

    return area * (dx/3)



