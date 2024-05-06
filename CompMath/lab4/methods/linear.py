from tools import *
from function import *

def aproximate(points):
    SX, SXX, SY, SXY = 0, 0, 0, 0
    for p in points:
        SX += p[0]
        SXX += p[0] ** 2
        SY += p[1]
        SXY += p[0] * p[1]

    a = (SXY * len(points) - SX*SY) / (SXX * len(points) - SX*SX)
    b = (SXX * SY - SX * SXY) / (SXX * len(points) - SX*SX)
    return Function([a, b], FunctionType.linear)
