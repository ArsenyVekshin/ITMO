%include "lib.inc"
%include "words.inc"

extern exit
extern read_string
extern find_word
extern print_string
extern print_newline

section .rodata
long_word_err_mes: db 'Max word length is 256 symb', 10, 0
not_found_err_mes: db 'There is no such word in dictionary', 10, 0
%define BUF_SIZE 256

section .text
global _start

; ������ ������ �������� �� ����� 255 �������� � ����� � stdin
; �������� ����� ��������� � �������:
;	���� �������, ������������� � stdout �������� �� ����� �����. 
;	����� ������ ��������� �� ������.
_start:
	call read_key

	push rdx		; ��������� ������ ������

	mov rdi, rax	; ����� ������ -> �������� "��������� �� ������"
	call find_key

	pop rdx
	add rax, 8		; �������� ��������� �� ���� �� ������ � ������� 
	add rax, rdx	; ������ ����� + ��������� -> rax
	inc rax			; ��������� ������ ����� ������

	call print_string
	call print_newline	

	xor rdi, rdi
	jmp exit

; ����� ����� �� �������
; ���������:
;	rdi - ��������� �� ������
; �����:
;	rax - ����� �������� �����
find_key:
	mov rsi, pointer	; ����� ������ ������� -> �������� 
	call find_word

	test rax, rax		; �������� �� ������ � �������? 
	je not_found_err
	ret

; ��������� ���� �� ������
; ���������� ��������� �������� � �����
; �����:
;	rax - ��������� �� �����
;	rdx - ������ ����������� �����
read_key:
	mov rsi, BUF_SIZE	; 256 -> �������� "������ ������"
	mov rdi, rsp	
	sub rdi, rsp	; rdi-rsp -> �������� "��������� �� ������ ������"

	call read_string

	cmp rax, 0		; �������� �� ������ � �������?
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
	mov rdi, 1
	jmp exit