import sys

from tools import *

def aproximate(points, degree):
    if(degree<2 or degree>8):
        print("АХТУНГ: степень апроксимации некоректна")
        sys.exit(-1)

    A = np.zeros((degree+1, degree+1))
    B = []

    A[0][0] = len(points)
    for i in range(1, degree*2+1):
        _val = 0
        for p in points: _val += p[0]**i
        fill_rev_diag(A, _val, i)

    for i in range(degree+1):
        _val = 0
        for p in points: _val += (p[0] ** i) * p[1]
        B.append(_val)

    # for row in A:
    #     print(row)
    # print("B = ", B)
    _koofs = solve_slau(A, B)

    out_f = lambda x: 0
    for i in range(len(_koofs)):
        out_f = lambda x :

    return out_f, _koofs

def tostr(koofs):
    out = "y = "
    for i in range(len(koofs)):
        out += str(round(koofs[len(koofs)-i -1], ROUND_LVL)) + " x^" + str(len(koofs) - i -1) + " + "
    return out