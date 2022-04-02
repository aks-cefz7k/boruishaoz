-- ----------------------------
-- Add "endtime", "operatetime" for table trafficincident
-- ----------------------------
ALTER TABLE "trafficincident" add "endtime" timestamp(6);
ALTER TABLE "trafficincident" add "operatetime" timestamp(6);