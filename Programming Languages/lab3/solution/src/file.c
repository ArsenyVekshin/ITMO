#include <stdio.h>
#include "enums.h"
enum status open_file(const char* path, FILE **file, enum open_mode mode){
    switch(mode){
        case OPEN_READ:
            *file = fopen(path, "r");
            break;
        case OPEN_WRITE:
            *file = fopen(path, "w");
            break;
        case OPEN_APPEND:
            *file = fopen(path, "a");
            break;
        case OPEN_READ_BINARY:
            *file = fopen(path, "rb");
            break;
        case OPEN_WRITE_BINARY:
            *file = fopen(path, "wb");
            break;
        case OPEN_APPEND_BINARY:
            *file = fopen(path, "ab");
            break;

        case OPEN_WRITE_READ:
            *file = fopen(path, "r+");
            break;
        case OPEN_NEW_WRITE_READ:
            *file = fopen(path, "w+");
            break;
        case OPEN_APPEND_OR_NEW_WRITE_READ:
            *file = fopen(path, "a+");
            break;
        case OPEN_WRITE_READ_BINARY:
            *file = fopen(path, "r+b");
            break;
        case OPEN_NEW_WRITE_READ_BINARY:
            *file = fopen(path, "w+b");
            break;
        case OPEN_APPEND_OR_NEW_WRITE_READ_BINARY:
            *file = fopen(path, "a+b");
            break;
        default:
            return FILE_OPEN_ERROR;
    }

    if(*file == NULL){
        return FILE_OPEN_ERROR;
    }
    return OK;
}

enum status close_file(FILE *file){
    if(fclose(file)) return FILE_CLOSE_ERROR;
    return OK;
}
