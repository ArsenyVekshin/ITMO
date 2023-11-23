#include "operator.h"

int32_t prepare_angle(int32_t angle){
    return ((angle % 360) + 360) % 360;
}

struct image rotate90(const struct image img){
    struct image new_image = create_image(img.height, img.width);
    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[x * img.height - y - 1] = img.data[y * img.width + x - 1];
        }
    }
    return new_image;
}

struct image rotate180(struct image img){
    struct image new_image = create_image(img.width, img.height);
    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[(img.height - y)*img.width - x - 1] = img.data[y * img.width + x];
        }
    }
    return new_image;
}

struct image rotate270(struct image img){
    struct image new_image = create_image(img.height, img.width);
    for(uint32_t y=0; y<img.height; y++){
        for(uint32_t x=0; x<img.width; x++) {
            new_image.data[(x+1)*img.height - y - 1] = img.data[y * img.height + x];
        }
    }
    return new_image;
}

void rotate_image(struct image *img, int32_t angle){
    angle = prepare_angle(-1*angle);
    if(angle % 90 != 0){
        fprintf(stderr, "error while rotating, code %d", ANGLE_INVALID);
        return ANGLE_INVALID;
    }

    struct image new_image;
    switch (angle) {
        case 0:
        case 360:
            new_image = *img;
            break;
        case 90:
            new_image = rotate90(*img);
            break;
        case 180:
            new_image = rotate180(*img);
            break;
        case 270:
            new_image = rotate270(*img);
            break;
        default:
            error_actions(ANGLE_INVALID, img, NULL);
    }

    clear_image(img);
    *img = new_image;
}

