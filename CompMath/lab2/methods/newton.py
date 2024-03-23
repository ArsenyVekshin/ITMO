import sys
from tools import *


def solve(f, deriv, a, b, start, accuracy):
    def find_x():
        return x - f(x) / deriv(x)

    iter = 0
    x, prev_x = start, 0
    while (abs(f(x)/deriv(f(x))) > accuracy and abs(x - prev_x) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        x = find_x()
        print_table_row([iter, prev_x, f(x), deriv(x), x, abs(x - prev_x)])
        prev_x = x

    if(abs(f(x)) < accuracy): return x, f(x)
    else:
        print("Поиск относительно", start, "- ошибка")
        return None

