#include <stdio.h>
#include <stdlib.h>
#include "vector.h"

void vector_init(vector *v){
    v->capacity = VECTOR_INIT_CAPACITY;
    v->total = 0;
    v->items = malloc(sizeof(void *) * v->capacity);
}

int vector_total(vector *v){
    return v->total;
}

void vector_resize(vector *v, int capacity){
    void **items = realloc(v->items, sizeof(void *) * capacity);
    if (items) {
        v->items = items;
        v->capacity = capacity;
    }
}

void vector_add(vector *v, void *item){
    if (v->capacity == v->total)
        vector_resize(v, v->capacity * 2);
    v->items[v->total++] = item;
}

void vector_set(vector *v, int index, void *item){
    if (index >= 0 && index < v->total)
        v->items[index] = item;
}

void *vector_get(vector *v, int index){
    if (index >= 0 && index < v->total)
        return v->items[index];
    return NULL;
}

void vector_delete(vector *v, int index){
    if (index < 0 || index >= v->total)
        return;
    v->items[index] = NULL;
    for (int i = index; i < v->total - 1; i++) {
        v->items[i] = v->items[i + 1];
        v->items[i + 1] = NULL;
    }

    v->total--;
    if (v->total > 0 && v->total == v->capacity / 4)
        vector_resize(v, v->capacity / 2);
}

void vector_free(vector *v){
    free(v->items);
}

void vector_foreach(vector* v){
    for (int i = 0; i < vector_total(v); i++) vector_get(v,i);
}

void print_vector(vector* v, FILE* f){
    for (int i = 0; i < vector_total(v); i++){
        char* str = vector_get(v,i);
        fprintf(f, "%s ", str);
    }
}