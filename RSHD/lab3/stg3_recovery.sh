#!/bin/bash

# Новая переменная окружения директории с дампами на основном узле
export LOCAL_DUMP_DIR=$HOME/dumps/

# Новая переменная окружения директории с дампами на резервном узле
export REMOTE_DUMP_DIR=/var/db/postgres3/dumps

# Создаём директорию для хранения дампов на основном узле
mkdir -p "$LOCAL_DUMP_DIR"

# Создаём директорию для хранения дампов на резервном узле
ssh pg113 "mkdir -p $REMOTE_DUMP_DIR"

# Выполнение логического резервного копирования для всех баз данных на сервере с помощью pg_dumpall
pg_dumpall -p 9296 -U postgres2 --exclude-database=template1 | gzip > "$LOCAL_DUMP_DIR/dump_$(date +%Y-%m-%d).sql.gz"

# Проверка, что резервное копирование прошло успешно
if [ $? -eq 0 ]; then
    echo "Резервное копирование успешно завершено на основном узле: $LOCAL_DUMP_DIR"

    # Копирование только что созданных дампов на резервный узел
    scp -r "$LOCAL_DUMP_DIR"/* pg113:"$REMOTE_DUMP_DIR/"

    if [ $? -eq 0 ]; then
        echo "Резервная копия успешно перенесена на резервный узел: $REMOTE_DUMP_DIR"

        # Удаление дампов после копирования на резервный узел
        find "$LOCAL_DUMP_DIR"/* -delete

        # Удаление дампов с резервного узла по истечении 4 недель
        ssh pg113 "find $REMOTE_DUMP_DIR -type f -name '*.gz' -mtime +28 -exec rm -f {} \;"

        echo "Старые резервные копии удалены на резервном узле"
    else
        echo "Ошибка при переносе резервной копии на резервный узел"
        exit 1
    fi
else
    echo "Ошибка при выполнении резервного копирования на основном узле"
    exit 1
fi
