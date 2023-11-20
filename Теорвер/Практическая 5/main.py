# Вариант 3
# Каждый студент получает выборку из 20 чисел. Необходимо определить следующие статистические
# характеристики: вариационный ряд, экстремальные значения и размах, оценки математического
# ожидания и среднеквадратического отклонения, эмпирическую функцию распределения и её
# график, гистограмму и полигон приведенных частот группированной выборки. Для расчета
# характеристик и построения графиков нужно написать программу на одном из языков
# программирования. Листинг программы и результаты работы должны быть представлены в отчете
# по практической работе
import math as m
import matplotlib.pyplot as plt

data = [0.83, 0.73, -0.59, -1.59, 0.38, 1.49, 0.14, -0.62, -1.59, 1.45, -0.38, -1.49, -0.15, 0.63, 0.06, -1.59, 0.61, 0.62, -0.05, 1.56]

print("Исходные данные:\n", data)
data.sort()
print("Вариационный ряд:\n", data, "\n")

print("Первая порядкавая статистика:", data[0])
print("n-я порядкавая статистика:", data[-1])
print("размах выборки:", '%.2f' % (data[-1] - data[0]), "\n")

data_uniq = list(set(data)) # набор уникальных значений
data_uniq.sort()
occur_num = [] # количество вхоождений для каждого уникального значения
p_arr = []  # вероятность для каждого уникального значения

for i in data_uniq:
    occur_num.append(data.count(i))
    p_arr.append(occur_num[-1]/len(data))

# мат. ожидание
M = 0
for i in range(len(data_uniq)):
    M += data_uniq[i] * p_arr[i]
print("Математическое ожидание:", M)

# дисперсия и среднеквадратическое отклонение
D = -1 * M**2
for i in range(len(data_uniq)):
    D += data_uniq[i]**2 * p_arr[i]
print("Дисперсия:", D)
print("Среднеквадратическое отклонение:", m.sqrt(D))
print()

# Функция распределения
print("Функция F:")
F = [0]
print(F[-1], "\t\t для x <=", data_uniq[0])
for i in range(1, len(data_uniq)):
    F.append(F[-1]+p_arr[i-1])
    print('%.2f' % F[-1], "\t для", data_uniq[i-1], "< x <=", data_uniq[i])
F.append(1)
print(F[-1], "\t\t для", data_uniq[-1], "< x")
print()

# Интервальный статистический ряд
print("Интервальный статистический ряд:")
dx = round((data_uniq[-1] - data_uniq[0]) / (1+m.log(20, 2)), 2)
start = data_uniq[0] - dx/2
finish = start + dx
axisX = []
axisY = []
_value = 0
for i in range(len(data_uniq)):
    if data_uniq[i] < finish:
        _value += occur_num[i]
    else:
        axisX.append((start+finish)/2)
        axisY.append(_value / len(data))
        print("[", '%.2f' % start, ",", '%.2f' % finish, "):\tчастота:", _value, "частотность:", '%.2f' % axisY[-1])
        _value = occur_num[i]
        start = finish
        finish = start + dx
print("[", '%.2f' % start, ",", '%.2f' % finish, "):\tчастота:", _value, "частотность:", '%.2f' % axisY[-1])

x, y = [data_uniq[0]-0.5], [0]
_pointer = 0
for i in data_uniq:
    x.append(data_uniq[_pointer])
    x.append(data_uniq[_pointer])
    y.append(F[_pointer])
    y.append(F[_pointer+1])
    _pointer+=1
x.append(data_uniq[-1]+0.5)
y.append(1)

fig = plt.figure()
ax = fig.add_subplot(1, 1, 1)
ax.spines['left'].set_position('center')
ax.spines['bottom'].set_position('zero')
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')
ax.xaxis.set_ticks_position('bottom')
ax.yaxis.set_ticks_position('left')
plt.plot(x, y, 'b')
plt.show()

plt.bar(axisX, axisY)
plt.show()

plt.plot(axisX, axisY, marker="o")
plt.show()
