
use `sa-backend`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
		 `user_id` bigint NOT NULL COMMENT '用户ID',# mybatis-plus使用雪花算法生成id，需要使用bigint
	     `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
	     `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
	     `password` varchar(255) DEFAULT NULL COMMENT '密码',
	     `sex` tinyint DEFAULT 0 COMMENT '0:未知 1:男 2:女',
	     `person_description` varchar(255) DEFAULT NULL COMMENT '个人描述',
	     `join_time` datetime DEFAULT NULL COMMENT '注册时间',
	     PRIMARY KEY (`user_id`),
	     UNIQUE KEY `key_email` (`email`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';
