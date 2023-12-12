#pragma once

#include "enums.h"
#include "file.h"
#include "image.h"

#include <stdio.h>
#include <stdlib.h>

void error_actions(enum status error, struct image *img, FILE *file);
