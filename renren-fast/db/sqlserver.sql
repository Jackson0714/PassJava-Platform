-- 菜单
CREATE TABLE sys_menu (
  menu_id bigint NOT NULL IDENTITY(1,1),
  parent_id bigint,
  name varchar(50),
  url varchar(200),
  perms varchar(500),
  type int,
  icon varchar(50),
  order_num int,
  PRIMARY KEY (menu_id)
);

-- 系统用户
CREATE TABLE sys_user (
  user_id bigint NOT NULL IDENTITY(1,1),
  username varchar(50) NOT NULL,
  password varchar(100),
  salt varchar(20),
  email varchar(100),
  mobile varchar(100),
  status tinyint,
  create_user_id bigint,
  create_time datetime,
  PRIMARY KEY (user_id),
  UNIQUE (username)
);

-- 系统用户Token
CREATE TABLE sys_user_token (
  user_id bigint NOT NULL,
  token varchar(100) NOT NULL,
  expire_time datetime,
  update_time datetime,
  PRIMARY KEY (user_id),
  UNIQUE (token)
);

-- 系统验证码
CREATE TABLE sys_captcha (
  uuid varchar(36) NOT NULL,
  code varchar(6) NOT NULL,
  expire_time datetime,
  PRIMARY KEY (uuid)
);

-- 角色
CREATE TABLE sys_role (
  role_id bigint NOT NULL IDENTITY(1,1),
  role_name varchar(100),
  remark varchar(100),
  create_user_id bigint,
  create_time datetime,
PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id bigint NOT NULL IDENTITY(1,1),
  user_id bigint,
  role_id bigint,
PRIMARY KEY (id)
);

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id bigint NOT NULL IDENTITY(1,1),
  role_id bigint,
  menu_id bigint,
PRIMARY KEY (id)
);

-- 系统配置信息
CREATE TABLE sys_config (
  id bigint NOT NULL IDENTITY(1,1),
  param_key varchar(50),
  param_value varchar(2000),
  status tinyint DEFAULT 1,
  remark varchar(500),
  PRIMARY KEY (id),
  UNIQUE (param_key)
);

-- 系统日志
CREATE TABLE sys_log (
  id bigint NOT NULL IDENTITY(1,1),
  username varchar(50),
  operation varchar(50),
  method varchar(200),
  params varchar(5000),
  time bigint NOT NULL,
  ip varchar(64),
  create_date datetime,
PRIMARY KEY (id)
);

-- 文件上传
CREATE TABLE sys_oss (
  id bigint NOT NULL IDENTITY(1,1),
  url varchar(200),
  create_date datetime,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE schedule_job (
  job_id bigint NOT NULL IDENTITY(1,1),
  bean_name varchar(200),
  params varchar(2000),
  cron_expression varchar(100),
  status tinyint,
  remark varchar(255),
  create_time datetime,
  PRIMARY KEY (job_id)
);

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id bigint NOT NULL IDENTITY(1,1),
  job_id bigint NOT NULL,
  bean_name varchar(200),
  params varchar(2000),
  status tinyint NOT NULL,
  error varchar(2000),
  times int NOT NULL,
  create_time datetime,
  PRIMARY KEY (log_id),
  INDEX job_id (job_id)
);

-- 用户表
CREATE TABLE tb_user (
  user_id bigint NOT NULL IDENTITY(1,1),
  username varchar(50) NOT NULL,
  mobile varchar(20) NOT NULL,
  password varchar(64),
  create_time datetime,
  PRIMARY KEY (user_id),
  UNIQUE (username)
);

SET IDENTITY_INSERT sys_user ON;
INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, create_user_id, create_time) VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
SET IDENTITY_INSERT sys_user OFF;

SET IDENTITY_INSERT sys_menu ON;
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

SET IDENTITY_INSERT sys_menu OFF;


INSERT INTO sys_config (param_key, param_value, status, remark) VALUES ('CLOUD_STORAGE_CONFIG_KEY',  '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"ios-app","qiniuDomain":"http://7xlij2.com1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}', '0', '云存储配置信息');

INSERT INTO schedule_job (bean_name, params, cron_expression, status, remark, create_time) VALUES ('testTask', 'renren', '0 0/30 * * * ?', '0', '参数测试', '2016-12-01 23:16:46');


-- 账号：13612345678  密码：admin
INSERT INTO tb_user (username, mobile, password, create_time) VALUES ('mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');





