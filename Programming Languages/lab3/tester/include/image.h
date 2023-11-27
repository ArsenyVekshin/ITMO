#pragma once

#include <stdint.h>

#pragma pack(push, 1)
struct pixel {
    uint8_t b, g, r;
};
#pragma pack(pop)


struct image {
    uint32_t width, height;
    struct pixel *data;
};
