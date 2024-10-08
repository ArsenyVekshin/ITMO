from tools import *

class Polynomial:
    name = ""
    points = []
    koofs = []

    def __init__(self, points, koofs, name=None):
        self.points = points
        self.koofs = koofs
        self.name = name
        # расчет ключевых значений

    def calc(self, x):
        out = 0
        for i in range(len(self.koofs)):
            out += self.koofs[i] * x ** i
        return out

    def tostr(self):
        out = "y = "
        for i in range(len(self.koofs), 0, -1):
            out += str(round(self.koofs[i - 1], ROUND_LVL))
            if (i > 1): out += " x^" + str(i - 1) + " + "
        return out

    def getName(self):
        return self.name