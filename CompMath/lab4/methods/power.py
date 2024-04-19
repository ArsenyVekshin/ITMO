import sys
import copy
import math as m
from methods import linear
from tools import *

def aproximate(points):
    _points = copy.deepcopy(points)
    for i in range(len(_points)):
        _points[i][0] = m.log(points[i][0], m.e)
        _points[i][1] = m.log(points[i][1], m.e)

    _, koofs = linear.aproximate(_points)
    koofs[0] = m.exp(koofs[0])
    f = lambda x: koofs[0] * (x**koofs[1])
    return f, koofs

def tostr(koofs):
    return "y = " + str(round(koofs[0], ROUND_LVL)) + " x^ " + str(round(koofs[1], ROUND_LVL))