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
    test rax, rax
    jz .EOF
    mov al, [rsp]
    jmp .END
    .EOF:
    xor rax, rax
    .END:
    inc rsp
    ret

checksymbol:
                    ; check "is sign?"
    _signcheck:
    call getsymbol
    cmp al, '+'
    je _checkNumeric
    cmp al, '-'
    je _checkNumeric
    
                    ;Check "is numeric symb?"
    _checkNumeric
    cmp al, '0'
    jb _Error
    cmp al, '9'
    ja _Error
    jmp _signcheck

    _B:
    call getsymbol
    cmp al, '0'
    jb _Error
    cmp al, '9'
    ja _Error
    jmp _C


   _Error:
    ; code to notify about failure
    mov rdi, fail_msg
    mov rsi, 5
    call print_string
    call exit