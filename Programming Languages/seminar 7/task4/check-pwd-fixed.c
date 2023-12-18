#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int check_password(FILE *f, const char *password) {
    int okay = 0;
    char buffer[100];
    fgets(buffer, sizeof(buffer), stdin);
    if (strcmp(buffer, password) == 0)
        okay = 1;
    return okay;
}

int main(int argc, char **argv) {
    if (check_password(stdin, "password"))
        puts("Access granted.");
    else
        puts("Wrong password.");
}

