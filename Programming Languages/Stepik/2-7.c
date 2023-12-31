#include <stdint.h>
enum move_dir { MD_UP, MD_RIGHT, MD_DOWN, MD_LEFT, MD_NONE };

typedef void (*move_callback)(enum move_dir);
// Робот и его callback'и
// callback'ов может быть неограниченное количество
struct robot {
    const char* name;
    move_callback* callbacks;
    size_t num_callbacks;
};


// Добавить callback к роботу, чтобы он вызывался при движении
// В callback будет передаваться направление движения
void register_callback(struct robot* robot, move_callback new_cb) {
    size_t new_size = robot->num_callbacks + 1;
    move_callback* new_callbacks = realloc(robot->callbacks, new_size * sizeof(move_callback));
    if (new_callbacks) {
        robot->callbacks = new_callbacks;
        robot->callbacks[robot->num_callbacks] = new_cb;
        robot->num_callbacks = new_size;
    }
}

// Отменить все подписки на события.
// Это нужно чтобы освободить зарезервированные ресурсы
// например, выделенную в куче память
void unregister_all_callbacks(struct robot* robot) {
    free(robot->callbacks); // освобождаем память, выделенную для массива указателей на функции
    robot->callbacks = NULL;
    robot->num_callbacks = 0;
}

// Вызывается когда робот движется
// Эта функция должна вызвать все обработчики событий
void move(struct robot* robot, enum move_dir dir) {
    for (size_t i = 0; i < robot->num_callbacks; i++) {
        robot->callbacks[i](dir); // вызываем каждый зарегистрированный обработчик события
    }
}