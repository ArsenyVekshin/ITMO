P=$(shell pwd)

.PHONY: all clean rebuld

all:
	nasm -g $(P)/hello.asm -felf64 -o hello.o
	ld -o executable hello.o
	#gcc -g -O0 -o ./executable main.c

clean:
	rm -f ./executable

rebuild:
	clean all
	