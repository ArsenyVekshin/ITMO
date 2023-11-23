#include <stdio.h>
#include "enums.h"
#include "image.h"
#include "file.h"

void error_prints(enum status error);
void error_actions(enum status error, struct image *img, FILE *file);
