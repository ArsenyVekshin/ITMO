#include "bmp.h"

enum status validate_bmp_header(const struct bmp_header *header) {
    if(header->biSize != HEADER_SIZE)
        return BMP_INVALID_HEADER;
    if(header->biPlanes != BMP_BI_PLANES)
        return BMP_INVALID_HEADER;
    if(header->biBitCount != BMP_BIT_COUNT)
        return BMP_INVALID_HEADER;  //wrong colour format
    if(header->biCompression != BMP_BI_COMPRESSION)
        return BMP_INVALID_HEADER;  //wrong compression
    if(header->biBitCount != BMP_BIT_COUNT)
        return BMP_INVALID_HEADER;  //wrong colour format
    if(header->biWidth == 0)
        return BMP_INVALID_HEADER;
    if(header->biHeight == 0)
        return BMP_INVALID_HEADER;
    return OK;
}

static uint32_t img_size(const struct image *img){
    return img->height * (img->width* (uint32_t)(sizeof(struct pixel)) + calc_padding(img->width));
}

struct bmp_header generate_bmp_header(const struct image *img){
    return (struct bmp_header){
            .bfType = BMP_HEADER,
            .bfileSize = sizeof(struct bmp_header) + img_size(img),
            .bfReserved = 0,
            .bOffBits = sizeof(struct bmp_header),
            .biSize = BMP_HEADER_SIZE,
            .biWidth = img->width,
            .biHeight = img->height,
            .biPlanes = BMP_BI_PLANES,
            .biBitCount = BMP_BIT_COUNT,
            .biCompression = BMP_BI_COMPRESSION,
            .biSizeImage = img_size(img),
            .biXPelsPerMeter = BMP_PEELS,
            .biYPelsPerMeter = BMP_PEELS,
            .biClrUsed = BMP_COLORS_UNUSED,
            .biClrImportant = BMP_COLORS_UNUSED,
    };
}

