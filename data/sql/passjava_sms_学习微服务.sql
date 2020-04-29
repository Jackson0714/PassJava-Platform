drop table if exists sms_study_time;

/*==============================================================*/
/* Table: sms_study_time                                        */
/*==============================================================*/
create table sms_study_time
(
   id                   bigint not null auto_increment,
   ques_type            bigint comment '题目类型id',
   member_id            bigint comment '用户id',
   total_time           int comment '学习时常（分）',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime default CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table sms_study_time comment '学习-用户学习时常表';

drop table if exists sms_view_log;

/*==============================================================*/
/* Table: sms_view_log                                          */
/*==============================================================*/
create table sms_view_log
(
   id                   bigint not null auto_increment comment 'id',
   ques_id              bigint comment '题目id',
   ques_type            bigint comment '题目类型id',
   member_id            bigint comment '用户id',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime default CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table sms_view_log comment '学习-用户学习浏览记录表';
