#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mem.h"
#include "mem_internals.h"
#include "util.h"

void debug_block(struct block_header *b, const char *fmt, ...);

void debug(const char *fmt, ...);

extern inline block_size size_from_capacity(block_capacity cap);

extern inline block_capacity capacity_from_size(block_size sz);

static bool block_is_big_enough(size_t query, struct block_header *block) { return block->capacity.bytes >= query; }

static size_t pages_count(size_t mem) { return mem / getpagesize() + ((mem % getpagesize()) > 0); }

static size_t round_pages(size_t mem) { return getpagesize() * pages_count(mem); }

static void block_init(void *restrict addr, block_size block_sz, void *restrict next) {
    *((struct block_header *) addr) = (struct block_header) {
            .next = next,
            .capacity = capacity_from_size(block_sz),
            .is_free = true
    };
}

static size_t region_actual_size(size_t query) { return size_max(round_pages(query), REGION_MIN_SIZE); }

extern inline bool region_is_invalid(const struct region *r);


static void *map_pages(void const *addr, size_t length, int additional_flags) {
    return mmap((void *) addr, length, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | additional_flags, -1, 0);
}

/*  аллоцировать регион памяти и инициализировать его блоком */
static struct region alloc_region(void const *addr, size_t query) {
    size_t reg_size = region_actual_size(size_from_capacity((block_capacity) {.bytes = query}).bytes);
    void *map_region = map_pages(addr, reg_size, MAP_FIXED_NOREPLACE);

    if (map_region == MAP_FAILED) {
        map_region = map_pages(addr, reg_size, 0);
        if (map_region == MAP_FAILED) return REGION_INVALID;
    }

    block_init(map_region, (block_size) {.bytes = reg_size}, NULL);
    return (struct region) {
            .addr = map_region,
            .size = reg_size,
            .extends = map_region == addr
    };
}

static void *block_after(struct block_header const *block);

void *heap_init(size_t initial) {
    const struct region region = alloc_region(HEAP_START, initial);
    if (region_is_invalid(&region)) return NULL;

    return region.addr;
}

/*  освободить всю память, выделенную под кучу */
#define BLOCK_MIN_CAPACITY 24

/*  --- Разделение блоков (если найденный свободный блок слишком большой )--- */

static bool block_splittable(struct block_header *restrict block, size_t query) {
    return block->is_free &&
           query + offsetof(struct block_header, contents) + BLOCK_MIN_CAPACITY <= block->capacity.bytes;
}

static bool split_if_too_big(struct block_header *block, size_t query) {
    if (block == NULL) { return false; }
    query = size_max(query, BLOCK_MIN_CAPACITY);
    if (!block_splittable(block, query)) return false;

    block_size blockSize = size_from_capacity((block_capacity) {.bytes = query});
    block_size newBlockSize = (block_size) {size_from_capacity(block->capacity).bytes - blockSize.bytes};
    block_init((void *) block + blockSize.bytes, newBlockSize, block->next);
    block_init(block, blockSize, (void *) block + blockSize.bytes);
    return true;
}


/*  --- Слияние соседних свободных блоков --- */

static void *block_after(struct block_header const *block) {
    return (void *) (block->contents + block->capacity.bytes);
}

static bool blocks_continuous(
        struct block_header const *fst,
        struct block_header const *snd) {
    return (void *) snd == block_after(fst);
}

void heap_term() {
    struct block_header *start_block = HEAP_START;
    while (start_block != NULL) {
        size_t size_cnt = 0;
        struct block_header *current = start_block;

        while (blocks_continuous(start_block, start_block->next)) {
            size_cnt += size_from_capacity(start_block->capacity).bytes;
            start_block = start_block->next;
        }
        size_cnt += size_from_capacity(start_block->capacity).bytes;
        start_block = start_block->next;
        munmap(current, size_cnt);
    }
}

static bool mergeable(struct block_header const *restrict fst, struct block_header const *restrict snd) {
    return fst->is_free && snd->is_free && blocks_continuous(fst, snd);
}

static bool try_merge_with_next(struct block_header *block) {
    if (block == NULL) { return false; }
    struct block_header *next_block = block->next;
    if (next_block == NULL) { return false; }
    if (!mergeable(block, next_block)) {
        return false;
    }
    block_size new_size = (block_size) {
            size_from_capacity(block->capacity).bytes + size_from_capacity(next_block->capacity).bytes};
    block_init(block, new_size, block->next->next);
    return true;
}


/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
    enum {
        BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED
    } type;
    struct block_header *block;
};


static struct block_search_result find_good_or_last(struct block_header *restrict block, size_t sz) {
    if (block == NULL) { return (struct block_search_result) {.type = BSR_CORRUPTED}; }
    struct block_header *current = block;

    while (true) {
        while (try_merge_with_next(current)) {}
        if (current->is_free && block_is_big_enough(sz, current)) {
            return (struct block_search_result) {.type = BSR_FOUND_GOOD_BLOCK, .block = current};
        }
        if (!current->next) {
            return (struct block_search_result) {.type = BSR_REACHED_END_NOT_FOUND, .block = current};
        }
        current = current->next;
    }
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь расширить кучу
 Можно переиспользовать как только кучу расширили. */
static struct block_search_result try_memalloc_existing(size_t query, struct block_header *block) {
    struct block_search_result result = find_good_or_last(block, query);
    if (result.type == BSR_CORRUPTED || result.type == BSR_REACHED_END_NOT_FOUND) {
        return result;
    }
    split_if_too_big(result.block, query);
    result.block->is_free = false;
    return result;
}


static struct block_header *grow_heap(struct block_header *restrict last, size_t query) {
    if (last == NULL) { return NULL; }
    struct region new_reg = alloc_region(block_after(last), query);
    if (region_is_invalid(&new_reg)) { return NULL; }
    last->next = new_reg.addr;
    if (try_merge_with_next(last) && new_reg.extends) { return last; }
    return last->next;
}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
static struct block_header *memalloc(size_t query, struct block_header *heap_start) {
    if (heap_start == NULL) return NULL;

    struct block_search_result result = try_memalloc_existing(query, heap_start);
    if (result.type == BSR_CORRUPTED) { return NULL; }
    if (result.type == BSR_FOUND_GOOD_BLOCK) { return result.block; }
    if (result.type == BSR_REACHED_END_NOT_FOUND) {
        struct block_header *new_block = grow_heap(result.block, query);
        if (new_block == NULL) return NULL;

        result = try_memalloc_existing(query, new_block);
        if (result.type == BSR_FOUND_GOOD_BLOCK) return result.block;
    }
    return NULL;
}

void *_malloc(size_t query) {
    struct block_header *const addr = memalloc(query, (struct block_header *) HEAP_START);
    if (addr) return addr->contents;
    else return NULL;
}

static struct block_header *block_get_header(void *contents) {
    return (struct block_header *) (((uint8_t *) contents) - offsetof(struct block_header, contents));
}

void _free(void *mem) {
    if (!mem) return;
    struct block_header *header = block_get_header(mem);
    header->is_free = true;
    while (try_merge_with_next(header)) {}
}
