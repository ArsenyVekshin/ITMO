from tools import *
import sys

def solve(functions, x0, y0, accuracy):
    x, y = x0, y0
    prev_x, prev_y = 0, 0

    iter = 0
    print_table_header(["#", "x", "y", "delta_x", "delta_y"])
    while max(abs(x-prev_x), abs(y-prev_y)) > accuracy:
        iter += 1

        prev_x, prev_y = x, y
        x = functions[0](x,y)
        y = functions[1](x,y)
        print_table_row([iter, x, y, abs(x-prev_x), abs(y-prev_y)])
    return x, y

def is_possible(deriv_functions, x0, y0):
    for df in deriv_functions:
        print(df(x0, y0))
        if (df(x0, y0)>1):
            print("ERROR: условие сходимости не выполняется в окресности заданной точки")
            sys.exit(1)
    print("Условие сходимости выполняется")






