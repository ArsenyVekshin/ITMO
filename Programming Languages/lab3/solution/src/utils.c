#include "utils.h"
const int32_t MAX_PADDING=4;
struct pixel *calc_image_line_pointer(struct pixel *ptr, uint32_t width, uint32_t row){
    return (struct pixel*)ptr + width * row;
}
uint32_t calc_padding(uint32_t w){
    return (MAX_PADDING - (w*sizeof(struct pixel)) % MAX_PADDING) % MAX_PADDING;
}
