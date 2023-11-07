use `sa-backend`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
		 `user_id` bigint NOT NULL COMMENT '用户ID',
		 # mybatis-plus使用雪花算法生成id，需要使用bigint
	     `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
	     `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
	     `password` varchar(255) DEFAULT NULL COMMENT '密码',
	     `sex` tinyint DEFAULT 0 COMMENT '0:未知 1:男 2:女',
	     `person_description` varchar(255) DEFAULT NULL COMMENT '个人描述',
	     `join_time` datetime DEFAULT NULL COMMENT '注册时间',
	     PRIMARY KEY (`user_id`),
	     UNIQUE KEY `key_email` (`email`),
	     UNIQUE KEY `key_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

# DROP TABLE IF EXISTS `paper`;
# CREATE TABLE `paper` (
# 	    `paper_id` bigint  not null COMMENT '论文ID',
# 	    `title` VARCHAR(255) DEFAULT null comment '标题',
# 	    `author_id` bigint DEFAULT null comment '作者id',
# 	    `publication_date` datetime DEFAULT null comment '发布日期',
# 	    `keywords` VARCHAR(255) DEFAULT null comment '关键词',
# 	    `citations` varchar(255) DEFAULT null comment '引用信息',
# 	    `is_deleted` int default 0 comment '逻辑删除',
# 	    primary key (`paper_id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='论文';
#
# DROP TABLE IF EXISTS `scholar`;
# CREATE TABLE `scholar` (
# 	    `scholar_id` bigint not null comment '学者id',
# 	    `name` varchar(255) DEFAULT NULL comment '学者姓名',
# 	    `email` varchar(255) DEFAULT NULL comment '邮箱',
# 	    `degree` varchar(255) DEFAULT null comment '学位',
# 	    `description` varchar(255) DEFAULT null comment '描述',
# 		primary key(`scholar_id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学者信息';


truncate table `user` ;
# truncate table `scholar`;
# truncate table `paper`;

-- 插入语句
INSERT INTO `user` (`user_id`, `nick_name`, `email`, `password`, `sex`, `person_description`, `join_time`) VALUES
(1, 'John Doe', 'john@example.com', 'password123', 1, 'Hello, I am John Doe', '2023-09-27 10:00:00'),
(2, 'Jane Smith', 'jane@example.com', 'pass456', 0, 'Nice to meet you!', '2023-09-27 11:30:00'),
(3, 'Alice Johnson', 'alice@example.com', 'pass789', 0, 'Welcome to my profile!', '2023-09-27 09:15:00'),
(4, 'Bob Williams', 'bob@example.com', 'pass321', 1, 'I love coding!', '2023-09-27 13:45:00'),
(5, 'Emily Davis', 'emily@example.com', 'pass654', 0, 'Passionate about technology', '2023-09-27 16:20:00');

# INSERT INTO `paper` (`paper_id`, `title`, `author_id`, `publication_date`, `keywords`, `citations`) VALUES
# (1, 'Introduction to Machine Learning', 1, '2023-09-25 14:00:00', 'machine learning, algorithms', 'Cited by 10'),
# (2, 'Data Analysis Techniques', 2, '2023-09-26 09:30:00', 'data analysis, statistics', 'Cited by 5'),
# (3, 'Artificial Intelligence Applications', 1, '2023-09-27 11:00:00', 'artificial intelligence, applications', 'Cited by 8'),
# (4, 'Database Management Systems', 3, '2023-09-27 16:30:00', 'database management, systems', 'Cited by 2'),
# (5, 'Web Development Fundamentals', 4, '2023-09-27 13:00:00', 'web development, fundamentals', 'Cited by 3');
#
# INSERT INTO `scholar` (`scholar_id`, `name`, `email`, `degree`, `description`) VALUES
# (1, 'Dr. Michael Johnson', 'michael@example.com', 'Ph.D.', 'Expert in artificial intelligence'),
# (2, 'Prof. Emily Davis', 'emily@example.com', 'Ph.D.', 'Specializes in data mining'),
# (3, 'Dr. Robert Thompson', 'robert@example.com', 'Ph.D.', 'Researcher in computer science'),
# (4, 'Prof. Sarah Parker', 'sarah@example.com', 'Ph.D.', 'Focuses on software engineering'),
# (5, 'Dr. Lisa Wilson', 'lisa@example.com', 'Ph.D.', 'Expertise in data analysis');