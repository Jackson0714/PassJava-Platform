DROP DATABASE IF EXISTS `passjava_ums`;
DROP DATABASE IF EXISTS `passjava_qms`;
DROP DATABASE IF EXISTS `passjava_sms`;
DROP DATABASE IF EXISTS `passjava_cms`;
DROP DATABASE IF EXISTS `passjava_chms`;

CREATE DATABASE passjava_ums CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE passjava_qms CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE passjava_sms CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE passjava_cms CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE passjava_chms CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;


USE passjava_ums;

DROP TABLE IF EXISTS ums_member;

/*==============================================================*/
/* Table: ums_member                                            */
/*==============================================================*/

CREATE TABLE ums_member
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   mini_openid          INT COMMENT '小程序openid',
   mp_openid            VARCHAR(64) COMMENT '服务号openid',
   unionid              VARCHAR(64) COMMENT '微信unionid',
   level_id             BIGINT COMMENT '会员等级id',
   user_name            CHAR(64) COMMENT '用户名',
   PASSWORD             VARCHAR(64) COMMENT '密码',
   nickname             VARCHAR(64) COMMENT '昵称',
   phone                VARCHAR(20) COMMENT '手机号码',
   email                VARCHAR(64) COMMENT '邮箱',
   avatar               VARCHAR(500) COMMENT '头像',
   gender               TINYINT COMMENT '性别',
   birth                DATE COMMENT '生日',
   city                 VARCHAR(500) COMMENT '所在城市',
   source_type          TINYINT COMMENT '用户来源',
   integration          INT COMMENT '积分',
   register_time        DATETIME COMMENT '注册时间',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE ums_member COMMENT '会员-会员表';

DROP TABLE IF EXISTS ums_growth_change_history;

/*==============================================================*/
/* Table: ums_growth_change_history                             */
/*==============================================================*/
CREATE TABLE ums_growth_change_history
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   member_id            BIGINT COMMENT '会员id',
   change_count         INT COMMENT '改变的值（正负计数）',
   note                 VARCHAR(500) COMMENT '备注',
   source_type          TINYINT COMMENT '0->扫码；1->搜索;2->分享',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE ums_growth_change_history COMMENT '会员-积分值变化历史记录表';




USE passjava_cms;

DROP TABLE IF EXISTS cms_banner;

/*==============================================================*/
/* Table: cms_banner                                            */
/*==============================================================*/
CREATE TABLE cms_banner
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   img_url              VARCHAR(500) COMMENT '图片路径',
   title                VARCHAR(500) COMMENT '标题',
   display_order        INT COMMENT '排序',
   ENABLE               TINYINT COMMENT '是否显示',
   render_type          TINYINT COMMENT '跳转类型',
   render_url           VARCHAR(500) COMMENT '跳转路径',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE cms_banner COMMENT '内容-横幅广告表';

DROP TABLE IF EXISTS cms_news;

/*==============================================================*/
/* Table: cms_news                                              */
/*==============================================================*/
CREATE TABLE cms_news
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   image_url            VARCHAR(500) COMMENT '图片路径',
   title                VARCHAR(500) COMMENT '标题',
   display_order        INT COMMENT '排序',
   render_url           VARCHAR(500) COMMENT '跳转路径',
   ENABLE               TINYINT COMMENT '是否显示',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE cms_news COMMENT '内容-资讯表';



USE passjava_qms;

DROP TABLE IF EXISTS qms_question;

/*==============================================================*/
/* Table: qms_question                                          */
/*==============================================================*/
CREATE TABLE qms_question
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   title                VARCHAR(500) COMMENT '题目标题',
   answer               VARCHAR(15000) COMMENT '题目解答',
   LEVEL                TINYINT COMMENT '题目难度等级',
   display_order        INT COMMENT '排序',
   sub_title            VARCHAR(500) COMMENT '副标题',
   TYPE                 BIGINT COMMENT '题目类型',
   ENABLE               TINYINT COMMENT '是否显示',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

DROP TABLE IF EXISTS qms_type;

DROP TABLE IF EXISTS qms_type;

/*==============================================================*/
/* Table: qms_type                                              */
/*==============================================================*/
CREATE TABLE qms_type
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   TYPE                 CHAR(64) COMMENT '类型名称',
   comments             CHAR(64) COMMENT '备注',
   logo_url             VARCHAR(500) COMMENT '类型logo路径',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE qms_type COMMENT '题目-题目类型表';





USE passjava_sms;

DROP TABLE IF EXISTS sms_study_time;

/*==============================================================*/
/* Table: sms_study_time                                        */
/*==============================================================*/
CREATE TABLE sms_study_time
(
   id                   BIGINT NOT NULL AUTO_INCREMENT,
   ques_type            BIGINT COMMENT '题目类型id',
   member_id            BIGINT COMMENT '用户id',
   total_time           INT COMMENT '学习时常（分）',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE sms_study_time COMMENT '学习-用户学习时常表';

DROP TABLE IF EXISTS sms_view_log;

/*==============================================================*/
/* Table: sms_view_log                                          */
/*==============================================================*/
CREATE TABLE sms_view_log
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   ques_id              BIGINT COMMENT '题目id',
   ques_type            BIGINT COMMENT '题目类型id',
   member_id            BIGINT COMMENT '用户id',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE sms_view_log COMMENT '学习-用户学习浏览记录表';





USE passjava_chms;

DROP TABLE IF EXISTS chms_channel;

/*==============================================================*/
/* Table: chms_channel                                          */
/*==============================================================*/
CREATE TABLE chms_channel
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   NAME                 VARCHAR(100) COMMENT '渠道名称',
   appid                VARCHAR(100) COMMENT '渠道appid',
   appsecret            VARCHAR(500) COMMENT '渠道appsecret',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE chms_channel COMMENT '渠道-渠道表';


DROP TABLE IF EXISTS chms_access_token;

/*==============================================================*/
/* Table: chms_access_token                                     */
/*==============================================================*/
CREATE TABLE chms_access_token
(
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   access_token         VARCHAR(500) COMMENT 'access_token',
   expire_time          DATETIME COMMENT '到期时间',
   channel_id           BIGINT COMMENT '渠道id',
   del_flag             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (id)
);

ALTER TABLE chms_access_token COMMENT '渠道-认证表';


