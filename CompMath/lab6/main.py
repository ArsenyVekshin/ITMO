from matplotlib import pyplot as plt

from methods import Euler as Euler
from methods import EulerExtend as EulerExtend
from methods import RungeKutta as RungeKutta
from methods import Adams as Adams
from methods import Miln as Miln
import numpy as np


from tools import *

def f1(x, y): return 2 * x * y
def f1_ac(x): return np.exp(x ** 2)
def f2(x, y): return 2 * x - y + x ** 2
def f3(x, y): return (x - y) ** 2 + 1
def example(x, y): return y + (1+x)*(y**2)

x0, y0, h, n, accuracy = 0, 0, 0, 0, 0

output = []

function_idx = 0
functions = [
    ["y\' = y + (1+x) * y^2", example, lambda x: -1 / x],
    ["y\' = 2xy", f1, lambda x: np.exp(x ** 2)],
    ["y\' = x^2 + 2x - y", f1, lambda x: x ** 2],
    ["y\' = (x - y)^2 + 1", f1, lambda x: x]
]

method_idx = 0
methods = [
    ["метод Эйлера", Euler.solve],
    ["улучшенный метод Эйлера", EulerExtend.solve],
    ["метод Рунге-Кутта", RungeKutta.solve],
    ["метод Адамса", Adams.solve],
    ["метод Милна", Miln.solve]
]


def show_plot():
    x_arr = np.linspace(x0, x0 + h*n, 1000)
    y_arr = [functions[function_idx][2](x) for x in x_arr]
    plt.plot(x_arr, y_arr, '--r')

    for i in range(n):
        plt.scatter(x0 + i*h, output[i])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.title(functions[function_idx][0])
    plt.legend()
    plt.show()


filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    print("Выберите функцию:")
    for i in range(len(functions)):
        print("\t" + str(i) + ". " + functions[i][0])
    function_idx = int(input("Выбранная функция: "))

    print("Выберите метод решения:")
    for i in range(len(methods)):
        print("\t" + str(i) + ". " + methods[i][0])
    function_idx = int(input("Выбранный метод: "))

    x0 = float(input("Введите начальное значение x0: "))
    y0 = float(input("Введите начальное условие y0: "))
    h = float(input("Введите шаг h: "))
    n = int(input("Введите количество отрезков n: "))
    accuracy = float(input("Введите точность: "))

else:
    with open(filename, 'r') as file:
        function_idx = int(file.readline().strip())
        method_idx = int(file.readline().strip())
        x0 = float(file.readline().strip())
        y0 = float(file.readline().strip())
        h = float(file.readline().strip())
        n = int(file.readline().strip())
        accuracy = float(file.readline().strip())

        print("Чтение из файла успешно")


print("\n")
print(methods[method_idx][0])
print_table_row(["i"] + list(range(8)))

x_arr = [round(x0 + i*h, 6) for i in range(n)]
print_table_header(["x"] + x_arr)


if method_idx != 4:
    output = methods[method_idx][1](example, x0, y0, h, n)
else:
    output = methods[method_idx][1](example, x0, y0, h, n, accuracy)

print_table_row(["y"] + output)
y_true = [functions[function_idx][2](x) for x in x_arr]
print_table_row(["true"] + y_true)
show_plot()
