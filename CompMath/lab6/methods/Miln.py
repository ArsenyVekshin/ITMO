from methods import RungeKutta

def solve(f, x0, y0, h, n, accuracy):
    n = n+1
    result = RungeKutta.solve(f, x0, y0, h, 3)
    xs = [x0 + h * i for i in range(n)]

    for i in range(4, n):
        # Предиктор
        yp = result[i - 4] + 4 * h * (2 * f(xs[i - 3], result[i - 3]) - f(xs[i - 2], result[i - 2]) + 2 * f(xs[i - 1], result[i - 1])) / 3

        # Корректор
        y_next = yp
        while True:
            yc = result[i - 2] + h * (f(xs[i - 2], result[i - 2]) + 4 * f(xs[i - 1], result[i - 1]) + f(xs[i], y_next)) / 3
            if abs(yc - y_next) < accuracy:
                y_next = yc
                break
            y_next = yc

        result.append(y_next)

    return result