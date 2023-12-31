#include <stdint.h>
#include <malloc.h>
#include <stdio.h>

enum either_type { ET_INT, ET_STRING };

struct either_int_string {
    enum either_type type;
    union {
        char* as_string;
        int64_t as_int;
    };
};


// Создание экземпляров из строки или из числа
// Всегда передаётся строка, выделенная в куче
struct either_int_string either_from_string(const char* s) {
    struct either_int_string result;
    result.type = ET_STRING;
    result.as_string = s;
    return result;
}

struct either_int_string either_from_int(int64_t i) {
    struct either_int_string result;
    result.type = ET_INT;
    result.as_int = i;
    return result;
}

// очистить память под строку (если строка)
void either_int_string_destroy(struct either_int_string e) {
    if (e.type == ET_STRING) {
        free(e.as_string);
    }
}

// конструкция switch используется для ветвления по значению числа,
// например, перечисления.
// Для строк и других типов данных использоваться не может
// Каждая ветка должна завершаться break
void print(struct either_int_string e) {
    switch (e.type) {
        case ET_INT: {
            printf("Int %" PRId64, e.as_int);
            break;
        }
        case ET_STRING: {
            printf("String %s", e.as_string);
            break;
        }
            // Ветка по-умолчанию, если e.type не равен ни ET_INT, ни ET_STRING
        default: {
            break;
        }
    }
}