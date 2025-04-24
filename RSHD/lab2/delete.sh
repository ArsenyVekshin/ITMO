CLUSTER_DIR="$HOME/cbz5"
PG_LOG="$CLUSTER_DIR/server.log"

pg_ctl -D "$HOME/cbz5" stop -m fast
rm -rf "$CLUSTER_DIR"
rm -f "$PG_LOG"
echo "✅ Кластер PostgreSQL уничтожен"