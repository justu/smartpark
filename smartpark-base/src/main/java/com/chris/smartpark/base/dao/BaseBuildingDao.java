package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseBuildingEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 园区楼宇信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@Mapper
public interface BaseBuildingDao extends BaseDao<BaseBuildingEntity> {
	
}
