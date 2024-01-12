#include "mem.h"
#include "mem_internals.h"
#include "util.h"

#include <fcntl.h>
#include <stddef.h>
#include <stdlib.h>
#include <unistd.h>

#include <sys/mman.h>


#define RUN_SINGLE_TEST(_name) do { \
    puts(" Run test \"" #_name "\"..."); \
    if(test_##_name()) {\
        puts("test failed");\
    } else { \
        puts("Ok");\
    }\
} while (0)

#define assert(expr) {\
    if(!(expr)) {\
        fprintf(stderr, "assert %s on line %d failed.\n", #expr, __LINE__);\
        heap_term();\
        return 1;\
    }\
}


static int test_malloc_normal() {
    void *heap = heap_init(1);
    struct block_header *head = heap;

    assert(size_from_capacity(head->capacity).bytes == REGION_MIN_SIZE);
    assert(head->is_free);
    assert(head->next == NULL);

    void *block1 = _malloc(1);
    assert(block1 == heap + offsetof(struct block_header, contents));
    assert(head->capacity.bytes == BLOCK_MIN_CAPACITY);
    assert(head->is_free);

    assert(head->next == (heap + (size_from_capacity((block_capacity) {BLOCK_MIN_CAPACITY}).bytes)));
    assert(head->next->is_free);
    assert(head->next->capacity.bytes ==
           (REGION_MIN_SIZE - BLOCK_MIN_CAPACITY - 2 * offsetof(struct block_header, contents)));
    heap_term();
    return 0;
}

static int test_free() {
    void *heap = heap_init(1);
    struct block_header *head = heap;

    assert(size_from_capacity(head->capacity).bytes == REGION_MIN_SIZE);
    assert(head->is_free);
    assert(head->next == NULL);

    void *block1 = _malloc(1);
    assert(block1 == (heap + offsetof(struct block_header, contents)));
    assert(head->capacity.bytes == BLOCK_MIN_CAPACITY);
    assert(!head->is_free);

    void *block2 = _malloc(100);
    assert(head->next->capacity.bytes == 100);
    assert(!head->next->is_free);

    void *block3 = _malloc(30);
    assert(head->next->next->capacity.bytes == 30);
    assert(!head->next->next->is_free);

    _free(block3);
    assert(head->next->next->is_free);
    assert(head->next->next->next == NULL);
    _free(block2);
    _free(block1);
    assert(head->is_free);
    assert(head->next == NULL);
    heap_term();
    return 0;
}

static int test_malloc_new_region_continous() {
    void *heap = heap_init(1);
    struct block_header *head = heap;

    assert(size_from_capacity(head->capacity).bytes == REGION_MIN_SIZE);
    assert(head->is_free);
    assert(head->next == NULL);

    void *block1 = _malloc(1);
    assert(block1 == (heap + offsetof(struct block_header, contents)));
    assert(head->capacity.bytes == BLOCK_MIN_CAPACITY);
    assert(!head->is_free);

    void *block2 = _malloc(REGION_MIN_SIZE);
    assert(block2 == heap + BLOCK_MIN_CAPACITY + 2 * offsetof(struct block_header, contents));
    assert(!head->is_free);
    heap_term();
    return 0;
}

static int test_malloc_new_region_not_continous() {
    void *heap = heap_init(1);
    struct block_header *head = heap;

    assert(size_from_capacity(head->capacity).bytes == REGION_MIN_SIZE);
    assert(head->is_free);
    assert(head->next == NULL);

    void *block1 = _malloc(1);
    assert(block1 == (heap + offsetof(struct block_header, contents)));
    assert(head->capacity.bytes == BLOCK_MIN_CAPACITY);
    assert(!head->is_free);

    void *addr = head->next->contents + head->next->capacity.bytes;
    void *addr2 = mmap((void *) addr,
                        REGION_MIN_SIZE, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED,
                        -1, 0);
    assert(addr2 != MAP_FAILED);
    void *block2 = _malloc(REGION_MIN_SIZE);
    assert(block2 != addr);
    munmap(addr2, REGION_MIN_SIZE);
    heap_term();
    return 0;
}


int main(void) {
    RUN_SINGLE_TEST(malloc_normal);
    RUN_SINGLE_TEST(free);
    RUN_SINGLE_TEST(malloc_new_region_continous);
    RUN_SINGLE_TEST(malloc_new_region_not_continous);
}
