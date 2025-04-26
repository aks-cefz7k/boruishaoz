-- insert permissions
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('*', '所有权限', 1, '{}', '2018-07-26 18:59:46.641823', '2018-07-26 18:59:46.641823');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:*', '系统管理权限', 1, '{}', '2018-07-26 19:03:46.840366', '2018-07-26 19:03:46.840366');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:*', '授权管理权限', 1, '{}', '2018-07-26 19:02:09.169094', '2018-07-26 19:02:09.169094');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:user:*', '用户管理', 1, '{}', '2018-07-26 19:02:48.349343', '2018-07-26 19:02:48.349343');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:role:*', '角色管理', 1, '{}', '2018-07-26 19:03:16.775325', '2018-07-26 19:03:16.775325');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:permission:*', 'Permission管理', 1, '{}', '2018-07-26 19:05:28.138890', '2018-07-26 19:05:28.138890');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:user:view', '查看用户权限', 1, '{}', '2018-07-26 19:06:56.635383', '2018-07-26 19:06:56.635383');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:user:modify', '修改用户权限', 1, '{}', '2018-07-26 19:09:00.884745', '2018-07-26 19:09:00.884745');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:role:view', '查看角色权限', 1, '{}', '2018-07-26 19:09:33.800890', '2018-07-26 19:09:33.800890');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:role:modify', '修改角色权限', 1, '{}', '2018-07-26 19:10:04.689981', '2018-07-26 19:10:04.689981');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:permission:view', '查看Permission权限', 1, '{}', '2018-07-26 19:14:25.246233', '2018-07-26 19:14:25.246233');
INSERT INTO public.t_permission (permission_code, description, status, ext_infos, create_time, update_time) VALUES ('sys:auth:permission:modify', '修改Permission权限', 1, '{}', '2018-07-26 19:14:48.542837', '2018-07-26 19:14:48.542837');

-- insert users
INSERT INTO public.t_user (user_name, password, nick_name, create_time, update_time, status, ext_infos, email, mobile_phone, login_ip_limit) VALUES ('admin', 'pmq7VoTEWWLYh1ZPCDRujQ==', 'Admin', '2018-07-26 19:27:19.761857', '2018-08-08 10:29:03.668191', 1, '{}', null, null, '*');
INSERT INTO public.t_user (user_name, password, nick_name, create_time, update_time, status, ext_infos, email, mobile_phone, login_ip_limit) VALUES ('demo_user', '123456', 'Demo User', '2018-07-26 19:27:53.978316', '2018-08-10 04:50:32.687712', 1, '{}', null, null, '*');

-- insert roles
INSERT INTO public.t_role (name, description, create_time, update_time, ext_infos, status) VALUES ('超级管理员', '超级管理员', '2018-07-26 19:28:26.496294', '2018-07-26 19:28:26.496294', '{}', 1);
INSERT INTO public.t_role (name, description, create_time, update_time, ext_infos, status) VALUES ('测试用户', '测试用户', '2018-08-03 01:15:00.672822', '2018-08-03 01:15:00.672822', '{}', 1);
INSERT INTO public.t_role (name, description, create_time, update_time, ext_infos, status) VALUES ('管理员', '管理员', '2018-08-03 01:15:00.672822', '2018-08-03 01:15:00.672822', '{}', 1);
INSERT INTO public.t_role (name, description, create_time, update_time, ext_infos, status) VALUES ('普通用户', '普通用户', '2018-08-03 01:15:00.672822', '2018-08-03 01:15:00.672822', '{}', 1);

-- insert role user
INSERT INTO public.t_user_role (role_id, user_id) VALUES (1, 1);
INSERT INTO public.t_user_role (role_id, user_id) VALUES (2, 2);

-- insert role permission
INSERT INTO public.t_role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO public.t_role_permission (role_id, permission_id) VALUES (2, 7);
INSERT INTO public.t_role_permission (role_id, permission_id) VALUES (3, 1);