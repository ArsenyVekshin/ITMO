
#include "test.h"
#include "util.h"


static void end_test(void *heap, int query, const char *const testName) {
    munmap(heap, size_from_capacity((block_capacity) {.bytes = query}).bytes);
    fprintf(stdout, "%s%s", testName, " is successful!\n");
}

static void check_heap(const void *const heap, const char *const msg) {
    printf("%s: %s", msg, "\n");
    debug_heap(stdout, heap);
}

//Обычное успешное выделение памяти.
void test1(void) {
    void *heap = heap_init(4096);
    if (!heap) {
        error_msg("Test number 1 failed", ", heap is not initialized.");
        return;
    }
    check_heap(heap, "Heap after init");

    void *mem = _malloc(2048);
    if (!mem) {
        error_msg("Test number 1 failed", ", memory is not allocated.");
        return;
    }
    check_heap(heap, "Heap after alloc");

    _free(mem);
    check_heap(heap, "Heap after free mem");

    end_test(heap, 4096, "Test number 1");
}

//Освобождение одного блока из нескольких выделенных.
void test2(void) {
    void *heap = heap_init(4096);
    if (!heap) {
        error_msg("Test number 2 failed", ", heap is not initialized.");
        return;
    }
    check_heap(heap, "Heap after init");

    void *mem1 = _malloc(1024);
    void *mem2 = _malloc(1024);
    if (!mem1 || !mem2){
        error_msg("Test number 2 failed", ", memory is not allocated.");
        return;
    }
    check_heap(heap, "Heap after alloc");

    _free(mem1);
    if (!mem2) {
        error_msg("test2 failed", ", release of the first damaged the second.");
        return;
    }
    check_heap(heap, "Heap after free mem");

    _free(mem2);
    end_test(heap, 4096, "Test number 2");
}

//Освобождение двух блоков из нескольких выделенных.
void test3(void) {
    void *heap = heap_init(4096);
    if (!heap) {
        error_msg("Test number 3 failed", ", heap is not initialized.");
        return;
    }
    check_heap(heap, "Heap after init");

    void *mem1 = _malloc(1024);
    void *mem2 = _malloc(1024);
    void *mem3 = _malloc(1024);
    if (!mem1 || !mem2 || !mem3) {
        error_msg("Test number 3 failed", ", memory is not allocated.");
        return;
    }
    check_heap(heap, "Heap after alloc");

    _free(mem1);
    _free(mem3);
    if (!mem2){
        error_msg("test3 failed", ", release of the first damaged the second.");
        return;
    }
    check_heap(heap, "Heap after free mem");

    end_test(heap, 4096, "Test number 3");
}

//Память закончилась, новый регион памяти расширяет старый.
void test4(void) {
    void *heap = heap_init(1);
    if (!heap) {
        error_msg("Test number 4 failed", ", heap is not initialized.");
        return;
    }
    check_heap(heap, "Heap after init");

    void *mem1 = _malloc(8192);
    void *mem2 = _malloc(16384);
    if (!mem1 || !mem2) error_msg("Test number 4 failed", ", memory is not allocated.");
    check_heap(heap, "Heap after alloc");

    struct block_header *header1 = block_get_header(mem1);
    struct block_header *header2 = block_get_header(mem2);

    if (header1 && header2) {
        int total_capacity = (int) (header1->capacity.bytes + header2->capacity.bytes +
                                    ((struct block_header *) heap)->capacity.bytes);
        end_test(heap,
                 total_capacity,
                 "Test number 4");
    } else {
        error_msg("Test number 4 failed", ", header1 or header2 is null.");
        return;
    }

    if (!header1 || header1->next != header2) {
        error_msg("Test number 4 failed", ", headers are not linked.");
        return;
    }


}

//Память закончилась, старый регион памяти не расширить из-за другого выделенного диапазона адресов, новый регион выделяется в другом месте.
void test5(void) {
    void *heap = heap_init(1);
    if (!heap) {
        error_msg("Test number 5 failed", ", heap is not initialized.");
        return;
    }
    check_heap(heap, "Heap after init");

    void *mem1 = _malloc(8192);
    struct block_header *header1 = block_get_header(mem1);
    if (!mem1) {
        error_msg("Test number 5 failed", ", the first memory is not allocated.");
        return;
    }
    if (!header1) {
        error_msg("Test number 5 failed", ", header is not find.");
        return;
    }
    check_heap(heap, "Heap after alloc.");

    void *newRegion = mmap((*header1).contents + (*header1).capacity.bytes, REGION_MIN_SIZE, PROT_READ | PROT_WRITE,
                           MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED_NOREPLACE, -1, 0);

    void *mem2 = _malloc(8192);
    struct block_header *header2 = block_get_header(mem2);
    if (!mem2) {
        error_msg("Test number 5 failed", ", the second memory is not allocated");
        return;
    }
    check_heap(heap, "Heap after second malloc");

    _free(mem1);
    _free(mem2);
    check_heap(heap, "Heap after realising");

    munmap(newRegion, size_from_capacity((block_capacity) {.bytes = REGION_MIN_SIZE}).bytes);

    end_test(heap,
             (int) (header1->capacity.bytes + header2->capacity.bytes +
                    ((struct block_header *) heap)->capacity.bytes),
             "Test number 5");
}
