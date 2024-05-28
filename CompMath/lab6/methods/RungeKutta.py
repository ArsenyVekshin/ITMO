def solve(f, x0, y0, h, n):
    n -= 1
    result = [y0]
    prev_y = y0
    x = x0

    for i in range(n):
        k1 = h * f(x, prev_y)
        k2 = h * f(x + h/2, prev_y + k1/2)
        k3 = h * f(x + h/2, prev_y + k2/2)
        k4 = h * f(x + h, prev_y + k3)
        x += h

        prev_y += 1/6 * (k1 + 2*k2 + 2*k3 + k4)
        result.append(prev_y)
    return result