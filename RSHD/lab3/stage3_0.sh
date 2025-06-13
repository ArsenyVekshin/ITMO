#!/bin/bash

# --- НАСТРОЙКИ ---
export PGUSER_INITIAL="postgres2" # Пользователь для работы с исходным и новым кластером
export DBNAME_TO_DAMAGE="dpk35"    # Имя БД для симуляции и восстановления
export TABLE_TO_DAMAGE="test_table" # Имя таблицы для симуляции
export SCHEMA_TO_DAMAGE="public"   # Схема таблицы

export PGDATA_OLD="$HOME/cbz5"
export TBDATA_OLD="$HOME/rez5"
export PGDATA_NEW="$HOME/cbz5_new"
export TBDATA_NEW="$HOME/rez5_new"
export PGPORT=9296 # Порт будет использоваться для обоих кластеров (старого и нового)

export LOCAL_DUMP_DIR="$HOME/dumps"
export DUMP_FILE="$LOCAL_DUMP_DIR/db_dump.sql.gz" # Имя файла бэкапа

# --- ОЧИСТКА ОТ ПРЕДЫДУЩИХ ЗАПУСКОВ (ОСТОРОЖНО!) ---
echo "Очистка старых директорий и остановка возможных процессов..."
pg_ctl -s -D "$PGDATA_OLD" stop -m immediate > /dev/null 2>&1
pg_ctl -s -D "$PGDATA_NEW" stop -m immediate > /dev/null 2>&1
sleep 1
rm -rf "$PGDATA_OLD" "$TBDATA_OLD" "$PGDATA_NEW" "$TBDATA_NEW" "$LOCAL_DUMP_DIR"
echo "✅ Старые директории очищены."

# --- СОЗДАНИЕ ДИРЕКТОРИЙ ---
mkdir -p "$PGDATA_OLD" "$TBDATA_OLD" "$PGDATA_NEW" "$TBDATA_NEW" "$LOCAL_DUMP_DIR"
echo "✅ Рабочие директории созданы."

# --- 1. ИНИЦИАЛИЗАЦИЯ И ЗАПУСК ИСХОДНОГО КЛАСТЕРА ---
echo -e "\n--- ЭТАП 1: Настройка исходного кластера ---"
initdb --username="$PGUSER_INITIAL" --no-locale -E UTF8 -D "$PGDATA_OLD" > /dev/null
if [ $? -ne 0 ]; then echo "❌ Ошибка initdb для $PGDATA_OLD"; exit 1; fi
echo "✅ Исходный кластер инициализирован в $PGDATA_OLD"

# Настраиваем порт в postgresql.conf для исходного кластера
echo "port = $PGPORT" >> "$PGDATA_OLD/postgresql.conf"
echo "listen_addresses = 'localhost'" >> "$PGDATA_OLD/postgresql.conf"

pg_ctl -s -D "$PGDATA_OLD" -l "$PGDATA_OLD/logfile.log" start
if [ $? -ne 0 ]; then echo "❌ Ошибка запуска исходного кластера"; exit 1; fi
echo "✅ Исходный кластер запущен на порту $PGPORT"
sleep 2 # Даем время серверу запуститься

# Создаем табличное пространство и базу данных в исходном кластере
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d postgres -c "CREATE TABLESPACE rez5_old LOCATION '$TBDATA_OLD';"
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d postgres -c "CREATE DATABASE $DBNAME_TO_DAMAGE TABLESPACE rez5_old;"
if [ $? -ne 0 ]; then echo "❌ Ошибка создания БД $DBNAME_TO_DAMAGE"; pg_ctl -s -D "$PGDATA_OLD" stop; exit 1; fi
echo "✅ БД $DBNAME_TO_DAMAGE и табличное пространство rez5_old созданы."

