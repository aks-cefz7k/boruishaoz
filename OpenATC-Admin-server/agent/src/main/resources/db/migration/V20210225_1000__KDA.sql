ALTER TABlE "t_user" ADD COLUMN expiration_time timestamp with time zone default now() not null;
UPDATE "t_user" SET expiration_time = '2099-07-27 13:52:59' where user_name = 'admin';
