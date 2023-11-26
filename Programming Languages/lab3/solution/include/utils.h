#pragma once

#include "image.h"

struct pixel *calc_image_line_pointer(struct pixel *ptr, uint32_t width, uint32_t row);
uint32_t calc_padding(uint32_t w);

