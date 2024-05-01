import numpy as np
import matplotlib.pyplot as plt
import math

from methods import base_iter_system

equation_idx, a, b,accuracy = 0, 0, 0, 0
system = [
    [lambda x, y: 3 + math.cos(y),          # x = ...
     lambda x, y: 0.5 - math.cos(x - 1)],   # y = ...

    [lambda x, y: (2 + math.cos(y))/2,      # x = ...
     lambda x, y: math.sin(x + 1) - 1.2],   # y = ...

    [lambda x, y: 0.7 - math.cos(y-1),      # x = ...
     lambda x, y: (2 - math.sin(x))/2]      # y = ...

]

deriv_system = [
    [lambda x, y: -1 * math.sin(y),         # x = ...
     lambda x, y: math.sin(x - 1)],         # y = ...

    [lambda x, y: -1 * math.sin(y)/2,       # x = ...
     lambda x, y: math.cos(x + 1)],         # y = ...

    [lambda x, y: math.sin(y-1),            # x = ...
     lambda x, y: -1 * math.cos(x)/2]       # y = ...

]

def show_plot():
    x_linspace = np.linspace(-1, 5, 1000)
    y_linspace = np.linspace(-2, 3, 1000)
    f1_arr = [system[equation_idx][0](0, i) for i in y_linspace]
    f2_arr = [system[equation_idx][1](i, 0) for i in x_linspace]
    plt.plot(f1_arr, y_linspace, color='blue')
    plt.plot(x_linspace, f2_arr, color='red')
    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)
    plt.show()


filename = input("Введите путь до файла или нажмите Enter: ")
if filename == '':
    print(""" Выберите систему:
    0)  ┌ cos(x-1) + y = 0.5    <-->    ┌ x = 3 + cos(y)
        └ x - cos(y) = 3        <-->    └ y = 0.5 - cos(x-1)
    1)  ┌ sin(x+1) - y = 1.2    <-->    ┌ x = (2 + cos(y))/2
        └ 2x + cos(y) = 2       <-->    └ y = sin(x+1) -1.2
    2)  ┌ sin(x) + 2y = 2       <-->    ┌ x = 0.7 - cos(y-1)
        └ x + cos(y-1) = 0.7    <-->    └ y = (2 - sin(x))/2
            """)
    equation_idx = int(input("Номер системы: "))
    show_plot()
    print("АХТУНГ: при вводе приближения на большом удалении точность не гарантируется!")
    a, b = map(float, input("Введите изначальное приближение: ").split())
    accuracy = float(input("Введите точность: "))


else:
    with open(filename, 'r') as file:
        equation_idx = int(file.readline().strip())
        a, b = map(float, file.readline().strip().split())
        accuracy = float(file.readline().strip())

        print("Чтение из файла успешно")
        print("Номер системы: ", equation_idx)
        print("Изначальное приближение: ", a , b)
        print("Точность: ", accuracy)
        show_plot()

base_iter_system.is_possible(deriv_system[equation_idx], a, b)
x, y = base_iter_system.solve(system[equation_idx], a, b, accuracy)
print("Найдено решение x =", x, "y =", y)
print("Дельты: delta_x = ", x - system[equation_idx][0](x,y), "delta_y = ", y - system[equation_idx][1](x,y), '\n')



