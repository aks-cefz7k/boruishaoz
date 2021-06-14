-- ----------------------------
-- Table structure for dev
-- ----------------------------
DROP TABLE IF EXISTS "dev";
CREATE TABLE "dev" (
"id" int4,
"lasttime" timestamp(6),
"descs" varchar COLLATE "default",
"name" varchar COLLATE "default",
"type" varchar COLLATE "default" NOT NULL,
"status" int4 DEFAULT 0 NOT NULL,
"geometry" varchar COLLATE "default",
"jsonparam" json,
"agentid" varchar(255) COLLATE "default" DEFAULT 'error'::character varying NOT NULL,
"protocol" varchar COLLATE "default",
"code" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
--for table dev
-- ----------------------------
ALTER TABLE "dev" ADD PRIMARY KEY ("id");


DROP sequence IF EXISTS sql_tbl_serial2_a;
create sequence sql_tbl_serial2_a increment by 1 minvalue 1 no maxvalue start with 2;

ALTER TABLE "dev" alter column id set default nextval('sql_tbl_serial2_a') ;

-- CREATE UNIQUE INDEX for agentid
-- ----------------------------
ALTER TABLE "dev" add CONSTRAINT "dev_agentid_key" UNIQUE ("agentid");