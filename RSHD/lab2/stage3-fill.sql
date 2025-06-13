-- psql -U gooduser -d dpk35db -p 9296 -h localhost
-- psql -d dpk35db -p 9296 -h localhost
-- psql -d dpk35db -p 9296 -h localhost
CREATE TABLE test_table1 (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO test_table3 (name) VALUES ('Alice1'), ('Bob1'), ('Charlie1');
