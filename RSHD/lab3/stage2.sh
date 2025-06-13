# recovery_rezerv_stg2.sh

export PGDATA=$HOME/cbz55
export TBDATA=$HOME/rez55
export PGPORT=9296


mkdir -p $PGDATA
mkdir -p $TBDATA
initdb -D $PGDATA


pg_ctl -D $PGDATA start

# Создаём  табличное пространство
psql -U postgres3 -p $PGPORT -d postgres -c "CREATE TABLESPACE rez55 LOCATION '/var/db/postgres3/rez55';"
# Разархивируем дамп, чтобы восстановить кластер по нему.
gunzip -c "$(ls -t dumps/dump_*.sql.gz | head -n1)" | psql -U postgres3 -d postgres -p $PGPORT

# Проверка, что сервер отвечает и всё хорошо
pg_ctl -D $PGDATA status
