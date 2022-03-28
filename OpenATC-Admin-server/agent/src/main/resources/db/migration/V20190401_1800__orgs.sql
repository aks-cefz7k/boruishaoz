-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS "area" cascade;
CREATE TABLE "area" (
"id" int8 NOT NULL,
"name" varchar(255) COLLATE "default",
"description" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table area
-- ----------------------------
ALTER TABLE "area" ADD PRIMARY KEY ("id");



-- ----------------------------
-- Table structure for intersection
-- ----------------------------
DROP TABLE IF EXISTS "intersection" cascade;
CREATE TABLE "intersection" (
"id" int8 NOT NULL,
"geometry" "public"."geometry",
"name" varchar(255) COLLATE "default",
"area_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------
-- ----------------------------


-- CREATE UNIQUE INDEX for intersection
-- ----------------------------
ALTER TABLE "intersection" add CONSTRAINT "intersection_name_key" UNIQUE ("name");
ALTER TABLE "intersection" add CONSTRAINT "intersection_geometry_key" UNIQUE ("geometry");


-- ----------------------------
-- Primary Key structure for table intersection
-- ----------------------------
ALTER TABLE "intersection" ADD PRIMARY KEY ("id");


-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS "route" cascade;
CREATE TABLE "route" (
"id" int8 NOT NULL,
"name" varchar(255) COLLATE "default",
"keyintsid" int8,
"type" varchar(255) COLLATE "default",
"areaid" int8,
"direction" int8,
"routegroupid" int8,
"optstrategy" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table route
-- ----------------------------
ALTER TABLE "route" ADD PRIMARY KEY ("id");

ALTER TABLE "route" ADD CONSTRAINT "route_area_id_fkey" FOREIGN KEY ("areaid") REFERENCES "area" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;

-- ----------------------------
-- Table structure for route_intersection
-- ----------------------------
DROP TABLE IF EXISTS "route_intersection";
CREATE TABLE "route_intersection" (
"route_id" int8,
"intersection_id" int8,
"sortid" int8,
"id" int8 NOT NULL,
"forwardphaseid" int8,
"backwardphaseid" int8,
"distance" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table route_intersection
-- ----------------------------
ALTER TABLE "route_intersection" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "public"."route_intersection"
-- ----------------------------
ALTER TABLE "route_intersection" ADD FOREIGN KEY ("intersection_id") REFERENCES "intersection" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "route_intersection" ADD FOREIGN KEY ("route_id") REFERENCES "route" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Table structure for intersection_phase
-- ----------------------------
DROP TABLE IF EXISTS "intersection_phase";
CREATE TABLE "intersection_phase" (
"id" int8 NOT NULL,
"headintersectionid" int8,
"intersectionid" int8,
"phaseid" int8,
"tailintersectionid" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table intersection_phase
-- ----------------------------
ALTER TABLE "intersection_phase" ADD PRIMARY KEY ("id");


-- ----------------------------
-- Foreign Key structure for table "public"."intersection"
-- ----------------------------
ALTER TABLE "intersection" ADD FOREIGN KEY ("area_id") REFERENCES "area" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."route_intersection"
-- ----------------------------
ALTER TABLE "route_intersection" ADD FOREIGN KEY ("intersection_id") REFERENCES "intersection" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "route_intersection" ADD FOREIGN KEY ("route_id") REFERENCES "route" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Table structure for intersection_dev
-- ----------------------------
DROP TABLE IF EXISTS "intersection_dev";
CREATE TABLE "intersection_dev" (
"id" int8 NOT NULL,
"intersectionid" int8,
"agentid" varchar(255) COLLATE "default",
CONSTRAINT "intersection_dev_pkey" PRIMARY KEY ("id"),
CONSTRAINT "intersection_dev_intersectionid_fkey" FOREIGN KEY ("intersectionid") REFERENCES "intersection" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
)
WITH (OIDS=FALSE)
;

----------------------------
-- Add View intersection_voronoi
-- ----------------------------
CREATE OR REPLACE VIEW "intersection_voronoi" AS
 WITH points AS (
         SELECT intersection_1.geometry
           FROM intersection intersection_1
        ), voro AS (
         SELECT "public".st_transform(("public".st_dump("public".st_voronoipolygons("public".st_collect("public".st_transform(points.geometry, 3785))))).geom, 4326) AS geometry
           FROM points
        )
 SELECT intersection.id,
    intersection.area_id,
    "public".st_buffer("public".st_intersection((voro.geometry)::"public".geography, "public".st_buffer((intersection.geometry)::"public".geography, (500)::double precision)), (0.01)::double precision) AS geometry
   FROM (intersection
     JOIN voro ON ("public".st_within(intersection.geometry, voro.geometry)));

ALTER TABLE "intersection_voronoi" OWNER TO "postgres";

-- ----------------------------
-- Add View intersection_area_voronoi
-- ----------------------------

CREATE OR REPLACE VIEW "intersection_area_voronoi" AS
 SELECT intsv.area_id,
    "public".st_union((intsv.geometry)::"public".geometry) AS geometry
   FROM intersection_voronoi intsv
  WHERE (intsv.area_id IS NOT NULL)
  GROUP BY intsv.area_id;

ALTER TABLE "intersection_area_voronoi" OWNER TO "postgres";

-- ----------------------------
-- Create sequence for hibernate_sequence
-- ----------------------------
 CREATE SEQUENCE IF NOT EXISTS "hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "hibernate_sequence" OWNER TO "postgres";

-- ----------------------------
-- Create VIEW for devplustype
-- ----------------------------

CREATE OR REPLACE VIEW "devplustype" AS
 SELECT dev.agentid,
    dev.type
   FROM dev;

ALTER TABLE "devplustype" OWNER TO "postgres";

-- ----------------------------
-- Create VIEW for intsphasewithname
-- ----------------------------
CREATE OR REPLACE VIEW "intsphasewithname" AS
 SELECT t1.id,
    t1.intersectionid,
    t4.name,
    t1.phaseid,
    t1.headintersectionid,
    t2.name AS headname,
    t1.tailintersectionid,
    t3.name AS tailname
   FROM (((intersection_phase t1
     LEFT JOIN intersection t4 ON ((t4.id = t1.intersectionid)))
     LEFT JOIN intersection t2 ON ((t2.id = t1.headintersectionid)))
     LEFT JOIN intersection t3 ON ((t3.id = t1.tailintersectionid)));

-- ----------------------------
-- create table routegroup
-- ----------------------------
DROP TABLE IF EXISTS "routegroup";
CREATE TABLE "routegroup" (
"id" int8 NOT NULL,
"keyrouteid" int8,
"ismerger" bool,
"cyclethreshold" int4
)
WITH (OIDS=FALSE)
;

-- ----------------------------
-- Primary Key structure for table routegroup
-- ----------------------------
ALTER TABLE "routegroup" ADD PRIMARY KEY ("id");