

def solve(f, x0, y0, h, n):
    result = [y0]
    prev_y = y0

    for i in range(n-1):
        prev_y += h * f(x0 + i*h, prev_y)
        result.append(prev_y)

    return result