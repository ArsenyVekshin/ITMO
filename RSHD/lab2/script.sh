# подключение
# ssh -J s367133@helios.cs.ifmo.ru:2222 postgres2@pg107
# unVL%1653
# /fE/sM38
# Вариант: 65296

# Этап 1
mkdir cbz5

CLUSTER_DIR="$HOME/cbz5"
ENCODING="KOI8-R"
LOCALE="en_US.US-ASCII"

initdb -D "$CLUSTER_DIR" \
       --encoding="$ENCODING" \
       --locale="$LOCALE" \
       --username=postgres

# Этап 2

# изменения в posgres.conf

# CONNECTIONS AND AUTHENTICATION
# - Connection Settings -
listen_addresses = 'localhost'		# подключения только с localhost
port = 9296							# Только локальные TCP/IP подключения
max_connections = 800 				# 200 пользователей * 4 сессии
unix_socket_directories = '/tmp' 	# Разрешён Unix-socket (по умолчанию)

# - Authentication -
password_encryption = scram-sha-256	# алгоритм кодировки пароля

# RESOURCE USAGE (except WAL)
# - Memory -
shared_buffers = 128MB		# Буфер shared memory для кеша страниц БД
temp_buffers = 8MB			# Буфер для временных объектов, создаётся при подключении
work_mem = 16MB				# Используется для сортировок, хэшей и т.п.

# WRITE-AHEAD LOG
# - Settings -
fsync = on			# Запись на диск до конца транзакции
commit_delay = 0	# Без задержек перед коммитом (максимальная производительность)

# - Checkpoints -
checkpoint_timeout = 10min	# Как часто выполнять checkpoint (под баланс запись/производительность)


# QUERY TUNING
# - Planner Cost Constants -
effective_cache_size = 4GB	# Зарезервированно ОС для postgres (для планировщика запросов)


# REPORTING AND LOGGING
# - Where to Log -
log_destination = 'stderr'	# Поток ошибок -> логи
logging_collector = on		# Включает сбор логов
log_directory = 'log'		# Каталог для логов
log_filename = 'postgresql-%Y-%m-%d_%H%M%S.log'	# Имя лог-файла (патерн)

# - What to Log -
log_connections = on		# Логировать подключения
log_disconnections = on		# Логировать отключения
log_statement = 'none'		# Не логировать запросы

# - When to Log -
log_min_messages = warning	# Логировать ошибки и все более критичное 


# Изменения в pg_hba.conf
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local" is for Unix domain socket connections only
local   all             all                                     peer
# Разрешить TCP/IP подключения только с localhost с аутентификацией SCRAM-SHA-256
host    all     	all             127.0.0.1/32            scram-sha-256
host    all     	all             ::1/128                 scram-sha-256
