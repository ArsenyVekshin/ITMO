#include <stdio.h>
#include <stdlib.h>
#include "vector.h"


int main() {
    vector v;

    vector_init(&v);
    vector_add(&v, "bobr");
    vector_add(&v, "kurwa");
    vector_add(&v, "ia");
    vector_add(&v, "perdole");

    FILE *file;
    file = fopen("f.txt", "w");

    if (file == NULL){
        printf("Error opening file!\n");
        exit(1);
    }

    print_vector(&v,file);
    fclose(file);

    print_vector(&v,stdout);
    printf("\n");

    vector_free(&v);

}