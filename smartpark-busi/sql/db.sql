/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/11/4 16:14:34                           */
/*==============================================================*/

drop table if exists sp_car_info;

drop table if exists sp_companions;

drop table if exists sp_visitor_info;

drop table if exists sp_visitor_info_his;

drop table if exists sp_visitor_reservation;

/*==============================================================*/
/* Table: sp_car_info                                           */
/*==============================================================*/
create table sp_car_info
(
   id                   bigint not null auto_increment comment 'id',
   car_no               varchar(10) comment '车牌号',
   name                 varchar(20) comment '驾驶员姓名',
   phone                varchar(20) comment '手机号',
   reservation_id       bigint comment '预约单id',
   ext1                 varchar(50) comment '扩展字段1',
   ext2                 varchar(50) comment '扩展字段2',
   ext3                 varchar(50) comment '扩展字段3',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id',
   remark               varchar(200) comment '备注',
   primary key (id)
);

alter table sp_car_info comment '车辆信息表';

/*==============================================================*/
/* Table: sp_companions                                         */
/*==============================================================*/
create table sp_companions
(
   id                   bigint not null auto_increment,
   reservation_id       int(10) comment '预约单id',
   name                 varchar(20) comment '姓名',
   physical_card_id     varchar(32),
   idcard_no            varchar(20) comment '身份证号',
   phone                varchar(20) comment '手机号',
   photo_url            varchar(128) comment '访客照片',
   ext1                 varchar(50) comment '扩展字段1',
   ext2                 varchar(50) comment '扩展字段2',
   ext3                 varchar(50) comment '扩展字段3',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id',
   primary key (id)
);

alter table sp_companions comment '同行人员信息表';

/*==============================================================*/
/* Table: sp_visitor_info                                       */
/*==============================================================*/
create table sp_visitor_info
(
   id                   bigint not null auto_increment comment '访客id',
   name                 varchar(20) comment '姓名',
   physical_card_id     varchar(32) comment '物理卡id',
   idcard_no            varchar(20) comment '身份证号',
   phone                varchar(20) comment '手机号',
   type                 tinyint(4) comment '访客类型',
   photo_url            varchar(128) comment '访客照片',
   company              varchar(200) comment '单位名称',
   remark               varchar(200) comment '备注',
   ext1                 varchar(50) comment '扩展字段1',
   ext2                 varchar(50) comment '扩展字段2',
   ext3                 varchar(50) comment '扩展字段3',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id',
   primary key (id)
);

alter table sp_visitor_info comment '访客信息表';

/*==============================================================*/
/* Table: sp_visitor_info_his                                   */
/*==============================================================*/
create table sp_visitor_info_his
(
   id                   bigint not null auto_increment,
   visitor_id           bigint comment '访客id',
   name                 varchar(20) comment '姓名',
   physical_card_id     varchar(32),
   idcard_no            varchar(20) comment '身份证号',
   phone                varchar(20) comment '手机号',
   type                 tinyint(4) comment '访客类型',
   photo_url            varchar(128) comment '访客照片',
   company              varchar(200) comment '单位名称',
   ext1                 varchar(50) comment '扩展字段1',
   ext2                 varchar(50) comment '扩展字段2',
   ext3                 varchar(50) comment '扩展字段3',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id',
   remark               varchar(200) comment '备注',
   primary key (id)
);

alter table sp_visitor_info_his comment '访客信息历史表';

/*==============================================================*/
/* Table: sp_visitor_reservation                                */
/*==============================================================*/
create table sp_visitor_reservation
(
   id                   bigint not null auto_increment comment '订单id',
   phone                varchar(20) comment '访客手机号',
   photo_url            varchar(128) comment '访客照片',
   visitor_id           bigint comment '访客id',
   staff_id             bigint comment '受访者id',
   staff_mobile         varchar(20) DEFAULT NULL COMMENT '被访人（员工）手机号',
   status               varchar(12) comment '订单状态',
   appoint_start_time   datetime comment '预约开始时间',
   appoint_end_time     datetime comment '预约结束时间',
   act_start_time       datetime comment '实际开始时间',
   act_end_time         datetime comment '实际结束时间',
   type                 varchar(12) comment '预约单类型',
   remark               varchar(200) comment '备注(来访目的)',
   companions           int(10) comment '同行人数',
   ext1                 varchar(50) comment '扩展字段1',
   ext2                 varchar(50) comment '扩展字段2',
   ext3                 varchar(50) comment '扩展字段3',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id',
   primary key (id)
);

alter table sp_visitor_reservation comment '访客预约登记单';

drop table if exists sp_group;
CREATE TABLE `sp_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '群组ID',
  `name` varchar(50) NOT NULL COMMENT '群组名称',
  `group_no` varchar(20) DEFAULT NULL COMMENT '群组编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='群组表';

drop table if exists sp_group_door;
CREATE TABLE `sp_group_door` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群组id',
  `door_id` bigint(20) DEFAULT NULL COMMENT '门id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='群组门表';

drop table if exists sp_group_station;
CREATE TABLE `sp_group_station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群组id',
  `station_id` bigint(20) DEFAULT NULL COMMENT '工位id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='群组工位表';
