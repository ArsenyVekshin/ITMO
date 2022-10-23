# isu=367133 --> var=5
import re

alph = ["БВГДЖЗЙКЛМНПРСТФХЦЧШЩбвгджзйклмнпрстфхцчшщ",
        "АаЕеЁёИиОоУуЫыЭэЮюЯя"]

def main(data):
    out=""
    data = re.split(r"\s", data)

    # костыль чтобы избавиться от предлогов итп
    _data=[]
    for i in range(len(data)):
        if(len(data[i])>=3):
            _data.append(data[i])
    data =_data

    for i in range(len(data)-1):
        # проверяем первое слово на гласные
        c0 = len(re.findall(r"[АаЕеЁёИиОоУуЫыЭэЮюЯя]{2}", data[i]))
        if (c0 > 0):
            # проверяем второе слово на согласные
            c1 = len(re.findall(r"[БВГДЖЗЙКЛМНПРСТФХЦЧШЩбвгджзйклмнпрстфхцчшщ]", data[i+1]))
            if (c1 <= 3):
                out+= data[i] + " "
    return out

def test():
    f = open("task2_data.txt", encoding="utf-8")
    for i in range(5):
        data = f.readline()
        print(main(data))

test()