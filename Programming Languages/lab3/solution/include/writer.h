#pragma once

#include "bmp.h"
#include "enums.h"
#include "error_handler.h"
#include "file.h"
#include "image.h"
#include "utils.h"

#include <stdint.h>

void write_bmp_image(const char *path, struct image *img);
