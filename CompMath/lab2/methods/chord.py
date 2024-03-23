import sys
from tools import *


def solve(f, a, b, accuracy):

    def find_x():
        return a - (b-a)*f(a)/(f(b)-f(a))

    print_table_header(["#", "a", "b", "x", "f(a)", "f(b)", "f(x)", "delta_x"])
    iter = 0
    x = find_x()
    prev_x = 0.0
    while(abs(a-b) > accuracy and abs(x-prev_x) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        if iter > 1: prev_x = x
        x = find_x()
        if is_dif_sign(f(a), f(x)):
            b = x
        elif is_dif_sign(f(b), f(x)):
            a = x
        else:
            print("ERROR: функция не пересекает 0 на заданном промежутке", a, b)
            sys.exit(-1)
        print_table_row([iter, a, b, x, f(a), f(b), f(x), abs(x - prev_x)])


    return x, f(x)



