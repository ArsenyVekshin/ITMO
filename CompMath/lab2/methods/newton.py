from tools import *


def solve(f, deriv, a, b, accuracy):
    iter = 0
    x = 0.0
    x_prev = 0.0

    while(abs(a-b) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        x = mid(a, b)
        if f(a)*f(x) > 0: a = x
        else: b = x
        print_table_row([iter, a, b, x, f(a), f(b), f(x), abs(a-b)])
    x = mid(a, b)
    return x, f(x)



