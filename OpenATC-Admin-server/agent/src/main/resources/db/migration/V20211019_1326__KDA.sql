DROP TABLE IF EXISTS flow;
CREATE TABLE flow (
    id serial PRIMARY KEY,
    "agentid" varchar(255),
    "time" timestamp(6),
    "data" json
)
;

DROP TABLE IF EXISTS pattern;
CREATE TABLE pattern (
    id serial PRIMARY KEY,
    "agentid" varchar(255),
    "time" timestamp(6),
    "data" json
)
;
