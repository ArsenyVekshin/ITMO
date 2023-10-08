; print_string.asm 
section .data
message:       db  'hello, world!', 10
det:           db  0x0
error_message: db  'Uve done a fuckin error !', 24

section .text
global _start

exit:
    mov  rax, 60
    xor  rdi, rdi          
    syscall

string_length:
    mov  rax, rdi
  .counter:
    cmp  byte [rdi], 0
    je   .end
    inc  rdi
    jmp  .counter
  .end:
    sub  rdi, rax
    mov  rax, rdi
    ret

print_string:
    mov  rdx, rsi
    mov  rsi, rdi
    mov  rax, 1
    mov  rdi, 1
    syscall
    ret

_start:
    mov  rdi, message
    push rdi
    call string_length
    pop rdi
    mov rsi, rax
    call print_string
    call exit