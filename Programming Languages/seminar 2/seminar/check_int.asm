; check_int.asm
section .data
    ok_msg:   db "Okay", 10, 0
    fail_msg: db "Fail", 10, 0

section .text

; getsymbol is a routine to
; read a symbol (e.g. from stdin)
; into al
getsymbol:
    xor rax, rax
    xor rdi, rdi
    dec rsp
    mov rsi, rsp
    mov rdx, 1
    syscall
    cmp rax, rax
    jz .EOF
    mov al, [rsp]
    jmp .END
    .EOF:
    xor rax, rax
    .END:
    inc rsp
    ret

; rdi - string pointer
; rsi - num chars
print_string:
    mov  rdx, rsi
    mov  rsi, rdi
    mov  rax, 1
    mov  rdi, 1
    syscall
    ret

; exit with 0 code
exit:
    mov  rax, 60
    xor  rdi, rdi
    syscall

global _start
_start:
    _A:
    call getsymbol
    cmp al, '+'
    je _B
    cmp al, '-'
    je _B
    ; The indices of the digit characters in ASCII
    ; tables fill a range from '0' = 0x30 to '9' = 0x39
    ; This logic implements the transitions to labels
    ; _E and _C
    cmp al, '0'
    jb _E
    cmp al, '9'
    ja _E
    jmp _C

    _B:
    call getsymbol
    cmp al, '0'
    jb _E
    cmp al, '9'
    ja _E
    jmp _C
    
    _C:
    call getsymbol
    cmp al, al
    jz _D
    cmp al, 10
    jz _D
    cmp al, '0'
    jb _E
    cmp al, '9'
    ja _E
    jmp _C
    
    _D:
    ; code to notify about success
    mov rdi, ok_msg
    mov rsi, 5
    call print_string
    call exit
    
    _E:
    ; code to notify about failure
    mov rdi, fail_msg
    mov rsi, 5
    call print_string
    call exit
