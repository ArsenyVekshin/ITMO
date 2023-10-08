; hello.asm 
section .data
message: db  'hello, world!', 10

section .text
global _start

exit:                        ; Это метка начала функции exit
    mov  rax, 60             ; Это функция exit
    xor  rdi, rdi            ; Это функция exit
    syscall                  ; Это функция exit

_start:
    mov  rax, 1
    mov  rdi, 1
    mov  rsi, message  
    mov  rdx, 14         
    syscall
    call exit                ; это вызов функции exit
