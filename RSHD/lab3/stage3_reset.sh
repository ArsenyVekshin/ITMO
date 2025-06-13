export PGDATA=$HOME/cbz5
export TBDATA=$HOME/rez5
export PGDATA_NEW=$HOME/cbz5_new
export TBDATA_NEW=$HOME/rez5_new
export PGPORT=9296
export REMOTE_DUMP_DIR=/var/db/postgres3/dumps
export LOCAL_DUMP_DIR=$HOME/dumps


pg_ctl -D $PGDATA stop
pg_ctl -D $PGDATA_NEW stop

rm -rf $PGDATA_NEW
pg_ctl -D $HOME/cbz5 start
