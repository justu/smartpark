package com.chris.smartpark.ibms.dao;

import com.chris.smartpark.ibms.entity.IBMSSubsystemEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * ibms子系统表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
@Mapper
public interface IBMSSubsystemDao extends BaseDao<IBMSSubsystemEntity> {
	
}
