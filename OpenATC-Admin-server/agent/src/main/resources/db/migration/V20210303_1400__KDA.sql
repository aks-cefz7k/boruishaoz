-- ----------------------------
-- Table structure for sysconfig
-- ----------------------------
DROP TABLE IF EXISTS "third_platform";

CREATE TABLE "third_platform" (
"name" varchar(255) NOT NULL,
"ip" varchar(255) NOT NULL,
"port" int4 NOT NULL,
"third_plat_ip" varchar(255) NOT NULL,
"third_plat_port" int4 NOT NULL
)
WITH (OIDS=FALSE)
;

-- ----------------------------
-- Table structure for third_platform
-- ----------------------------
DROP TABLE IF EXISTS "third_platform";
CREATE TABLE "third_platform" (
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "ip" varchar(255) COLLATE "pg_catalog"."default",
  "port" int4,
  "third_plat_ip" varchar(255) COLLATE "pg_catalog"."default",
  "third_plat_port" int4
)
;