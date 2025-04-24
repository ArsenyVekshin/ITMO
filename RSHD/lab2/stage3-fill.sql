-- psql -U gooduser -d dpk35db -p 9296 -h localhost
-- psql -d dpk35db -p 9296 -h localhost
-- psql -d dpk35db -p 9296 -h localhost
CREATE TABLE test_table2 (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO test_table2 (name) VALUES ('Alice1'), ('Bob1'), ('Charlie1');
