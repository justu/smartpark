DROP TABLE IF EXISTS `base_park`;
CREATE TABLE IF NOT EXISTS `base_park` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '园区ID',
  `name` varchar(64) DEFAULT NULL COMMENT '园区名称',
  `province` varchar(10) DEFAULT NULL COMMENT '园区所在省',
  `city` varchar(10) DEFAULT NULL COMMENT '园区所在城市',
  `area` varchar(10) DEFAULT NULL COMMENT '园区所在区县',
  `detail_address` varchar(128) DEFAULT NULL COMMENT '园区详细地址',
  `longitude` varchar(32) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) DEFAULT NULL COMMENT '纬度',
  `building_time` datetime DEFAULT NULL COMMENT '始建时间',
  `building_area` varchar(32) DEFAULT NULL COMMENT '建筑面积',
  `remark` varchar(500) DEFAULT NULL COMMENT '园区简介',
  `park_pic_url` varchar(255) DEFAULT NULL COMMENT '园区图片',
  `status` char(1) DEFAULT '1' COMMENT '状态，1：有效 0：无效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区信息表';


DROP TABLE IF EXISTS `base_park_area`;
CREATE TABLE IF NOT EXISTS `base_park_area` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `area_name` varchar(64) DEFAULT NULL COMMENT '区域名称',
  `area_desc` varchar(500) DEFAULT NULL COMMENT '区域描述',
  `park_id` int(10) DEFAULT NULL COMMENT '园区ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区区域信息表';


DROP TABLE IF EXISTS `base_building`;
CREATE TABLE IF NOT EXISTS `base_building` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '楼宇ID',
  `building_name` varchar(64) DEFAULT NULL COMMENT '楼宇名称',
  `address` varchar(128) DEFAULT NULL COMMENT '楼宇地址',
  `floors` int(2) DEFAULT NULL COMMENT '楼宇层数',
  `park_id` int(10) DEFAULT NULL COMMENT '园区ID',
  `area_id` int(10) DEFAULT NULL COMMENT '区域ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建人',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区楼宇信息表';


DROP TABLE IF EXISTS `base_company`;
CREATE TABLE IF NOT EXISTS `base_company` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '公司ID',
  `company_name` varchar(64) DEFAULT NULL COMMENT '公司名称',
  `park_id` int(10) DEFAULT NULL COMMENT '园区ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区公司信息表';


DROP TABLE IF EXISTS `base_floor`;
CREATE TABLE IF NOT EXISTS `base_floor` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '楼层ID',
  `floor_name` varchar(64) DEFAULT NULL COMMENT '楼层名称',
  `rooms` int(2) DEFAULT NULL COMMENT '楼层房间数',
  `company_id` int(10) DEFAULT NULL COMMENT '公司ID',
  `building_id` int(10) DEFAULT NULL COMMENT '楼宇ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区楼层信息表';


DROP TABLE IF EXISTS `base_room`;
CREATE TABLE IF NOT EXISTS `base_room` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `room_no` varchar(16) DEFAULT NULL COMMENT '房间号',
  `room_name` varchar(32) DEFAULT NULL COMMENT '房间名称',
  `room_type` varchar(32) DEFAULT NULL COMMENT '房间类型',
  `floor_id` int(10) DEFAULT NULL COMMENT '楼层ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区房间信息表';