from abstaract_poly import Polynomial
import numpy as np
from tools import *

class Lagrange_Polynomial(Polynomial):
    def __init__(self, points):
        super().__init__(points, list(np.zeros(len(points))), "lagrange")

        for i in range(len(points)):
            numerator = []
            buff = points[i][1]

            for j in range(len(points)):
                if i==j: continue
                buff /= points[i][0] - points[j][0] # считаем знаменатель
                numerator.append(-1 * points[j][0]) # собираем числитель

            _polynom = expand_brackets(numerator)
            _polynom = [elem * buff for elem in _polynom]
            self.koofs = [self.koofs[i] + _polynom[i] for i in range(len(self.koofs)) ]


