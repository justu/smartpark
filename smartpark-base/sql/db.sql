CREATE TABLE `base_park` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '园区ID',
  `name` varchar(64) NOT NULL COMMENT '园区名称',
  `province` varchar(10) COMMENT '园区所在省',
  `city` varchar(10) COMMENT '园区所在城市',
  `area` varchar(10) COMMENT '园区所在区县',
  `detail_address` varchar(128) COMMENT '园区详细地址',
  `longitude` varchar(32) COMMENT '经度',
  `latitude` varchar(32) COMMENT '纬度',
  `remark` varchar(500) COMMENT '园区描述',
  `building_area` varchar(32) COMMENT '建筑面积',
  `status` char(1) DEFAULT 1 COMMENT '状态，1：有效 0：无效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='园区信息表';