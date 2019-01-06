package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.OpenDoorLogEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 开门日志表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@Mapper
public interface OpenDoorLogDao extends BaseDao<OpenDoorLogEntity> {
	
}
