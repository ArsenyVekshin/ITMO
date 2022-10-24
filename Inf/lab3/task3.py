# isu=367133 --> var=3
import re
groupe = "P3116"
groupe = re.escape(groupe)


def main(data):
    data = re.findall(r"[АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ]", data)
    for i in range(1,len(data)):
        if(data[i]!=data[0]): return True
    return False

def test():
    f = open("task3_data.txt", encoding="utf-8")
    data="   "
    while(len(data)>0):
        data = f.readline()
        flag = re.search(groupe, data)
        if(flag == None): continue
        if(main(data)):
            print(data)

test()