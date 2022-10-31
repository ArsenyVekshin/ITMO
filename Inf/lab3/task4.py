import re
def isValuedPass(data, _pass):
    flag = re.search(r"\S{6,20}", data)
    if(flag==None): return False
    if (_pass == data):
        return True
    return False

print(isValuedPass("asdfghj1", "testpass"))


