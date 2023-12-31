#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>

#include "util.h"

_Noreturn void err(const char *msg, ...) {
    va_list args;
    va_start (args, msg);
    vfprintf(stderr, msg, args); // NOLINT
    va_end (args);
    abort();
}


void error_msg(const char *msg, const char *msg2) {
    fprintf(stderr, "%s%s%s", msg, msg2, "\n");
}

extern inline size_t size_max(size_t x, size_t y);
