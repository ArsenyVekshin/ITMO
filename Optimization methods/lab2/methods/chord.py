import sys
from tools import *


def solve(f, deriv, a, b, accuracy, max_iter):
    def find_x():
        return a - (a-b) * deriv(a) /(deriv(a) - deriv(b))

    iter = 0
    x = find_x()
    print_table_header(["#", "a", "b", "x", "f(a)", "f(b)", "f(x)"])
    while(abs(deriv(x)) > accuracy and iter<max_iter):
        iter += 1
        x = find_x()
        if deriv(x) > 0:
            b = x
        else:
            a = x

        print_table_row([iter, a, b, x, f(a), f(b), f(x)])
    return x



