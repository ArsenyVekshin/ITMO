import sys
import numpy as np
import matplotlib.pyplot as plt
from tools import *

from methods import linear
from methods import polynomial
from methods import power
from methods import exponent
from methods import logariphm



test1 = [[1.1, 3.5], [2.3, 4.1], [3.7, 5.2], [4.5, 6.9], [5.4, 8.3], [6.8, 14.8], [7.5, 21.2]]
test2 = [[-2, -0.42105263157894735], [-1.8, -0.5334281650071124], [-1.6, -0.669904538603249], [-1.4000000000000001, -0.8185219831618334], [-1.2000000000000002, -0.9460737937559129], [-1.0000000000000002, -1.0], [-0.8000000000000003, -0.938526513374003], [-0.6000000000000003, -0.7668711656441721], [-0.4000000000000003, -0.5288207297726074], [-0.2000000000000003, -0.26652452025586393], [-2.7755575615628914e-16, -3.7007434154171886e-16]]
test3 = [[1.1, 2.73], [2.3, 5.12], [3.7, 7.74], [4.5, 8.91], [5.4, 10.59], [6.8, 12.75], [7.5, 13.43]]



points = []
a, b, h = -2, 0, 0.2
base_f = lambda x: 4*x / (x**4 +3)
f = lambda x: 4*x / (x**4 +3)
koofs = []

def gen_points_for_calc():
    x = a
    out = []
    while (x <= b):
        out.append([x, base_f(x)])
        x += h
    return out

def show_plot():
    x_arr = np.linspace(a, b, 1000)
    y_arr = f(x_arr)
    plt.plot(x_arr, y_arr, '--r')

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.show()

def solve_by_id(method_id):
    global f
    if method_id == 0:
        print("Линейная апроксимация")
        f, koofs = linear.aproximate(points)
        print(linear.tostr(koofs))

    elif method_id == 1:
        print("Полиноминальная апроксимация степени 2")
        f, koofs = polynomial.aproximate(points, 2)
        print(polynomial.tostr(koofs))

    elif method_id == 2:
        print("Степенная апроксимация")
        f, koofs = power.aproximate(points)
        print(power.tostr(koofs))

    elif method_id == 3:
        print("Экспоненциальная апроксимация")
        f, koofs = exponent.aproximate(points)
        print(exponent.tostr(koofs))

    elif method_id == 4:
        print("Логарифмическая апроксимация")
        f, koofs = logariphm.aproximate(points)
        print(logariphm.tostr(koofs))

    else:
        print("ERROR: id метода не опознано")
        sys.exit(-1)

    print("Расчет точности:")
    S, eps, R2 = check_accuracy(f, points, koofs)
    show_plot()



if input("Показать график? Y/N : ") == "Y": show_plot()
filename = input("Введите путь до файла или нажмите Enter: ")

if filename == '':
    print(""" Выберите метод решения:
                0)  Линейная апроксимация
                1)  Полиноминальная апроксимация
                2)  Степенная апроксимация
                3)  Экспоненциальная апроксимация
                4)  Логарифмическая апроксимация
                """)

    method_idx = int(input("Номер метода: "))
    a, b = map(float, input("Введите границы отрезка: ").split())
    h = float(input("Введите шаг по Ox: "))

    if input("Сгенерировать точки по базовой функции? Y/N : ") == "Y": gen_points_for_calc()
    else:
        print("Введите", int((b - a) / h), "точек для обработки:")
        for _ in range(int((b - a) / h)):
            points.append(list(map(float, input().split())))

else:
    with open(filename, 'r') as file:
        method_idx = int(file.readline().strip())
        a, b = map(float, file.readline().strip().split())
        h = float(file.readline().strip())

        if file.readline().strip() == "Y":
            gen_points_for_calc()
        else:
            for line in file:
                points.append(list(map(float, line.strip().split())))

        print("Чтение из файла успешно")
        print("Метод решения: ", method_idx)
        print("Границы отрезка: ", a, b)
        print("Введите шаг по Ox: ", h)
        print("Точки для обработки:", points)

gen_points_for_calc()
for i in range(5):
    print("\n", i, "--"*14)
    solve_by_id(i)