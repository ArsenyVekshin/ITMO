ROUND_LVL = 4
COLOUMN_SIZE = 12

def expand_brackets(equation):
    """
    Раскрыть скобки правильной скобочной последовательности вида (x-a)(x-b)(x-c)(x-d)...

    :param equation: коэфиценты [a, b, c, d ...]
    :return: [...] коэфиценты при членах полинома
    """
    out = [1, equation[0]]
    for k in equation[1:]:
        a, b = [0] + out, out +[0]
        out = [a[i]*k + b[i] for i in range(len(a)) ]
    out.reverse()
    return out

