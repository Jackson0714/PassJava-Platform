drop table if exists chms_channel;

/*==============================================================*/
/* Table: chms_channel                                          */
/*==============================================================*/
create table chms_channel
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(100) comment '渠道名称',
   appid                varchar(100) comment '渠道appid',
   appsecret            varchar(500) comment '渠道appsecret',
   create_time          datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime DEFAULT CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table chms_channel comment '渠道-渠道表';


drop table if exists chms_access_token;

/*==============================================================*/
/* Table: chms_access_token                                     */
/*==============================================================*/
create table chms_access_token
(
   id                   bigint not null auto_increment comment 'id',
   access_token         varchar(500) comment 'access_token',
   expire_time          datetime comment '到期时间',
   channel_id           bigint comment '渠道id',
   create_time          datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime DEFAULT CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table chms_access_token comment '渠道-认证表';

