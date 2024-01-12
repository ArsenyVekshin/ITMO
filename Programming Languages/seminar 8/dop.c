#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>
#include <pthread.h>

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}


int main() {
    int* shared_memory = create_shared_memory(sizeof(int) * 10);
    int* updated = create_shared_memory(sizeof(int));
    int* child_finished = create_shared_memory(sizeof(int));
    pthread_spinlock_t lock;
    pthread_spin_init(&lock, PTHREAD_PROCESS_SHARED);

    for (size_t i = 0; i < 10; i++) {
        shared_memory[i] = i + 1;
    }

    printf("Shared memory at: %p\n" , shared_memory);
    int pid = fork();

    if (pid == 0) {
        int index, value;
        while (1) {
            printf("Enter index and value to change: \n");
            scanf("%d %d", &index, &value);
            if (index < 0) {
                break;
            }
            if (index >= 10) {
                printf("Index out of bounds\n");
                continue;
            }
            pthread_spin_lock(&lock);
            shared_memory[index] = value;
            updated[0] = 1;
            pthread_spin_unlock(&lock);

        }
        child_finished[0] = 1;
        return 0;
    } else {
        while (1) {
            pthread_spin_lock(&lock);
            if (updated[0]) {
                for (int i = 0; i < 10; i++) {
                    printf("%d ", shared_memory[i]);
                }
                printf("\nArray updated\n");
                updated[0] = 0;
            }
            if (child_finished[0]) {
                break;
            }
            pthread_spin_unlock(&lock);
        }
    }
    printf("end\n");
    pthread_spin_destroy(&lock);
}
