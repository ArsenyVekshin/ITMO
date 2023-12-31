#ifndef TEST_H
#define TEST_H


#include "mem.h"
#include "mem_internals.h"

#ifndef MAP_ANONYMOUS
#define MAP_ANONYMOUS 0x20
#endif

#ifndef MAP_FIXED_NOREPLACE
#define MAP_FIXED_NOREPLACE 0x100000
#endif


void test1(void);

void test2(void);

void test3(void);

void test4(void);

void test5(void);

#endif