import sys

import numpy as np

from utils.matrix_tools import *

matrix = []
movements_num = 0

filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    for _ in range(int(input("Размер матрицы: "))):
        row = list(map(float, input().split()))
        matrix.append(row)
else:
    with open(filename, 'r') as file:
        lines = file.readlines()
        matrix = [list(map(float, line.strip().split())) for line in lines]

entered_matrix = [row.copy() for row in matrix]

print("Введенная матрица:")
print_matrix(matrix)
print()


if(not is_matrix_correct(matrix)):
    print("Обнаруженна ошибка в исходных данных")

    print("Попытка исправить ошибку сортировкой...")
    matrix = sort_matrix(matrix)
    print_matrix(matrix)

    if (not is_matrix_correct(matrix)):
        print("Введенная матрица содержит ошибку, проверьте корректность ввода")
        sys.exit(1)

    movements_num = calc_move_num(matrix, entered_matrix)
    print("Количество перестановок:", movements_num)


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


print("Определитель:", calc_triangle_det(matrix, movements_num))

left, right = default_matrix_to_numpy([row.copy() for row in matrix])
print("Определитель от библиотеки:", np.linalg.det(np.array(left)))

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

