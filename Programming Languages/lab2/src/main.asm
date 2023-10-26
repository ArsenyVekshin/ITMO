%include "lib.inc"
%include "words.inc"

extern exit
extern read_word
extern find_word
extern print_string
extern print_newline

section .rodata
long_word_err_mes: db 'Max word length is 256 symb', 10, 0
not_found_err_mes: db 'There is no such word in dictionary', 10, 0


section .text
global _start

; Читает строку размером не более 255 символов в буфер с stdin
; Пытается найти вхождение в словаре:
;	Если найдено, распечатывает в stdout значение по этому ключу. 
;	Иначе выдает сообщение об ошибке.
_start:
	call read_key

	push rdx		; найденная длинна строки

	mov rdi, rax	; адрес буфера -> аргумент "указатель на строку"
	call find_key

	pop rdx
	add rax, 8		; получаем указатель на ключ из записи в словаре 
	add rax, rdx	; длинна ключа + указатель -> rax
	inc rax			; учитываем символ конца строки

	call print_string
	call print_newline	

	mov rdi, 0
	jmp exit

; Поиск ключа по словарю
; Аргументы:
;	rdi - указатель на строку
; Вывод:
;	rax - адрес искомого ключа
find_key:
	mov rsi, pointer	; адрес начала словаря -> аргумент 
	call find_word

	cmp rax, 0			; возникла ли ошибка в функции? 
	je not_found_err
	ret

; Считывает ключ из строки
; записывает считанное значение в буфер
; Вывод:
;	rax - указатель на буфер
;	rdx - длинна полученного ключа
read_key:
	mov rsi, 256	; 256 -> аргумент "размер буфера"
	mov rdi, rsp	
	sub rdi, rsp	; rdi-rsp -> аргумент "указатель на начало буфера"

	call read_word

	cmp rax, 0		; возникла ли ошибка в функции?
	je long_word_err
	ret

not_found_err:
	mov rdi, not_found_err_mes
	jmp print_error

long_word_err:
	mov rdi, long_word_err_mes
	jmp print_error

print_error:
	call print_string
	mov rdi, 0
	jmp exit