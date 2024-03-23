from tools import *

def solve(f, start, base_move, accuracy):
    iter = 0
    x, prev_x = start, base_move

    def find_x():
        return x - (x-prev_x) / (f(x)-f(prev_x)) * f(x)


    while (abs(x - prev_x) > accuracy and abs(f(x)) >= accuracy):
        iter += 1
        _out = [iter, prev_x, x]
        x = find_x()
        print_table_row(_out + [x, f(x), x, abs(x - prev_x)])
        prev_x = x

    if(abs(f(x)) < accuracy): return x, f(x)
    else:
        print("Поиск относительно", start, base_move, "- ошибка")
        return None

