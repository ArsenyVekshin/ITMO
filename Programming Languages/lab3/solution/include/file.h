#pragma once

#include "enums.h"

#include <stdio.h>

enum status open_file(const char* path, FILE **file, enum open_mode mode);
enum status close_file(FILE *file);
