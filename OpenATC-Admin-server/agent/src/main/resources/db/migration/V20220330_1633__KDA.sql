DROP TABLE IF EXISTS featureall;
CREATE TABLE featureall (
    id serial PRIMARY KEY,
    "agentid" varchar(255) unique ,
    "time" timestamp,
    "data" varchar
)
;