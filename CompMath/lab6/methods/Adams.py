from methods import RungeKutta


def solve(f, x0, y0, h, n):
    result = RungeKutta.solve(f, x0, y0, h, 4)
    pre_f = []

    for i in range(4):
        pre_f.append(f(x0 + h*i, result[i]))


    prev_y = result[-1]

    for i in range(4, n):

        df = pre_f[-1] - pre_f[-2]
        d2f = pre_f[-1] - 2*pre_f[-2] + pre_f[-3]
        d3f = pre_f[-1] - 2*pre_f[-2] + 3*pre_f[-3] - pre_f[-4]

        prev_y += h*pre_f[-1] + (h**2)/2 * df + 5*(h**3)/12 * d2f + 3*(h**4)/8 * d3f
        result.append(prev_y)
        pre_f.append(f(x0 + h*i, result[-1]))

    return result