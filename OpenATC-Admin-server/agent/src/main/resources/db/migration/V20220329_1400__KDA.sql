DROP TABLE IF EXISTS featureall;
CREATE TABLE featureall (
     id serial PRIMARY KEY,
     "agentid" varchar(255),
     "time" timestamp,
     "data" varchar
)
;