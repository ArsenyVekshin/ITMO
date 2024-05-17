from methods.lagrange import *
from methods.newton import *
from methods.newton_stable import *
from abstaract_poly import Polynomial
import matplotlib.pyplot as plt
from tools import *


clc_x = 0
points =[]
functions =[]

def is_stable(points):
    delta = round(points[1][0] - points[0][0], 5)
    for i in range(len(points)-1):
        if(round(points[i+1][0] - points[i][0],5) != delta): return False
    return True

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

points = sorted(points, key=lambda x: x[0])
print(points)

functions.append(Lagrange_Polynomial(points))


functions.append(Newton_Polynomial(points))
x_check = float(input("Введите X точки для расчета: "))

for f in functions:
    print()
    print(f.getName())
    print("function:", f.tostr())
    print("f(", x_check, ") =", f.calc(x_check))


functions[-1].print_tree()
show_plot()


if(is_stable(points)):
    print("\nnewton_stable")
    newton_stable = Newton_Stable_Polynomial(points)
    newton_stable.print_tree()

    if(x_check <= (points[-1][0]-points[0][0])/2):
        print("front: ", newton_stable.calc_straight(x_check))
    else:
        print("back: ", newton_stable.calc_back(x_check))
else:
    print("точки не расвностоящие :(")

