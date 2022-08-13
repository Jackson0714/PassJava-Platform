use passjava_ums;

BEGIN;
INSERT INTO `ums_member` (`id`, `mini_openid`, `mp_openid`, `unionid`, `level_id`, `user_name`, `PASSWORD`, `nickname`, `phone`, `email`, `avatar`, `gender`, `birth`, `city`, `source_type`, `integration`, `register_time`, `del_flag`, `create_time`, `update_time`, `user_id`) VALUES (123, NULL, NULL, NULL, NULL, '悟空', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-08-12 16:22:53', '2022-08-12 16:22:53', 'wukong');
COMMIT;


use passjava_auth;

BEGIN;
INSERT INTO `sys_user` (`username`, `password`, `org_id`, `enabled`, `phone`, `email`, `create_time`, `update_time`, `id`, `user_id`) VALUES ('wukong', '$2a$10$IAHUqoKYSgu/P0l9CiraTOQ/SJottVw9uS.QX9AS.NpZsLmtP5QgW', 1, b'1', '15912345678', 'abc@qq.com', '2022-08-10 07:20:30', '2022-08-10 07:24:33', 1, 'wukong');
COMMIT;
