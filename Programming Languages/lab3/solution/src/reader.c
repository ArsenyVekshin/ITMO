#include "reader.h"

enum status parse_bmp(FILE *file, struct image *img){
    struct bmp_header header;
    if(fread(&header, sizeof(struct bmp_header), 1, file) !=1)
        return READ_INVALID_FILE;
    if(header.bfType!= BMP_HEADER)
        return READ_INVALID_SIGNATURE;
    if(!validate_bmp_header(&header))
        return READ_INVALID_HEADER;
    if(fseek(file, header.bOffBits, SEEK_SET)!=0)
        return READ_INVALID_BITS;

    img->height = header.biHeight;
    img->width = header.biWidth;

    uint32_t height = img->height;
    uint32_t width = img->width;
    uint32_t padding = calc_padding(width);
    *img = create_image(width, height);

    for (uint32_t h=0; h<height; h++){
        if(fread(calc_image_line_pointer(img->data, width, h),
                 sizeof(struct pixel)*width, 1, file) != 1)
            return READ_FAILED;
        if(fseek(file, padding, SEEK_CUR))
            return READ_FAILED;
    }
    return OK;
}

void read_bmp_image(const char *path, struct image *img) {
    FILE *file = NULL;
    enum status read_status;

    read_status = open_file(path, &file, OPEN_READ_BINARY);
    if(read_status != OK){
        error_actions(read_status, img, file);
    }

    read_status = parse_bmp(file, img);
    if(read_status != OK){
        error_actions(read_status, img, file);
    }

    read_status = close_file(file);
    if(read_status != OK){
        error_actions(read_status, img, file);
    }
}
