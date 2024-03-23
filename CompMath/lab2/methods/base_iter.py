from tools import *


def get_lambda(f, a, b, accuracy):
    x = a
    x_max = x
    l = 0
    sign = 1
    while (x <= b):
        if l < abs(f(x)):
            l = max(l, abs(f(x)))
            sign = abs(f(x)) / f(x)
            x_max = x
        x += accuracy
    return -1 / l * sign, x_max


def simple_iteration(f, fp, a, b, accuracy):
    x = a
    prev_x = x - 2 * accuracy
    iter = 1
    lambd, x0 = get_lambda(fp, a, b, accuracy)
    q = abs(1 + lambd * fp(x0))
    print(1 + lambd * fp(a), 1 + lambd * fp(b))
    if q > 1:
        print('Достаточное условие сходимости не выполняется!')
    elif q > 0.5:
        accuracy = (1 - q) / q * accuracy
    print_table_header(["#", "x_i", "x_{i+1}", "f(x_i)", "x_i-x_{i-1}"])
    print('№\tx_i\tx_{i-1}\tf(x_i)\t|x_i-x_{i-1}|')
    while abs(f(x)) > accuracy and iter < 10000:
        if (q >= 1 and iter > 3):
            break
        prev_x = x
        x = x + f(x) * lambd
        print_table_row([iter, x, prev_x, f(x), abs(x - prev_x)])
        iter += 1
    return x
