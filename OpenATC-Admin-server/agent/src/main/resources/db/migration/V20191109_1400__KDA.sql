

create table t_permission
(
	id serial not null constraint t_permission_pkey primary key,
	permission_code varchar(255) default ''::character varying,
	description varchar(255) default ''::character varying,
	status smallint default 1 not null,
	ext_infos varchar(255),
--     ext_infos jsonb,
	create_time timestamp with time zone default now() not null,
	update_time timestamp with time zone default now() not null
)
;

comment on table t_permission is '后台权限表'
;

comment on column t_permission.id is '自定id,主要供前端展示权限列表分类排序使用.'
;

comment on column t_permission.permission_code is '权限的代码/通配符,对应代码中@RequiresPermissions 的value'
;

comment on column t_permission.description is '本权限的中文释义'
;

comment on column t_permission.status is '状态 0无效，1有效'
;

create table t_role
(
	id serial not null constraint t_role_pkey primary key,
	name varchar(20) default NULL::character varying,
	description varchar(255),
	create_time timestamp with time zone default now() not null,
	update_time timestamp with time zone default now() not null,
	ext_infos varchar(255),
--     ext_infos jsonb,
	status smallint default 1 not null
)
;

comment on table t_role is '后台角色表'
;

comment on column t_role.name is '角色名'
;

comment on column t_role.description is '描述'
;

comment on column t_role.status is '状态，1为有效，0为无效'
;

create table t_role_permission
(
	id serial not null constraint t_role_permission_pkey primary key,
	role_id integer
		constraint fk_t_role_permission_2_role_id
			references t_role
				on delete cascade,
	permission_id integer
		constraint fk_t_role_permission_2_permission_id
			references t_permission
				on delete cascade
)
;

comment on table t_role_permission is '角色-权限关联表'
;

comment on column t_role_permission.role_id is '角色id'
;

comment on column t_role_permission.permission_id is '权限id'
;

create table t_user
(
	id serial not null constraint t_user_pkey primary key,
	user_name varchar(32) default NULL::character varying,
	password varchar(64) default NULL::character varying,
	nick_name varchar(64) default NULL::character varying,
	create_time timestamp with time zone default now() not null,
	update_time timestamp with time zone default now() not null,
	status smallint default 1 not null,
	ext_infos varchar(255),
	email varchar(255),
	mobile_phone varchar(16),
	login_ip_limit varchar(255) default '*'::character varying,
	salt int8 default '0',
	organization varchar(64) default ''::character varying
)
;

comment on table t_user is '运营后台用户表'
;

comment on column t_user.user_name is '用户名'
;

comment on column t_user.password is '密码'
;

comment on column t_user.nick_name is '昵称'
;

comment on column t_user.create_time is '创建时间'
;

comment on column t_user.update_time is '修改时间'
;

comment on column t_user.status is '是否有效  1有效  0无效'
;

comment on column t_user.email is 'EMail'
;

comment on column t_user.mobile_phone is '手机号码'
;

comment on column t_user.login_ip_limit is '登录IP限制'
;

comment on column t_user.salt is '用于m5加密的盐'
;

comment on column t_user.organization is '组织机构'
;

create unique index t_user_name__index
	on t_user (user_name)
;

create table t_user_role
(
	id serial not null
		constraint t_user_role_pkey
			primary key,
	role_id integer
		constraint t_user_role_role_id_fkey
			references t_role,
	user_id integer
		constraint t_user_role_user_id_fkey
			references t_user
)
;

--- set seq start to avoid initial value conflict
-- ALTER SEQUENCE auth2.t_permission_id_seq START WITH 1000;
-- ALTER SEQUENCE auth2.t_role_id_seq START WITH 1000;
-- ALTER SEQUENCE auth2.t_user_id_seq START WITH 1000;
-- ALTER SEQUENCE auth2.t_role_permission_id_seq START WITH 5000;
-- ALTER SEQUENCE auth2.t_role_user_id_seq START WITH 5000;
