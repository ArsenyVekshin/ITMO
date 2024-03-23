def print_table_row(row):
    for i in row:
        print(str(round(i, 3)).ljust(5), end=' ')

def mid(a, b):
    return (a+b)/2