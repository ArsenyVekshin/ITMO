
#include <stdlib.h>
#include "error_handler.h"

void error_prints(enum status error){
    switch (error) {
        case OK:
            break;
        case FILE_OPEN_ERROR:
            fprintf(stderr, "error while opening file");
            break;
        case FILE_CLOSE_ERROR:
            fprintf(stderr, "error while closing file");
            break;
        case READ_FAILED:
            fprintf(stderr, "error while reading file");
            break;
        case READ_INVALID_SIGNATURE:
            fprintf(stderr, "error while reading file: incorrect signature");
            break;
        case READ_INVALID_BITS:
            fprintf(stderr, "error while reading file: incorrect bits");
            break;
        case READ_INVALID_HEADER:
            fprintf(stderr, "error while reading file: incorrect header");
            break;
        case READ_INVALID_FILE:
            fprintf(stderr, "error while reading file: file broken");
            break;
        case WRITE_FAILED:
            fprintf(stderr, "error while writing to file");
            break;
        case WRITE_CANNOT_OPEN_FILE:
            fprintf(stderr, "error while writing to file: the file cannot be opened");
            break;
        case WRITE_IMAGE_INVALID:
            fprintf(stderr, "error while writing to file: image contains an error");
            break;
        case WRITE_CANNOT_WRITE:
            fprintf(stderr, "error while writing to file: the file does not support writing");
            break;
        case ANGLE_INVALID:
            fprintf(stderr, "error while rotation: entered angle is incorrect");
            break;
        case ANGLE_ROTATE_ERROR:
            fprintf(stderr, "error while rotation: an error occurred in the process");
            break;
        case BMP_INVALID_HEADER:
            fprintf(stderr, "error while parsing header: invalid bmp header");
            break;
        case WRONG_ARGUMENTS_NUMBER:
            fprintf(stderr, "error: wrong arguments number (must be 4)");
            break;
    }
}

void error_actions(enum status error, struct image *img, FILE *file){
    if(error == OK)
        return;
    error_prints(error);
    if(img!=NULL)
        clear_image(img);
    if(file!=NULL)
        close_file(file);
    exit(error);
}
