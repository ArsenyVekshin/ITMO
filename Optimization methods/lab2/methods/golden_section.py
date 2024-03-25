import math
from tools import *


def solve(f, a, b, accuracy, max_iter):
    iter = 0
    fC, fD = 0, 0
    gr = (math.sqrt(5) + 1) / 2  # золотое сечение
    c = b - (b - a) / gr
    d = a + (b - a) / gr
    print_table_header(["#", "a", "b", "c", "d", "f(c)", "f(d)", "|a-b|"])
    while abs(c - d) > accuracy and iter<max_iter:
        iter += 1
        if (fC == 0): fC = f(c)
        if (fD == 0): fD = f(d)
        if fC < fD:
            b = d
            fC, fD = 0, fC
        else:
            a = c
            fC, fD = fD, 0

        print_table_row([iter, a, b, c, d, f(c), f(d), abs(a - b)])
        c = b - (b - a) / gr
        d = a + (b - a) / gr
    return mid(a, b)
