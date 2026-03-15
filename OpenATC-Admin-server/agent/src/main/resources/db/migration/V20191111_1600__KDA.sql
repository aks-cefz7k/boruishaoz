-- ----------------------------
-- Table structure for sysconfig
-- ----------------------------
DROP TABLE IF EXISTS "sysconfig";

CREATE TABLE "sysconfig" (
"id" int4 NOT NULL,
"ceattime" timestamp(6),
"configcode" varchar(255) COLLATE "default",
"configname" varchar(255) COLLATE "default",
"configtype" int4 NOT NULL,
"creatby" varchar(255) COLLATE "default",
"description" varchar(255) COLLATE "default",
"sort" int4 NOT NULL,
CONSTRAINT "sysconfig_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;