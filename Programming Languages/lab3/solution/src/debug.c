#include "debug.h"

void print_img(struct image *img){
    for(uint32_t y=0; y<img->height; y++){
        for(uint32_t x=0; x<img->width; x++) {
            printf("%d %d %d, %s", img->data[x + y*img->width].r, img->data[x + y*img->width].g, img->data[x + y*img->width].b, "");
        }
        printf(" ");
    }
}
