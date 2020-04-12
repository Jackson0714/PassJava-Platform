-- 菜单
CREATE TABLE sys_menu (
  menu_id NUMBER(20, 0) NOT NULL,
  parent_id NUMBER(20, 0) NOT NULL,
  name varchar2(50),
  url varchar2(200),
  perms varchar2(500),
  type NUMBER(2, 0),
  icon varchar2(50),
  order_num NUMBER(8, 0),
  PRIMARY KEY (menu_id)
);

-- 系统用户
CREATE TABLE sys_user (
  user_id NUMBER(20, 0) NOT NULL,
  username varchar2(50) NOT NULL,
  password varchar2(100),
  salt varchar2(20),
  email varchar2(100),
  mobile varchar2(100),
  status NUMBER(2, 0) NOT NULL,
  create_user_id NUMBER(20, 0) NOT NULL,
  create_time timestamp,
  PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEX index_sys_user_username on sys_user(username);

-- 系统用户Token
CREATE TABLE sys_user_token (
  user_id NUMBER(20, 0) NOT NULL,
  token varchar2(100) NOT NULL,
  expire_time timestamp,
  update_time timestamp,
  PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEX index_token on sys_user_token(token);

-- 系统验证码
CREATE TABLE sys_captcha (
  uuid varchar2(36) NOT NULL,
  code varchar2(6) NOT NULL,
  expire_time timestamp,
  PRIMARY KEY (uuid)
);

-- 角色
CREATE TABLE sys_role (
  role_id NUMBER(20, 0) NOT NULL,
  role_name varchar2(100),
  remark varchar2(100),
  create_user_id NUMBER(20, 0) NOT NULL,
  create_time timestamp,
  PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id NUMBER(20, 0) NOT NULL,
  user_id NUMBER(20, 0) NOT NULL,
  role_id NUMBER(20, 0) NOT NULL,
  PRIMARY KEY (id)
);

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id NUMBER(20, 0) NOT NULL,
  role_id NUMBER(20, 0) NOT NULL,
  menu_id NUMBER(20, 0) NOT NULL,
  PRIMARY KEY (id)
);

-- 系统配置信息
CREATE TABLE sys_config (
  id NUMBER(20, 0) NOT NULL,
  param_key varchar2(50),
  param_value varchar2(4000),
  status NUMBER(2, 0) DEFAULT 1 NOT NULL,
  remark varchar2(500),
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX index_param_key on sys_config(param_key);


-- 系统日志
CREATE TABLE sys_log (
  id NUMBER(20, 0) NOT NULL,
  username varchar2(50),
  operation varchar2(50),
  method varchar2(200),
  params clob,
  time NUMBER(20, 0) NOT NULL,
  ip varchar2(64),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 文件上传
CREATE TABLE sys_oss (
  id NUMBER(20, 0) NOT NULL,
  url varchar2(200),
  create_date timestamp,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE schedule_job (
  job_id NUMBER(20, 0) NOT NULL,
  bean_name varchar2(200),
  params varchar2(2000),
  cron_expression varchar2(100),
  status NUMBER(2, 0) NOT NULL,
  remark varchar2(255),
  create_time timestamp,
  PRIMARY KEY (job_id)
);

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id NUMBER(20, 0) NOT NULL,
  job_id NUMBER(20, 0) NOT NULL,
  bean_name varchar2(200),
  params varchar2(2000),
  status NUMBER(2, 0) NOT NULL,
  error varchar2(2000),
  times NUMBER(10, 0) NOT NULL,
  create_time timestamp,
  PRIMARY KEY (log_id)
);
CREATE INDEX index_job_id on schedule_job_log(job_id);

-- 用户表
CREATE TABLE tb_user (
  user_id NUMBER(20, 0) NOT NULL,
  username varchar2(50) NOT NULL,
  mobile varchar2(20) NOT NULL,
  password varchar2(64),
  create_time timestamp,
  PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEX index_tb_user_username on tb_user(username);

INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, create_user_id, create_time) VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', CURRENT_DATE);

INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);



INSERT INTO sys_config (id, param_key, param_value, status, remark) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY',  '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"ios-app","qiniuDomain":"http://7xlij2.com1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', '0', '云存储配置信息');

INSERT INTO schedule_job (job_id, bean_name, params, cron_expression, status, remark, create_time) VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', '0', '参数测试', CURRENT_DATE);


-- 账号：13612345678  密码：admin
INSERT INTO tb_user (user_id, username, mobile, password, create_time) VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', CURRENT_DATE);




--  quartz自带表结构
CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,
  IS_DURABLE VARCHAR2(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  JOB_NAME  VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  NEXT_FIRE_TIME NUMBER(13) NULL,
  PREV_FIRE_TIME NUMBER(13) NULL,
  PRIORITY NUMBER(13) NULL,
  TRIGGER_STATE VARCHAR2(16) NOT NULL,
  TRIGGER_TYPE VARCHAR2(8) NOT NULL,
  START_TIME NUMBER(13) NOT NULL,
  END_TIME NUMBER(13) NULL,
  CALENDAR_NAME VARCHAR2(200) NULL,
  MISFIRE_INSTR NUMBER(2) NULL,
  JOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  REPEAT_COUNT NUMBER(7) NOT NULL,
  REPEAT_INTERVAL NUMBER(12) NOT NULL,
  TIMES_TRIGGERED NUMBER(10) NOT NULL,
  CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  CRON_EXPRESSION VARCHAR2(120) NOT NULL,
  TIME_ZONE_ID VARCHAR2(80),
  CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  STR_PROP_1 VARCHAR2(512) NULL,
  STR_PROP_2 VARCHAR2(512) NULL,
  STR_PROP_3 VARCHAR2(512) NULL,
  INT_PROP_1 NUMBER(10) NULL,
  INT_PROP_2 NUMBER(10) NULL,
  LONG_PROP_1 NUMBER(13) NULL,
  LONG_PROP_2 NUMBER(13) NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR2(1) NULL,
  BOOL_PROP_2 VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME  VARCHAR2(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR2(200) NOT NULL,
  CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  ENTRY_ID VARCHAR2(95) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  FIRED_TIME NUMBER(13) NOT NULL,
  SCHED_TIME NUMBER(13) NOT NULL,
  PRIORITY NUMBER(13) NOT NULL,
  STATE VARCHAR2(16) NOT NULL,
  JOB_NAME VARCHAR2(200) NULL,
  JOB_GROUP VARCHAR2(200) NULL,
  IS_NONCONCURRENT VARCHAR2(1) NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NULL,
  CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
  CHECKIN_INTERVAL NUMBER(13) NOT NULL,
  CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR2(120) NOT NULL,
  LOCK_NAME  VARCHAR2(40) NOT NULL,
  CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);


