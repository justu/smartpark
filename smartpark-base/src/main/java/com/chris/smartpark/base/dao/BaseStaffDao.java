package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 园区员工表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 11.18
 */
@Mapper
public interface BaseStaffDao extends BaseDao<BaseStaffEntity> {
	
}
