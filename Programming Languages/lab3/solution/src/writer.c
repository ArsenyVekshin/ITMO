#include "writer.h"

static void create_padding_arr(uint8_t *const padding_arr, const uint8_t padding){
    for(size_t i = 0; i < padding; i++){
        padding_arr[i] = 0;
    }
}

enum status convert_to_bmp(FILE *file, struct image *img){
    struct bmp_header header = generate_bmp_header(img);

    if(fwrite(&header, sizeof(header), 1, file) != 1)
        return WRITE_CANNOT_WRITE;
    if(img->height * img->width == 0)
        return WRITE_IMAGE_INVALID;

    uint32_t padding = calc_padding(img->width);
    uint8_t padding_array[4];
    create_padding_arr(padding_array, 4);
    for(uint32_t i = 0; i < img->height; i++){
        if(fwrite(img->data + img->width * i,
                  img->width * sizeof(struct pixel),1, file) < 1)
            return WRITE_CANNOT_WRITE;
        if(fwrite(padding_array, padding, 1, file) < 1)
            return WRITE_CANNOT_WRITE;
    }
    return OK;
}

void write_bmp_image(const char *path, struct image *img) {
    FILE *file = NULL;
    enum status file_status;

    file_status = open_file(path, &file, OPEN_WRITE_BINARY);
    if(file_status != OK){
        error_actions(file_status, img, file);
    }

    file_status = convert_to_bmp(file, img);
    if(file_status != OK){
        error_actions(file_status, img, file);
    }

    file_status = close_file(file);
    if(file_status != OK){
        error_actions(file_status, img, file);
    }
    clear_image(img);
}
