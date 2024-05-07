import sys
import numpy as np
import matplotlib.pyplot as plt
from tools import *
from function import *


from methods import linear
from methods import polynomial
from methods import power
from methods import exponent
from methods import logariphm


points = []
a, b, h = -2, 0, 0.2
base_f = lambda x: 4*x / (x**4 +3)
f = None
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
    if(f != None):
        y_arr = [f.calc(x) for x in x_arr]
        plt.plot(x_arr, y_arr, '--r', label=f.tostr())

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.title(f.get_str_type())
    plt.legend()
    plt.show()

def solve_by_id(method_id):
    global f
    if method_id == 0:
        f = linear.aproximate(points)

    elif method_id == 1:
        f = power.aproximate(points)

    elif method_id == 2:
        f = exponent.aproximate(points)

    elif method_id == 3:
        f = logariphm.aproximate(points)

    elif method_id >= 4 and method_id <= 10:
        f = polynomial.aproximate(points, method_id - 2)

    else:
        print("ERROR: id метода не опознано")
        sys.exit(-1)

    return f


filename = input("Введите путь до файла или нажмите Enter: ")

if filename == '':
    a, b = map(float, input("Введите границы отрезка: ").split())
    h = float(input("Введите шаг по Ox: "))

    if input("Сгенерировать точки по базовой функции? Y/N : ") == "Y": points = gen_points_for_calc()
    else:
        for _ in range(int(input("Количество точек: "))):
            points.append(list(map(float, input().split())))

else:
    with open(filename, 'r') as file:
        a, b = map(float, file.readline().strip().split())
        h = float(file.readline().strip())

        if file.readline().strip() == "Y":
            points = gen_points_for_calc()
        else:
            for line in file:
                points.append(list(map(float, line.strip().split())))

        print("Чтение из файла успешно")
        print("Границы отрезка: ", a, b)
        print("Шаг по Ox: ", h)
        print("Точки для обработки:", points)



approxes = []
for i in range(10):
    try:
        f = solve_by_id(i)
        S, eps, R2 = check_accuracy(f, points)
        approxes.append([f, S, eps, R2])
        # show_plot()
    except (ValueError, np.linalg.LinAlgError):
        pass

approxes = sorted(approxes, key=lambda x: x[-2])
print_table_header(["method", "S", "𝜹", "R2"])
for f, S, eps, R2 in approxes:
    print_table_row([f.get_str_type(), S, eps, R2])


print()
print("Лучшая апроксимация:")
for f, S, eps, R2 in approxes:
    if(eps != approxes[0][-2]): continue
    print()
    print("Метод:\t", f.get_str_type())
    print("Функция:\t", f.tostr())
    print("Мера отклонения:\t",  approxes[0][1])
    print("Среднеквадратичное отклонение:\t", approxes[0][2])
    print("Достоверность аппроксимации:\t", approxes[0][3])
    show_plot()
