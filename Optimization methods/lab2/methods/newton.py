import sys
from tools import *


def solve(f, deriv, deriv2, start, accuracy):
    def find_x():
        return x - deriv(x) / deriv2(x)

    iter = 1
    x, prev_x = start, start
    x = find_x()
    print_table_header(["#", "x_i", "f(x)", "f'(x)", "x_{i+1}"])
    print_table_row([iter, prev_x, f(x), deriv(x), x])
    while (abs(deriv(x)) > accuracy):
        iter += 1
        prev_x = x
        x = find_x()
        print_table_row([iter, prev_x, f(x), deriv(x), x])
    return x


