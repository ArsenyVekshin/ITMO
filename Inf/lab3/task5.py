import re

def main(data):
    data = list(data.split(" "))
    for val in data:
        _val = re.search(r"(^[w])(\S*)($[w])", val)
        print(_val)
        """for s in _val:
            q=s.replace("w", "2", -1)
            print(q)"""

main("wow widow wolk ww www w@")