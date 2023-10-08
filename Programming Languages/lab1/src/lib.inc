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
 
; Принимает код возврата и завершает текущий процесс
exit: 
    xor rax, rax,
    ret 

; Принимает указатель на нуль-терминированную строку, возвращает её длину
; аргументы:
;   rdi - адрес начала строки
; вывод:
;   rax - длинна строки
string_length:
    mov  rax, rdi   ; адрес с которого начали проверку
  .counter:
    cmp  byte [rdi], 0
    je   .return
    inc  rdi
    jmp  .counter
  .return:
    sub  rdi, rax
    mov  rax, rdi
    ret


; Печать N символов из памяти в stdout
; аргументы:
;   rsi - количество символов
;   rdi - адрес начала 
print_N_symb:
    mov  rdx, rsi   ; аргумент syscall "длинна строки"
    mov  rsi, rdi   ; указатель syscall "адрес начала строки"
    mov rax, 1      ; флаг syscall "запись"
    mov rdi, 1      ; флаг syscall "stdout"
    syscall         ; запуск функции по флагам
    ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
; аргументы:
;   rax - указатель на начало строки
print_string:
    mov  rdi, rax
    push rdi        ; сохраняем регистры перед вызовом функции
    call string_length
    pop rdi         ; востанавливаем состояние регистров
    mov rsi, rax    ; вывод функции -> указатель syscall "адрес начала строки" 
    call print_N_symb
    ret

; Принимает код символа и выводит его в stdout
; аргументы:
;   rdi - указатель на начало строки
print_char:
    push rdi        ; Сохранить выводимый символ на стеке
    mov rax, 1      ; флаг syscall "запись"
    mov rdi, 1      ; флаг syscall "stdout"
    mov rsi, rsp    ; адрес символа в стеке -> указатель syscall "адрес начала строки"
    mov rdx, 1      ; аргумент syscall "длинна строки"
    syscall         ; запуск функции по флагам
    pop rdi         ; Возвращение стека в исходное состояние
    ret

; Переводит строку (выводит символ с кодом 0xA)
; аргументы:
; rdi - адрес начала 
print_newline: 
    mov rdi, 0xA
    call print_char
    ret

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
; аргументы:
;   rdi - число
; локально:
;   rdi - счетчик цикла
;   r12 - остаток от деления
print_uint:
    mov r12, 10
    mov rax, rdi    
    mov rdi, rsp    
    dec rdi         
    push 0              ; Символ окончания строки
    sub rsp, 16         ; Выделения буфера в стеке для строки
.loop:
    xor rdx, rdx        ; Обнуление rdx
    div r12             ; Целочисленное деление на 10
    add rdx, '0'        ; получение кода необходимого символа
    dec rdi
    mov byte[rdi], dl
    test rax, rax       ; Проверка, продолжать ли вычисление 10-чной записи числа
    jnz .loop
    call print_string
    add rsp, 24         ; Возврат стека в исходное состояние
    ret


; Выводит знаковое 8-байтовое число в десятичном формате 
; аргументы:
;   rdi - число
; локально:
;   rdi - счетчик цикла
;   r12 - остаток от деления
print_int:
    cmp rdi, 0      ; Определение знака числа
    jge .positive
    push rdi
    mov rdi, '-'
    call print_char ; если число отрицательное - печатем '-' и инвертируем заданное
    pop rdi         
    neg rdi
.positive:
    call print_uint ; Вывод беззнакового целого числа
    ret



; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
; аргументы:
;   rdi - указатель на начало 1й строки
;   rsi - указатель на начало 2й строки
; локально:
;   rcx - счетчик цикла
;   d1 - буфер для символа
; вывод:
;   rax - 1 - да / 0 - нет
string_equals:
    xor rcx, rcx        ; обнуляем rcx
    push rdi
    push rsi
    call string_length  ; вывод функции -> rax
    pop rdi             ; rdi <-> rsi
    pop rsi
    push rdi
    push rsi
    push rax            ; сохраняем длинну 1й строки в стек
    call string_length  ; вывод функции -> rax
    pop rdx             ; длинна 1й строки -> rdx
    pop rdi
    
    cmp rdx, rax        ; сравниваем длины строк
    jne .false

  .loop:
    mov dl, byte[rdi + rcx] ; символ 1 -> буфер
    cmp dl, byte[rsi + rcx] ; символ 1 == символ 2?
    jne .false
    inc rcx
    cmp dl, 0               ; символ == конец строки?
    jne .loop
    mov rax, 1              ; возвращаем 1
    ret

  .false:
    xor rax, rax    ; возвращаем 0
    ret


; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    xor rax, rax    ; флаг syscall "чтение"
    xor rdi, rdi    ; флаг syscall "stdin"
    push 0          ; буфер в стеке
    mov rsi, rsp    ; адрес символа в стеке -> указатель syscall "адрес начала строки"
    mov rdx, 1      ; аргумент syscall "длинна строки"
    syscall         ; запуск функции по флагам
    pop rax
    ret 


; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор
; аргументы:
;   rdi - указатель на начало буфера
;   rsi - размер буфера
;   [input_rax, input_rax + input_rsi] - буфер
; локально:
;   rcx - счетчик цикла
; вывод:
;   rax - адрес начала строки
;   rdx - длинна строки
read_word:
    xor rcx, rcx    ; обнуляем rcx
  .loop:
    push rdi        ; сохраняем регистры перед вызовом функции
    push rsi
    push rcx
    call read_char  ; вывод функции -> rax
    pop rcx         ; востанавливаем состояние регистров
    pop rsi
    pop rdi

    cmp rax, 0x20   ; проверки на "пробельные символы"
    je .space
    cmp rax, 0x9
    je .space
    cmp rax, 0xA
    je .space
    cmp rax, 0x0
    je .return

    mov [rdi + rcx], rax    ; считанный символ -> буфер
    inc rcx
    cmp rsi, rcx    ; проверка на переполнение буфера
    jg .err
    jmp .loop
  .err:
    xor rax, rax    ; обнуляем rax
    xor rdx, rdx    ; обнуляем rdx
    ret  

  .space:
    cmp rcx, 0
    je .loop        
    jmp .return
  .return:
    xor rax, rax            ; обнуляем rax
    mov [rdi + rcx], rax    ; считанный символ -> буфер
    mov rax, rdi            ; адрес 
    mov rdx, rcx            ; длинна строки
    ret
 

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
; аргументы:
;   rdi - указатель на начало строки
; локально:
;   r12 - обрабатываемый символ
;   rcx - счетчик цикла
; вывод:
;   rax - число
;   rdx - длинна строки
parse_uint:
    push rbx            ; сохраняем регистры в стеке       
    push r12   
    mov rbx, 10         ; буфер для упрощения деления на разряды
    xor rcx, rcx        ; обнуляем rcx
    xor rax, rax        ; обнуляем rax

  .loop:
    mov r12, [rdi+rcx]  ; считываем символ
    and r12, 0xff       ; применяем маску
    cmp r12b, 0         ; символ == окончание строки?
    je .return
    cmp r12b, 0x39      ; символ == число?    
    ja .return 
    cmp r12b, 0x30
    jb .return 
    
    mul rbx             ; умножаем на rax 10
    sub r12b, 0x30      ; получаем цифру
    add rax, r12        ; rax + цифра
    inc rcx
    jmp .loop
  .return:
    mov rdx, rcx
    pop r12             ; востанавливаем состояние регистров
    pop rbx
    ret


; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
; аргументы:
;   rdi - указатель на начало строки
; локально:
;   r12 - обрабатываемый символ
;   rdi - счетчик цикла
; вывод:
;   rax - число
;   rdx - длинна строки
parse_int:
    movzx r12, byte[rdi]; считываем символ
    cmp r12b, '-'       ; число отрицательное?
    je .negative
    push 0              ; флаг N=0 -> стек

  .negative:
    push 1              ; флаг N=1  -> стек
    inc rdi
  .parse:
    call parse_uint
    pop r12             ; считываем значение N из стека
    cmp r12, 0
    je .return
    neg rax             ; если число отрцательное, инвертируем число
    inc rdx
  .return:
    ret


; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
; аргументы:
;   rdi - указатель на начало строки
;   rcx - указатель на начало буфера
;   rdx - размер буфера
; локально:
;   r12 - буфер для копируемого символа
;   rcx - счетчик цикла
; вывод:
;   rax - длинна строки
string_copy:
    push r12                ; сохраняем регистр в стеке  
    call string_length      ; вывод функции -> rax
    inc rax
    cmp rax, rdx            ; длинна строки > длинна буфера ? 
    jg .err
    xor rcx, rcx            ; обнуляем rcx 
    xor r12, r12            ; обнуляем r12 
.loop:
    mov r12, [rdi+rcx]      ; копируем символ
    mov [rsi+rcx], r12
    inc rcx
    cmp byte[rdi+rcx], 0    ; символ == конец строки?
    jne .loop               
    pop r12                 ; востанавливаем состояние регистров
    ret
.err:
    xor rax, rax            ; обнуляем rax 
    pop r12                 ; востанавливаем состояние регистров
    ret

