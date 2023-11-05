P = $(shell pwd)
ASM=nasm
ASM_KEYS=-f elf64 -o

%.o:%.asm
	$(ASM) $(ASM_KEYS) $@ $<

main: main.o dict.o lib.o
	ld -o main main.o dict.o lib.o

clean:
	rm -rf *.o main

test:
	python3 test.py

.PHONY: clean test