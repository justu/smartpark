CREATE TABLE `sp_visitor_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '访客姓名',
  `gender` char(1) COMMENT '访客性别',
  `nation` varchar(10) COMMENT '访客民族',
  `birthday` datetime COMMENT '访客生日',
  `idcard_no` varchar(20) NOT NULL COMMENT '访客身份证号码',
  `photo_url` varchar(128) COMMENT '访客照片',
  `remark` varchar(200) COMMENT '备注信息',
  `ext1` varchar(32) COMMENT '扩展字段1',
  `ext2` varchar(32) COMMENT '扩展字段2',
  `ext3` varchar(32) COMMENT '扩展字段3',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客表';