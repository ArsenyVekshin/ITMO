# isu=367133 --> var=3
import re

def main(data):
    data = re.split(r"[. \-]", data)
    for i in range(1,len(data)-1):
        if(len(data[i])==0): continue
        if(data[i][0]!=data[0][0]): return True
    return False

def test():
    f = open("task3_data.txt", encoding="utf-8")
    for i in range(5):
        data = f.readline()
        if(main(data)):
            print(data)

test()