  
insert  into `sys_menu`(`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) 
values 
#(31,0,'题目中心','','',0,'editor',0),
# (32,31,'题目配置','question/question','',1,'config', 0),
# (33,31,'类型配置','question/type','',1,'config', 0);
(34,0,'内容中心','','',0,'editor',0),
 (35,34,'横幅配置','content/banner','',1,'config', 0),
 (36,34,'资讯配置','content/news','',1,'config', 0);