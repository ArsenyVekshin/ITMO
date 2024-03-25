import numpy as np
import matplotlib.pyplot as plt

import methods.half_div as half_div
import methods.chord as chord
import methods.newton as newton
import methods.secant as secant
import methods.base_iter as base_iter


from methods import *

functions = [[lambda x: x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766, lambda x: 3*x**2 + 5.68*x -5.06]]
a, b = -3.5, -2.5


for f, deriv in functions:
    print("Метод половинного деления")
    x = half_div.solve(f, a, b, 0.001)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод хорд")
    x = chord.solve(f, a, b, 0.001)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод ньютона")
    x = newton.solve(f, deriv, a, b, b, 0.001)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод секущих")
    x = secant.solve(f, a, a+0.1, 0.001)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод простой итерации")
    x = base_iter.solve(f, deriv, a, b, 0.001)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")

