import math as m

# АХТУНГ!
# Селина крутит правиласи округления как ей хочется...
# Тут все округления ВВЕРХ!

# вторая строка исходных данных
data = [427, 235, 72, 21, 1, 1]
n = sum(data)

X = 0
for i in range(len(data)):
    X += data[i] * i
print("lambda = X =", X, "/", sum(data), "=", round(X / sum(data), 1))
X = round(X / sum(data), 1)

table1 = []
for i in range(len(data)):
    p_i = X ** i * m.e ** (-1 * X) / m.factorial(i)
    table1.append([i, data[i], round(p_i, 5), m.ceil(p_i * n)])

print()
print("i\t| ni\t| pi\t\t| ni*")
for i in range(len(data)):
    print(i, "\t|", table1[i][1], "\t|", table1[i][2], "\t|", table1[i][3])
print("sum:", sum(data))


table2 = []
for i in range(len(table1)):
    if n*table1[i][2] < len(data)-1:
        table2[-1][1] += table1[i][1]
        table2[-1][2] += table1[i][3]
        if(i == len(data)-1):
            table2[-1][3] = round((table2[-1][1] - table2[-1][2]) ** 2 / table2[-1][2], 3)
    else:
        table2.append([i, table1[i][1], table1[i][3], round((table1[i][1] - table1[i][3]) ** 2 / table1[i][3], 3)])

print()
print("i\t| ni\t| ni*\t| */*")
for i in range(len(table2)):
    print(i, "\t|", table2[i][1], "\t|", table2[i][2], "\t|", table2[i][3])

X_nabl = 0
for i in table2:
    X_nabl+=i[-1]
print("X2 набл:", X_nabl)

print("Дальше уже работа с таблицами: Удачи!")
