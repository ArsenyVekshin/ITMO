section .text
 
global exit
global string_length
global print_string
global print_char
global print_newline
global print_uint
global print_int
global string_equals
global read_char
global read_word
global read_string
global parse_uint
global parse_int
global string_copy
 
; ��������� ��� �������� � ��������� ������� �������
exit: 
    mov rax, 60
    syscall

; ��������� ��������� �� ����-��������������� ������, ���������� � �����
; ���������:
;   rdi - ����� ������ ������
; �����:
;   rax - ������ ������
string_length:
    xor rax, rax
  .loop:
    cmp byte [rdi+rax], 0
    je .end
    inc rax
    jmp .loop
  .end: 
    ret

; ������ N �������� �� ������ � stdout
; ���������:
;   rsi - ���������� ��������
;   rdi - ����� ������ 
print_N_symb:
    mov  rdx, rsi   ; �������� syscall "������ ������"
    mov  rsi, rdi   ; ��������� syscall "����� ������ ������"
    mov rax, 1      ; ���� syscall "������"
    mov rdi, 1      ; ���� syscall "stdout"
    syscall         ; ������ ������� �� ������
    ret

; ��������� ��������� �� ����-��������������� ������, ������� � � stdout
; ���������:
;   rax - ��������� �� ������ ������
print_string:
    push rdi        ; ��������� �������� ����� ������� �������
    call string_length
    pop rdi         ; �������������� ��������� ���������
    mov rsi, rax    ; ����� ������� -> ��������� syscall "����� ������ ������" 
    call print_N_symb
    ret

; ��������� ��� ������� � ������� ��� � stdout
; ���������:
;   rdi - ��������� �� ������ ������
print_char:
    push rdi        ; ��������� ��������� ������ �� �����
    mov rax, 1      ; ���� syscall "������"
    mov rdi, 1      ; ���� syscall "stdout"
    mov rsi, rsp    ; ����� ������� � ����� -> ��������� syscall "����� ������ ������"
    mov rdx, 1      ; �������� syscall "������ ������"
    syscall         ; ������ ������� �� ������
    pop rdi         ; ����������� ����� � �������� ���������
    ret

; ��������� ������ (������� ������ � ����� 0xA)
; ���������:
; rdi - ����� ������ 
print_newline: 
    mov rdi, 0xA
    call print_char
    ret

; ������� ����������� 8-�������� ����� � ���������� ������� 
; �����: �������� ����� � ����� � ������� ��� ���������� �������
; �� �������� ��������� ����� � �� ASCII ����.
; ���������:
;   rdi - �����
; ��������:
;   rdi - ������� �����
;   r12 - ������� �� �������
print_uint:
    push r12
    mov r12, 10
    mov rax, rdi    
    mov rdi, rsp    
    dec rdi         
    push 0              ; ������ ��������� ������
    sub rsp, 16         ; ��������� ������ � ����� ��� ������
.loop:
    xor rdx, rdx        ; ��������� rdx
    div r12             ; ������������� ������� �� 10
    add rdx, '0'        ; ��������� ���� ������������ �������
    dec rdi
    mov byte[rdi], dl
    test rax, rax       ; ��������, ���������� �� ���������� 10-���� ������ �����
    jnz .loop
    call print_string
    add rsp, 24         ; ������� ����� � �������� ���������
    pop r12
    ret


; ������� �������� 8-�������� ����� � ���������� ������� 
; ���������:
;   rdi - �����
; ��������:
;   rdi - ������� �����
;   r12 - ������� �� �������
print_int:
    push r12
    cmp rdi, 0      ; ����������� ����� �����
    jge .positive
    push rdi
    mov rdi, '-'
    call print_char ; ���� ����� ������������� - ������� '-' � ����������� ��������
    pop rdi         
    neg rdi
.positive:
    call print_uint ; ����� ������������ ������ �����
    pop r12
    ret



; ��������� ��� ��������� �� ����-��������������� ������, ���������� 1 ���� ��� �����, 0 �����
; ���������:
;   rdi - ��������� �� ������ 1� ������
;   rsi - ��������� �� ������ 2� ������
; ��������:
;   rcx - ������� �����
; �����:
;   rax - 1 - �� / 0 - ���
string_equals:
    cmp rcx, 0       ; �������� rcx
  .loop:
    mov al, byte[rdi + rcx] ; ������ 1 -> �����
    cmp al, byte[rsi + rcx] ; ������ 1 == ������ 2?
    jne .false
    inc rcx
    cmp al, 0               ; ������ == ����� ������?
    jne .loop
    mov rax, 1              ; ���������� 1
    ret

  .false:
    xor rax, rax    ; ���������� 0
    ret


; ������ ���� ������ �� stdin � ���������� ���. ���������� 0 ���� ��������� ����� ������
read_char:
    mov rax, 0      ; ���� syscall "������"
    mov rdi, 0      ; ���� syscall "stdin"
    push 0          ; ����� � �����
    mov rsi, rsp    ; ����� ������� � ����� -> ��������� syscall "����� ������ ������"
    mov rdx, 1      ; �������� syscall "������ ������"
    syscall         ; ������ ������� �� ������
    pop rax
    ret 


