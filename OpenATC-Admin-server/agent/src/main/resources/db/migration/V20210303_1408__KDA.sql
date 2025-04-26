

-- ----------------------------
-- Table structure for ht_device
-- ----------------------------
DROP TABLE IF EXISTS "public"."ht_device";
CREATE TABLE "public"."ht_device" (
  "agentid" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(50) COLLATE "pg_catalog"."default",
  "data" json
)
;

-- ----------------------------
-- Primary Key structure for table ht_device
-- ----------------------------
ALTER TABLE "public"."ht_device" ADD CONSTRAINT "ht_device_pkey" PRIMARY KEY ("agentid");
