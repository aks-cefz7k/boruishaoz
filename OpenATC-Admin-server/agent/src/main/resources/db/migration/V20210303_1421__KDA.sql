-- ----------------------------
-- Table structure for scats_device
-- ----------------------------
DROP TABLE IF EXISTS "public"."scats_device";
CREATE TABLE "public"."scats_device" (
  "agentid" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(50) COLLATE "pg_catalog"."default",
  "data" json
)
;

-- ----------------------------
-- Primary Key structure for table scats_device
-- ----------------------------
ALTER TABLE "public"."scats_device" ADD CONSTRAINT "scats_device_pkey" PRIMARY KEY ("agentid");
