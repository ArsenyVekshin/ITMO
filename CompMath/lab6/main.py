from decimal import Decimal, getcontext
from matplotlib import pyplot as plt

from methods import Euler as Euler
from methods import EulerExtend as EulerExtend
from methods import RungeKutta as RungeKutta
from methods import Adams as Adams
from methods import Miln as Miln
import numpy as np


from tools import *

getcontext().prec = 50

def f1(x, y): return 2 * x * y
def f1_ac(x): return np.exp(x ** 2)
def f2(x, y): return 2 * x - y + x ** 2
def f3(x, y): return (x - y) ** 2 + 1
def example(x, y): return y + (1+x)*float(Decimal(y)**2)

x0, y0, h, n, accuracy = 0, 0, 0, 0, 0

output = []
output_half=[]

function_idx = 0
functions = [
    ["y\' = y + (1+x) * y^2", example, lambda x: -1 / x],
    ["y\' = x^2 + 2x - y", f2, lambda x: x ** 2],
    ["y\' = (x - y)^2 + 1", f3, lambda x: x]
]

method_idx = 0
methods = [
    ["метод Эйлера", Euler.solve, 1],
    ["улучшенный метод Эйлера", EulerExtend.solve, 2],
    ["метод Рунге-Кутта", RungeKutta.solve, 4],
    ["метод Адамса", Adams.solve, 4],
    # ["метод Милна", Miln.solve, 4]
]

def accuracy_check(values, values_half):
    flag = True
    out = []
    for i in range(len(values)):
        err = (values[i]-values_half[2*i])/(2**methods[method_idx][2]-1)

        if err > accuracy: flag = False
        out.append(err)
    return out, flag

def show_plot():
    x_arr = np.linspace(x0, x0 + h*n, 1000)
    y_arr = [functions[function_idx][2](x) for x in x_arr]
    plt.plot(x_arr, y_arr, '--r')

    print("debug:", n,h)
    for i in range(n):
        plt.scatter(x0 + i*h, output[i])

    plt.grid(True)
    #plt.axhline(0, color='black', linewidth=1)
    #plt.axvline(0, color='black', linewidth=1)

    plt.title(functions[function_idx][0])
    #plt.legend()
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
        print("\tФункция:", functions[function_idx][0])
        print("\tМетод:", methods[method_idx][0])
        print("\tначальное значение x_0 =", x0)
        print("\tНачальное приближение y_0 =", y0)
        print("\tШаг h =", h)
        print("\tКоличество отрезков n =", n)
        print("\tТочность accuracy =", accuracy)



endflag = False
while(True):
    output = output_half
    output_half = []

    print("\n")
    print_table_row(["i"] + list(range(n)))

    x_arr = [round(x0 + i*h, 6) for i in range(n)]
    print_table_header(["x"] + x_arr)

    if method_idx != 4:
        if len(output) == 0: output = methods[method_idx][1](functions[function_idx][1], x0, y0, h, n)
        output_half = methods[method_idx][1](functions[function_idx][1], x0, y0, h/2, n*2)
    else:
        if len(output) == 0: output = methods[method_idx][1](functions[function_idx][1], x0, y0, h, n, accuracy)
        output_half = methods[method_idx][1](functions[function_idx][1], x0, y0, h/2, n*2, accuracy)

    print_table_row(["y"] + output)
    y_true = [functions[function_idx][2](x) for x in x_arr]
    print_table_row(["true"] + y_true)

    errors, endflag = accuracy_check(output, output_half)
    #print_table_row(["error"] + errors)
    if(endflag): break
    else:
        n *= 2
        h /= 2

        print("Точное решение не найдено")
        print("\t h =", h)
        print("\t n =", n)
        #print("DEBUG:", errors)



show_plot()
