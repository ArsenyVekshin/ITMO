from tools import *

def solve(f, start, base_move, accuracy):
    iter = 0
    x, prev_x = base_move, start

    def find_x():
        return x - (x-prev_x) / (f(x)-f(prev_x)) * f(x)

    print_table_header(["#", "x_{i-1}", "x_i", "x_{i+1}", "f(x_i+1)" ,"delta_x"])
    while (abs(x - prev_x) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        _out = [iter, prev_x, x]
        x = find_x()
        prev_x = _out[-1]
        print_table_row(_out + [x, f(x), abs(x - prev_x)])

    return x