; ���������: ����� ������ ������, ������ ������
; ������ � ����� ����� �� stdin, ��������� ���������� ������� � ������, .
; ���������� ������� ��� ������ 0x20, ��������� 0x9 � ������� ������ 0xA.
; ��������������� � ���������� 0 ���� ����� ������� ������� ��� ������
; ��� ������ ���������� ����� ������ � rax, ����� ����� � rdx.
; ��� ������� ���������� 0 � rax
; ��� ������� ������ ���������� � ����� ����-����������
; ���������:
;   rdi - ��������� �� ������ ������
;   rsi - ������ ������
;   [input_rax, input_rax + input_rsi] - �����
; ��������:
;   rdx - ������� �����
; �����:
;   rax - ����� ������ ������
;   rdx - ������ ������
read_word:
    mov rax, 0      ; �������� rax
    mov rdx, 0      ; �������� rdx
    push rdi

  .space:
    push rdi        ; ��������� �������� ����� ������� �������
    push rsi
    push rdx
    call read_char  ; ����� ������� -> rax
    pop rdx         ; �������������� ��������� ���������
    pop rsi
    pop rdi

    cmp al, 0x20   ; �������� �� "���������� �������"
    je .space
    cmp al, 0x9
    je .space
    cmp al, 0xA
    je .space
    cmp al, 0x0
    je .return
  .loop:
    cmp rsi, rdx    ; �������� �� ������������ ������
    je .err
    cmp al, 0x20   ; �������� �� "���������� �������"
    je .return
    cmp al, 0x9
    je .return
    cmp al, 0xA
    je .return
    cmp al, 0x0
    je .return
    mov byte[rdi + rdx], al    ; ��������� ������ -> �����
    inc rdx         ; 
    push rdi        ; ��������� �������� ����� ������� �������
    push rsi
    push rdx
    call read_char  ; ����� ������� -> rax
    pop rdx         ; �������������� ��������� ���������
    pop rsi
    pop rdi 
    jmp .loop
  .err:
    pop rax
    xor rax, rax
    ret

  .return:
    mov byte[rdi + rdx], 0
    pop rax           ; ������ ������
    ret
 

; ��������� ��������� �� ������, ��������
; ��������� �� � ������ ����������� �����.
; ���������� � rax: �����, rdx : ��� ����� � ��������
; rdx = 0 ���� ����� ��������� �� �������
; ���������:
;   rdi - ��������� �� ������ ������
; ��������:
;   rcx - �������������� ������
;   rdx - ������� �����
; �����:
;   rax - �����
;   rdx - ������ ������
parse_uint:
    mov rax, 0          ; �������� rax
    mov rdx, 0          ; �������� rdx
    mov rcx, 0          ; �������� rcx
    cmp byte [rdi], 0   ; ������ ������?
    je .return
  .loop:
    movzx rcx, byte [rdi + rdx] ; ��������� ������ � �����
    cmp rcx, '0'                ; ������ == �����?
    jb .return
    cmp rcx, '9'
    ja .return

    push rdx
    imul rax, rax, 10
    pop rdx

    sub rcx, '0'
    add rax, rcx

    inc rdx
    jmp .loop
  .return:
    ret

; ��������� ��������� �� ������, ��������
; ��������� �� � ������ �������� �����.
; ���� ���� ����, ������� ����� ��� � ������ �� ���������.
; ���������� � rax: �����, rdx : ��� ����� � �������� (������� ����, ���� �� ���) 
; rdx = 0 ���� ����� ��������� �� �������
; ���������:
;   rdi - ��������� �� ������ ������
; ��������:
;   rdi - �������������� ������
;   rdx - ������� �����
; �����:
;   rax - �����
;   rdx - ������ ������
parse_int:
    mov rdx, 0          ; �������� rdx
    cmp byte [rdi], '-' ; �������� �� ���� ����� ������
    jne .positive

  .negative:
    inc rdi         ; ��������� ���� � ������
    call parse_uint
    neg rax         ;����������� ���������� �����
    inc rdx
    ret
  .positive:
    jmp parse_uint

; ��������� ��������� �� ������, ��������� �� ����� � ����� ������
; �������� ������ � �����
; ���������� ����� ������ ���� ��� ��������� � �����, ����� 0
; ���������:
;   rdi - ��������� �� ������ ������
;   rcx - ��������� �� ������ ������
;   rdx - ������ ������
; ��������:
;   r12 - ����� ��� ����������� �������
;   rcx - ������� �����
; �����:
;   rax - ������ ������
string_copy:
    push r12                ; ��������� �������� � �����  
    push rdi
    push rsi
    push rdx
    call string_length      ; ����� ������� -> rax
    inc rax
    pop rdx
    pop rsi
    pop rdi
    cmp rax, rdx            ; ������ ������ > ������ ������ ? 
    jg .err
    xor rcx, rcx            ; �������� rcx 
    xor r12, r12            ; �������� r12 
.loop:
    mov r12b, [rdi+rcx]      ; �������� ������
    mov [rsi+rcx], r12
    inc rcx
    cmp byte[rdi+rcx], 0    ; ������ == ����� ������?
    jne .loop               
    pop r12                 ; �������������� ��������� ���������
    ret
.err:
    xor rax, rax            ; �������� rax 
    pop r12                 ; �������������� ��������� ���������
    ret

