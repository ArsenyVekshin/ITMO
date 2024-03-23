import numpy as np
import matplotlib.pyplot as plt

import methods.half_div as half_div
import methods.chord as chord

from methods import *

functions = [lambda x: x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766]

for f in functions:
    x = half_div.solve(functions[0], -4, -2.5, 0.0001)
    print("Найдено решение с точностью x =", x, "f(x) =", f(x))

    chord.solve(functions[0], -3.5, -2.5, 0.0001)
    # axis = np.linspace(-5, 3, 1000)
    # plt.plot(axis, f(axis), color='blue')
    # plt.grid()
    # plt.show()