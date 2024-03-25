import sys
from tools import *


def solve(f, deriv, a, b, start, accuracy):
    def find_x():
        return x - f(x) / deriv(x)

    iter = 0
    x, prev_x = start, 0
    print_table_header(["#", "x_i", "f(x)", "f'(x)", "x_{i+1}", "delta_x"])
    while (abs(f(x)/deriv(f(x))) > accuracy
           and abs(x - prev_x) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        prev_x = x
        x = find_x()
        print_table_row([iter, prev_x, f(x), deriv(x), x, abs(x - prev_x)])

    return x


