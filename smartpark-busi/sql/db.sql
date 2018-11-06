/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/11/4 16:14:34                           */
/*==============================================================*/


drop table if exists base_staff;

drop table if exists sp_car_info;

drop table if exists sp_companions;

drop table if exists sp_visitor_info;

drop table if exists sp_visitor_info_his;

drop table if exists sp_visitor_reservation;

/*==============================================================*/
/* Table: base_staff                                            */
/*==============================================================*/
create table base_staff
(
   id                   bigint comment '员工ID',
   name                 varchar(10) comment '员工姓名',
   work_no              varchar(10) comment '员工工号',
   dept_id              bigint comment '部门ID',
   company_id           bigint comment '公司IDv',
   park_id              bigint comment '园区ID',
   id_card              varchar(20) comment '身份证',
   position             varchar(32) comment '职务',
   gender               char(1) comment '性别',
   born_date            datetime comment '出生日期',
   status               char(1) comment '状态',
   create_time          datetime comment '创建时间',
   create_user_id       bigint comment '创建人id',
   update_time          datetime comment '更新时间',
   update_user_id       bigint comment '更新人id'
);

alter table base_staff comment '园区员工表';

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
   staff_id       bigint comment '受访者id',
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

