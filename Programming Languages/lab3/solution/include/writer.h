#include <stdint.h>
#include "enums.h"
#include "file.h"
#include "image.h"
#include "bmp.h"
#include "utils.h"
#include "error_handler.h"

void write_bmp_image(const char *path, struct image *img);
