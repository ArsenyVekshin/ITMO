from tools import *
import sys

def get_lambda(f, a, b, accuracy):
    x = a
    x_max = x
    l = 0
    sign = 1
    while x <= b:
        if l < abs(f(x)):
            l = max(l, abs(f(x)))
            sign = abs(f(x)) / f(x)
            x_max = x
        x += accuracy
    return 1 / l * sign, x_max

def solve(f, deriv, a, b, accuracy):
    x = (a + b) / 2  # Начальная точка как середина отрезка
    lambd, x0 = get_lambda(deriv, a, b, accuracy)
    q = abs(1 + lambd * deriv(x0))
    
    print(1 + lambd * deriv(a), 1 + lambd * deriv(b), lambd)
    if q > 0.5:
        accuracy = abs((1 - q) / q * accuracy)

    print_table_header(["#", "x_i", "x_{i+1}", "f(x_i)", "delta_x"])
    
    iter = 0
    print(accuracy)
    prev_x = x - 2 * accuracy  # Начальное значение для prev_x, чтобы зайти в цикл
    while abs(f(x)) > accuracy and abs(x - prev_x) > accuracy and iter < 1000:
        prev_x = x
        x = x - f(x) * lambd  # Итерационный шаг
        print_table_row([iter, x, prev_x, f(x), abs(x - prev_x)])
        iter += 1

    return x
