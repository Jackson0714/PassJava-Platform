use passjava_ums;

BEGIN;
INSERT INTO `ums_member` (`id`, `mini_openid`, `mp_openid`, `unionid`, `level_id`, `user_name`, `PASSWORD`, `nickname`, `phone`, `email`, `avatar`, `gender`, `birth`, `city`, `source_type`, `integration`, `register_time`, `del_flag`, `create_time`, `update_time`, `user_id`) VALUES (123, NULL, NULL, NULL, NULL, '悟空', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-08-12 16:22:53', '2022-08-12 16:22:53', 'wukong');
COMMIT;


use passjava_auth;

BEGIN;
INSERT INTO `sys_user` (`username`, `password`, `org_id`, `enabled`, `phone`, `email`, `create_time`, `update_time`, `id`, `user_id`) VALUES ('wukong', '$2a$10$IAHUqoKYSgu/P0l9CiraTOQ/SJottVw9uS.QX9AS.NpZsLmtP5QgW', 1, b'1', '15912345678', 'abc@qq.com', '2022-08-10 07:20:30', '2022-08-10 07:24:33', 1, 'wukong');
COMMIT;


USE passjava_qms;
BEGIN;
INSERT INTO `qms_type` (`id`, `TYPE`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES (1, 'javaBasic', 'Java基础', 'https://passjava.oss-cn-beijing.aliyuncs.com/2022-08-16/b150bdb3-9e57-4354-85e0-21c0d48d3581_logo.png', 0, '2022-08-16 22:28:16', '2022-08-16 22:28:16');
INSERT INTO `qms_type` (`id`, `TYPE`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES (2, 'jvm', 'Java虚拟机', 'https://passjava.oss-cn-beijing.aliyuncs.com/2022-08-16/b150bdb3-9e57-4354-85e0-21c0d48d3581_logo.png', 0, '2022-08-16 22:28:16', '2022-08-16 22:28:16');
INSERT INTO `qms_type` (`id`, `TYPE`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES (3, 'spring', 'Spring核心原理', 'https://passjava.oss-cn-beijing.aliyuncs.com/2022-08-16/b150bdb3-9e57-4354-85e0-21c0d48d3581_logo.png', 0, '2022-08-16 22:28:16', '2022-08-16 22:28:16');
INSERT INTO `qms_type` (`id`, `TYPE`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES (4, 'bigData', '大数据', 'https://passjava.oss-cn-beijing.aliyuncs.com/2022-08-16/b150bdb3-9e57-4354-85e0-21c0d48d3581_logo.png', 0, '2022-08-16 22:28:16', '2022-08-16 22:28:16');
INSERT INTO `qms_type` (`id`, `TYPE`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES (5, 'thread', '多线程', 'https://passjava.oss-cn-beijing.aliyuncs.com/2022-08-16/b150bdb3-9e57-4354-85e0-21c0d48d3581_logo.png', 0, '2022-08-16 22:28:16', '2022-08-16 22:28:16');

INSERT INTO `passjava_qms`.`qms_question` (`id`, `title`, `answer`, `LEVEL`, `display_order`, `sub_title`, `TYPE`, `ENABLE`, `del_flag`, `create_time`, `update_time`) VALUES (1, 'JVM垃圾回收机制', '垃圾自动回收', 1, 1, 'GC', 1, 1, 0, '2022-08-16 22:40:53', '2022-08-16 22:40:53');
COMMIT;