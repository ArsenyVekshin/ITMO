import numpy as np

matrix = []
free_v = []

filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    for _ in range(int(input("Размер матрицы: "))):
        row = list(map(float, input().split()))
        matrix.append(row[:-1])
        free_v.append(row[-1])
else:
    with open(filename, 'r') as file:
        lines = file.readlines()
        matrix = [list(map(float, line.strip().split())) for line in lines]
        for i in range(len(matrix)):
            free_v.append(matrix[i][-1])
            matrix[i].pop(-1)

# Определение матрицы коэффициентов A и вектора свободных членов b
A = np.array(matrix)
b = np.array(free_v)

# Решение СЛАУ
x = np.linalg.solve(A, b)

print("Решение СЛАУ:")
print(x)

det_A = np.linalg.det(A)

print("Определитель матрицы A:")
print(det_A)