ROUND_LVL = 4
COLOUMN_SIZE = 8
def print_table_row(row):
    for i in row:
        if type(i) == str:
            print(i.ljust(COLOUMN_SIZE), end=' | ')
        else:
            print(str(round(i, 4)).ljust(COLOUMN_SIZE), end=' | ')
    print()


def print_table_header(row):
    for i in row:
        print(str(i).ljust(COLOUMN_SIZE), end=' | ')
    print("\n" + len(row) * (COLOUMN_SIZE+3) * "-")