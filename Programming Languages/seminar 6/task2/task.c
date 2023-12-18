#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
    struct block {
        char contents[BLOCK_CAPACITY];
    } blocks[HEAP_BLOCKS];
    enum block_status status[HEAP_BLOCKS];
} global_heap = { 0 };

struct block_id {
    size_t       value;
    bool         valid;
    struct heap* heap;
};

struct block_id block_id_new(size_t value, struct heap* from) {
    return (struct block_id) { .valid = true, .value = value, .heap = from };
}
struct block_id block_id_invalid() {
    return (struct block_id) { .valid = false };
}

bool block_id_is_valid(struct block_id bid) {
    return bid.valid && bid.value < HEAP_BLOCKS;
}

/* Find block */

bool block_is_free(struct block_id bid) {
    if (!block_id_is_valid(bid))
        return false;
    return bid.heap->status[bid.value] == BLK_FREE;
}

/* Allocate */

//���������, �������� �� ������������������ ������
bool check_block_size_valid(struct heap* heap, size_t block_id, size_t size) {
    if (block_id + size > HEAP_BLOCKS || size <= 0) return false;
    for (size_t i = 0; i < size; i++) {
        if (heap->status[i+block_id] != BLK_FREE) {
            return false;
        }
    }
    return true;
}

//���� ��������� ���� ������� size
struct block_id get_blocks_of_size(struct heap* heap, size_t size) {
    for (size_t i = 0; i < HEAP_BLOCKS; i++) {
        if (heap->status[i] == BLK_FREE) {
            if (check_block_size_valid(heap, i, size)) {
                return (struct block_id) { .value = i, .valid = true, .heap = heap };
            }
        }
    }
    return block_id_invalid();
}

struct block_id block_allocate(struct heap* heap, size_t size) {
    if (size > HEAP_BLOCKS || size <= 0) return block_id_invalid();

    //�������� 1 ����
    if (size == 1) {
        struct block_id blk = get_blocks_of_size(heap, 1);
        if (blk.valid) {
            heap->status[blk.value] = BLK_ONE;
            return blk;
        }
        return block_id_invalid();
    }

    //�������� ������� ����
    struct block_id blk = get_blocks_of_size(heap, size);
    if (blk.valid) {
        heap->status[blk.value] = BLK_FIRST;
        for (size_t i = blk.value + 1; i < blk.value + size - 1; i++) {
            heap->status[i] = BLK_CONT;
        }
        heap->status[blk.value + size - 1] = BLK_LAST;
        return blk;
    }

    return block_id_invalid();
}
/* Free */
void block_free(struct block_id bid) {
    if (!block_id_is_valid(bid)) return;
    if (bid.heap->status[bid.value] == BLK_FREE) return;
    if (bid.heap->status[bid.value] == BLK_ONE) {
        bid.heap->status[bid.value] = BLK_FREE;
        return;
    }

    do {
        bid.heap->status[bid.value] = BLK_FREE;
        bid.value++;
    } while (bid.heap->status[bid.value] != BLK_LAST);
    bid.heap->status[bid.value] = BLK_FREE;
}
/* Printer */
const char* block_repr(struct block_id b) {
    static const char* const repr[] = { [BLK_FREE] = "  .",
            [BLK_ONE] = "  *",
            [BLK_FIRST] = " [=",
            [BLK_LAST] = " =]",
            [BLK_CONT] = "  =" };
    if (b.valid)
        return repr[b.heap->status[b.value]];
    else
        return "INVALID";
}

void block_debug_info(struct block_id b, FILE* f) {
    fprintf(f, "%s", block_repr(b));
}

void block_foreach_printer(struct heap* h, size_t count,
                           void printer(struct block_id, FILE* f), FILE* f) {
    for (size_t c = 0; c < count; c++)
        printer(block_id_new(c, h), f);
}

void heap_debug_info(struct heap* h, FILE* f) {
    block_foreach_printer(h, HEAP_BLOCKS, block_debug_info, f);
    fprintf(f, "\n\n");
}
/*  -------- */

int main() {
    struct block_id blk_1 = block_allocate(&global_heap, 4);
    heap_debug_info(&global_heap, stdout);
    struct block_id blk_2 = block_allocate(&global_heap, 2);
    heap_debug_info(&global_heap, stdout);
    struct block_id blk_3 = block_allocate(&global_heap, 11);
    heap_debug_info(&global_heap, stdout);
    block_free(blk_1);
    heap_debug_info(&global_heap, stdout);
    return 0;
}

