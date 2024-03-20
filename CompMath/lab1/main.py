import sys
from utils.matrix_tools import *

matrix = []

filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    for _ in range(int(input("Размер матрицы: "))):
        row = list(map(float, input().split()))
        matrix.append(row)
else:
    with open(filename, 'r') as file:
        lines = file.readlines()
        matrix = [list(map(float, line.strip().split())) for line in lines]

entered_matrix = matrix.copy()

#matrix = sort_matrix(matrix)
print_matrix(matrix)
print()

print("Прямой ход: ")
for i in range(len(matrix)):
    if is_triangle(matrix): break
    print("\nшаг", i + 1)
    for j in range(i + 1, len(matrix)):
        if matrix[i][i] == 0 : break
        if matrix[j][i] == 0 : continue

        a = mul_line_by(matrix[i], -1.0 * matrix[j][i] / matrix[i][i])
        matrix[j] = sum_lines(matrix[j], a)
    print_matrix(matrix)
    print("Triangle: ", is_triangle(matrix))


print("\nРезультат прямого хода:")
print_matrix(matrix)
if not is_triangle(matrix):
    print("Матрицу невозможно привести к треугольному виду - вычисления окончены")
    sys.exit(1)

result = []
print("Обратный ход:", end='')
for i in range(len(matrix)-1, -1, -1):
    print(".", end='')
    for j in range(i+1, len(matrix[i])-1):  # переносим все свободные члены вправо
        matrix[i][-1] += -1 * matrix[i][j]
        matrix[i][j] = 0

    matrix[i][-1] /= matrix[i][i]   # считаем значение переменной
    matrix[i][i] = 1

    for j in range(i-1, -1, -1):    # заменяем остальные вхождения этой переменной на число
        matrix[j][i] *= matrix[i][-1]
    result.append(matrix[i][-1])
print("done")
result.reverse()

print("Вектор решений:", result)
print("Вектор несвязок:", calc_error_rate(entered_matrix, result))