# Создаем тестовую таблицу и наполняем ее данными
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -c "
  CREATE TABLE ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} (id SERIAL PRIMARY KEY, name TEXT);
  INSERT INTO ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} (name) VALUES ('data1'), ('data2'), ('data3');
  SELECT 'Создана таблица ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} и заполнены данные.' AS msg;
"
echo "✅ Тестовая таблица ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} создана и заполнена в БД $DBNAME_TO_DAMAGE."

# --- 2. СОЗДАНИЕ РЕЗЕРВНОЙ КОПИИ ---
echo -e "\n--- ЭТАП 2: Создание резервной копии ---"
pg_dump -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" | gzip > "$DUMP_FILE"
if [ ${PIPESTATUS[0]} -ne 0 ]; then echo "❌ Ошибка создания дампа БД $DBNAME_TO_DAMAGE"; pg_ctl -s -D "$PGDATA_OLD" stop; exit 1; fi
echo "✅ Резервная копия БД $DBNAME_TO_DAMAGE сохранена в $DUMP_FILE"

# --- 3. СИМУЛЯЦИЯ СБОЯ ---
echo -e "\n--- ЭТАП 3: Симуляция сбоя ---"
echo "Ищем файл для таблицы ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} в БД $DBNAME_TO_DAMAGE..."

