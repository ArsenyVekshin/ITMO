#include <corecrt.h>
#include <stdint.h>
#include <malloc.h>
#include <stdio.h>
#include <stdbool.h>

struct list {
    int64_t value;
    struct list* next;
};

struct list* node_create( int64_t value ) {
    struct list* ptr = malloc(sizeof(struct list));
    *ptr = (struct list) {value, NULL};
    return ptr;
}

void list_add_front(struct list** old, int64_t value ) {
    struct list* first_mark = node_create(value);
    (*first_mark).next = *old;
    *old = first_mark;
}

size_t list_length(const struct list *elem) {
    if(elem == NULL) return 0;
    size_t size=1;
    while(elem->next != NULL){
        size++;
        elem = elem->next;
    }
    return size;
}

void list_destroy( struct list* list ) {
    if(list == NULL) return;
    struct list *prev;
    while (list != NULL) {
        prev = list;
        list = list -> next;
        free( prev );
    }
}

struct list* list_last( struct list * list ) {
    if(list == NULL) return NULL;
    while(list->next != NULL)
        list = list->next;
    return list;
}

void list_add_back( struct list** old, int64_t value ) {
    if(old == NULL) list_add_front(old, value);
    struct list *new_node = node_create(value);
    struct list *last = list_last(*old);
    //printf("insert %ld after %ld \n", new_node->value, last->value);
    last->next = new_node;
    printf("insert %ld after %ld \n", last->next->value, last->value);
}


int64_t list_sum(const struct list *elem) {
    if(elem == NULL) return 0;
    int64_t sum = elem->value;
    while(elem->next != NULL){
        elem = elem->next;
        sum += elem->value;
    }
    return sum;
}

struct maybe_int64 {
    bool valid;
    int64_t value;
};

struct maybe_int64 some_int64( int64_t i ) {
    return (struct maybe_int64) { .value = i, .valid = true };
}

const struct maybe_int64 none_int64 = { 0 };

struct maybe_int64 list_at(const struct list *list, size_t idx ) {
    if(list == NULL) return none_int64;
    if(idx >= list_length(list)) return none_int64;
    for(size_t i=0; i < idx; i++)
        list = list->next;
    return some_int64(list->value);
}

struct list* list_reverse(const struct list *list) {
    if(list == NULL) return NULL;
    struct list *new_list = node_create(list->value);
    while(list->next != NULL){
        list = list->next;
        list_add_front(&new_list, list->value);
    }
    return new_list;
}

struct maybe_int64 maybe_read_int64() {
    int64_t input;
    if (scanf("%" SCNd64, &input) > 1) {
        return some_int64(input);
    } else {
        return none_int64;
    }
}

struct list* list_read() {
    struct list* list = NULL;
    struct list* elem = NULL;
    struct maybe_int64 maybeNum;

    while (1) {
        maybeNum = maybe_read_int64();
        if (!maybeNum.valid) break;

        elem = node_create(maybeNum.value);
        if(list == NULL) list = elem;
        list_add_back(&head, maybeNum.value);
    }
    return head;
}
