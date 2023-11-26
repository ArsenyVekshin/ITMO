#pragma once

#include "bmp.h"
#include "error_handler.h"
#include "file.h"
#include "image.h"
#include "utils.h"

void read_bmp_image(const char *path, struct image *img);
