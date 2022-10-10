import sys

def ReadActErrBites(data):
    arr = [0, 0, 0]
    arr[0] = (data[2] + data[4] + data[6]) % 2
    arr[1] = (data[2] + data[5] + data[6]) % 2
    arr[2] = (data[4] + data[5] + data[6]) % 2
    return arr

def CheckInputValue(data):
    if len(data) != 7: return False
    for s in data:
        try:
            buff = int(s)
            if buff not in [0,1]:
                return False

        except ValueError:
            return False
    return True

InData = input()
if not CheckInputValue(InData):
    print("Wrong input data")
    print("Make sure, that you use 7-bit binary word")
    sys.exit()

bytes = list(map(int, list(InData)))
ContBitesRed = [bytes[0], bytes[1], bytes[3]]
ContBitesAct = ReadActErrBites(bytes)
DataBites = [bytes[2], bytes[4], bytes[5], bytes[6]]

print("Input: ", bytes, "  -  ", [bytes[2]] + bytes[4:7])
if ContBitesAct != ContBitesRed:
    Hsum1 = 0
    for i in range(len(ContBitesRed)):
        if ContBitesAct[i] != ContBitesRed[i]:
            Hsum1 += 2**i

    print('error found in ', Hsum1, " bit")
    bytes[Hsum1 - 1] = 1 if bytes[Hsum1 - 1] == 0 else 0

    print("correct value: ", bytes, "  -  ", [bytes[2]] + bytes[4:7])

else:
    print("data is correct")
