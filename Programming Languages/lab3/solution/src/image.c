#include <malloc.h>
#include "image.h"

struct image create_image(uint32_t width, uint32_t height){
    return (struct image) {
        .width = width,
        .height = height,
        .data = malloc(width * height * (uint32_t)sizeof(struct pixel))
    };
}

void clear_image(struct image *img){
    free(img->data);
    img->data = NULL;
}

