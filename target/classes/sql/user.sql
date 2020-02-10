create table user(
  id bigint(20) not null comment '用户id',
  nickname varchar(64) not null comment '用户昵称',
  password varchar(32) default null comment '用户密码',
  salt varchar(10) default null comment '无法分离的盐',
  head  varchar(128) default null comment '用户头像',
  register_date datetime default null comment '注册时间',
  last_login_date datetime default null comment '上次登录时间',
  login_count int(11) default 0 comment '登陆次数',
  primary key(id)
)engine=innoDB default charset=utf8mb4