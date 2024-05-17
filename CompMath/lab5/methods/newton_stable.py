import math

from abstaract_poly import Polynomial
import numpy as np
from tools import *




class Newton_Stable_Polynomial(Polynomial):

    h = 0
    tree = []
    def __init__(self, points):

        super().__init__(points, list(np.zeros(len(points))), "newton_stable")
        self.h = points[0][0] - points[1][0]

        # Соберем таблицу по слоям
        self.tree.append([])

        for i in range(1, len(points)): # собираем первый слой
            self.tree[-1].append(points[i][1]-points[i-1][1])

        for depth in range(1, len(points)-1):
            self.tree.append([])
            for i in range(1, len(self.tree[-2])):
                # print(self.tree[-2][i], self.tree[-2][i-1],"/", points[right][0], points[left][0] )
                self.tree[-1].append(self.tree[-2][i] - self.tree[-2][i-1])




    def calc_straight(self, x):
        t_idx = 0

        # ищем самую близкую(слева) точку для поиска вперед
        for i in range(len(self.points)):
            if(self.points[i][0] < x): t_idx = i
            else: break

        out = 0
        t = (x - self.points[t_idx][0]) / self.h
        for i in range(len(self.tree) - t_idx): # собираем слагаемые
            buff = self.tree[i][t_idx]
            for j in range(i): buff *= t-i
            buff /= math.factorial(i)

            out+=buff


    def calc_back(self, x):
        t_idx = 0

        # ищем самую близкую(справа) точку для поиска назад
        for i in range(len(self.points)-1, 0, -1):
            if (self.points[i][0] > x):  t_idx = i
            else: break

        out = 0
        t = (x - self.points[t_idx][0]) / self.h
        for i in range(len(self.tree) - t_idx):  # собираем слагаемые
            buff = self.tree[i][t_idx]
            for j in range(i): buff *= t + j
            buff /= math.factorial(i)

            out += buff



    def calc_by_tree(self, x):
        out = self.points[0][1]
        for i in range(len(self.points) - 1):
            buff = self.tree[i][0]
            for j in range(i+1):
                buff *= (x - self.points[j][0])
            out+=buff
        return out

    def calc(self, x):
        return self.calc_by_tree(x)

    def print_tree(self):
        print()
        print_table_header(["x\\y"] + list(range(len(self.tree))))
        for x_i in range(len(self.tree)):
            buff = [x_i]
            for j in range(len(self.tree) - x_i):
                buff.append(self.tree[j][x_i])
            print_table_row(buff)





    def getPoints(self):
        return self.points