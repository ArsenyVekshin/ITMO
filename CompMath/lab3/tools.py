COLOUMN_SIZE = 7
def print_table_row(row):
    for i in row:
        print(str(round(i, 4)).ljust(COLOUMN_SIZE), end=' | ')
    print()


def print_table_header(row):
    for i in row:
        print(str(i).ljust(COLOUMN_SIZE), end=' | ')
    print("\n" + len(row) * (COLOUMN_SIZE+3) * "-")


def mid(a, b):
    return (a+b)/2

def is_dif_sign(a, b):
    return (a>0 and b<0) or (a<0 and b>0)

def add_vertical_line(x, arr):
    arr[0].append(x)
    arr[1].append(0)
    arr[0].append(x)
    arr[1].append(1)