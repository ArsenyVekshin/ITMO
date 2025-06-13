#!/bin/bash

# --- НАСТРОЙКИ ---
export PGUSER_INITIAL="postgres2"
export PGDATA="$HOME/cbz5"
export DEFAULTS_DIR="$HOME/defaults"
export PGPORT=9296 




echo " ---------- Остановка всех запущенных кластеров ---------- "
pg_ctl -s -D "$HOME/cbz5" stop
pg_ctl -s -D "$HOME/cbz5_new" stop
pg_ctl -s -D "$HOME/pg_data_recovery" stop

sleep 2

echo " ---------- Очистка директорий ---------- "
rm -rf "$HOME/cbz5"
rm -rf "$HOME/cbz5_new"
rm -rf "$HOME/pg_data_recovery"
rm -f $HOME/dumps/*
rm -f $HOME/wal_dir/*
rm -f $HOME/backup/*

echo " ---------- Создаем директории ---------- "
mkdir "$HOME/pg_data_recovery"
mkdir "$HOME/pg_wal"

echo " ---------- Инициализируем кластер ---------- "

# создаем бд
mkdir $PGDATA
ENCODING="KOI8-R"
LOCALE="en_US.US-ASCII"
initdb -D "$PGDATA" \
       --encoding="$ENCODING" \
       --locale="$LOCALE" \
       --username=$PGUSER_INITIAL

echo "Скопируем конфигурационные файлы"
rm $PGDATA/postgresql.conf
rm $PGDATA/pg_hba.conf
cp $DEFAULTS_DIR/postgresql.conf $PGDATA/postgresql.conf
cp $DEFAULTS_DIR/pg_hba.conf $PGDATA/pg_hba.conf

pg_ctl -D "$PGDATA" start

echo " ---------- Заполним данными ---------- "
psql -p 9296 -U postgres2 -d postgres -f $DEFAULTS_DIR/fill.sql


echo " ---------- ЛР3 этап 1 ---------- "
psql -p 9296 -d postgres -c "create role backup_user with login password 'strongpass' superuser"
# добавь сюда проверку, если "~/.pgpass" не существует - выполнять 2 команды ниже, иначе - нет
# echo "localhost:9296:*:backup_user:strongpass" >> ~/.pgpass
# chmod 600 ~/.pgpass
pg_ctl -D $PGDATA restart 