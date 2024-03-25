import numpy as np
import matplotlib.pyplot as plt

import methods.half_div as half_div
import methods.chord as chord
import methods.newton as newton
import methods.golden_section as golden_section


from methods import *

functions = [[lambda x: 0.25*x ** 4 + x ** 2 - 8 * x + 12, lambda x: x**3 + 2*x - 8, lambda x: 3*x**2 + 2]]
a, b = 0, 2
max_iter = 25
accuracy = 10**(-10)


for f, deriv, deriv2 in functions:
    print("Метод половинного деления")
    x = half_div.solve(f, a, b, accuracy, max_iter)
    print("Ответ: x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод хорд")
    x = chord.solve(f, deriv, a, b, accuracy, max_iter)
    print("Ответ: x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод ньютона")
    x = newton.solve(f, deriv, deriv2, a, accuracy, max_iter)
    print("Ответ: x =", x, "f(x) =", round(f(x), 10), "\n")

    print("Метод золотого сечения")
    x = golden_section.solve(f, a, b, accuracy, max_iter)
    print("Ответ: x =", x, "f(x) =", round(f(x), 10), "\n")
