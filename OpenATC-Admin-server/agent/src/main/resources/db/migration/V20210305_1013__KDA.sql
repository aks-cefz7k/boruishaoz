-- ----------------------------
-- Table structure for t_orgnization
-- ----------------------------
DROP TABLE IF EXISTS "t_orgnization";
CREATE TABLE "t_orgnization" (
  "id" int4 NOT NULL,
  "create_time" timestamp(6),
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2,
  "update_time" timestamp(6),
  "orgnization_code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Primary Key structure for table t_orgnization
-- ----------------------------
ALTER TABLE "t_orgnization" ADD CONSTRAINT "t_orgnization_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Records of t_orgnization
-- ----------------------------
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (1, '2018-07-26 19:09:33.800890', '上海腾讯分公司', 0, '2018-07-26 19:09:33.800890', 'tengxun:shanghai');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (2,'2020-08-13 17:37:42.856', '上海徐汇区办事处', 0, '2020-08-13 17:56:55.719', 'tengxun:shanghai:xuhui');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (3,'2020-08-14 09:12:24.779', '嘉定办事处', 0, '2020-08-14 09:12:24.779', 'kedacom:jiading');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (4,'2020-08-13 17:33:13.095', '腾讯', 0, '2020-08-14 09:49:38.665', 'tengxun');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (5,'2020-08-14 09:11:22.712', '科达', 0, '2020-08-14 09:50:06.677', 'kedacom');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (6,'2020-08-14 09:11:49.794', '上研所', 0, '2020-08-14 09:55:48.026', 'kedacom:shangyanshuo');
--INSERT INTO public.t_orgnization (id, create_time, description, status, update_time, orgnization_code) VALUES (7,'2020-08-14 10:28:57.472', '13楼', 0, '2020-08-14 10:29:24.43', 'kedacom:jiading:13楼');
