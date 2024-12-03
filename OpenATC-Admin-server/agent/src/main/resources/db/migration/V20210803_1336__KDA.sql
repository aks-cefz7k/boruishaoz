

DROP TABLE IF EXISTS "public"."third_dev";
CREATE TABLE "public"."third_dev" (
  "agentid" varchar(255) COLLATE "default",
  "type" varchar(255) COLLATE "default",
  "data" json
)
;