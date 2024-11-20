import numpy as np

# из расчетов
size = 300
v = 1.483
t = 25.946
q = 0.62

t1 = (1 + np.sqrt((1 - q) / (2 * q) * (v ** 2 - 1))) * t
t2 = (1 - np.sqrt(q / (2 * (1 - q)) * (v ** 2 - 1))) * t

print("t1 = ", t1, "t2 = ", t2)
type = [1 / t1, 1 / t2]  # интенсивность
prob = [q, 1 - q]  # вероятность

for _ in range(size):
    chosen = np.random.choice(2, p=prob)
    print('{0:10f}'.format(np.random.exponential(1 / type[chosen])).replace('.', ','))
