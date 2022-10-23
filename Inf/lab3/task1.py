from random import *
import re

seed()
alph=[[":", ";", "X", "8", "=", "["],
          ["-", "<", "-{", "<{"],
          ["(", ")", "O", "|", '\\', "/", "P"]
          ]
isu = 367133

def testGenerator(ln, patern):
    out=""
    for i in range(ln):
        if (random() < 0.2): out+=" "
        q=randint(0,2)
        out += alph[q][randint(0, len(alph[q])-1)]

    for _ in range(len(out)):
        i = randint(0, len(out))
        out = out[:i] + patern + out[i:]
    return out

def main(isu, data):
    patern = alph[0][isu%6] + alph[1][isu%4] + alph[2][isu%7]
    patern = re.escape(patern)
    return len(re.findall(patern, data))

def test():
    f=open("task1_data.txt")
    for i in range(5):
        data = f.readline()
        print("Ans:", main(isu, data))


#for _ in range(6):
#    print(testGenerator(randint(100, 10000), alph[0][isu%6] + alph[1][isu%4] + alph[2][isu%7]))
test()