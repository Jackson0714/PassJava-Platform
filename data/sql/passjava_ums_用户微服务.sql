drop table if exists ums_member;

/*==============================================================*/
/* Table: ums_member                                            */
/*==============================================================*/
create table ums_member
(
   id                   bigint not null auto_increment comment 'id',
   mini_openid          int comment '小程序openid',
   mp_openid            varchar(64) comment '服务号openid',
   unionid              varchar(64) comment '微信unionid',
   level_id             bigint comment '会员等级id',
   user_name            char(64) comment '用户名',
   password             varchar(64) comment '密码',
   nickname             varchar(64) comment '昵称',
   phone                varchar(20) comment '手机号码',
   email                varchar(64) comment '邮箱',
   avatar               varchar(500) comment '头像',
   gender               tinyint comment '性别',
   birth                date comment '生日',
   city                 varchar(500) comment '所在城市',
   source_type          tinyint comment '用户来源',
   integration          int comment '积分',
   register_time        datetime comment '注册时间',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）'
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime default CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table ums_member comment '会员-会员表';

drop table if exists ums_growth_change_history;

/*==============================================================*/
/* Table: ums_growth_change_history                             */
/*==============================================================*/
create table ums_growth_change_history
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment '会员id',
   change_count         int comment '改变的值（正负计数）',
   note                 varchar(500) comment '备注',
   source_type          tinyint comment '0->扫码；1->搜索;2->分享',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）'
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime default CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table ums_growth_change_history comment '会员-积分值变化历史记录表';
