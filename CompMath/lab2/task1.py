import sys
import numpy as np
import matplotlib.pyplot as plt

import methods.half_div as half_div
import methods.chord as chord
import methods.newton as newton
import methods.secant as secant
import methods.base_iter as base_iter


f = lambda x: x ** 3 + 2.84*x**2 - 5.606*x - 14.766
deriv = lambda x: 3*x**2 + 5.68*x - 5.606

a, b, accuracy, method_idx = 0, 0, 0, 0

def show_plot():
    x_arr = np.linspace(-4.0, 2.5, 500)
    y_arr = f(x_arr)
    plt.plot(x_arr, y_arr)
    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)
    plt.show()

if input("Показать график? Y/N : ") == "Y": show_plot()

def solve_by_id(method_id):
    if method_id == 0:      x = half_div.solve(f, a, b, accuracy)
    elif method_id == 1:    x = chord.solve(f, a, b, accuracy)
    elif method_id == 2:    x = newton.solve(f, deriv, a, b, a, accuracy)
    elif method_id == 3:    x = secant.solve(f, a, b, accuracy)
    elif method_id == 4:    x = base_iter.solve(f, deriv, a, b, accuracy)
    else:
        print("ERROR: id метода не опознано")
        sys.exit(-1)
    print("Найдено решение с точностью x =", x, "f(x) =", round(f(x), 10), "\n")


filename = input("Введите путь до файла или нажмите Enter: ")
for _ in range(3):
    if filename == '':
        print(""" Выберите метод решения:
                    0)  Метод половинного деления 
                    1)  Метод хорд
                    2)  Метод Ньютона 
                    3)  Метод секущих (вместо b указать точку со сдвигом)
                    4)  Метод простой итерации
                    """)
        method_idx = int(input("Номер метода: "))
        if input("Показать график? Y/N : ") == "Y": show_plot()
        a, b = map(float, input("Введите границы отрезка: ").split())
        accuracy = float(input("Введите точность: "))

    else:
        with open(filename, 'r') as file:
            method_idx = int(file.readline().strip())
            a, b = map(float, file.readline().strip().split())
            accuracy = float(file.readline().strip())

            print("Чтение из файла успешно")
            print("Метод решения: ", method_idx)
            print("Границы отрезка: ", a, b)
            print("Точность: ", accuracy)

    if(a<-5 or b>3):
        print("ERROR: границы введенного отрезка слишком далеко от корней - поиск прерван")
        sys.exit(0)
    solve_by_id(method_idx)
