CREATE TABLE `ibms_subsystem` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '子系统ID',
  `name` varchar(64) NOT NULL COMMENT '子系统名称',
  `remark` varchar(500) COMMENT '子系统描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(10) COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(10) COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ibms子系统表';