# Получаем относительный путь к файлу таблицы
TABLE_FILE_REL_PATH=$(psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -Atc "
  SELECT pg_relation_filepath('${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}'::regclass);
")

if [[ -z "$TABLE_FILE_REL_PATH" ]]; then
  echo "❌ Не удалось получить путь к файлу таблицы ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}"
  pg_ctl -s -D "$PGDATA_OLD" stop; exit 1;
fi

TABLE_FILE_ABS_PATH="$PGDATA_OLD/$TABLE_FILE_REL_PATH"
echo "✅ Файл таблицы найден: $TABLE_FILE_ABS_PATH"

echo "Удаляем файл таблицы: $TABLE_FILE_ABS_PATH"
rm -f "$TABLE_FILE_ABS_PATH"
if [ $? -ne 0 ]; then echo "❌ Ошибка удаления файла таблицы"; pg_ctl -s -D "$PGDATA_OLD" stop; exit 1; fi
echo "✅ Файл таблицы удалён."

echo "Проверка состояния текущего кластера ПОСЛЕ удаления файла:"
pg_ctl -s -D "$PGDATA_OLD" status

echo "Попытка чтения данных из поврежденной таблицы (ожидается ошибка):"
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -c "SELECT * FROM ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} LIMIT 1;" || echo "ℹ️ Ошибка при чтении (ожидаемо)"

echo "Перезапускаем СУБД на ОСНОВНОМ узле (ожидаются проблемы при старте или доступе к таблице):"
pg_ctl -s -D "$PGDATA_OLD" restart -l "$PGDATA_OLD/logfile_after_damage.log"
sleep 2
echo "Проверка логов после перезапуска (ищите ошибки, связанные с отсутствующим файлом): $PGDATA_OLD/logfile_after_damage.log"
grep "could not open relation file" "$PGDATA_OLD/logfile_after_damage.log" || echo "ℹ️ Ошибок 'could not open relation file' в логе не найдено, но проблемы с таблицей все равно могут быть."

echo "Повторная попытка чтения данных из поврежденной таблицы ПОСЛЕ перезапуска:"
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -c "SELECT * FROM ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE} LIMIT 1;" || echo "ℹ️ Ошибка при чтении (ожидаемо)"
echo "✅ Симуляция сбоя завершена."

# --- 4. ВОССТАНОВЛЕНИЕ ДАННЫХ ---
echo -e "\n--- ЭТАП 4: Восстановление данных в новом расположении ---"
echo "Останавливаем старый (поврежденный) кластер..."
pg_ctl -s -D "$PGDATA_OLD" stop -m immediate
echo "✅ Старый кластер остановлен."

echo "Инициализируем новый кластер в $PGDATA_NEW..."
initdb --username="$PGUSER_INITIAL" --no-locale -E UTF8 -D "$PGDATA_NEW" > /dev/null
if [ $? -ne 0 ]; then echo "❌ Ошибка initdb для $PGDATA_NEW"; exit 1; fi
echo "✅ Новый кластер инициализирован."

# Настраиваем порт в postgresql.conf для нового кластера
echo "port = $PGPORT" >> "$PGDATA_NEW/postgresql.conf"
echo "listen_addresses = 'localhost'" >> "$PGDATA_NEW/postgresql.conf"

echo "Запускаем PostgreSQL на новом кластере ($PGDATA_NEW)..."
pg_ctl -s -D "$PGDATA_NEW" -l "$PGDATA_NEW/logfile.log" start
if [ $? -ne 0 ]; then echo "❌ Ошибка запуска нового кластера"; exit 1; fi
echo "✅ Новый кластер запущен."
sleep 2



echo "Создаём табличное пространство в новом кластере..."
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d postgres -c "CREATE TABLESPACE rez5_new LOCATION '$TBDATA_NEW';"
if [ $? -ne 0 ]; then echo "❌ Ошибка создания табличного пространства rez5_new"; pg_ctl -s -D "$PGDATA_NEW" stop; exit 1; fi
echo "✅ Табличное пространство rez5_new создано в $TBDATA_NEW."

echo "Создаём базу данных $DBNAME_TO_DAMAGE в новом кластере..."
# База данных будет создана суперпользователем кластера ($PGUSER_INITIAL)
# и затем восстановлена от его же имени.
psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d postgres -c "CREATE DATABASE $DBNAME_TO_DAMAGE  rez5_new;"
if [ $? -ne 0 ]; then echo "❌ Ошибка создания БД $DBNAME_TO_DAMAGE в новом кластере"; pg_ctl -s -D "$PGDATA_NEW" stop; exit 1; fi
echo "✅ База данных $DBNAME_TO_DAMAGE создана в новом кластере."

echo "Восстанавливаем данные из $DUMP_FILE..."
gunzip -c "$DUMP_FILE" | psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE"
if [ ${PIPESTATUS[0]} -ne 0 -o ${PIPESTATUS[1]} -ne 0 ]; then
  echo "❌ Ошибка восстановления данных из дампа."
  pg_ctl -s -D "$PGDATA_NEW" stop
  exit 1
fi
echo "✅ Данные восстановлены."

# --- 5. ПРОВЕРКА ---
echo -e "\n--- ЭТАП 5: Проверка восстановленного кластера ---"
echo "Проверка статуса нового кластера..."
pg_ctl -s -D "$PGDATA_NEW" status

echo "Проверка наличия восстановленной таблицы '${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}'..."
# Используем to_regclass. Если она возвращает не NULL (не пустую строку для -Atc), значит объект найден.
# Имя, которое она возвращает, может быть нормализовано (например, без схемы 'public' если она в search_path).
TABLE_REGCLASS_OUTPUT=$(psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -Atc "
  SELECT to_regclass('${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}');
")

if [[ -n "$TABLE_REGCLASS_OUTPUT" ]]; then # -n проверяет, что строка не пустая
    echo "✅ Таблица '${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}' успешно восстановлена (PostgreSQL идентифицирует ее как: '$TABLE_REGCLASS_OUTPUT')."
    echo "Проверка данных в таблице:"
    psql -U "$PGUSER_INITIAL" -p "$PGPORT" -d "$DBNAME_TO_DAMAGE" -c "SELECT * FROM ${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE};"
else
    echo "❌ Таблица '${SCHEMA_TO_DAMAGE}.${TABLE_TO_DAMAGE}' не найдена после восстановления (to_regclass не нашла объект)."
fi

echo -e "\nРабота завершена. Вы можете остановить новый кластер командой:"
echo "pg_ctl -D \"$PGDATA_NEW\" stop"