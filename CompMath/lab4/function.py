from enum import Enum
import copy
import math as m

ROUND_LVL = 3

class FunctionType(Enum):
    linear = 0
    polynomial = 1
    power = 2
    exponent = 3
    logariphm = 4


class Function:
    type = FunctionType.linear
    koofs = []

    def __init__(self, koofs, type):
        self.koofs = koofs
        self.type = type

    def calc(self, x: float):
        if self.type == FunctionType.linear:
            return self.koofs[0] * x + self.koofs[1]

        if self.type == FunctionType.polynomial:
            out = 0
            for i in range(len(self.koofs)):
                out += self.koofs[i] * x ** i
            return out

        if self.type == FunctionType.power:
            return self.koofs[0] * (x ** self.koofs[1])

        if self.type == FunctionType.exponent:
            return self.koofs[0] * m.exp(x * self.koofs[1])

        if self.type == FunctionType.logariphm:
            return self.koofs[0] * m.log(x, m.e) + self.koofs[1]

    def tostr(self):
        if self.type == FunctionType.linear:
            return "y = " + str(round(self.koofs[0], ROUND_LVL)) + " x + " + str(round(self.koofs[1], ROUND_LVL))

        if self.type == FunctionType.polynomial:
            out = "y = "
            for i in range(len(self.koofs), 0, -1):
                out += str(round(self.koofs[i - 1], ROUND_LVL))
                if(i>1): out+= " x^" + str(i - 1) + " + "
            return out

        if self.type == FunctionType.power:
            return "y = " + str(round(self.koofs[0], ROUND_LVL)) + " x^ " + str(round(self.koofs[1], ROUND_LVL))

        if self.type == FunctionType.exponent:
            return "y = " + str(round(self.koofs[0], ROUND_LVL)) + " e^ " + str(round(self.koofs[1], ROUND_LVL)) + " x"

        if self.type == FunctionType.logariphm:
            return "y = " + str(round(self.koofs[0], ROUND_LVL)) + " ln(x) + " + str(round(self.koofs[1], ROUND_LVL))

    def get_koofs(self):
        return copy.deepcopy(self.koofs)

    def get_str_type(self):
        out = str(self.type)[13:]
        if self.type == FunctionType.polynomial:
            out += " " + str(len(self.koofs)-1)
        return out