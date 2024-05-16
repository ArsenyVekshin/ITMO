from methods.lagrange import *
from methods.newton import *
from abstaract_poly import Polynomial
import matplotlib.pyplot as plt
from tools import *


clc_x = 0
points =[]
functions =[]

def show_plot():
    x_arr = np.linspace(min(points, key=lambda x: x[0])[0], max(points, key=lambda x: x[0])[0], 1000)
    for f in functions:
        y_arr = [f.calc(x) for x in x_arr]
        plt.plot(x_arr, y_arr, label=f.getName())

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.legend()
    plt.show()


filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    for _ in range(int(input("Количество точек: "))):
        points.append(list(map(float, input().split())))
else:
    with open(filename, 'r') as file:
        for line in file:
            points.append(list(map(float, line.strip().split())))
    print("Чтение из файла успешно")

functions.append(Lagrange_Polynomial(points))

functions.append(Newton_Polynomial(points))

for f in functions:
    print()
    print(f.getName())
    print("function:", f.tostr())
    print("f(", clc_x, ") =", f.calc(clc_x))

show_plot()



