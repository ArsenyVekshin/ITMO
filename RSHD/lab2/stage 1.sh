mkdir cbz5

CLUSTER_DIR="$HOME/cbz5"
ENCODING="KOI8-R"
LOCALE="en_US.US-ASCII"

initdb -D "$CLUSTER_DIR" \
       --encoding="$ENCODING" \
       --locale="$LOCALE" \
       --username=postgres2
