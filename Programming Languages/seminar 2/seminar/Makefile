P=$(shell pwd)

.PHONY: all clean rebuld

all:
	nasm -g $(P)/test.asm -felf64 -o test.o
	ld -o executable test.o
	#examples:
	#nasm -g $(P)/hello-2.asm -felf64 -o hello-2.o
	#ld -o executable hello-2.o
	#gcc -g -O0 -o ./executable main.c

	#task1.1
	#nasm -g $(P)/hello-2.asm -felf64 -o hello-2.o
	#ld -o executable hello-2.o

	#task1.2
	#nasm -g $(P)/print-hex.asm -felf64 -o print-hex.o
	#ld -o executable print-hex.o

	#task2
	#nasm -g $(P)/print_string.asm -felf64 -o print_string.o
	#ld -o executable print_string.o

	#task3


	#task4
	#nasm -g $(P)/check_int.asm -felf64 -o check_int.o
	#ld -o executable check_int.o

clean:
	rm -f ./executable

rebuild:
	clean all
	