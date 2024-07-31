CREATE USER alabtaal WITH PASSWORD 'abt';
CREATE DATABASE alabtaal;
GRANT ALL PRIVILEGES ON DATABASE alabtaal TO alabtaal;

\connect alabtaal;
create schema inventory;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

GRANT ALL ON SCHEMA inventory TO alabtaal;