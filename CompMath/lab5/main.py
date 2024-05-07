from methods.lagrange import *
from methods.newton import *
from abstaract_poly import Polynomial
import matplotlib.pyplot as plt
from tools import *

points = [[1, 0], [2, 3], [3, 5], [4, 7]]
a, b = 0, 5
f = None

def show_plot():
    x_arr = np.linspace(a, b, 1000)
    if(f != None):
        y_arr = [f.calc(x) for x in x_arr]
        plt.plot(x_arr, y_arr, '--r', label=f.tostr())

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.legend()
    plt.show()




# print(expand_brackets([-1, -2, -3]))


lagrange = Lagrange_Polynomial(points)
f = lagrange

print(f.tostr())
show_plot()

points = [[0.15, 1.25], [0.2, 2.38], [0.33, 3.79], [0.47, 5.44]]
a, b = 0, 0.5
newton = Newton_Polynomial(points)
f = newton

print(f.tostr())
show_plot()