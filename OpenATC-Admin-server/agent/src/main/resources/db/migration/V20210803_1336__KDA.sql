

DROP TABLE IF EXISTS third_dev;
CREATE TABLE third_dev (
  "agentid" varchar(255) COLLATE "default",
  "type" varchar(255) COLLATE "default",
  "data" json
)
;