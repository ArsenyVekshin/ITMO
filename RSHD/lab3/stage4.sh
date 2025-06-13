echo " Состояние таблицы:"
psql -p 9296 -U postgres2 -d postgres -c "SELECT * FROM test_table3;"
sleep 1


echo "Создаем бэкап..."
pg_basebackup -p 9296 -D $HOME/backup -Ft -Xs -P
chmod -R 777 backup
chmod -R 777 dumps
sleep 1

echo "Добавим новые данные и зафиксируем изменения..."
psql -p 9296 -U postgres2 -d postgres -f stage4_add_test_data.sql
sleep 1

echo "Имитируем ошибку... "
psql -p 9296 -U postgres2 -d postgres -f stage4_failure_init.sql
sleep 1

echo " Состояние таблицы:"
psql -p 9296 -U postgres2 -d postgres -c "SELECT * FROM test_table3;"
sleep 1


echo " Востановим базу:"
bash stage4.sh

echo " Состояние таблицы:"
psql -p 5432 -U postgres2 -d postgres -c "SELECT * FROM test_table3;"
sleep 1