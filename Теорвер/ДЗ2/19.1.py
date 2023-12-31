start = [
    189, 207, 213, 208, 186, 210, 198, 219, 231, 227,
    202, 211, 220, 236, 227, 220, 210, 183, 213, 190,
    197, 227, 187, 226, 213, 191, 209, 196, 202, 235,
    211, 214, 220, 195, 182, 228, 202, 207, 192, 226,
    193, 203, 232, 202, 215, 195, 220, 233, 214, 185,
    234, 215, 196, 220, 203, 236, 225, 221, 193, 215,
    204, 184, 217, 193, 216, 205, 197, 203, 229, 204,
    225, 216, 233, 223, 208, 204, 207, 182, 216, 191,
    210, 190, 207, 205, 232, 222, 198, 217, 211, 201,
    185, 217, 225, 201, 208, 211, 189, 205, 207, 199
]
start.sort()
print("ПУНКТ А: ")
for i in range(10):
    print(start[10*i:10*(i+1)])

print("Находим размах варьирования: ω=x_max-x_min=",max(start), "-", min(start), "=", max(start) - min(start) )
h = round((max(start) - min(start)) / 9, 2)
print(f"h: {h}" + "\n")

intervals = {1:[],2:[],3:[],4:[],5:[],6:[],7:[],8:[],9:[]}
F_value = 0
for i in range(1, 10):
    left = round(min(start) + h*(i-1), 2)
    right = round(min(start) + h*i, 2)
    center = round((right + left)/2, 2)
    freq = 0
    for z in start:
        if z >= left and z <= right:
            freq += 1
    rel_freq = round(freq/100, 2)
    density = round(rel_freq/h, 2)
    F_value += rel_freq
    intervals[i] = [left,
                    right,
                    center,
                    freq,
                    rel_freq,
                    density,
                    F_value]
#intervals[9][3] += 1
print("ПУНКТ Б: ")
for i in intervals:
    print([i]+intervals[i])
print("\n")

print("ПУНКТ В: ")
print("График полигона частот: X, Y")
for i in intervals:
    print(intervals[i][2], ",", intervals[i][3])
print("Гистограмма относительных частот: X, Y")
for i in intervals:
    print(intervals[i][0], ",", intervals[i][4])
print("График функции F: X, Y")
for i in intervals:
    print(intervals[i][1], ",", intervals[i][-1])



ras_interv = {1:[],2:[],3:[],4:[],5:[],6:[],7:[],8:[],9:[]}
for i in intervals:
    a = round(intervals[i][2] * intervals[i][3], 2)
    b = round(intervals[i][2]**2, 2)
    c = round(intervals[i][3] * b, 2)
    ras_interv[i] = [a,b,c]



print("ПУНКТ Г: ")
for i in ras_interv:
    print([i]+ras_interv[i])

vib_sr = 0
for i in ras_interv:
    vib_sr += ras_interv[i][0]
vib_sr = round(vib_sr / 100, 2)
print(f"ВЫБОРОЧНОЕ СРЕДНЕЕ: {vib_sr}")

vib_disp = 0
for i in ras_interv:
    vib_disp += ras_interv[i][2]
vib_disp = round(vib_disp / 100 - vib_sr**2, 2)
print(f"ВЫБОРОЧНАЯ ДИСПЕРСИЯ: {vib_disp}")

vib_sko = round(vib_disp**0.5, 2)
print(f"ВЫБОРОЧНОЕ СКО: {vib_sko}")

vib_isp_disp = round(100/99 * vib_disp, 2)
print(f"ВЫБОРОЧНАЯ ИСПРАВЛЕННАЯ ДИСПЕРСИЯ: {vib_isp_disp}")

vib_isp_sko = round(vib_isp_disp**0.5, 2)
print(f"ВЫБОРОЧНОЕ СКО: {vib_isp_sko}")

start_new_ch = {1:[],2:[],3:[],4:[],5:[],6:[],7:[],8:[],9:[]}
for i in intervals:
    left = round(intervals[i][0] - vib_sr, 2)
    right = round(intervals[i][1] - vib_sr, 2)
    z_left = round(left / vib_sko, 2)
    z_right = round(right / vib_sko, 2)
    start_new_ch[i] = [left,
                       right,
                       z_left,
                       z_right]
print("\n")
print("ПУНКТ Д:")
for i in start_new_ch:
    print([i]+start_new_ch[i])
