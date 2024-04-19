import math as m
import sys

import numpy as np

ROUND_LVL = 4
COLOUMN_SIZE = 7
def print_table_row(row):
    for i in row:
        print(str(round(i, 4)).ljust(COLOUMN_SIZE), end=' | ')
    print()


def print_table_header(row):
    for i in row:
        print(str(i).ljust(COLOUMN_SIZE), end=' | ')
    print("\n" + len(row) * (COLOUMN_SIZE+3) * "-")


def mid(a, b):
    return (a+b)/2

def is_dif_sign(a, b):
    return (a>0 and b<0) or (a<0 and b>0)

def add_vertical_line(x, arr):
    arr[0].append(x)
    arr[1].append(0)
    arr[0].append(x)
    arr[1].append(1)



def solve_slau(matrix, free_v):
    A = np.array(matrix)
    b = np.array(free_v)
    x = np.linalg.solve(A, b)
    return list(x)

def fill_rev_diag(matrix, value, lvl):
    x, y = 0, 0
    if (lvl < len(matrix)):
        x = 0
        y = lvl
    elif (lvl >= len(matrix) and lvl<(len(matrix)+len(matrix[0]))):
        x = lvl - len(matrix) + 1
        y = len(matrix)-1
    else:
        print("ошибка при заполнении диагонали")
        sys.exit(-1)

    while (x<len(matrix[0]) and y>=0):
        matrix[y][x] = value
        x += 1
        y -= 1

def find_R2(f, points):
    mid_f = 0
    for p in points: mid_f += f(p[0])
    mid_f /= len(points)

    S1, S2 = 0, 0
    for p in points:
        S1 += (p[1] - f(p[0])) ** 2
        S2 += (p[1] - mid_f) ** 2
    return 1 - S1/S2

def check_accuracy(f, points):
    print("Расчитаем отклонение: ")
    delta, eps, R2 = 0, 0, 0
    print_table_header(["#", "x", "y", "P(x)", "eps"])
    for i in range(len(points)):
        _y = f(points[i][0])
        delta += (_y - points[i][1])**2
        print_table_row([i + 1, points[i][0], points[i][1], _y, _y - points[i][1]])

    eps = m.sqrt(delta/len(points))
    R2 = find_R2(f, points)
    print("Мера отклонения:", delta)
    print("Среднеквадратичное отклонение:", eps)
    print("Достоверность аппроксимации: ", R2)
    return delta, eps, R2

