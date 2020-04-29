drop table if exists cms_banner;

/*==============================================================*/
/* Table: cms_banner                                            */
/*==============================================================*/
create table cms_banner
(
   id                   bigint not null auto_increment comment 'id',
   img_url              varchar(500) comment '图片路径',
   title                varchar(500) comment '标题',
   display_order        int comment '排序',
   enable               tinyint comment '是否显示',
   render_type          tinyint comment '跳转类型',
   render_url           varchar(500) comment '跳转路径',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime DEFAULT CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table cms_banner comment '内容-横幅广告表';

drop table if exists cms_news;

/*==============================================================*/
/* Table: cms_news                                              */
/*==============================================================*/
create table cms_news
(
   id                   bigint not null auto_increment comment 'id',
   image_url            varchar(500) comment '图片路径',
   title                varchar(500) comment '标题',
   display_order        int comment '排序',
   render_url           varchar(500) comment '跳转路径',
   enable               tinyint comment '是否显示',
   del_flag             tinyint(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   create_time          datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间',
   update_time          datetime DEFAULT CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table cms_news comment '内容-资讯表';
