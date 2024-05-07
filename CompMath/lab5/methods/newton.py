from abstaract_poly import Polynomial
import numpy as np
from tools import *




class Newton_Polynomial(Polynomial):

    tree = []
    def __init__(self, points):

        super().__init__(points, list(np.zeros(len(points))))

        # Соберем таблицу по слоям
        self.tree.append([])

        for i in range(1, len(points)): # собираем первый слой
            self.tree[-1].append((points[i][1]-points[i-1][1]) / (points[i][0]-points[i-1][0]))

        for depth in range(1, len(points)-1):
            self.tree.append([])
            left, right = 0, depth+1
            for i in range(1, len(self.tree[-2])):
                print(self.tree[-2][i], self.tree[-2][i-1],"/", points[right][0], points[left][0] )
                self.tree[-1].append((self.tree[-2][i] - self.tree[-2][i-1]) /
                                      (points[right][0] - points[left][0]))
                right += 1
                left += 1

        for lvl in self.tree:
            print(lvl)

        print("-"*8, "\n"*2)
        # Соберем полином из таблицы
        # self.koofs[0] = points[0][1]
        # for i in range(len(points)-1):
        #     _polynom = [-1 * points[j][0] for j in range(i+1)]
        #     print(_polynom, self.tree[i][0] )
        #
        #     _polynom = expand_brackets(_polynom)
        #     _polynom = [elem * self.tree[i][0] for elem in _polynom]
        #
        #     while len(_polynom)<len(self.koofs): _polynom = [0] + _polynom
        #     print(len(self.koofs), len(_polynom))
        #     self.koofs = [self.koofs[i] + _polynom[i] for i in range(len(self.koofs))]

    def calc(self, x):
        out = self.points[0][1]
        for i in range(len(self.points) - 1):
            buff = self.tree[i][0]
            for j in range(i+1):
                buff *= (x - self.points[j][0])
            out+=buff
        return out






