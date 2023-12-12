#include "operator.h"

int32_t prepare_angle(int32_t angle){
    return ((angle % 360) + 360) % 360;
}

static struct image rotate90(const struct image img){
    struct image new_image = create_image(img.height, img.width);
    enum status img_status = check_image(&new_image);
    if (img_status != OK)
        error_actions(img_status, NULL, NULL);

    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[x*img.height + img.height - y - 1] = img.data[y * img.width + x];
        }
    }
    return new_image;
}

static struct image rotate180(struct image img){
    struct image new_image = create_image(img.width, img.height);
    enum status img_status = check_image(&new_image);
    if (img_status != OK)
        error_actions(img_status, NULL, NULL);

    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[(img.height - y)*img.width - x - 1] = img.data[y * img.width + x];
        }
    }
    return new_image;
}

static struct image rotate270(const struct image img){
    struct image new_image = create_image(img.height, img.width);
    enum status img_status = check_image(&new_image);
    if (img_status != OK)
        error_actions(img_status, NULL, NULL);

    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[(img.width - x - 1)*img.height + y] = img.data[y * img.width + x];
        }
    }
    return new_image;
}

void rotate_image(struct image *img, int32_t angle){
    if(angle==0) return;
    angle = prepare_angle(-1*angle);
    if(angle % 90 != 0){
        error_actions(ANGLE_INVALID, img, NULL);
    }

    struct image new_image;
    switch (angle) {
        case 90:
            new_image = rotate90(*img);
            break;
        case 180:
            new_image = rotate180(*img);
            break;
        case 270:
            new_image = rotate270(*img);
            break;
    }

/*    printf("input img: ");
    print_img(img);

    printf("output img:");
    print_img(&new_image);*/

    clear_image(img);
    *img = new_image;
}

