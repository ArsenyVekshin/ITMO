def print_matrix(matrix):
    for row in matrix:
        for i in row:
            print(str(round(i, 5)).ljust(8), end=' ')
        print()


def sort_matrix(matrix):
    powers = []
    for line in matrix:
        powers.append(0)
        for i in range(len(line)):
            powers[-1] += (1 if line[i] != 0 else 0) * pow(2, len(line) - i)
    return [row for _, row in sorted(zip(powers, matrix), key=lambda x: x[0], reverse=True)]


def mul_line_by(line, val):
    new_line = []
    for i in range(len(line)):  new_line.append(line[i] * val)
    return new_line


def sum_lines(line1, line2):
    new_line = []
    for i in range(len(line1)):  new_line.append(line1[i] + line2[i])
    return new_line


def is_triangle(matrix):
    for i in range(1, len(matrix)):
        for j in range(i):
            if matrix[i][j] != 0.0: return False

    return True

def calc_error_rate(matrix, result):
    deltas = []
    for i in range(len(matrix)):
        buff = 0    # сумма слева
        for j in range(len(matrix[0]) - 1):
            buff += matrix[i][j] * result[j]
        deltas.append(buff - matrix[i][-1])
    return deltas

def calc_triangle_det(matrix, k):
    out = 1
    for i in range(len(matrix)):
        out *= matrix[i][i]
    return out * pow(-1, k)

def is_matrix_correct(matrix):
    out_flag =0
    #Проверим на квадратность
    for i in range(len(matrix)):
        if(len(matrix[i]) != len(matrix)+1):
            print("некоректный размер матрицы")
            return False

    #проверка на нулевые строки
    for i in range(len(matrix)):
        flag = True
        for j in range(len(matrix[i])-1):
            if(matrix[i][j]!=0.0):
                flag = False
                break
        if flag:
            print("обнаружены нулевые строки")
            return False

    # проверка на нулевые столбцы
    for i in range(len(matrix)):
        flag = True
        for j in range(len(matrix)):
            if (matrix[i][j] != 0.0):
                flag = False
                break
        if flag:
            print("обнаружены нулевые столбы")
            return False

    for i in range(len(matrix)):
        if(matrix[i][i]) == 0:
            print("На диагонали обнаружены нулевые элементы")
            return False

    return True


def calc_move_num(matrix1, matrix2):
    out = 0
    for i, j in matrix1, matrix2:
        if i != j: out += 1
    return out//2

def default_matrix_to_numpy(matrix):
    new_matrix = matrix.copy()
    free_v =[]
    for i in range(len(matrix)):
        free_v.append(matrix[i][-1])
        new_matrix[i].pop(-1)
    return new_matrix, free_v