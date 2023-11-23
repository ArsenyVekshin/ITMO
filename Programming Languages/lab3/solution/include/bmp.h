#include <stdint.h>
#include "image.h"

#define BMP_HEADER 0x4D42
#define HEADER_SIZE 54
#define BMP_HEADER_SIZE 40
#define BMP_BI_PLANES 1
#define BMP_BIT_COUNT 24
#define BMP_BI_COMPRESSION 0
#define BMP_PEELS 2834
#define BMP_COLORS_UNUSED 0

#pragma pack(push, 1)
struct bmp_header
{
    // FILE HEADER (14 bytes)
    uint16_t bfType; //Signature
    uint32_t bfileSize; //
    uint32_t bfReserved;
    uint32_t bOffBits;
    // BITMAP HEADER V3 (40 bytes)
    uint32_t biSize;
    uint32_t biWidth;
    uint32_t biHeight;
    uint16_t biPlanes;
    uint16_t biBitCount;
    uint32_t biCompression;
    uint32_t biSizeImage;
    uint32_t biXPelsPerMeter;
    uint32_t biYPelsPerMeter;
    uint32_t biClrUsed;
    uint32_t biClrImportant;
    // bitmask and other else
};
#pragma pack(pop)

enum status validate_bmp_header(const struct bmp_header *header);
struct bmp_header generate_bmp_header(const struct image *img);
