-- ----------------------------
-- Table structure for trafficincident
-- ----------------------------
DROP TABLE IF EXISTS "trafficincident";
CREATE TABLE "trafficincident" (
"id" varchar(255) COLLATE "default" NOT NULL,
"state" int4,
"type" varchar(255) COLLATE "default",
"begintime" timestamp(6),
"duration" int4,
"level" int4,
"flag" int4,
"lastoperater" varchar(255) COLLATE "default",
"desc" varchar(255) COLLATE "default",
"junction" int8,
"phase" int4,
"abnormalstate" int4,
"upstream" int8,
"downstream" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Primary Key structure for table trafficincident
-- ----------------------------
ALTER TABLE "trafficincident" ADD PRIMARY KEY ("id");