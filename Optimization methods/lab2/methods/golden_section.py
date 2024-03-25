import math
from tools import *


def solve(f, a, b, accuracy):
    iter = 0
    gr = (math.sqrt(5) + 1) / 2  # золотое сечение
    c = b - (b - a) / gr
    d = a + (b - a) / gr
    print_table_header(["#", "a", "b", "c", "d", "f(a)", "f(b)", "f(c)", "f(d)", "|a-b|"])
    while abs(c - d) > accuracy:
        iter += 1
        if f(c) < f(d):
            b = d
        else:
            a = c
        print_table_row([iter, a, b, c, d, f(a), f(b), f(c), f(d), abs(a - b)])
        c = b - (b - a) / gr
        d = a + (b - a) / gr
    return mid(a, b)
