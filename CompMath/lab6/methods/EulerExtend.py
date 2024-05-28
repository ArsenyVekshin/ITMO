def solve(f, x0, y0, h, n):
    result = [y0]
    prev_x, prev_y = x0, y0
    x = prev_x + h

    for i in range(n):
        prev_y += (h/2) * (f(prev_x, prev_y) + f(x, prev_y + h*f(prev_x, prev_y)))
        prev_x, x = x, x + h
        result.append(prev_y)

    return result