--  quartz自带表结构
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_TRIGGERS] DROP CONSTRAINT FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] DROP CONSTRAINT FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] DROP CONSTRAINT FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISFOREIGNKEY') = 1)
  ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] DROP CONSTRAINT FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_CALENDARS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_CALENDARS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_CRON_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_CRON_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_BLOB_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_BLOB_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_FIRED_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_FIRED_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_PAUSED_TRIGGER_GRPS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SCHEDULER_STATE]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SCHEDULER_STATE]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_LOCKS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_LOCKS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_JOB_DETAILS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_JOB_DETAILS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SIMPLE_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_SIMPROP_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS]
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[QRTZ_TRIGGERS]') AND OBJECTPROPERTY(id, N'ISUSERTABLE') = 1)
  DROP TABLE [dbo].[QRTZ_TRIGGERS]
GO

CREATE TABLE [dbo].[QRTZ_CALENDARS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [CALENDAR_NAME] [VARCHAR] (200)  NOT NULL ,
  [CALENDAR] [IMAGE] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_CRON_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [CRON_EXPRESSION] [VARCHAR] (120)  NOT NULL ,
  [TIME_ZONE_ID] [VARCHAR] (80)
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_FIRED_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [ENTRY_ID] [VARCHAR] (95)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [FIRED_TIME] [BIGINT] NOT NULL ,
  [SCHED_TIME] [BIGINT] NOT NULL ,
  [PRIORITY] [INTEGER] NOT NULL ,
  [STATE] [VARCHAR] (16)  NOT NULL,
  [JOB_NAME] [VARCHAR] (200)  NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NULL ,
  [IS_NONCONCURRENT] [VARCHAR] (1)  NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SCHEDULER_STATE] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [LAST_CHECKIN_TIME] [BIGINT] NOT NULL ,
  [CHECKIN_INTERVAL] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_LOCKS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [LOCK_NAME] [VARCHAR] (40)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_JOB_DETAILS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [JOB_CLASS_NAME] [VARCHAR] (250)  NOT NULL ,
  [IS_DURABLE] [VARCHAR] (1)  NOT NULL ,
  [IS_NONCONCURRENT] [VARCHAR] (1)  NOT NULL ,
  [IS_UPDATE_DATA] [VARCHAR] (1)  NOT NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NOT NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [REPEAT_COUNT] [BIGINT] NOT NULL ,
  [REPEAT_INTERVAL] [BIGINT] NOT NULL ,
  [TIMES_TRIGGERED] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [STR_PROP_1] [VARCHAR] (512) NULL,
  [STR_PROP_2] [VARCHAR] (512) NULL,
  [STR_PROP_3] [VARCHAR] (512) NULL,
  [INT_PROP_1] [INT] NULL,
  [INT_PROP_2] [INT] NULL,
  [LONG_PROP_1] [BIGINT] NULL,
  [LONG_PROP_2] [BIGINT] NULL,
  [DEC_PROP_1] [NUMERIC] (13,4) NULL,
  [DEC_PROP_2] [NUMERIC] (13,4) NULL,
  [BOOL_PROP_1] [VARCHAR] (1) NULL,
  [BOOL_PROP_2] [VARCHAR] (1) NULL,
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_BLOB_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [BLOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_TRIGGERS] (
  [SCHED_NAME] [VARCHAR] (120)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [NEXT_FIRE_TIME] [BIGINT] NULL ,
  [PREV_FIRE_TIME] [BIGINT] NULL ,
  [PRIORITY] [INTEGER] NULL ,
  [TRIGGER_STATE] [VARCHAR] (16)  NOT NULL ,
  [TRIGGER_TYPE] [VARCHAR] (8)  NOT NULL ,
  [START_TIME] [BIGINT] NOT NULL ,
  [END_TIME] [BIGINT] NULL ,
  [CALENDAR_NAME] [VARCHAR] (200)  NULL ,
  [MISFIRE_INSTR] [SMALLINT] NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CALENDARS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CALENDARS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [CALENDAR_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CRON_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_FIRED_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_FIRED_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [ENTRY_ID]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_PAUSED_TRIGGER_GRPS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SCHEDULER_STATE] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SCHEDULER_STATE] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [INSTANCE_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_LOCKS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_LOCKS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [LOCK_NAME]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_JOB_DETAILS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_JOB_DETAILS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [JOB_NAME],
      [JOB_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SIMPLE_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SIMPROP_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_TRIGGERS] PRIMARY KEY  CLUSTERED
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_SIMPROP_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
    (
      [SCHED_NAME],
      [TRIGGER_NAME],
      [TRIGGER_GROUP]
    ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [SCHED_NAME],
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS] FOREIGN KEY
    (
      [SCHED_NAME],
      [JOB_NAME],
      [JOB_GROUP]
    ) REFERENCES [dbo].[QRTZ_JOB_DETAILS] (
    [SCHED_NAME],
    [JOB_NAME],
    [JOB_GROUP]
  )
GO

