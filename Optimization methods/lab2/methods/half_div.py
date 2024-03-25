from tools import *


def solve(f, a, b, accuracy):
    iter = 0
    print_table_header(["#", "a", "b", "f(x1)", "f(x2)", "|a-b|"])
    while(abs(a-b) > 2*accuracy):
        iter += 1
        x1 = mid(a, b-accuracy)
        x2 = mid(a, b+accuracy)
        if f(x1)>f(x2): a = x1
        else: b = x2
        print_table_row([iter, a, b, f(x1), f(x2), abs(a-b)])
    x = mid(a, b)
    return x



