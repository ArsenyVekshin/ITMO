import math as m
# Исходные данные
n = 100.0
X = 76.0
o = None
Ex = None
Ex2 = 686800.0
Ex_X = None  # E(x-X)^2

# Квантиль распределения
# (приложение 1: найдите какому знаечнию соответсвует значение Fy)
# где Fy = (y+1)/2
ty = 1.761

if X is None and Ex is not None:
    X = Ex / n
print(X)

if Ex_X is not None:
    o = m.sqrt(Ex_X/n)
elif Ex2 is not None:
    o = m.sqrt(Ex2 / n)
print("o =", o)


print("Искомый интервал:", X - ty * o / m.sqrt(n), "< m <", X + ty * o / m.sqrt(n))
