import sys
import numpy as np
from scipy.integrate import quad
import matplotlib.pyplot as plt

import methods.rectangles as rectangles
import methods.sympson as sympson
import methods.trapeze as trapeze

error = 0
accuracy = 0.001
pS, S = 0, 0
a, b, k = 0, 2, 10
f = lambda x: -3 * x**3 - 5 * x**2 + 4*x - 2
#f = lambda x: x**2

def show_plot():
    x_arr = np.linspace(-4, 4, 500)
    y_arr = f(x_arr)
    plt.plot(x_arr, y_arr)
    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)
    plt.show()

def solve_by_id(method_id):
    _S = 0
    if method_id == 0:      _S = rectangles.solve(f, a, b, k, "left")
    elif method_id == 1:    _S = rectangles.solve(f, a, b, k, "middle")
    elif method_id == 2:    _S = rectangles.solve(f, a, b, k, "right")
    elif method_id == 3:    _S = trapeze.solve(f, a, b, k)
    elif method_id == 4:    _S = sympson.solve(f, a, b, k)
    else:
        print("ERROR: id метода не опознано")
        sys.exit(-1)

    #print("k =", k,"\t-> S =", _S, end ='')
    return _S

def find_accuracy(S1, S2, k):
    return abs((S2-S1)/(2**k -1))


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
    accuracy = float(input("Введите необходимую точность: "))
else:
    with open(filename, 'r') as file:
        method_idx = int(file.readline().strip())
        a, b = map(float, file.readline().strip().split())
        k = int(file.readline().strip())
        accuracy = float(file.readline().strip())

        print("Чтение из файла успешно")
        print("Метод решения: ", method_idx)
        print("Границы отрезка: ", a, b)
        print("Количество отрезков: ", k)
        print("Необходимая точность: ", accuracy)

pS = solve_by_id(method_idx)
print("k =", k,"\t-> S =", pS)
k*=2
S = solve_by_id(method_idx)
print("k =", k,"\t-> S =", S)

error = find_accuracy(pS, S, k)
print("Start err =", error)
while(error > accuracy):
    pS = S
    k *= 2
    S = solve_by_id(method_idx)
    error = find_accuracy(pS, S, 2)
    print("k =", k,"\t-> S =", S, " \terr =", error)

print("Ответ: S =", S)
print("Библиотека: ", quad(f, a, b))
# for i in range(5):
#     print("\n", i, "--"*14)
#     solve_by_id(i)




