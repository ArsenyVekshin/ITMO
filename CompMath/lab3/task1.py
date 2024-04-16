import sys
import numpy as np
import matplotlib.pyplot as plt

import methods.rectangles as rectangles
import methods.sympson as sympson
import methods.trapeze as trapeze


S = 0
a, b, k = 0, 2, 10
f = lambda x: -3 * x**3 - 5 * x**2 + 4*x - 2

def show_plot():
    x_arr = np.linspace(-4, 4, 500)
    y_arr = f(x_arr)
    plt.plot(x_arr, y_arr)
    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)
    plt.show()

def solve_by_id(method_id):
    if method_id == 0:      S = rectangles.solve(f, a, b, k, "left")
    elif method_id == 1:    S = rectangles.solve(f, a, b, k, "middle")
    elif method_id == 2:    S = rectangles.solve(f, a, b, k, "right")
    elif method_id == 3:    S = trapeze.solve(f, a, b, k)
    elif method_id == 4:    S = sympson.solve(f, a, b, k)
    else:
        print("ERROR: id метода не опознано")
        sys.exit(-1)

    print("Найдено решение S=", S, "\n")



if input("Показать график? Y/N : ") == "Y": show_plot()
filename = input("Введите путь до файла или нажмите Enter: ")

if filename == '':
    print(""" Выберите метод решения:
                0)  Метод прямоугольников (слева)
                1)  Метод прямоугольников (посередине)
                2)  Метод прямоугольников (справа)
                3)  метод трапеции
                4)  метод Симпсона
                """)

    method_idx = int(input("Номер метода: "))
    a, b = map(float, input("Введите границы отрезка: ").split())
    k = int(input("Введите количество отрезков: "))
else:
    with open(filename, 'r') as file:
        method_idx = int(file.readline().strip())
        a, b = map(float, file.readline().strip().split())
        k = int(file.readline().strip())

        print("Чтение из файла успешно")
        print("Метод решения: ", method_idx)
        print("Границы отрезка: ", a, b)
        print("Количество отрезков: ", k)
# solve_by_id(method_idx)
for i in range(5):
    print("\n", i, "--"*14)
    solve_by_id(i)