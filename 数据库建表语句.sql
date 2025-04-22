-- 用户表
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `role` varchar(20) DEFAULT NULL COMMENT '角色',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `account` decimal(10,2) DEFAULT '0.00' COMMENT '账户余额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商家表
CREATE TABLE `business` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '商家名称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `role` varchar(20) DEFAULT NULL COMMENT '角色',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `info` text DEFAULT NULL COMMENT '商家信息',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `license` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `type` varchar(50) DEFAULT NULL COMMENT '商家类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- 订单表
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` text DEFAULT NULL COMMENT '订单内容',
  `total` decimal(10,2) NOT NULL COMMENT '订单总额',
  `user_id` int NOT NULL COMMENT '用户ID',
  `time` varchar(50) DEFAULT NULL COMMENT '下单时间',
  `status` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 餐桌表
CREATE TABLE `tables` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(50) NOT NULL COMMENT '桌号',
  `unit` varchar(50) DEFAULT NULL COMMENT '单位',
  `free` varchar(10) DEFAULT NULL COMMENT '是否空闲',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_no` (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='餐桌表';

-- 食品表
CREATE TABLE `foods` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '食品名称',
  `descr` text DEFAULT NULL COMMENT '描述',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `img` varchar(255) DEFAULT NULL COMMENT '图片',
  `businessname` varchar(100) DEFAULT NULL COMMENT '商家名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食品表';
-- 管理员表
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `role` varchar(20) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';