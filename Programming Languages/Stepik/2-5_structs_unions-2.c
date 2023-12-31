struct heap_string {
    char *addr;
};

struct heap_string halloc(const char *s) {
    struct heap_string result;
    result.addr = malloc(strlen(s) + 1);
    if (result.addr) {
        strcpy(result.addr, s);
    }
    return result;
}

void heap_string_free(struct heap_string h) {
    free(h.addr); // освобождаем память, выделенную под строку в куче
}