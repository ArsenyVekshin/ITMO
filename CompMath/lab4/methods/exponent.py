import copy
from function import *
from methods import linear
from tools import *

def aproximate(points):
    _points = copy.deepcopy(points)
    for i in range(len(_points)):
        _points[i][1] = m.log(points[i][1], m.e)

    koofs = linear.aproximate(_points).get_koofs()
    koofs[0] = m.exp(koofs[0])

    return Function(koofs, FunctionType.exponent)
