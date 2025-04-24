CREATE TABLESPACE dpk35 LOCATION '/var/db/postgres2/dpk35';

CREATE DATABASE dpk35 TEMPLATE template0 TABLESPACE dpk35;
CREATE DATABASE goodblackrole TEMPLATE template1;


CREATE ROLE gooduser WITH LOGIN PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE dpk35 TO gooduser;
GRANT ALL PRIVILEGES ON DATABASE goodblackrole TO gooduser;

GRANT USAGE ON SCHEMA public TO gooduser;
GRANT CREATE ON SCHEMA public TO gooduser;

ALTER SCHEMA public OWNER TO gooduser;

ALTER ROLE gooduser SET default_tablespace = goodblackrole;

CREATE TABLESPACE meow LOCATION '/var/db/postgres2/meow';
