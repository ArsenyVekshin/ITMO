#include "error_handler.h"
#include "operator.h"
#include "reader.h"
#include "writer.h"

#include <stdlib.h>

#define REQUIRED_ARGUMENTS_NUM 4
#define ARG_INPUT_FILE 1
#define ARG_OUTPUT_FILE 2
#define ARG_ANGLE 3

int main( int argc, char** argv ) {
    if(argc != REQUIRED_ARGUMENTS_NUM)
        error_actions(WRONG_ARGUMENTS_NUMBER, NULL, NULL);

    char *input_file = argv[ARG_INPUT_FILE];
    char *output_file = argv[ARG_OUTPUT_FILE];
    int32_t angle = (int32_t) atoi(argv[ARG_ANGLE]);

    struct image img = {.width = 0, .height = 0, .data = NULL};
    read_bmp_image(input_file, &img);
    rotate_image(&img, angle);
    write_bmp_image(output_file, &img);
}
